package com.hjc.CardAdventure.effect.basic;

import com.hjc.CardAdventure.Utils.AttributeUtils;
import com.hjc.CardAdventure.effect.Effect;
import com.hjc.CardAdventure.effect.target.TargetedEffect;
import com.hjc.CardAdventure.pojo.Role;

import java.util.ArrayList;

import static com.hjc.CardAdventure.effect.Effect.*;

//物理伤害
public class PhysicalDamage extends TargetedEffect {
    public PhysicalDamage(Role from, String effect, Role to) {
        super(from, effect, to);
    }

    @Override
    public void action() {
        if (getFrom() == null || getTo() == null) return;
        //获取效果序列
        ArrayList<String> effect = cutEffect(getEffect());
        //获取初始数值
        int value = changeToInt(getFirst(effect));
        //获取倍率
        int magnification = changeToInt(getFirst(effect));
        //计算真实伤害数值
        int realValue = AttributeUtils.mathPhyDamage(getFrom(), getTo(), value, magnification);
        //对目标造成伤害
        getTo().phyHurt(realValue);
        //继续执行下面的序列
        Effect.continueAction(getFrom(), montage(effect), null);
    }

    @Override
    public String toString() {
        //获取效果序列
        ArrayList<String> effect = cutEffect(getEffect());
        //获取初始数值
        int value = changeToInt(getFirst(effect));
        //获取倍率
        int magnification = changeToInt(getFirst(effect));
        //计算真实伤害数值
        int realValue = AttributeUtils.mathPhyDamage(getFrom(), getTo(), value, magnification);
        String next = Effect.getNextEffectString(getFrom(), montage(effect), null, ",");
        return "造成" + realValue + "点物理伤害" + (magnification == 1 ? "" : "," + magnification + "倍力量加成") + next;
    }
}
