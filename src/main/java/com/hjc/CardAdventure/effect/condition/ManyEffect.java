package com.hjc.CardAdventure.effect.condition;

import com.hjc.CardAdventure.effect.Effect;
import com.hjc.CardAdventure.effect.target.TargetedEffect;
import com.hjc.CardAdventure.pojo.BattleInformation;
import com.hjc.CardAdventure.pojo.Role;

import java.util.ArrayList;

//多段效果
public class ManyEffect extends TargetedEffect {
    public ManyEffect(Role from, String effect, Role to) {
        super(from, effect, to);
    }

    @Override
    public void action() {
        if (getFrom() == null || getTo() == null) return;
        //获取效果序列
        ArrayList<String> effect = cutEffect(getEffect());
        //获取多段数值
        int value = changeToInt(getFirst(effect));
        //获得接下来的效果
        Effect next = parse(getFrom(), montage(effect), getTo());
        //向序列器添加多个效果
        for (int i = 0; i < value; i++) {
            BattleInformation.insetEffect(next);
        }
    }

    @Override
    public String toString() {
        //获取效果序列
        ArrayList<String> effect = cutEffect(getEffect());
        //获取多段数值
        int value = changeToInt(getFirst(effect));
        //获得接下来的效果
        String next = getNextEffectString(getFrom(), montage(effect), getTo(), "");
        return next.isEmpty() ? "" : next + value + "次";
    }
}
