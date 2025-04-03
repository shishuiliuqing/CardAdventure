package com.hjc.CardAdventure.effect.target;

import com.hjc.CardAdventure.effect.Effect;
import com.hjc.CardAdventure.pojo.Role;

//指定目标效果
public class TargetDesignation extends Effect {
    public TargetDesignation(Role from, String effect) {
        super(from, effect);
    }

    @Override
    public void action() {
        if (getFrom() == null) return;
    }
}
