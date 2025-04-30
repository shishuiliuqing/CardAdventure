package com.hjc.CardAdventure.pojo.enemy;

import com.hjc.CardAdventure.Utils.OtherUtils;
import com.hjc.CardAdventure.effect.Effect;
import com.hjc.CardAdventure.effect.opportunity.Opportunity;
import com.hjc.CardAdventure.pojo.BattleInformation;
import com.hjc.CardAdventure.pojo.Role;

import java.util.ArrayList;
import java.util.Random;

import static com.hjc.CardAdventure.Global.PLAYER.cards;
import static com.hjc.CardAdventure.Global.PLAYER.player;
import static com.hjc.CardAdventure.pojo.BattleInformation.nowAction;

public enum IntentionGenerateType {
    //正常随机触发
    NORMAL,
    //效果触发
    EFFECT,
    //条件触发
    CONDITION;

    public static void generateIntention(Enemy enemy) {
        //可用意图
        ArrayList<Intention> usableIntention = new ArrayList<>();

        //判断有无条件触发意图
        for (Intention intention : enemy.getIntentions()) {
            //条件触发意图
            if (intention.getIntentionGenerateType() == CONDITION) {
                //取意图效果第一个
                String condition = intention.getEffects().get(0);
                //已连续使用2次
                if (intention.getConsecutiveStarts() == 2) {
                    //打断
                    Intention.interrupt(intention);
                    //返回
                    continue;
                }
                //进行条件解析
                if (parseCondition(enemy, condition)) {
                    //若条件成立，生成此意图
                    enemy.setNowIntention(intention);
                    //获取此意图连续次数
                    int consecutiveStarts = intention.getConsecutiveStarts();
                    //打断所有意图
                    interrupt(enemy.getIntentions());
                    //此意图连续次数+1
                    intention.setConsecutiveStarts(consecutiveStarts + 1);
                    return;
                }
                //条件不成立，不进行判断
                continue;
            }
            //已有意图，无需生成
            if (enemy.getNowIntention() != null) {
                //打断所有意图
                interrupt(enemy.getIntentions());
                return;
            }
            //效果触发意图，无法主动生成
            if (intention.getIntentionGenerateType() == EFFECT) continue;
            //已连续执行2次的意图
            if (intention.getConsecutiveStarts() == 2) {
                Intention.interrupt(intention);
                continue;
            }
            //正常随机生成的意图,添加到可随机生成序列
            usableIntention.add(intention);
        }

        //从可随机生成序列中随机生成一个意图
        Intention nowIntention = usableIntention.get(new Random().nextInt(usableIntention.size()));
        //获取该意图连续次数
        int consecutiveStarts = nowIntention.getConsecutiveStarts();
        //打断所有意图
        interrupt(enemy.getIntentions());
        //该意图连续生成次数+1
        nowIntention.setConsecutiveStarts(consecutiveStarts + 1);
//        //可用意图移除该意图
//        usableIntention.remove(nowIntention);
//        //打断其他意图的连续执行次数
//        interrupt(usableIntention);
        //将随机生成的意图设置为当前意图
        enemy.setNowIntention(nowIntention);
    }

    //条件触发效果解析
    private static boolean parseCondition(Enemy enemy, String condition) {
        //获得效果序列
        ArrayList<String> effect = Effect.cutEffect(condition);
        //取第一个操作码
        String first = Effect.getFirst(effect);
        switch (first) {
            case "GENERATE_CR": {
                //取概率
                int p = Effect.changeToInt(Effect.getFirst(effect));
                //判断概率
                boolean result = OtherUtils.isSuccess(p);
                //若失败直接返回
                if (!result) return false;
            }
        }
        //取操作码
        String operation = Effect.getFirst(effect);
        switch (operation) {
            //某回合必定触发
            case "ROUND" -> {
                //取回合数
                int round = Effect.changeToInt(Effect.getFirst(effect));
                if (BattleInformation.rounds == round) return true;
                else {
                    String next = Effect.getFirst(effect);
                    if (next.equals("NULL")) return false;
                }
            }
            //无某种效果
            case "NO_HAVE_EFFECT" -> {
                //取效果名
                String name = Effect.getFirst(effect);
                //判断有无效果
                return !Opportunity.exist(enemy, name);
            }
            //某对象纯洁超过某值
            case "PURITY_OVER" -> {
                //取对象
                Role role = Effect.getFirst(effect).equals("PLAYER") ? player : enemy;
                //role为null，直接返回false
                if (role == null) return false;
                //获取数值
                int value = Effect.changeToInt(Effect.getFirst(effect));
                return role.getRoleAttribute().getPurity() > value;
            }
            //某对象速度超过某值
            case "SPEED_OVER" -> {
                //取对象
                Role role = Effect.getFirst(effect).equals("PLAYER") ? player : enemy;
                //role为null，直接返回false
                if (role == null) return false;
                //获取数值
                int value = Effect.changeToInt(Effect.getFirst(effect));
                return role.getRoleAttribute().getSpeed() > value;
            }
        }
        return false;
    }

    //打断意图序列的所有意图
    private static void interrupt(ArrayList<Intention> intentions) {
        for (Intention intention : intentions) {
            Intention.interrupt(intention);
        }
    }
}
