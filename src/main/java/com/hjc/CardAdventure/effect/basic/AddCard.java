package com.hjc.CardAdventure.effect.basic;

import com.almasb.fxgl.dsl.FXGL;
import com.hjc.CardAdventure.Global;
import com.hjc.CardAdventure.Utils.CardsUtils;
import com.hjc.CardAdventure.Utils.EffectUtils;
import com.hjc.CardAdventure.effect.Effect;
import com.hjc.CardAdventure.pojo.Role;
import com.hjc.CardAdventure.pojo.card.Card;

import java.util.ArrayList;

//向牌堆添加指定卡
public class AddCard extends Effect {
    public AddCard(Role from, String effect) {
        super(from, effect);
    }

    @Override
    public void action() {
        if (getFrom() == null) return;
        //获得效果序列
        ArrayList<String> effect = cutEffect(getEffect());
        //获取要塞的牌堆
        ArrayList<Card> cards = CardsUtils.getCards(getFirst(effect));
        //获取要塞的张数
        int value = changeToInt(getFirst(effect));
        //获取要塞牌的类型
        String type = getFirst(effect);
        //要塞牌名字
        String name = getFirst(effect);
        ArrayList<Card> result = new ArrayList<>();
        switch (type) {
            case "STATUS" -> {
                //加载卡牌
                for (int i = 0; i < value; i++) {
                    Card card = FXGL.getAssetLoader().loadJSON(Global.getJsonAddress(Global.CARDS_ADDRESS, "status/" + name), Card.class).get();
                    result.add(card);
                }
            }
        }
        //添加
        cards.addAll(result);
        //动画播放
        EffectUtils.addCard(result, cards);
        //继续接下来的效果
        continueAction(getFrom(), montage(effect), null);
    }

    @Override
    public String toString() {
        return "";
    }
}
