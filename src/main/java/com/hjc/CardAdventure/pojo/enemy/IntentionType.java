package com.hjc.CardAdventure.pojo.enemy;

import com.hjc.CardAdventure.Global;
import com.hjc.CardAdventure.Utils.AttributeUtils;
import com.hjc.CardAdventure.effect.Effect;

import java.util.ArrayList;

public enum IntentionType {
    //0.攻击类意图
    ATTACK,
    //1.防御类意图
    DEFENSE,
    //2.强化类意图
    STRENGTHEN,
    //3.削弱类意图
    WEAKEN,
    //4.准备类意图
    PREPARE,
    //5.攻击防御类
    ATTACK_DEFENSE,
    //6.攻击弱化类
    ATTACK_WEAKEN,
    //7.攻击强化类
    ATTACK_STRENGTHEN,
    //8.防御强化类
    DEFENSE_STRENGTHEN,
    //9.防御削弱类
    DEFENSE_WEAK,
    //10.其他类意图
    OTHER;

    //计算攻击类伤害数值
    public static int[] getAttackValue(Enemy enemy, ArrayList<String> effects) {
        int[] value = new int[2];
        for (String effect : effects) {
            //解析效果
            ArrayList<String> result = Effect.cutEffect(effect);
            //非2位长效果
            if (result.size() < 2) continue;
            //第2位为攻击效果
            if (result.get(1).equals("DAMAGE")) {
                //初始数值为第三位
                int damageValue = Effect.changeToInt(result.get(2));
                //倍率为第四位
                int magnification = Effect.changeToInt(result.get(3));
                value[1] = AttributeUtils.mathPhyDamage(enemy, Global.PLAYER.player, damageValue, magnification);
            }
            //第2位为多段效果，且第4位为攻击效果
            else if (result.get(1).equals("MANY") && result.get(3).equals("DAMAGE")) {
                //初始化多段第三位
                int manyNum = Effect.changeToInt(result.get(2));
                //初始数值为第五位
                int damageValue = Effect.changeToInt(result.get(4));
                //倍率为第六位
                int magnification = Effect.changeToInt(result.get(5));
                value[0] = manyNum;
                value[1] = AttributeUtils.mathPhyDamage(enemy, Global.PLAYER.player, damageValue, magnification);
            }
        }
        return value;
    }
}
