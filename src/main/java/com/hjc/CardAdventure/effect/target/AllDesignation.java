package com.hjc.CardAdventure.effect.target;

import com.hjc.CardAdventure.Utils.AttributeUtils;
import com.hjc.CardAdventure.effect.Effect;
import com.hjc.CardAdventure.effect.basic.PhysicalDamage;
import com.hjc.CardAdventure.effect.opportunity.Opportunity;
import com.hjc.CardAdventure.effect.opportunity.OpportunityType;
import com.hjc.CardAdventure.pojo.BattleInformation;
import com.hjc.CardAdventure.pojo.Role;
import com.hjc.CardAdventure.pojo.enemy.Enemy;

import java.util.ArrayList;

public class AllDesignation extends Effect {
    public AllDesignation(Role from, String effect) {
        super(from, effect);
    }

    @Override
    public void action() {
        if (getFrom() == null) return;
        Effect effect = parse(getFrom(), getEffect(), null);
        if (effect instanceof PhysicalDamage) {
            //物理伤害效果，群体攻击仅触发一次攻击时机
            ArrayList<String> result = cutEffect(effect.getEffect());
            //获取伤害数值
            int value = changeToInt(getFirst(result));
            //获得力量加成倍率
            int magnification = changeToInt(getFirst(result));
            //对每个敌人造成一次伤害
            for (Enemy enemy : BattleInformation.ENEMIES) {
                int realValue = AttributeUtils.mathPhyDamage(getFrom(), enemy, value, magnification);
                enemy.phyHurt(realValue);
            }
            //触发攻击后时机效果
            Opportunity.launchOpportunity(getFrom(), OpportunityType.PHY_ATTACK_END);
            //继续接下来的效果
            continueAction(getFrom(), montage(result), null);
        } else {
            //群体必为有目标效果
            TargetedEffect targetedEffect = (TargetedEffect) effect;
            for (Enemy enemy : BattleInformation.ENEMIES) {
                //修改效果目标
                targetedEffect.setTo(enemy);
                //执行效果
                targetedEffect.action();
            }
        }
    }

    @Override
    public String toString() {
        return getNextEffectString(getFrom(), getEffect(), null, "[A]");
    }
}
