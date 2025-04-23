package com.hjc.CardAdventure.effect.basic;

import com.hjc.CardAdventure.effect.target.TargetedEffect;
import com.hjc.CardAdventure.pojo.Role;

import java.util.ArrayList;

//设置护盾是否消失
public class ArmorDisappearSet extends TargetedEffect {
    public ArmorDisappearSet(Role from, String effect, Role to) {
        super(from, effect, to);
    }

    @Override
    public void action() {
        if (getFrom() == null || getTo() == null) return;
        //获取效果序列
        ArrayList<String> effect = cutEffect(getEffect());
        //获取是/否
        boolean armorDisappear = getFirst(effect).equals("YES");
        getTo().setRoleArmorDisappear(armorDisappear);

        continueAction(getFrom(), montage(effect), getTo());
    }

    @Override
    public String toString() {
        return "";
    }
}
