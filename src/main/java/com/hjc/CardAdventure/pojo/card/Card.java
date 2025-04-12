package com.hjc.CardAdventure.pojo.card;

import com.almasb.fxgl.entity.Entity;
import com.hjc.CardAdventure.Global;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String cardEffect : cardEffects) {
            //解析卡牌效果
            Effect effect = Effect.parse(player, cardEffect, null);
            //解析失败
            if (effect == null) break;
            //获取卡牌效果描述
            String description = effect.toString();
            if (!description.isEmpty()) {
                sb.append(effect);
                sb.append("；");
            }
        }

        return sb.toString();
    }

    //卡牌使用
    public void action() {
        //解析卡牌效果
        ArrayList<Effect> effects = new ArrayList<>();
        for (String cardEffect : cardEffects) {
            effects.add(Effect.parse(player, cardEffect, null));
        }
        //将所有效果添加至效果序列
        BattleInformation.insetEffect(effects);
        //执行效果序列
        BattleInformation.effectExecution();
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

        BattleEntity.target.getComponent(TargetComponent.class).update();
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
    }
}
