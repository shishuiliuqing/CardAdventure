package com.hjc.CardAdventure.effect.player;

import com.hjc.CardAdventure.Utils.BattleUtils;
import com.hjc.CardAdventure.Utils.CardsUtils;
import com.hjc.CardAdventure.Utils.EffectUtils;
import com.hjc.CardAdventure.effect.Effect;
import com.hjc.CardAdventure.pojo.BattleInformation;
import com.hjc.CardAdventure.pojo.Role;
import com.hjc.CardAdventure.pojo.card.Card;
import javafx.scene.paint.Color;

import java.util.ArrayList;

import static com.hjc.CardAdventure.Global.PLAYER.player;

//将某个牌堆的牌置入其他牌堆
public class CardsToCards extends Effect {
    public CardsToCards(Role from, String effect) {
        super(from, effect);
    }

    @Override
    public void action() {
        if (getFrom() == null) return;
        //获取效果序列
        ArrayList<String> effect = cutEffect(getEffect());
        //获取出发卡组
        ArrayList<Card> From_Cards = getCards(getFirst(effect));
        //获取目的卡组
        ArrayList<Card> To_Cards = getCards(getFirst(effect));
        //获取转移牌数
        int value = changeToInt(getFirst(effect));
        //获取操作码
        String operation = getFirst(effect);
        switch (operation) {
            case "TOP" -> {
                if (From_Cards.isEmpty()) break;
                while (!From_Cards.isEmpty() && value > 0) {
                    To_Cards.add(From_Cards.get(0));
                    From_Cards.remove(0);
                    value--;
                }
                double[] fromLocation = EffectUtils.getCardsLocal(From_Cards);
                CardsUtils.updateCards(From_Cards);
                double[] toLocation = EffectUtils.getCardsLocal(To_Cards);
                EffectUtils.CircleToCards(fromLocation[0], fromLocation[1], toLocation[0], toLocation[1], Color.valueOf(player.getColorS()), To_Cards);
                BattleUtils.pause(0.3);
            }
        }

        //继续下面的操作
        continueAction(getFrom(), montage(effect), null);
    }

    @Override
    public String toString() {
        //获取效果序列
        ArrayList<String> effect = cutEffect(getEffect());
        //出发牌堆
        String From_Cards = getCardsName(getFirst(effect));
        //目的牌堆
        String To_Cards = getCardsName(getFirst(effect));
        //数值
        int value = changeToInt(getFirst(effect));
        //获取操作码
        String operation = getFirst(effect);
        String next = getNextEffectString(getFrom(), montage(effect), null, ",");
        if (operation.equals("TOP")) {
            return "将" + From_Cards + "的前" + value + "张牌置入" + To_Cards + next;
        } else return "";
    }

    //根据名字获取牌堆
    public static ArrayList<Card> getCards(String cards) {
        return switch (cards) {
            case "DRAW" -> BattleInformation.DRAW_CARDS;
            case "ABANDON" -> BattleInformation.ABANDON_CARDS;
            default -> BattleInformation.CONSUME_CARDS;
        };
    }

    //根据名字获取牌堆中文名
    public static String getCardsName(String cards) {
        return switch (cards) {
            case "DRAW" -> "抽牌堆";
            case "ABANDON" -> "弃牌堆";
            default -> "消耗牌堆";
        };
    }
}
