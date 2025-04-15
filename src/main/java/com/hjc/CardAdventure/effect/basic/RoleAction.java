package com.hjc.CardAdventure.effect.basic;

import com.hjc.CardAdventure.effect.Effect;
import com.hjc.CardAdventure.effect.target.TargetedEffect;
import com.hjc.CardAdventure.pojo.Role;

//获得回合
public class RoleAction extends TargetedEffect {
    public RoleAction(Role from, String effect, Role to) {
        super(from, effect, to);
    }

    @Override
    public void action() {
        if (getFrom() == null || getTo() == null) return;
        getTo().action();
        Effect.continueAction(getFrom(), getEffect(), null);
    }

    @Override
    public String toString() {
        return "获得回合";
    }
}
