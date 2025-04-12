package com.hjc.CardAdventure.effect.target;

import com.hjc.CardAdventure.Global;
import com.hjc.CardAdventure.effect.Effect;
import com.hjc.CardAdventure.pojo.Role;

//指定玩家效果
public class PlayerDesignation extends Effect {
    public PlayerDesignation(Role from, String effect) {
        super(from, effect);
    }

    @Override
    public void action() {
        if (getFrom() == null) return;
        //解析剩下的效果
        Effect.continueAction(getFrom(), getEffect(), Global.PLAYER.player);
    }

    @Override
    public String toString() {
        return Effect.getNextEffectString(getFrom(), getEffect(), Global.PLAYER.player, "[P]");
    }
}
