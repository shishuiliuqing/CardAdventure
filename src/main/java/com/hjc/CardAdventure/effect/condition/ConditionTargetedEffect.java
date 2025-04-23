package com.hjc.CardAdventure.effect.condition;

import com.hjc.CardAdventure.effect.Effect;
import com.hjc.CardAdventure.effect.effectType.Negative;
import com.hjc.CardAdventure.effect.opportunity.Opportunity;
import com.hjc.CardAdventure.effect.target.TargetedEffect;
import com.hjc.CardAdventure.pojo.Role;

import java.util.ArrayList;

//带条件效果类型（有目标类型）
public class ConditionTargetedEffect extends TargetedEffect implements Negative {
    public ConditionTargetedEffect(Role from, String effect, Role to) {
        super(from, effect, to);
    }

    @Override
    public void action() {
        if (getFrom() == null || getTo() == null) return;
        //获取效果序列
        ArrayList<String> effect = cutEffect(getEffect());
        //获取条件码
        String operation = getFirst(effect);
        switch (operation) {
            //条件1，目标无护盾
            case "ONE" -> {
                if (getTo().getRoleArmor() == 0) continueAction(getFrom(), montage(effect), getTo());
            }
            //条件2，目标无某时机效果
            case "TWO" -> {
                String name = getFirst(effect);
                if (Opportunity.exist(getTo(), name)) return;
                continueAction(getFrom(), montage(effect), getTo());
            }
            //条件3，目标力量低于发动者
            case "THREE" -> {
                if (getTo().getRoleAttribute().getPower() < getFrom().getRoleAttribute().getPower()) {
                    continueAction(getFrom(), montage(effect), getTo());
                }
            }
        }
    }

    @Override
    public String toString() {
        String s;
        //获取效果序列
        ArrayList<String> effect = cutEffect(getEffect());
        //获取条件码
        String operation = getFirst(effect);
        switch (operation) {
            case "ONE" -> s = "若无护盾" + getNextEffectString(getFrom(), montage(effect), getTo(), ",");
            case "two" -> {
                String name = getFirst(effect);
                s = "若无\"" + name + "\"" + getNextEffectString(getFrom(), montage(effect), getTo(), ",");
            }
            case "THREE" -> s = "若目标力量低于发动者" + getNextEffectString(getFrom(), montage(effect), getTo(), ",");
            default -> s = "";
        }

        return s;
    }

    @Override
    public boolean isNegative() {
        //获取效果序列
        ArrayList<String> effect = cutEffect(getEffect());
        getFirst(effect);
        getFirst(effect);
        Effect next = parse(getFrom(), montage(effect), getTo());
        if (next instanceof Negative negative) {
            return negative.isNegative();
        }
        return false;
    }
}
