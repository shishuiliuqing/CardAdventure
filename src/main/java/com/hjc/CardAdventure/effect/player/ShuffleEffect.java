package com.hjc.CardAdventure.effect.player;

import com.hjc.CardAdventure.Utils.CardsUtils;
import com.hjc.CardAdventure.component.battle.AbandonCardsComponent;
import com.hjc.CardAdventure.effect.Effect;
import com.hjc.CardAdventure.entity.BattleEntity;
import com.hjc.CardAdventure.pojo.BattleInformation;
import com.hjc.CardAdventure.pojo.Role;

import static com.hjc.CardAdventure.Global.PLAYER.player;

//洗牌效果
public class ShuffleEffect extends Effect {
    public ShuffleEffect(Role from, String effect) {
        super(from, effect);
    }

    @Override
    public void action() {
        if (getFrom() == null) return;
        //将弃牌堆的牌放回抽牌堆
        BattleInformation.DRAW_CARDS.addAll(BattleInformation.ABANDON_CARDS);
        //清空弃牌堆的牌
        BattleInformation.ABANDON_CARDS.clear();
        //打乱抽牌堆的牌
        CardsUtils.disruptCards(BattleInformation.DRAW_CARDS);
        //补回抽牌,不洗牌抽牌
        BattleInformation.insetEffect(new DrawNoShuffle(player, getEffect()));
        //更新弃牌堆
        BattleEntity.abandonCards.getComponent(AbandonCardsComponent.class).update();
    }

    @Override
    public String toString() {
        return "洗牌" + Effect.getNextEffectString(getFrom(), getEffect(), null, ",");
    }
}
