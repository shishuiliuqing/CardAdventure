package com.hjc.CardAdventure.pojo.enemy;

import java.util.ArrayList;
import java.util.Random;

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
                //进行条件解析
                if (parseCondition(condition)) {
                    //若条件成立，生成此意图
                    enemy.setNowIntention(intention);
                    //打断所有意图
                    interrupt(enemy.getIntentions());
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
        //该意图随机连续生成次数+1
        nowIntention.setConsecutiveStarts(nowIntention.getConsecutiveStarts() + 1);
        //可用意图移除该意图
        usableIntention.remove(nowIntention);
        //打断其他意图的连续执行次数
        interrupt(usableIntention);
        //将随机生成的意图设置为当前意图
        enemy.setNowIntention(nowIntention);
    }

    //条件触发效果解析
    private static boolean parseCondition(String condition) {
        return false;
    }

    //打断意图序列的所有意图
    private static void interrupt(ArrayList<Intention> intentions) {
        for (Intention intention : intentions) {
            Intention.interrupt(intention);
        }
    }
}
