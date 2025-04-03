package com.hjc.CardAdventure.effect.basic;

import com.hjc.CardAdventure.effect.target.TargetedEffect;
import com.hjc.CardAdventure.pojo.Role;

//物理攻击
public class PhysicalAttack extends TargetedEffect {
    public PhysicalAttack(Role from, String effect, Role to) {
        super(from, effect, to);
    }

    @Override
    public void action() {

    }
}
