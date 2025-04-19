package com.hjc.CardAdventure.effect.basic;

import com.hjc.CardAdventure.effect.target.TargetedEffect;
import com.hjc.CardAdventure.pojo.HurtType;
import com.hjc.CardAdventure.pojo.Role;

import java.util.ArrayList;

//法术伤害
public class MagicDamage extends TargetedEffect {
    public MagicDamage(Role from, String effect, Role to) {
        super(from, effect, to);
    }

    @Override
    public void action() {
        if (getFrom() == null || getTo() == null) return;
        //获取效果序列
        ArrayList<String> effect = cutEffect(getEffect());
        //获取伤害种类
        HurtType hurtType = HurtType.getHurtType(getFirst(effect));
        //获取伤害数值
        int value = changeToInt(getFirst(effect));
        getTo().specialHurt(hurtType, value);

        continueAction(getFrom(), montage(effect), getTo());
    }

    @Override
    public String toString() {
        //获取效果序列
        ArrayList<String> effect = cutEffect(getEffect());
        //获取伤害种类
        HurtType hurtType = HurtType.getHurtType(getFirst(effect));
        //获取伤害数值
        int value = changeToInt(getFirst(effect));
        String next = getNextEffectString(getFrom(), montage(effect), getTo(), ",");
        return switch (hurtType) {
            case FIRE -> "受到火焰伤害" + value + "点";
            case NULL -> "";
        } + next;
    }
}
