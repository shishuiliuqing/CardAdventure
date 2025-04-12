package com.hjc.CardAdventure.effect.enemy;

import com.hjc.CardAdventure.effect.Effect;
import com.hjc.CardAdventure.pojo.Role;
import com.hjc.CardAdventure.pojo.enemy.Enemy;

//删除当前意图
public class IntentionDelete extends Effect {
    public IntentionDelete(Role from, String effect) {
        super(from, effect);
    }

    @Override
    public void action() {
        if (getFrom() == null) return;
        Enemy enemy = (Enemy) getFrom();
        enemy.setNowIntention(null);
        enemy.update();
    }

    @Override
    public String toString() {
        return "";
    }
}
