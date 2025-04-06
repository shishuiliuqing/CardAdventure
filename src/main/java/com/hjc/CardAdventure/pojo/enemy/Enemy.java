package com.hjc.CardAdventure.pojo.enemy;

import com.almasb.fxgl.entity.Entity;
import com.hjc.CardAdventure.component.role.EnemyComponent;
import com.hjc.CardAdventure.effect.Effect;
import com.hjc.CardAdventure.entity.BattleEntity;
import com.hjc.CardAdventure.pojo.Role;
import com.hjc.CardAdventure.pojo.attribute.Attribute;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

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
    //敌人意图
    private ArrayList<Intention> intentions;
    //敌人当前意图
    private Intention nowIntention;


    @Override
    public void action() {

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
    public void phyHurt(int value) {

        //伤害减护盾
        value -= getRoleArmor();

        if (value > 0) {
            //护盾值变为0
            setRoleArmor(0);
            //血量减少
            lossBlood(value);
        } else {
            //护盾减少
            int x = getRoleArmor() + value;
            setRoleArmor(value * (-1));
            update();
        }
    }

    @Override
    public void lossBlood(int value) {
        this.blood -= value;
        if (this.blood < 0) this.blood = 0;
        update();
    }

    @Override
    public int getRoleArmor() {
        return getArmor();
    }

    @Override
    public void setRoleArmor(int armor) {
        setArmor(armor);
    }

    @Override
    public Attribute getRoleAttribute() {
        return getAttribute();
    }

    //更新敌人
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
        return getLocation() + "";
        //return name + "   " + blood + "/" + maxBlood + Effect.NEW_LINE + attribute.displayAttribute() + Effect.NEW_LINE;
    }
}
