package com.hjc.CardAdventure.effect.basic;

import com.hjc.CardAdventure.Utils.AttributeUtils;
import com.hjc.CardAdventure.effect.target.TargetedEffect;
import com.hjc.CardAdventure.pojo.Role;

import java.util.ArrayList;

//回复效果
public class RestoreEffect extends TargetedEffect {
    public RestoreEffect(Role from, String effect, Role to) {
        super(from, effect, to);
    }

    @Override
    public void action() {
        if (getFrom() == null || getTo() == null) return;
        //获取效果序列
        ArrayList<String> effect = cutEffect(getEffect());
        //获取数值
        int value = changeToInt(getFirst(effect));
        //计算真实回血数值
        int realValue = AttributeUtils.mathRestore(getFrom(), value);
        getTo().restore(realValue);

        continueAction(getFrom(), montage(effect), getTo());
    }

    @Override
    public String toString() {
        //获取效果序列
        ArrayList<String> effect = cutEffect(getEffect());
        //获取数值
        int value = changeToInt(getFirst(effect));
        //计算真实回血数值
        int realValue = AttributeUtils.mathRestore(getFrom(), value);
        return "回复" + realValue + "点血量";
    }
}
