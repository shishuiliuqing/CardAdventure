package com.hjc.CardAdventure.effect.player;

import com.hjc.CardAdventure.Global;
import com.hjc.CardAdventure.effect.Effect;
import com.hjc.CardAdventure.pojo.BattleInformation;
import com.hjc.CardAdventure.pojo.Role;

import java.util.ArrayList;

import static com.hjc.CardAdventure.Global.CARD_USE.usingCard;

//复用卡牌
public class Reuse extends Effect {
    public Reuse(Role from, String effect) {
        super(from, effect);
    }

    @Override
    public void action() {
        if (getFrom() == null) return;
        //获取效果序列
        ArrayList<String> effect = cutEffect(getEffect());
        //需要执行效果
        ArrayList<Effect> effects = new ArrayList<>();
        for (String s : effect) {
            //获取执行效果索引
            int index = changeToInt(s);
            //解析效果
            Effect e = parse(getFrom(), usingCard.getCardEffects().get(index), null);
            effects.add(e);
        }
        //插入效果序列
        BattleInformation.insetEffect(effects);
    }

    @Override
    public String toString() {
        return "复用";
    }
}
