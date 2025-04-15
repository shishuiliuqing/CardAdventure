package com.hjc.CardAdventure.effect.basic;

import com.hjc.CardAdventure.component.battle.ActionOverComponent;
import com.hjc.CardAdventure.effect.Effect;
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
        BattleEntity.actionOver.getComponent(ActionOverComponent.class).over();
    }

    @Override
    public String toString() {
        return "结束回合" + Effect.getNextEffectString(getFrom(), getEffect(), null, ",");
    }
}
