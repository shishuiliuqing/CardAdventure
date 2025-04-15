package com.hjc.CardAdventure.configuration;

import com.almasb.fxgl.dsl.FXGL;
import com.hjc.CardAdventure.pojo.card.Card;
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

    //日常战斗，60%白，30蓝，10%金
    //根据数量随机获得人物卡牌
    public static ArrayList<String> getCardReward(int num, PlayerCards playerCards) {
        Random r = new Random();
        ArrayList<String> cards = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            int n = r.nextInt(100) + 1;
            if (n <= 60) {
                cards.add(playerCards.getWhite().get(r.nextInt()));
            } else if (n <= 90) {
                cards.add(playerCards.getBlue().get(r.nextInt()));
            } else {
                cards.add(playerCards.getYellow().get(r.nextInt()));
            }
        }
        return cards;
    }
}
