package com.hjc.CardAdventure.Utils;

import com.hjc.CardAdventure.pojo.card.Card;

import java.util.ArrayList;
import java.util.Random;

public class CardsUtils {
    private CardsUtils(){}

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
}
