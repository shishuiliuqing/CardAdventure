package com.hjc.CardAdventure.effect.target;

import com.hjc.CardAdventure.effect.Effect;
import com.hjc.CardAdventure.pojo.BattleInformation;
import com.hjc.CardAdventure.pojo.Role;

public class NowDesignation extends Effect {
    public NowDesignation(Role from, String effect) {
        super(from, effect);
    }

    @Override
    public void action() {
        if (getFrom() == null) return;
        Effect.continueAction(getFrom(), getEffect(), BattleInformation.nowAction);
    }

    @Override
    public String toString() {
        return getNextEffectString(getFrom(), getEffect(), BattleInformation.nowAction, "[N]");
    }
}
