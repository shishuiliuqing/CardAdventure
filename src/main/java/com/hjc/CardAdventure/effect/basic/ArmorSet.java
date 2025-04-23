package com.hjc.CardAdventure.effect.basic;

import com.hjc.CardAdventure.effect.target.TargetedEffect;
import com.hjc.CardAdventure.pojo.Role;

import java.util.ArrayList;

//目标护盾值设置
public class ArmorSet extends TargetedEffect {
    public ArmorSet(Role from, String effect, Role to) {
        super(from, effect, to);
    }

    @Override
    public void action() {
        if (getFrom() == null || getTo() == null) return;
        //获取效果序列
        ArrayList<String> effect = cutEffect(getEffect());
        //获取数值
        int value = changeToInt(getFirst(effect));
        getTo().setRoleArmor(value);

        continueAction(getFrom(), montage(effect), getTo());
    }

    @Override
    public String toString() {
        return "";
    }
}
