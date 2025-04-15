package com.hjc.CardAdventure.pojo;

import com.hjc.CardAdventure.Global;
import com.hjc.CardAdventure.Utils.AttributeUtils;
import com.hjc.CardAdventure.Utils.CardsUtils;
import com.hjc.CardAdventure.component.battle.DrawCardsComponent;
import com.hjc.CardAdventure.component.card.CardComponent;
import com.hjc.CardAdventure.configuration.MonsterPool;
import com.hjc.CardAdventure.effect.Effect;
import com.hjc.CardAdventure.effect.basic.ActionOver;
import com.hjc.CardAdventure.effect.basic.PauseEffect;
import com.hjc.CardAdventure.effect.basic.RoleAction;
import com.hjc.CardAdventure.effect.opportunity.Opportunity;
import com.hjc.CardAdventure.effect.target.TargetedEffect;
import com.hjc.CardAdventure.entity.BattleEntity;
import com.hjc.CardAdventure.pojo.attribute.Attribute;
import com.hjc.CardAdventure.pojo.card.Card;
import com.hjc.CardAdventure.pojo.enemy.Enemy;
import com.hjc.CardAdventure.pojo.enemy.EnemyType;
import com.hjc.CardAdventure.pojo.enemy.IntentionGenerateType;
import com.hjc.CardAdventure.pojo.environment.InsideInformation;
import com.hjc.CardAdventure.pojo.environment.TimeStatus;

import static com.hjc.CardAdventure.Global.CONFIGURATION.*;
import static com.hjc.CardAdventure.Global.PLAYER.*;
import static com.hjc.CardAdventure.Global.CARD_USE.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

//战斗信息类
public class BattleInformation {
    //敌人序列
    public static final ArrayList<Enemy> ENEMIES = new ArrayList<>();
    //当前行动序列
    public static final ArrayList<Role> THIS_ACTION = new ArrayList<>();
    //下一回合行动序列
    public static final ArrayList<Role> NEXT_ACTION = new ArrayList<>();
    //抽牌堆
    public static final ArrayList<Card> DRAW_CARDS = new ArrayList<>();
    //弃牌堆
    public static final ArrayList<Card> ABANDON_CARDS = new ArrayList<>();
    //消耗牌堆
    public static final ArrayList<Card> CONSUME_CARDS = new ArrayList<>();
    //效果序列器
    public static final ArrayList<Effect> EFFECTS = new ArrayList<>();
    //当前回合数
    public static int rounds;
    //人物属性拷贝
    public static Attribute attribute;
    //判断是否在战斗
    public static boolean isBattle = false;
    //当前回合执行人
    public static Role nowAction;

    private BattleInformation() {
    }

    public static void initBattle() {
        //初始化回合数
        rounds = 1;

        //初始化敌人序列
        initEnemies();

        //初始化牌堆
        initCards();

        //初始化人物属性
        initAttribute();

        //初始化行动序列
        initActions();

        //初始化工具类
        AttributeUtils.initAttributeUtils();

        //正在战斗
        isBattle = true;

    }


    //初始化敌人序列
    private static void initEnemies() {
        //初始化敌人序列
        ENEMIES.clear();
        //添加怪物
        ENEMIES.addAll(MonsterPool.getEnemies(InsideInformation.day));
        //为怪物分配位置
        for (int i = 0; i < ENEMIES.size(); i++) {
            ENEMIES.get(i).setLocation(BattleEntity.enemyGenerateOrder[i]);
        }

        //怪物进场效果解析
//        for (Enemy enemy : ENEMIES) {
//            insetEffect(IntentionType.intentionEffects(enemy, enemy.getEntryEffects()));
//            effectExecution();
//        }

        //为每位敌人初始化意图
        for (Enemy enemy : ENEMIES) {
            IntentionGenerateType.generateIntention(enemy);
        }
    }

    //初始化牌堆
    private static void initCards() {
        //初始化抽牌堆
        DRAW_CARDS.clear();
        DRAW_CARDS.addAll(Global.PLAYER.cards);
        CardsUtils.disruptCards(DRAW_CARDS);
        //初始化弃牌堆
        ABANDON_CARDS.clear();
        //初始化消耗牌堆
        CONSUME_CARDS.clear();
        //初始化手牌区
        CardComponent.HAND_CARDS.clear();
        //清除手牌区状态
        Arrays.fill(DrawCardsComponent.CARD_BOX_STATUS, 0);
    }

    //保存角色原有属性,并初始化玩家属性
    private static void initAttribute() {
        attribute = new Attribute();
        //保留玩家初始属性
        Attribute.cloneAttribute(player.getAttribute(), attribute);
        //清除人物护盾
        armor = 0;
        //初始化各组件布尔值
        initCardUse();
        //玩家时机效果序列刷新
        opportunities.clear();
    }

    //初始化行动序列
    private static void initActions() {
        //初始化行动序列
        THIS_ACTION.clear();
        NEXT_ACTION.clear();
        //添加所有角色，根据速度进行排序
        THIS_ACTION.add(player);
        THIS_ACTION.addAll(ENEMIES);
        sort(THIS_ACTION);
        NEXT_ACTION.addAll(THIS_ACTION);
    }

    //根据速度排序行动序列
    public static void sort(ArrayList<Role> roles) {
        //排序结果
        ArrayList<Role> result = new ArrayList<>();
        //添加玩家
        result.add(player);
        //记录玩家索引
        int index = 0;

        //进行分类
        for (Role role : roles) {
            //若为玩家，不进行操作
            if (role == player) continue;
            //如果速度大于玩家
            if (role.getRoleAttribute().getSpeed() > player.getAttribute().getSpeed()) {
                //将其添加至玩家前面
                result.add(0, role);
                index++;
            } else {
                //否则添加至玩家后面
                result.add(role);
            }
        }

        //对玩家之前的序列排序
        sort(result, 0, index - 1);
        //对玩家之后的序列排序
        sort(result, index + 1, result.size() - 1);

        //清空原来的序列
        roles.clear();
        roles.addAll(result);
    }

    //对指定范围行动序列排序
    private static void sort(ArrayList<Role> roles, int begin, int end) {
        if (end <= 0 || begin >= roles.size()) return;
        for (int i = begin; i <= end; i++) {
            Enemy enemy1 = (Enemy) roles.get(i);
            for (int j = i + 1; j <= end; j++) {
                Enemy enemy2 = (Enemy) roles.get(j);
                //如果速度大者优先向前
                if (enemy2.getAttribute().getSpeed() > enemy1.getAttribute().getSpeed()) {
                    roles.set(i, enemy2);
                    roles.set(j, enemy1);
                    enemy1 = enemy2;
                }
                //速度相同，位置前者优先
                else if (enemy2.getAttribute().getSpeed() == enemy1.getAttribute().getSpeed() && enemy2.getLocation() < enemy1.getLocation()) {
                    roles.set(i, enemy2);
                    roles.set(j, enemy1);
                    enemy1 = enemy2;
                }
            }
        }
    }

    //战斗开始
    public static void battle() {
        if (THIS_ACTION.isEmpty()) {
            THIS_ACTION.addAll(NEXT_ACTION);
            //回合添加
            rounds++;
            //触发所有角色的回合开始效果
            for (int i = ENEMIES.size() - 1; i >= 0; i--) {
                if (ENEMIES.get(i) == null) continue;
                Opportunity.roundLaunch(ENEMIES.get(i));
            }
            Opportunity.roundLaunch(player);
        }
//        //获取当前行动对象
        Role role = THIS_ACTION.get(0);
        //生成行动执行效果
        RoleAction roleAction = new RoleAction(role, "", role);
        EFFECTS.add(roleAction);
        //效果执行
        //effectExecution();
    }

    //效果执行器
    public static void effectExecution() {
        while (!EFFECTS.isEmpty()) {
            System.out.println(EFFECTS);
            if (ENEMIES.isEmpty() && isBattle) {
                EFFECTS.clear();
                Attribute.cloneAttribute(attribute, player.getAttribute());
                //FXGL.getSceneService().pushSubScene(new BattleRewardSubScene());
                //reward();
                Reward reward = Reward.getReward(getBattleReward());
                isBattle = false;
                break;
            }

            Effect effect = EFFECTS.get(0);
            EFFECTS.remove(0);
            if (effect != null) effect.action();
            if (effect instanceof PauseEffect) break;
        }
    }

    //战斗奖励获取
    private static ArrayList<String> getBattleReward() {
//        if (enemyType == EnemyType.LITTLE_MONSTER) {
//            BattleRewardSubScene.REWARD.clear();
//            BattleRewardSubScene.REWARD.add(1);
//            //BattleRewardSubScene.REWARD.add(1);
//            BattleRewardSubScene.REWARD.add(4);
//            FXGL.getSceneService().pushSubScene(new BattleRewardSubScene());
//        }
        Random r = new Random();
        ArrayList<String> rewards = new ArrayList<>();
        if (MonsterPool.enemyType == EnemyType.LITTLE_MONSTER) {
            //卡牌奖励
            rewards.add("CARDS#3");
            //金币奖励
            int gold = r.nextInt(19) + 30;
            rewards.add("GOLD#" + gold);
            //经验奖励
            int experience = InsideInformation.day;
            rewards.add("EXPERIENCE#" + experience);
        }

        return rewards;
    }

    //删除某个角色
    public static void clearRole(Role role) {
        //行动序列移除该角色
        THIS_ACTION.remove(role);
        NEXT_ACTION.remove(role);

        int n = 0;
        //移除所有有关该角色的效果（除回合结束）
        while (n < EFFECTS.size()) {
            Effect effect = EFFECTS.get(n);

            if (effect instanceof ActionOver) {
                n++;
                continue;
            }

            if (effect.getFrom() == role) {
                EFFECTS.remove(n);
                continue;
            }

            if (effect instanceof TargetedEffect targetedEffect) {
                if (targetedEffect.getTo() == role) {
                    EFFECTS.remove(n);
                    continue;
                }
            }

            n++;
        }
    }

    //效果序列插入一种效果
    public static void insetEffect(Effect effect) {
        EFFECTS.add(0, effect);
    }

    //效果序列插入多种效果
    public static void insetEffect(ArrayList<Effect> effects) {
        for (int i = effects.size() - 1; i >= 0; i--) {
            EFFECTS.add(0, effects.get(i));
        }
    }
}
