package com.hjc.CardAdventure.effect.basic;

import com.hjc.CardAdventure.Utils.AttributeUtils;
import com.hjc.CardAdventure.Utils.EffectUtils;
import com.hjc.CardAdventure.effect.effectType.Negative;
import com.hjc.CardAdventure.effect.opportunity.Opportunity;
import com.hjc.CardAdventure.effect.opportunity.OpportunityStatus;
import com.hjc.CardAdventure.effect.opportunity.OpportunityType;
import com.hjc.CardAdventure.effect.target.TargetedEffect;
import com.hjc.CardAdventure.pojo.HurtType;
import com.hjc.CardAdventure.pojo.Role;
import javafx.scene.paint.Color;

import java.util.ArrayList;

//法术效果
public class MagicEffect extends TargetedEffect implements Negative {
    public MagicEffect(Role from, String effect, Role to) {
        super(from, effect, to);
    }

    @Override
    public void action() {
        if (getFrom() == null || getTo() == null) return;
        //获取效果序列
        ArrayList<String> effect = cutEffect(getEffect());
        //获取法术种类
        HurtType hurtType = HurtType.getHurtType(getFirst(effect));
        //获取数值
        int value = changeToInt(getFirst(effect));
        //计算真实数值
        int realValue = AttributeUtils.mathMagic(getFrom(), value);
        switch (hurtType) {
            case FIRE -> {
                //播放特效
                EffectUtils.displayEffect("burn", 16, 0.3, 1.8, getTo(), 40, 50);
                //播放字体
                EffectUtils.textBigger("燃烧效果⬆", getTo(), Color.ORANGE);
                String e = "FROM#MAGIC_DAMAGE#FIRE#SValueS";
                Opportunity opportunity = new Opportunity("燃烧", OpportunityType.OWN_ROUND_BEGIN, OpportunityStatus.NEGATIVE,
                        0, 1, realValue, true, e, null);
                Opportunity.addOpportunity(getTo(), opportunity);
            }
        }

        continueAction(getFrom(), montage(effect), getTo());
    }

    @Override
    public String toString() {
        //获取效果序列
        ArrayList<String> effect = cutEffect(getEffect());
        //获取法术种类
        HurtType hurtType = HurtType.getHurtType(getFirst(effect));
        //获取数值
        int value = changeToInt(getFirst(effect));
        //计算真实数值
        int realValue = AttributeUtils.mathMagic(getFrom(), value);
        String next = getNextEffectString(getFrom(), montage(effect), getTo(), ",");
        return switch (hurtType) {
            case FIRE -> "获得\"燃烧 " + realValue + "\"";
            case NULL -> "";
        } + next;
    }

    @Override
    public boolean isNegative() {
        return true;
    }
}
