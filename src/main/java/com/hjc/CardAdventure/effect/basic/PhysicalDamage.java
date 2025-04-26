package com.hjc.CardAdventure.effect.basic;

import com.hjc.CardAdventure.Global;
import com.hjc.CardAdventure.Utils.AttributeUtils;
import com.hjc.CardAdventure.effect.Effect;
import com.hjc.CardAdventure.effect.opportunity.Opportunity;
import com.hjc.CardAdventure.effect.opportunity.OpportunityType;
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
        //为物理攻击
        Global.CARD_USE.isAttack = true;
        //获取效果序列
        ArrayList<String> effect = cutEffect(getEffect());
        //获取初始数值
        int value = changeToInt(getFirst(effect));
        //获取倍率
        int magnification = changeToInt(getFirst(effect));
        //计算真实伤害数值
        int realValue = AttributeUtils.mathPhyDamage(getFrom(), getTo(), value, magnification);
        //对目标造成伤害
        boolean isHurt = getTo().phyHurt(realValue);

        //受到物理伤害后效果
        Opportunity.launchOpportunity(getTo(), OpportunityType.PHY_HURT);
        //触发造成物理伤害后效果
        Opportunity.launchOpportunity(getFrom(), OpportunityType.PHY_DAMAGE_END);

        if (isHurt) {
            //成功造成伤害，触发攻击者攻击后造成伤害效果
            Opportunity.launchOpportunity(getFrom(), OpportunityType.PHY_DAMAGE_SUCCESS);
        } else {
            //失败，触发受伤者完全抵挡物理伤害效果
            Opportunity.launchOpportunity(getTo(), OpportunityType.DEFENSE_PHY_HURT);
        }
        //继续执行下面的序列
        Effect.continueAction(getFrom(), montage(effect), getTo());
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
        //System.out.println(realValue);
        return "造成" + realValue + "点物理伤害" + (magnification == 1 ? "" : "," + magnification + "倍力量加成") + next;
    }
}
