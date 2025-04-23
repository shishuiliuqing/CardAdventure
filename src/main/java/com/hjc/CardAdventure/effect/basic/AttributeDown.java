package com.hjc.CardAdventure.effect.basic;

import com.hjc.CardAdventure.Global;
import com.hjc.CardAdventure.Utils.EffectUtils;
import com.hjc.CardAdventure.component.battle.ActionComponent;
import com.hjc.CardAdventure.component.battle.AttributeComponent;
import com.hjc.CardAdventure.effect.effectType.Negative;
import com.hjc.CardAdventure.effect.target.TargetedEffect;
import com.hjc.CardAdventure.entity.BattleEntity;
import com.hjc.CardAdventure.pojo.BattleInformation;
import com.hjc.CardAdventure.pojo.Role;
import com.hjc.CardAdventure.pojo.attribute.Attribute;
import com.hjc.CardAdventure.pojo.attribute.DownType;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class AttributeDown extends TargetedEffect implements Negative {
    public AttributeDown(Role from, String effect, Role to) {
        super(from, effect, to);
    }

    @Override
    public void action() {
        if (getFrom() == null || getTo() == null) return;
        //获取效果序列
        ArrayList<String> effect = cutEffect(getEffect());
        //提升属性类型
        DownType downType = DownType.getInstance(getFirst(effect));
        //获取数值
        int value = changeToInt(getFirst(effect));
        attributeDown(getTo(), downType, value);
        continueAction(getFrom(), montage(effect), getTo());
    }

    @Override
    public String toString() {
        //获取效果序列
        ArrayList<String> effect = cutEffect(getEffect());
        //提升属性类型
        DownType downType = DownType.getInstance(getFirst(effect));
        //获取数值
        int value = changeToInt(getFirst(effect));
        String next = getNextEffectString(getFrom(), montage(effect), null, ",");
        return "失去" + value + "点" + switch (downType) {
            case POWER_DOWN -> "力量";
            case INTELLIGENCE_DOWN -> "智力";
            case SPEED_DOWN -> "速度";
            case PURITY_DOWN -> "纯洁";
            case AGILITY_DOWN -> "敏捷";
            case DEFENSE_DOWN -> "防御";
            case ALL_DOWN -> "全属性";
        } + next;
    }

    public static void attributeDown(Role role, DownType downType, int value) {
        if (value == 0) return;
        Attribute attribute = role.getRoleAttribute();
        switch (downType) {
            case POWER_DOWN -> {
                attribute.setPower(Math.max(0, attribute.getPower() - value));
                EffectUtils.textBigger("力量下降⬇", role, Color.RED);
            }
            case INTELLIGENCE_DOWN -> {
                attribute.setIntelligence(Math.max(0, attribute.getIntelligence() - value));
                EffectUtils.textBigger("智力下降⬇", role, Color.BLUE);
            }
            case DEFENSE_DOWN -> {
                attribute.setDefense(Math.max(0, attribute.getDefense() - value));
                EffectUtils.textBigger("防御下降⬇", role, Color.ORANGE);
            }
            case AGILITY_DOWN -> {
                attribute.setAgility(Math.max(0, attribute.getAgility() - value));
                EffectUtils.textBigger("敏捷下降⬇", role, Color.GREEN);
            }
            case PURITY_DOWN -> {
                attribute.setPurity(Math.max(0, attribute.getPurity() - value));
                EffectUtils.textBigger("纯洁下降⬇", role, Color.PURPLE);
            }
            case SPEED_DOWN -> {
                attribute.setSpeed((Math.max(0, attribute.getSpeed() - value)));
                EffectUtils.textBigger("速度下降⬇", role, Color.WHITE);
                BattleInformation.sort(BattleInformation.NEXT_ACTION);
                BattleEntity.actionBox.getComponent(ActionComponent.class).update();
            }
            case ALL_DOWN -> {
                attribute.setPower(Math.max(0, attribute.getPower() - value));
                attribute.setIntelligence(Math.max(0, attribute.getIntelligence() - value));
                attribute.setDefense(Math.max(0, attribute.getDefense() - value));
                attribute.setAgility(Math.max(0, attribute.getAgility() - value));
                attribute.setPurity(Math.max(0, attribute.getPurity() - value));
                attribute.setSpeed((Math.max(0, attribute.getSpeed() - value)));
                EffectUtils.textBigger("全属性下降⬇", role, Color.YELLOW);
                BattleInformation.sort(BattleInformation.NEXT_ACTION);
                BattleEntity.actionBox.getComponent(ActionComponent.class).update();
            }
        }

        if (role == Global.PLAYER.player) {
            BattleEntity.attribute.getComponent(AttributeComponent.class).update();
        }
    }

    @Override
    public boolean isNegative() {
        return true;
    }
}
