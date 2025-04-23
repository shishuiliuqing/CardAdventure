package com.hjc.CardAdventure.effect.basic;

import com.hjc.CardAdventure.Utils.AttributeUtils;
import com.hjc.CardAdventure.Utils.CardsUtils;
import com.hjc.CardAdventure.effect.effectType.Negative;
import com.hjc.CardAdventure.effect.target.TargetedEffect;
import com.hjc.CardAdventure.pojo.BattleInformation;
import com.hjc.CardAdventure.pojo.Role;
import com.hjc.CardAdventure.pojo.enemy.Enemy;

import java.util.ArrayList;

public class PhyDamageValueAdd extends TargetedEffect implements Negative {
    public PhyDamageValueAdd(Role from, String effect, Role to) {
        super(from, effect, to);
    }

    @Override
    public void action() {
        if (getFrom() == null || getTo() == null) return;
        //获取效果序列
        ArrayList<String> effect = cutEffect(getEffect());
        //获取发动者还是目标
        String operation = getFirst(effect);
        //获取加减
        String operator = getFirst(effect);
        //获取数值
        int value = changeToInt(getFirst(effect));

        if (operation.equals("FROM")) {
            int origin = AttributeUtils.FROM_ADD_DAMAGE.get(getTo());
            if (operator.equals("V_A")) {
                AttributeUtils.FROM_ADD_DAMAGE.replace(getTo(), origin + value);
            } else {
                AttributeUtils.FROM_ADD_DAMAGE.replace(getTo(), origin - value);
            }
        } else {
            int origin = AttributeUtils.TO_ADD_DAMAGE.get(getTo());
            if (operator.equals("V_A")) {
                AttributeUtils.TO_ADD_DAMAGE.replace(getTo(), origin + value);
            } else {
                AttributeUtils.TO_ADD_DAMAGE.replace(getTo(), origin - value);
            }
        }

        //更新手牌
        CardsUtils.updateHands();
        //更新敌人
        for (Enemy enemy : BattleInformation.ENEMIES) {
            enemy.update();
        }
        continueAction(getFrom(), montage(effect), getTo());
    }

    @Override
    public String toString() {
        return "";
    }

    @Override
    public boolean isNegative() {
        //获取效果序列
        ArrayList<String> effect = cutEffect(getEffect());
        //获得操做对象
        String operation = getFirst(effect);
        //获取加减
        String operator = getFirst(effect);
        //获取数值
        int value = changeToInt(getFirst(effect));
        return (operation.equals("FROM") && operator.equals("V_D")) || (operation.equals("TO") && operator.equals("V_D"));
    }
}
