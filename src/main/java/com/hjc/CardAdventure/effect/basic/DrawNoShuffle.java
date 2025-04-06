package com.hjc.CardAdventure.effect.basic;

import com.hjc.CardAdventure.component.battle.DrawCardsComponent;
import com.hjc.CardAdventure.effect.Effect;
import com.hjc.CardAdventure.entity.BattleEntity;
import com.hjc.CardAdventure.pojo.Role;

import java.util.ArrayList;

//抽牌效果，不洗牌
public class DrawNoShuffle extends Effect {
    public DrawNoShuffle(Role from, String effect) {
        super(from, effect);
    }

    @Override
    public void action() {
        if (getFrom() == null) return;
        //解析效果
        ArrayList<String> effect = Effect.cutEffect(getEffect());
        //获取抽牌数
        int value = Effect.changeToInt(Effect.getFirst(effect));
        //抽不够，不洗牌
        for (int i = 0; i < value; i++) {
            BattleEntity.drawCards.getComponent(DrawCardsComponent.class).draw();
        }
    }

    @Override
    public String toString() {
        return "";
    }
}
