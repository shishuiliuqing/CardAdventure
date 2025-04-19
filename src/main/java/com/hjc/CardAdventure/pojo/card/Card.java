package com.hjc.CardAdventure.pojo.card;

import com.almasb.fxgl.entity.Entity;
import com.hjc.CardAdventure.Global;
import com.hjc.CardAdventure.component.card.CardComponent;
import com.hjc.CardAdventure.component.card.TargetComponent;
import com.hjc.CardAdventure.component.role.EnemyComponent;
import com.hjc.CardAdventure.component.role.PlayerComponent;
import com.hjc.CardAdventure.effect.Effect;
import com.hjc.CardAdventure.entity.BattleEntity;
import com.hjc.CardAdventure.pojo.BattleInformation;
import com.hjc.CardAdventure.pojo.Role;
import com.hjc.CardAdventure.pojo.attribute.Attribute;
import com.hjc.CardAdventure.pojo.enemy.Enemy;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Objects;

import static com.hjc.CardAdventure.Global.PLAYER.player;
import static com.hjc.CardAdventure.Global.CARD_USE.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Card {
    //卡牌英文地址
    private String cardAddress;
    //卡牌名字
    private String cardName;
    //卡牌属性
    private Attribute attribute;
    //卡牌品质
    private CardQuality cardQuality;
    //卡牌颜色
    private String colorS;
    //卡牌效果序列
    private ArrayList<String> cardEffects;
    //卡牌指定目标类型
    private TargetType targetType;
    //卡牌有效效果数量（用于复制）
    private int effectiveEffect;
    //卡牌描述
    private String description;
    //卡牌详情
    private String detail;

    @Override
    public String toString() {
        if (description == null) return "";

        String[] strings = description.split(";");
        //解析部分指定效果
        for (int i = 0; i < strings.length; i++) {
            String s = strings[i];
            if (s.contains("EFFECT")) {
                int index = Integer.parseInt(s.substring(6));
                Effect effect = Effect.parse(player, this.cardEffects.get(index), null);
                strings[i] = effect.toString();
            }
        }

        //拼接
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strings.length; i++) {
            String s = strings[i];
            sb.append(s);
            if (i != strings.length - 1) {
                sb.append(";");
            }
        }
        //返回
        return sb.toString();
    }

    //卡牌详情
    private String detail() {
        if (detail == null || detail.isEmpty()) return "";
        String[] strings = detail.split(";");
        //解析每个特殊效果
        for (int i = 0; i < strings.length; i++) {
            String s = strings[i];
            strings[i] = strings[i] + "\n" + Effect.effectDetail(s);
        }

        //拼接
        StringBuilder sb = new StringBuilder();
        for (String string : strings) {
            sb.append(string).append(Effect.NEW_LINE);
        }

        return sb.toString();
    }

    //卡牌完整描述
    public String cardDescription() {
        return cardName +
                Effect.NEW_LINE +
                "该牌打出需要属性\n" +
                attribute.displayAttribute() +
                Effect.NEW_LINE +
                this +
                Effect.NEW_LINE +
                detail() +
                "目标指定类型：" + targetType.getTargetString();
    }

    //卡牌使用
    public void action() {
        //解析卡牌效果
        ArrayList<Effect> effects = new ArrayList<>();
        for (String cardEffect : cardEffects) {
            effects.add(Effect.parse(player, cardEffect, null));
        }
        //将所有效果添加至效果序列
        BattleInformation.EFFECTS.addAll(effects);
        //执行效果序列
        //BattleInformation.effectExecution();
    }

    //当卡牌被选择时调用
    public void isSelected() {
        //群体指定
        if (targetType == TargetType.ALL) {
            isAll = true;
            for (Entity enemy : BattleEntity.enemies) {
                if (enemy == null) continue;
                enemy.getComponent(EnemyComponent.class).update(true);
            }
            //随机指定
        } else if (targetType == TargetType.RANDOMIZED) {
            isRam = true;
            //自身指定
        } else if (targetType == TargetType.OWN) {
            target = player;
            BattleEntity.playerBattle.getComponent(PlayerComponent.class).update(true);
            //单体指定
        } else {
            needTarget = true;
            Global.CARD_USE.target = BattleInformation.ENEMIES.get(0);
            int index = BattleInformation.ENEMIES.get(0).getIndex();
            BattleEntity.enemies[index].getComponent(EnemyComponent.class).update(true);
        }

        //目标指定更新
        BattleEntity.target.getComponent(TargetComponent.class).update();
        //手牌区更新
        for (CardComponent handCard : CardComponent.HAND_CARDS) {
            handCard.update();
        }
    }

    //当卡牌被放下时使用
    public void putDown() {
        //群体指定
        if (targetType == TargetType.ALL) {
            isAll = false;
            for (Entity enemy : BattleEntity.enemies) {
                if (enemy == null) continue;
                enemy.getComponent(EnemyComponent.class).update(false);
            }
            //随机指定
        } else if (targetType == TargetType.RANDOMIZED) {
            isRam = false;
            //其他
        } else {
            Role target = Global.CARD_USE.target;
            needTarget = false;
            if (targetType == TargetType.INDIVIDUAL) {
                int index = ((Enemy) target).getIndex();
                if (index != -1 && BattleEntity.enemies[index] != null)
                    BattleEntity.enemies[index].getComponent(EnemyComponent.class).update(false);
            } else {
                BattleEntity.playerBattle.getComponent(PlayerComponent.class).update(false);
            }
        }

        Global.CARD_USE.target = null;
        BattleEntity.target.getComponent(TargetComponent.class).update();
        //手牌区更新
        for (CardComponent handCard : CardComponent.HAND_CARDS) {
            handCard.update();
        }
    }
}
