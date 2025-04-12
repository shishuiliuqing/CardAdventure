package com.hjc.CardAdventure.effect.player;

import com.hjc.CardAdventure.component.battle.DrawCardsComponent;
import com.hjc.CardAdventure.effect.Effect;
import com.hjc.CardAdventure.entity.BattleEntity;
import com.hjc.CardAdventure.pojo.Role;

import java.util.ArrayList;

//洗牌抽牌效果
public class DrawEffect extends Effect {
    public DrawEffect(Role from, String effect) {
        super(from, effect);
    }

    @Override
    public void action() {
        //无发动者
        if (getFrom() == null) return;
        //解析效果
        ArrayList<String> effect = Effect.cutEffect(getEffect());
        //获取抽牌数
        int value = Effect.changeToInt(Effect.getFirst(effect));
        int lessDraw = 0;
        for (int i = 0; i < value; i++) {
            lessDraw += BattleEntity.drawCards.getComponent(DrawCardsComponent.class).draw();
        }
        if (lessDraw > 0) new ShuffleEffect(getFrom(), String.valueOf(lessDraw)).action();
        //执行接下来的效果
        Effect.continueAction(getFrom(), Effect.montage(effect), null);
    }

    @Override
    public String toString() {
        //解析效果
        ArrayList<String> effect = Effect.cutEffect(getEffect());
        //获取抽牌数
        int value = Effect.changeToInt(Effect.getFirst(effect));
        String next = Effect.getNextEffectString(getFrom(), Effect.montage(effect), null, ",");
        return "抽" + value + "张牌" + next;
    }
}
