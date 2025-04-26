package com.hjc.CardAdventure.effect.player;

import com.hjc.CardAdventure.Global;
import com.hjc.CardAdventure.component.battle.SumCardsComponent;
import com.hjc.CardAdventure.effect.Effect;
import com.hjc.CardAdventure.entity.BattleEntity;
import com.hjc.CardAdventure.pojo.Role;

import java.util.ArrayList;

//获得出牌数
public class GetProduceNum extends Effect {
    public GetProduceNum(Role from, String effect) {
        super(from, effect);
    }

    @Override
    public void action() {
        if (getFrom() == null) return;
        //获取效果序列
        ArrayList<String> effect = cutEffect(getEffect());
        //获取数值
        int value = changeToInt(getFirst(effect));
        //增加出牌数
        Global.CARD_USE.remainingProduce += value;
        //更新出牌数
        BattleEntity.sumProduce.getComponent(SumCardsComponent.class).update();

        continueAction(getFrom(), montage(effect), null);
    }

    @Override
    public String toString() {
        ArrayList<String> effect = cutEffect(getEffect());
        String value = getFirst(effect);
        return "本回合增加" + value + "次出牌数";
    }
}
