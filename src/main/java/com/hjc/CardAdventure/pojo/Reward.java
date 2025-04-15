package com.hjc.CardAdventure.pojo;

import com.almasb.fxgl.dsl.FXGL;
import com.hjc.CardAdventure.configuration.MonsterPool;
import com.hjc.CardAdventure.configuration.PlayerCards;
import com.hjc.CardAdventure.effect.Effect;
import com.hjc.CardAdventure.pojo.card.Card;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

import static com.hjc.CardAdventure.Global.CONFIGURATION.*;
import static com.hjc.CardAdventure.Global.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reward {
    //卡牌奖励
    private ArrayList<ArrayList<Card>> cards;
    //金币奖励
    private int gold;
    //经验奖励
    private int experience;

    //奖励解析
    public static Reward getReward(ArrayList<String> rewards) {
        Reward result = new Reward();
        //奖励解析
        for (String reward : rewards) {
            ArrayList<String> r = Effect.cutEffect(reward);
            //取第一个操作符
            String operation = Effect.getFirst(r);
            //解析
            switch (operation) {
                case "CARDS" -> {
                    //奖励卡牌
                    //取第二操作符
                    int value = Effect.changeToInt(Effect.getFirst(r));

                    //获取角色卡牌奖励
                    ArrayList<String> rewardCards = PlayerCards.getCardReward(value - 1, playerCards);
                    ArrayList<Card> cardList = new ArrayList<>(PlayerCards.getCards(rewardCards));

                    //获取怪物卡牌奖励
                    String enemyCard = MonsterPool.getCardReward();
                    cardList.add(FXGL.getAssetLoader().loadJSON(getJsonAddress(CARDS_ADDRESS, "enemy/" + enemyCard), Card.class).get());

                    result.getCards().add(cardList);
                }
                case "GOLD" -> {
                    //奖励金币
                    //取第二操作符
                    int value = Effect.changeToInt(Effect.getFirst(r));
                    result.setGold(value);
                }
                case "EXPERIENCE" -> {
                    //奖励经验
                    //取第二操作符
                    int value = Effect.changeToInt(Effect.getFirst(r));
                    result.setGold(value);
                }
            }
        }

        return result;
    }
}
