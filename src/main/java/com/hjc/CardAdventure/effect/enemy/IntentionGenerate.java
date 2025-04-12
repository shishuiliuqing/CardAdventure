package com.hjc.CardAdventure.effect.enemy;

import com.hjc.CardAdventure.effect.Effect;
import com.hjc.CardAdventure.pojo.Role;
import com.hjc.CardAdventure.pojo.enemy.Enemy;
import com.hjc.CardAdventure.pojo.enemy.IntentionGenerateType;

public class IntentionGenerate extends Effect {
    public IntentionGenerate(Role from, String effect) {
        super(from, effect);
    }

    @Override
    public void action() {
        if (getFrom() == null) return;
        Enemy enemy = (Enemy) getFrom();
        IntentionGenerateType.generateIntention(enemy);
        enemy.update();
    }

    @Override
    public String toString() {
        return "";
    }
}
