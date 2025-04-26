package com.hjc.CardAdventure.configuration;

import com.almasb.fxgl.dsl.FXGL;
import com.hjc.CardAdventure.Utils.OtherUtils;
import com.hjc.CardAdventure.pojo.card.Card;
import com.hjc.CardAdventure.pojo.environment.InsideInformation;
import com.hjc.CardAdventure.pojo.player.Player;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Random;

import static com.hjc.CardAdventure.Global.*;
import static com.hjc.CardAdventure.Global.PLAYER.player;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerCards {
    //初始卡组
    ArrayList<String> initial;
    //白卡
    ArrayList<String> white;
    //蓝卡
    ArrayList<String> blue;
    //金卡
    ArrayList<String> yellow;

    //获取玩家牌组
    public static ArrayList<Card> getCards(ArrayList<String> cards) {
        ArrayList<Card> result = new ArrayList<>();
        for (String s : cards) {
            Card card = FXGL.getAssetLoader().loadJSON(getJsonAddress(CARDS_ADDRESS, player.getImg() + "/" + s), Card.class).get();
            result.add(card);
        }
        return result;
    }

    //根据阶段获得各品质卡的概率
    private static final int[][] PROBABILITIES = {
            {80, 80}, {70, 80}, {60, 80}, {50, 80}
    };

    //获得卡牌奖励
    public static ArrayList<String> getCardReward(int num, PlayerCards playerCards) {
        Random r = new Random();
        //获取当前概率
        //获得当前阶段
        int stage = (InsideInformation.day - 1) / 6 + 1;
        //白卡概率
        int white = PROBABILITIES[stage][0];
        //蓝卡概率
        int blue = PROBABILITIES[stage][1];

        String card;
        ArrayList<String> fromCards;
        ArrayList<String> cards = new ArrayList<>();

        //获得
        while (cards.size() < num) {
            //判断是否是白卡
            if (OtherUtils.isSuccess(white)) {
                //从白卡堆获取
                fromCards = playerCards.getWhite();
                card = fromCards.get(r.nextInt(fromCards.size()));
                insertCard(card, cards);
                continue;
            }

            //判断是否为蓝卡
            if (OtherUtils.isSuccess(blue)) {
                //从蓝卡堆获取
                fromCards = playerCards.getBlue();
                card = fromCards.get(r.nextInt(fromCards.size()));
                insertCard(card, cards);
                continue;
            }

            //从金卡堆获取
            fromCards = playerCards.getYellow();
            card = fromCards.get(r.nextInt(fromCards.size()));
            insertCard(card, cards);
        }
        return cards;
    }

    //添加卡牌
    private static void insertCard(String card, ArrayList<String> cards) {
        //如果已存在此卡，返回
        for (String s : cards) {
            if (s.equals(card)) return;
        }

        cards.add(card);
    }
}
