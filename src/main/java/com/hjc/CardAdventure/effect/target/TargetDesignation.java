package com.hjc.CardAdventure.effect.target;

import com.hjc.CardAdventure.Global;
import com.hjc.CardAdventure.effect.Effect;
import com.hjc.CardAdventure.pojo.Role;

import java.util.ArrayList;

import static com.hjc.CardAdventure.effect.Effect.*;

//指定目标效果
public class TargetDesignation extends Effect {
    public TargetDesignation(Role from, String effect) {
        super(from, effect);
    }

    @Override
    public void action() {
        if (getFrom() == null) return;
        //解析剩下的效果
        Effect.continueAction(getFrom(), getEffect(), Global.CARD_USE.target);
    }

    @Override
    public String toString() {
        return Effect.getNextEffectString(getFrom(), getEffect(), Global.CARD_USE.target, "[T]");
    }
}
