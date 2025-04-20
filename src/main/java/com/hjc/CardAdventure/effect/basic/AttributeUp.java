package com.hjc.CardAdventure.effect.basic;

import com.hjc.CardAdventure.Global;
import com.hjc.CardAdventure.Utils.EffectUtils;
import com.hjc.CardAdventure.component.battle.ActionComponent;
import com.hjc.CardAdventure.component.battle.AttributeComponent;
import com.hjc.CardAdventure.effect.Effect;
import com.hjc.CardAdventure.effect.effectType.Negative;
import com.hjc.CardAdventure.effect.target.TargetedEffect;
import com.hjc.CardAdventure.entity.BattleEntity;
import com.hjc.CardAdventure.pojo.BattleInformation;
import com.hjc.CardAdventure.pojo.Role;
import com.hjc.CardAdventure.pojo.attribute.Attribute;
import com.hjc.CardAdventure.pojo.attribute.UpType;
import javafx.scene.paint.Color;

import java.util.ArrayList;

//属性上升
public class AttributeUp extends TargetedEffect {
    public AttributeUp(Role from, String effect, Role to) {
        super(from, effect, to);
    }

    @Override
    public void action() {
        if (getFrom() == null || getTo() == null) return;
        //获取效果序列
        ArrayList<String> effect = cutEffect(getEffect());
        //提升属性类型
        UpType upType = UpType.getInstance(getFirst(effect));
        //获取数值
        int value = changeToInt(getFirst(effect));
        //增加数值
        attributeUp(getTo(), upType, value);

        continueAction(getFrom(), montage(effect), getTo());
    }

    @Override
    public String toString() {
        //获取效果序列
        ArrayList<String> effect = cutEffect(getEffect());
        //提升属性类型
        UpType upType = UpType.getInstance(getFirst(effect));
        //获取数值
        int value = changeToInt(getFirst(effect));
        String next = getNextEffectString(getFrom(), montage(effect), null, ",");
        return "获得" + value + "点" + switch (upType) {
            case POWER_UP -> "力量";
            case INTELLIGENCE_UP -> "智力";
            case SPEED_UP -> "速度";
            case PURITY_UP -> "纯洁";
            case AGILITY_UP -> "敏捷";
            case DEFENSE_UP -> "防御";
            case ALL_UP -> "全属性";
        } + next;
    }

    //提升目标属性
    public static void attributeUp(Role role, UpType upType, int value) {
        Attribute attribute = role.getRoleAttribute();
        switch (upType) {
            case POWER_UP -> {
                attribute.setPower(Math.min(999, value + attribute.getPower()));
                EffectUtils.textBigger("力量上升⬆", role, Color.RED);
            }
            case INTELLIGENCE_UP -> {
                attribute.setIntelligence(Math.min(999, value + attribute.getIntelligence()));
                EffectUtils.textBigger("智力上升⬆", role, Color.BLUE);
            }
            case DEFENSE_UP -> {
                attribute.setDefense(Math.min(999, value + attribute.getDefense()));
                EffectUtils.textBigger("防御上升⬆", role, Color.ORANGE);
            }
            case AGILITY_UP -> {
                attribute.setAgility(Math.min(999, value + attribute.getAgility()));
                EffectUtils.textBigger("敏捷上升⬆", role, Color.GREEN);
            }
            case PURITY_UP -> {
                attribute.setPurity(Math.min(999, value + attribute.getPurity()));
                EffectUtils.textBigger("纯洁上升⬆", role, Color.PURPLE);
            }
            case SPEED_UP -> {
                attribute.setSpeed((Math.min(999, value + attribute.getSpeed())));
                EffectUtils.textBigger("速度上升⬆", role, Color.WHITE);
                BattleInformation.sort(BattleInformation.NEXT_ACTION);
                BattleEntity.actionBox.getComponent(ActionComponent.class).update();
            }
            case ALL_UP -> {
                attribute.setPower(Math.min(999, value + attribute.getPower()));
                attribute.setIntelligence(Math.min(999, value + attribute.getIntelligence()));
                attribute.setDefense(Math.min(999, value + attribute.getDefense()));
                attribute.setAgility(Math.min(999, value + attribute.getAgility()));
                attribute.setPurity(Math.min(999, value + attribute.getPurity()));
                attribute.setSpeed((Math.min(999, value + attribute.getSpeed())));
                EffectUtils.textBigger("全属性上升⬆", role, Color.YELLOW);
                BattleInformation.sort(BattleInformation.NEXT_ACTION);
                BattleEntity.actionBox.getComponent(ActionComponent.class).update();
            }
        }

        if (role == Global.PLAYER.player) {
            BattleEntity.attribute.getComponent(AttributeComponent.class).update();
        }
    }
}
