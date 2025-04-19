package com.hjc.CardAdventure.effect.player;

import com.hjc.CardAdventure.Global;
import com.hjc.CardAdventure.component.card.CardComponent;
import com.hjc.CardAdventure.effect.Effect;
import com.hjc.CardAdventure.pojo.BattleInformation;
import com.hjc.CardAdventure.pojo.Role;

public class CardUseEnd extends Effect {
    public CardUseEnd(Role from, String effect) {
        super(from, effect);
    }

    @Override
    public void action() {
        if (getFrom() == null) return;
        Global.CARD_USE.actionCard.disappear(switch (getEffect()) {
            case "CONSUME" -> BattleInformation.CONSUME_CARDS;
            case "DRAW" -> BattleInformation.DRAW_CARDS;
            default -> BattleInformation.ABANDON_CARDS;
        });
        //卡牌使用结束
        Global.CARD_USE.isUsing = false;
    }

    @Override
    public String toString() {
        return switch (getEffect()) {
            case "CONSUME" -> "消耗";
            case "DRAW" -> "回抽";
            default -> "";
        };
    }
}
