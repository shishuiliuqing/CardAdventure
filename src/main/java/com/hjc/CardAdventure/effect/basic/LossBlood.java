package com.hjc.CardAdventure.effect.basic;

import com.hjc.CardAdventure.effect.target.TargetedEffect;
import com.hjc.CardAdventure.pojo.HurtType;
import com.hjc.CardAdventure.pojo.Role;

import java.util.ArrayList;

//失去生命效果
public class LossBlood extends TargetedEffect {
    public LossBlood(Role from, String effect, Role to) {
        super(from, effect, to);
    }

    @Override
    public void action() {
        if (getFrom() == null || getTo() == null) return;
        //获得效果序列
        ArrayList<String> effect = cutEffect(getEffect());
        //获得数值
        int value = changeToInt(getFirst(effect));
        getTo().specialHurt(HurtType.NULL, value);
        continueAction(getFrom(), montage(effect), getTo());
    }

    @Override
    public String toString() {
        //获得效果序列
        ArrayList<String> effect = cutEffect(getEffect());
        //获得数值
        int value = changeToInt(getFirst(effect));
        String next = getNextEffectString(getFrom(), montage(effect), null, ",");
        return "失去" + value + "点生命" + next;
    }
}
