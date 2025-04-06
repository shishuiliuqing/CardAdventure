package com.hjc.CardAdventure.configuration;

import com.almasb.fxgl.dsl.FXGL;
import com.hjc.CardAdventure.pojo.card.Card;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;

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

    //初始化玩家牌组
    public static ArrayList<Card> getCards(ArrayList<String> cards) {
        ArrayList<Card> result = new ArrayList<>();
        for (String s : cards) {
            Card card = FXGL.getAssetLoader().loadJSON(getJsonAddress(CARDS_ADDRESS,player.getImg() + "/" + s), Card.class).get();
            result.add(card);
        }
        return result;
    }
}
