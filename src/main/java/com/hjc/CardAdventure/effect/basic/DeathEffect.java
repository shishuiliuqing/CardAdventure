package com.hjc.CardAdventure.effect.basic;

import com.hjc.CardAdventure.effect.Effect;
import com.hjc.CardAdventure.pojo.BattleInformation;
import com.hjc.CardAdventure.pojo.Role;

//死亡
public class DeathEffect extends Effect {
    public DeathEffect(Role from, String effect) {
        super(from, effect);
    }

    @Override
    public void action() {
        if (getFrom() == null) return;
        BattleInformation.clearRole(getFrom());
        getFrom().die();
    }

    @Override
    public String toString() {
        return "死亡";
    }
}
