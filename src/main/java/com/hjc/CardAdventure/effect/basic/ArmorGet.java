package com.hjc.CardAdventure.effect.basic;

import com.hjc.CardAdventure.Utils.AttributeUtils;
import com.hjc.CardAdventure.Utils.EffectUtils;
import com.hjc.CardAdventure.Utils.EntityUtils;
import com.hjc.CardAdventure.effect.Effect;
import com.hjc.CardAdventure.effect.target.TargetedEffect;
import com.hjc.CardAdventure.pojo.BattleInformation;
import com.hjc.CardAdventure.pojo.Role;

import java.util.ArrayList;

//获得护盾
public class ArmorGet extends TargetedEffect {
    public ArmorGet(Role from, String effect, Role to) {
        super(from, effect, to);
    }

    @Override
    public void action() {
        if (getFrom() == null || getTo() == null) return;
        //获取操作序列
        ArrayList<String> effect = Effect.cutEffect(getEffect());
        //获取数值
        int value = Effect.changeToInt(Effect.getFirst(effect));
        //获取目标
        Role to = getTo();
        //计算获得的护盾值
        int realValue = AttributeUtils.mathArmor(to, value);
        to.setRoleArmor(to.getRoleArmor() + realValue);
        //播放动画
        EffectUtils.getArmor(getTo());
        //暂停播放动画
        BattleInformation.insetEffect(new PauseEffect(null,"2"));
        //继续执行下面的效果
        Effect.continueAction(getFrom(), montage(effect), getTo());
    }

    @Override
    public String toString() {
        //获得操作序列
        ArrayList<String> effect = Effect.cutEffect(getEffect());
        //获取数值
        int value = Effect.changeToInt(Effect.getFirst(effect));
        //计算真实数值
        int realValue = AttributeUtils.mathArmor(getTo(), value);
        String next = Effect.getNextEffectString(getFrom(), montage(effect), null, ",");
        return "获得" + realValue + "点护盾" + next;
    }
}
