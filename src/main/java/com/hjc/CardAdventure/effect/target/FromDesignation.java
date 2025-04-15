package com.hjc.CardAdventure.effect.target;

import com.hjc.CardAdventure.Global;
import com.hjc.CardAdventure.effect.Effect;
import com.hjc.CardAdventure.pojo.Role;

public class FromDesignation extends Effect {
    public FromDesignation(Role from, String effect) {
        super(from, effect);
    }

    @Override
    public void action() {
        if (getFrom() == null) return;
        Effect.continueAction(getFrom(), getEffect(), getFrom());
    }

    @Override
    public String toString() {
        return Effect.getNextEffectString(getFrom(), getEffect(), Global.PLAYER.player, "[F]");
    }
}
