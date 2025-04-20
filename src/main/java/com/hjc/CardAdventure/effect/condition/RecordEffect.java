package com.hjc.CardAdventure.effect.condition;

import com.hjc.CardAdventure.effect.target.TargetedEffect;
import com.hjc.CardAdventure.pojo.Role;

import java.util.ArrayList;

//记录器效果
public class RecordEffect extends TargetedEffect {
    public RecordEffect(Role from, String effect, Role to) {
        super(from, effect, to);
    }

    @Override
    public void action() {
        if (getFrom() == null || getTo() == null) return;
        //获取效果序列
        ArrayList<String> effect = cutEffect(getEffect());
        //获取记录对象
        Role role = getFirst(effect).equals("FROM") ? getFrom() : getTo();
        String value;
        switch (getFirst(effect)) {
            case "ARMOR" -> value = String.valueOf(getTo().getRoleArmor());
            default -> value = "";
        }
        //获取接下来执行的效果
        String nextEffect = montage(effect);
        //替换记录值，并运行
        continueAction(getFrom(), nextEffect.replace("SRecordS", value), getTo());
    }

    @Override
    public String toString() {
        return "";
    }
}
