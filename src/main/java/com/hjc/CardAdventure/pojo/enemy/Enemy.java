package com.hjc.CardAdventure.pojo.enemy;

import com.almasb.fxgl.entity.Entity;
import com.hjc.CardAdventure.Utils.BattleUtils;
import com.hjc.CardAdventure.Utils.EffectUtils;
import com.hjc.CardAdventure.component.role.EnemyComponent;
import com.hjc.CardAdventure.effect.Effect;
import com.hjc.CardAdventure.effect.basic.DeathEffect;
import com.hjc.CardAdventure.effect.basic.PauseEffect;
import com.hjc.CardAdventure.effect.opportunity.Opportunity;
import com.hjc.CardAdventure.effect.opportunity.OpportunityType;
import com.hjc.CardAdventure.entity.BattleEntity;
import com.hjc.CardAdventure.pojo.BattleInformation;
import com.hjc.CardAdventure.pojo.HurtType;
import com.hjc.CardAdventure.pojo.Role;
import com.hjc.CardAdventure.pojo.attribute.Attribute;
import javafx.scene.paint.Color;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Random;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Enemy implements Role {
    //怪物编号
    private int enemyNum;
    //名字
    private String name;
    //生命
    private int blood;
    //最大生命值
    private int maxBlood;
    //属性
    private Attribute attribute;
    //角色类型
    private EnemyType enemyType;
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

    //战斗用属性
    //怪物所在位置
    private int location;
    //护甲
    private int armor;
    //护盾是否消失
    private boolean armorDisappear = true;
    //敌人意图
    private ArrayList<Intention> intentions;
    //敌人当前意图
    private Intention nowIntention;
    //怪物进场效果
    private ArrayList<String> entryEffects;

    //怪物时机效果
    private ArrayList<Opportunity> opportunities = new ArrayList<>();

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
        //触发自身回合效果
        Opportunity.launchOpportunity(this, OpportunityType.OWN_ROUND_BEGIN);

        //解析并执行当前意图效果
        ArrayList<Effect> effects = new ArrayList<>();
        for (String effect : nowIntention.getEffects()) {
            Effect now = Effect.parse(this, effect, null);
            if (now == null) continue;
            effects.add(now);
        }
        BattleInformation.EFFECTS.addAll(effects);

        //行动结束
        //BattleInformation.EFFECTS.add(new PauseEffect(null, "999"));
        BattleUtils.actionOver();
    }

    @Override
    public void initEntryEffects() {
        //随机添加-3 - 3 滴血
        Random r = new Random();
        int value = r.nextInt(7) - 3;
        maxBlood += value;
        blood += value;
        //update();
        //初始化进场效果
        if (entryEffects == null) return;
        ArrayList<Effect> effects = new ArrayList<>();
        for (String entryEffect : entryEffects) {
            Effect effect = Effect.parse(this, entryEffect, null);
            if (effect != null) effects.add(effect);
        }
        BattleInformation.insetEffect(effects);
        BattleInformation.effectExecution();
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
            //返回true
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
        Opportunity.launchOpportunity(this, OpportunityType.SPECIAL_HURT);
    }

    @Override
    public void lossBlood(int value) {
        this.blood -= value;
        if (this.blood < 0) this.blood = 0;
        EffectUtils.displayValue(value, this, Color.RED, "-");
        BattleUtils.pause(0.3);
        update();
        //触发失去生命效果
        Opportunity.launchOpportunity(this, OpportunityType.LOSS_BLOOD);
        if (this.blood == 0) BattleInformation.insetEffect(new DeathEffect(this, ""));
    }

    @Override
    public void restore(int value) {
        blood += value;
        if (blood > maxBlood) blood = maxBlood;
        //如果战斗，播放回血文字
        if (BattleInformation.isBattle) {
            EffectUtils.displayEffect("restore", 27, 1, 0.8, this, -10, -90);
            EffectUtils.displayValue(value, this, Color.GREEN, "+");
            BattleInformation.insetEffect(new PauseEffect(null, "5"));
        }
        //更新
        update();
    }

    @Override
    public int getRoleArmor() {
        return getArmor();
    }

    @Override
    public void setRoleArmor(int armor) {
        if (armor == 0 && this.armor != 0) {
            EffectUtils.amplifyAndDisappear(this, 30, -40, "armorBreak");
        }
        setArmor(armor);
        update();
    }

    @Override
    public void setRoleArmorDisappear(boolean armorDisappear) {
        this.armorDisappear = armorDisappear;
    }

    @Override
    public Attribute getRoleAttribute() {
        return getAttribute();
    }

    @Override
    public ArrayList<Opportunity> getRoleOpportunities() {
        return getOpportunities();
    }

    @Override
    public void die() {
        int index = getIndex();
        //怪物序列移除该敌人
        BattleInformation.ENEMIES.remove(this);
        //播放死亡动画
        BattleEntity.enemies[index].getComponent(EnemyComponent.class).deathAnimation();
        //删除该实体
        BattleEntity.enemies[getIndex()] = null;
    }

    //更新敌人
    @Override
    public void update() {
        int index = getIndex();
        Entity entity = BattleEntity.enemies[index];
        entity.getComponent(EnemyComponent.class).update();
    }

    //获取敌人实体索引
    public int getIndex() {
        for (int i = 0; i < BattleEntity.enemyGenerateOrder.length; i++) {
            if (BattleEntity.enemyGenerateOrder[i] == getLocation()) return i;
        }
        return -1;
    }

    @Override
    public String toString() {
        return name + "   " + blood + "/" + maxBlood +
                Effect.NEW_LINE +
                attribute.displayAttribute() +
                Effect.NEW_LINE +
                IntentionType.intentionParse(this) +
                Effect.NEW_LINE +
                "该敌人拥有效果" +
                Effect.NEW_LINE +
                Opportunity.displayOpportunities(opportunities) +
                Opportunity.detailOpportunities(this);
    }
}
