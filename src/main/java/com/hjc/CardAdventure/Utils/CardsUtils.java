package com.hjc.CardAdventure.Utils;

import com.hjc.CardAdventure.Global;
import com.hjc.CardAdventure.component.battle.AbandonCardsComponent;
import com.hjc.CardAdventure.component.battle.ConsumeCardsComponent;
import com.hjc.CardAdventure.component.battle.DrawCardsComponent;
import com.hjc.CardAdventure.component.card.CardComponent;
import com.hjc.CardAdventure.entity.BattleEntity;
import com.hjc.CardAdventure.pojo.BattleInformation;
import com.hjc.CardAdventure.pojo.card.Card;

import static com.hjc.CardAdventure.pojo.BattleInformation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class CardsUtils {
    private CardsUtils() {
    }

    //数组打乱
    public static void disruptCards(ArrayList<Card> cards) {
        Random r = new Random();
        for (int i = 0; i < cards.size(); i++) {
            int n = r.nextInt(cards.size());
            Card t = cards.get(i);
            cards.set(i, cards.get(n));
            cards.set(n, t);
        }
    }

    //手牌区更新
    public static void updateHands() {
        for (CardComponent handCard : CardComponent.HAND_CARDS) {
            handCard.update();
        }
    }

    //更新指定牌堆
    public static void updateCards(ArrayList<Card> cards) {
        if (cards == BattleInformation.DRAW_CARDS)
            BattleEntity.drawCards.getComponent(DrawCardsComponent.class).update();
        else if (cards == BattleInformation.ABANDON_CARDS)
            BattleEntity.abandonCards.getComponent(AbandonCardsComponent.class).update();
        else BattleEntity.consumeCards.getComponent(ConsumeCardsComponent.class).update();
    }

    //根据名字获取牌堆中文名
    public static String getCardsName(String cards) {
        return switch (cards) {
            case "DRAW" -> "抽牌堆";
            case "ABANDON" -> "弃牌堆";
            default -> "消耗牌堆";
        };
    }

    //根据名字获取牌堆
    public static ArrayList<Card> getCards(String cards) {
        return switch (cards) {
            case "DRAW" -> BattleInformation.DRAW_CARDS;
            case "ABANDON" -> BattleInformation.ABANDON_CARDS;
            default -> BattleInformation.CONSUME_CARDS;
        };
    }

    //获取哈希表中最近牌位的卡牌
    public static Integer getNearFindCards() {
        final ArrayList<Integer> integers = new ArrayList<>();
        HashMap<Integer, Card> cards = Global.CARD_USE.findCards;
        //将卡牌地址置于数组中
        cards.forEach((integer, card) ->
                integers.add(integer)
        );
        //寻找最近地址
        int nearBoxNum = 999;
        for (Integer integer : integers) {
            if (integer < nearBoxNum) nearBoxNum = integer;
        }
        //返回对应地址
        return nearBoxNum;
    }
}
