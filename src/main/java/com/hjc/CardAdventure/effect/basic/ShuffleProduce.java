package com.hjc.CardAdventure.effect.basic;

import com.hjc.CardAdventure.component.battle.SumCardsComponent;
import com.hjc.CardAdventure.effect.Effect;
import com.hjc.CardAdventure.entity.BattleEntity;
import com.hjc.CardAdventure.pojo.Role;

//刷新出牌数
public class ShuffleProduce extends Effect {

    public ShuffleProduce(Role from, String effect) {
        super(from, effect);
    }

    @Override
    public void action() {
        if (getFrom() == null) return;
        BattleEntity.sumProduce.getComponent(SumCardsComponent.class).shuffleAndUpdate();
        Effect.continueAction(getFrom(), getEffect(), null);
    }

    @Override
    public String toString() {
        return "逆转";
    }
}
