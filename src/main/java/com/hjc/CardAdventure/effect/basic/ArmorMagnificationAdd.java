package com.hjc.CardAdventure.effect.basic;

import com.hjc.CardAdventure.Utils.AttributeUtils;
import com.hjc.CardAdventure.effect.effectType.Negative;
import com.hjc.CardAdventure.effect.target.TargetedEffect;
import com.hjc.CardAdventure.pojo.Role;

import java.util.ArrayList;

//护盾添加倍率变化
public class ArmorMagnificationAdd extends TargetedEffect implements Negative {

    public ArmorMagnificationAdd(Role from, String effect, Role to) {
        super(from, effect, to);
    }

    @Override
    public void action() {
        if (getFrom() == null || getTo() == null) return;
        //获取效果序列
        ArrayList<String> effect = cutEffect(getEffect());
        //获得操作数
        String operator = getFirst(effect);
        //获取数值
        int value = changeToInt(getFirst(effect));
        //获取初始数值
        double origin = AttributeUtils.ARMOR_ADD_MAGNIFICATION.get(getTo());
        //替换数值
        if (operator.equals("A")) AttributeUtils.ARMOR_ADD_MAGNIFICATION.replace(getTo(), origin * (value / 100.0));
        else AttributeUtils.ARMOR_ADD_MAGNIFICATION.replace(getTo(), origin / (value / 100.0));

        //继续
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
        //获得操作数
        String operator = getFirst(effect);
        //获取数值
        int value = changeToInt(getFirst(effect));
        return operator.equals("A") && value < 100;
    }
}
