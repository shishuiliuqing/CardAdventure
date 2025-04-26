package com.hjc.CardAdventure.effect.player;

import com.hjc.CardAdventure.component.battle.ActionOverComponent;
import com.hjc.CardAdventure.effect.Effect;
import com.hjc.CardAdventure.entity.BattleEntity;
import com.hjc.CardAdventure.pojo.Role;

public class PlayerActionOver extends Effect {
    public PlayerActionOver(Role from, String effect) {
        super(from, effect);
    }

    @Override
    public void action() {
        if (getFrom() == null) return;
        BattleEntity.actionOver.getComponent(ActionOverComponent.class).onOver();
    }

    @Override
    public String toString() {
        return "";
    }
}
