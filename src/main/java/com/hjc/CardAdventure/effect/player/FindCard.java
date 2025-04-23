package com.hjc.CardAdventure.effect.player;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.SpawnData;
import com.hjc.CardAdventure.Global;
import com.hjc.CardAdventure.Utils.CardsUtils;
import com.hjc.CardAdventure.component.battle.DrawCardsComponent;
import com.hjc.CardAdventure.effect.Effect;
import com.hjc.CardAdventure.pojo.BattleInformation;
import com.hjc.CardAdventure.pojo.Role;
import com.hjc.CardAdventure.pojo.card.Card;

import java.util.ArrayList;

import static com.hjc.CardAdventure.Utils.CardsUtils.getCards;

//从牌堆寻找卡牌
public class FindCard extends Effect {

    public FindCard(Role from, String effect) {
        super(from, effect);
    }

    @Override
    public void action() {
        if (getFrom() == null) return;
        //获取效果序列
        ArrayList<String> effect = cutEffect(getEffect());
        //取牌堆
        ArrayList<Card> cards = getCards(getFirst(effect));
        //获取是否使用
        boolean isProduce = getFirst(effect).equals("PRODUCE");
        //获取卡牌名字
        String name = getFirst(effect);
        //获取寻找张数
        int value = changeToInt(getFirst(effect));
        //寻找卡牌
        for (int i = 0; i < value; i++) {
            //获取手牌区牌位信息
            int boxNum = DrawCardsComponent.nearEmptyBox();
            //无牌位，结束
            if (boxNum == -1) break;
            for (Card card : cards) {
                //卡名一致
                if (card.getCardName().equals(name)) {
                    //占牌位
                    DrawCardsComponent.CARD_BOX_STATUS[boxNum - 1] = 1;
                    //添加此卡
                    Global.CARD_USE.findCards.put(boxNum, card);
                    //牌堆删除此卡
                    cards.remove(card);
                    break;
                }
            }
        }

        CardsUtils.updateCards(cards);

        if (isProduce) {
            BattleInformation.insetEffect(new SpecialProduce(getFrom(), ""));
        } else {
            Global.CARD_USE.findCards.forEach((integer, card) -> {
                int boxNum = integer;
                FXGL.spawn("card", new SpawnData().put("boxNum", boxNum).put("card", card));
            });
        }

        continueAction(getFrom(), montage(effect), null);
    }

    @Override
    public String toString() {
        return "";
    }
}
