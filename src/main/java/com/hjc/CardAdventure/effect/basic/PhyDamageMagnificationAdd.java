package com.hjc.CardAdventure.effect.basic;

import com.hjc.CardAdventure.Utils.AttributeUtils;
import com.hjc.CardAdventure.component.camp.CardsComponent;
import com.hjc.CardAdventure.component.card.CardComponent;
import com.hjc.CardAdventure.effect.Effect;
import com.hjc.CardAdventure.effect.target.TargetedEffect;
import com.hjc.CardAdventure.pojo.Role;

import java.util.ArrayList;

//伤害增加倍率器
public class PhyDamageMagnificationAdd extends TargetedEffect {
    public PhyDamageMagnificationAdd(Role from, String effect, Role to) {
        super(from, effect, to);
    }

    @Override
    public void action() {
        if (getFrom() == null || getTo() == null) return;
        //获取效果序列
        ArrayList<String> effect = cutEffect(getEffect());
        //获取初始数值
        double value = changeToInt(getFirst(effect)) * 0.01;
        //获取当前倍率
        double magnification = AttributeUtils.FROM_MAGNIFICATION.get(getTo());
        //设置倍率
        AttributeUtils.FROM_MAGNIFICATION.replace(getTo(), value * magnification);
        //更新手牌区
        for (CardComponent handCard : CardComponent.HAND_CARDS) {
            handCard.update();
        }
        //继续执行接下来的效果
        Effect.continueAction(getFrom(), montage(effect), null);
    }

    @Override
    public String toString() {
        String s;
        //获取效果序列
        ArrayList<String> effect = cutEffect(getEffect());
        //获取初始数值
        double value = changeToInt(getFirst(effect)) * 0.01;
        if (value == 2.0) {
            s = "使物理伤害翻倍";
        } else {
            s = "";
        }

        String next = Effect.getNextEffectString(getFrom(), montage(effect), null, ",");
        return s + next;
    }
}
