package com.hjc.CardAdventure.effect.player;

import com.hjc.CardAdventure.Global;
import com.hjc.CardAdventure.component.battle.SumCardsComponent;
import com.hjc.CardAdventure.effect.Effect;
import com.hjc.CardAdventure.entity.BattleEntity;
import com.hjc.CardAdventure.pojo.Role;

import java.util.ArrayList;

//减少出牌数
public class ReduceProduce extends Effect {
    public ReduceProduce(Role from, String effect) {
        super(from, effect);
    }

    @Override
    public void action() {
        if (getFrom() == null) return;
        //获取效果序列
        ArrayList<String> effect = cutEffect(getEffect());
        //获取数值
        int value = changeToInt(getFirst(effect));
        Global.CARD_USE.remainingProduce = Math.max((Global.CARD_USE.remainingProduce - value), 0);
        BattleEntity.sumProduce.getComponent(SumCardsComponent.class).update();
    }

    @Override
    public String toString() {
        //获取效果序列
        ArrayList<String> effect = cutEffect(getEffect());
        //获取数值
        int value = changeToInt(getFirst(effect));
        if (value == 1) return "";
        if(value == 0) return "轻盈";
        String next = Effect.getNextEffectString(getFrom(), montage(effect), null, ",");
        return "沉重" + value + next;
    }
}
