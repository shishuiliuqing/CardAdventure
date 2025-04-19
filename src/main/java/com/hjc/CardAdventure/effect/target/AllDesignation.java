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
        if (effect == null) return;
        //群体必为有目标效果
        TargetedEffect targetedEffect = (TargetedEffect) effect;
        for (Enemy enemy : BattleInformation.ENEMIES) {
            //修改效果目标
            targetedEffect.setTo(enemy);
            //执行效果
            targetedEffect.action();
        }
    }

    @Override
    public String toString() {
        return getNextEffectString(getFrom(), getEffect(), null, "[A]");
    }
}
