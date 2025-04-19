package com.hjc.CardAdventure.effect.basic;

import com.hjc.CardAdventure.component.battle.ActionOverComponent;
import com.hjc.CardAdventure.effect.Effect;
import com.hjc.CardAdventure.effect.opportunity.Opportunity;
import com.hjc.CardAdventure.effect.opportunity.OpportunityType;
import com.hjc.CardAdventure.entity.BattleEntity;
import com.hjc.CardAdventure.pojo.BattleInformation;
import com.hjc.CardAdventure.pojo.Role;

public class ActionOver extends Effect {
    public ActionOver(Role from, String effect) {
        super(from, effect);
    }

    @Override
    public void action() {
        if (getFrom() == null) return;
        //触发自身回合结束时机
        Opportunity.launchOpportunity(BattleInformation.nowAction, OpportunityType.OWN_ROUND_END);
        //结束回合
        BattleEntity.actionOver.getComponent(ActionOverComponent.class).over();
    }

    @Override
    public String toString() {
        return "结束回合" + Effect.getNextEffectString(getFrom(), getEffect(), null, ",");
    }
}
