package com.hjc.CardAdventure.effect.target;

import com.hjc.CardAdventure.effect.Effect;
import com.hjc.CardAdventure.pojo.Role;
import lombok.Getter;

@Getter
//有目标效果
public abstract class TargetedEffect extends Effect {
    private final Role to;

    public TargetedEffect(Role from, String effect, Role to) {
        super(from, effect);
        this.to = to;
    }
}
