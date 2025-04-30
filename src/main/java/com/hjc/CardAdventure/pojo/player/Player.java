package com.hjc.CardAdventure.pojo.player;

import com.hjc.CardAdventure.Global;
import com.hjc.CardAdventure.Utils.AttributeUtils;
import com.hjc.CardAdventure.Utils.BattleUtils;
import com.hjc.CardAdventure.Utils.EffectUtils;
import com.hjc.CardAdventure.component.information.BloodComponent;
import com.hjc.CardAdventure.component.role.PlayerComponent;
import com.hjc.CardAdventure.effect.Effect;
import com.hjc.CardAdventure.effect.basic.PauseEffect;
import com.hjc.CardAdventure.effect.opportunity.Opportunity;
import com.hjc.CardAdventure.effect.opportunity.OpportunityType;
import com.hjc.CardAdventure.effect.player.DrawEffect;
import com.hjc.CardAdventure.effect.player.ShuffleProduce;
import com.hjc.CardAdventure.entity.BattleEntity;
import com.hjc.CardAdventure.entity.InformationEntity;
import com.hjc.CardAdventure.pojo.BattleInformation;
import com.hjc.CardAdventure.pojo.HurtType;
import com.hjc.CardAdventure.pojo.Role;
import com.hjc.CardAdventure.pojo.attribute.Attribute;
import com.hjc.CardAdventure.pojo.card.Card;
import javafx.scene.paint.Color;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

import static com.hjc.CardAdventure.Global.CARD_USE.*;
import static com.hjc.CardAdventure.Global.PLAYER.*;
import static com.hjc.CardAdventure.pojo.BattleInformation.effectExecution;
import static com.hjc.CardAdventure.pojo.BattleInformation.insetEffect;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Player implements Role {
    //名字
    private String name;
    //生命
    private int blood;
    //最大生命值
    private int maxBlood;
    //属性
    private Attribute attribute;
    //人物经验
    private int experience;


    //角色类型
    private PlayerType playerType;
    //角色颜色
    private String colorS;
    //角色图片
    private String img;
    //角色图片宽度
    private double width;
    //角色图片高度
    private double height;
    //角色图片x偏移量
    private double x;
    //角色图片y偏移量
    private double y;

    @Override
    public void action() {
        //当前行动对象
        BattleInformation.nowAction = this;

        if (getRoleArmor() != 0 && armorDisappear) {
            //失去所有护盾
            setRoleArmor(0);
            //暂缓0.5秒
            BattleInformation.EFFECTS.add(new PauseEffect(null, "5"));
        }

        //触发自身回合开始效果
        Opportunity.launchOpportunity(player, OpportunityType.OWN_ROUND_BEGIN);

        //回合开始阶段
        isPlayer = true;

        //刷新本回合出牌数
        BattleInformation.EFFECTS.add(new ShuffleProduce(this, ""));
        //抽牌阶段
        int drawNum = AttributeUtils.mathDraw();
        BattleInformation.EFFECTS.add(new DrawEffect(this, String.valueOf(drawNum)));

        //可选择手牌
        selectable = true;
    }

    @Override
    public void initEntryEffects() {
        //卡牌的进场效果
        for (Card card : cards) {
            if (card.getEntryEffects() == null) continue;
            //解析卡牌效果
            ArrayList<Effect> effects = new ArrayList<>();
            for (String entryEffect : card.getEntryEffects()) {
                Effect e = Effect.parse(player, entryEffect, null);
                if (e != null) effects.add(e);
            }
            //插入执行该效果
            insetEffect(effects);
        }
        effectExecution();
    }

    @Override
    public String getRoleName() {
        return getName();
    }

    @Override
    public int getRoleBlood() {
        return getBlood();
    }

    @Override
    public int getRoleMaxBlood() {
        return getMaxBlood();
    }

    @Override
    public boolean phyHurt(int value) {
        //伤害减护盾
        value -= getRoleArmor();

        if (value > 0) {
            //护盾值变为0
            setRoleArmor(0);
            //血量减少
            lossBlood(value);
            return true;
        } else {
            //护盾减少
            int x = getRoleArmor() + value;
            setRoleArmor(value * (-1));
            EffectUtils.displayValue(x, this, Color.valueOf("#15FEFC"), "-");
            update();
        }
        return false;

    }

    @Override
    public void specialHurt(HurtType hurtType, int value) {
        Role.specialHurtEffect(this, hurtType);
        lossBlood(value);
        //触发特殊伤害效果
        Opportunity.launchOpportunity(this, OpportunityType.SPECIAL_HURT);
    }

    @Override
    public void lossBlood(int value) {
        this.blood -= value;
        if (this.blood < 0) this.blood = 0;
        //播放文字
        EffectUtils.displayValue(value, this, Color.RED, "-");
        //暂停0.3秒
        BattleUtils.pause(0.3);
        update();
        //触发失去生命效果
        Opportunity.launchOpportunity(this, OpportunityType.LOSS_BLOOD);
    }

    @Override
    public void restore(int value) {
        blood += value;
        if (blood > maxBlood) blood = maxBlood;
        //如果战斗，播放回血文字
        if (BattleInformation.isBattle) {
            //播放动画
            EffectUtils.displayEffect("restore", 27, 1, 0.8, this, -10, -90);
            EffectUtils.displayValue(value, this, Color.GREEN, "+");
            insetEffect(new PauseEffect(null, "5"));
        }
        //更新
        update();
    }

    @Override
    public int getRoleArmor() {
        return Global.PLAYER.armor;
    }

    @Override
    public void setRoleArmor(int armor) {
        if (armor == 0 && Global.PLAYER.armor != 0) {
            EffectUtils.amplifyAndDisappear(this, 30, -40, "armorBreak");
        }
        Global.PLAYER.armor = armor;
        update();
    }

    @Override
    public void setRoleArmorDisappear(boolean armorDisappear) {
        Global.PLAYER.armorDisappear = armorDisappear;
    }

    @Override
    public Attribute getRoleAttribute() {
        return getAttribute();
    }

    @Override
    public ArrayList<Opportunity> getRoleOpportunities() {
        return opportunities;
    }

    @Override
    public void die() {

    }

    //更新玩家
    @Override
    public void update() {
        BattleEntity.playerBattle.getComponent(PlayerComponent.class).update();
        InformationEntity.playerBlood.getComponent(BloodComponent.class).update();
    }

    @Override
    public String toString() {
        return "当前角色拥有效果" + Effect.NEW_LINE + Opportunity.displayOpportunities(getRoleOpportunities()) + Opportunity.detailOpportunities(player);
    }
}
