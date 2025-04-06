package com.hjc.CardAdventure.pojo.player;

import com.hjc.CardAdventure.Global;
import com.hjc.CardAdventure.Utils.AttributeUtils;
import com.hjc.CardAdventure.component.role.PlayerComponent;
import com.hjc.CardAdventure.effect.basic.DrawEffect;
import com.hjc.CardAdventure.effect.basic.ShuffleProduce;
import com.hjc.CardAdventure.entity.BattleEntity;
import com.hjc.CardAdventure.pojo.BattleInformation;
import com.hjc.CardAdventure.pojo.Role;
import com.hjc.CardAdventure.pojo.attribute.Attribute;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.hjc.CardAdventure.Global.PLAYER.*;
import static com.hjc.CardAdventure.Global.CARD_USE.*;

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
        //回合开始阶段
        isPlayer = true;
        BattleInformation.nowAction = this;

        //刷新本回合出牌数
        BattleInformation.EFFECTS.add(new ShuffleProduce(this, ""));
        //抽牌阶段
        int drawNum = AttributeUtils.mathDraw();
        BattleInformation.EFFECTS.add(new DrawEffect(this, String.valueOf(drawNum)));

        //可选择手牌
        selectable = true;
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
        return Global.PLAYER.armor;
    }

    @Override
    public void setRoleArmor(int armor) {
        Global.PLAYER.armor = armor;
    }

    @Override
    public Attribute getRoleAttribute() {
        return getAttribute();
    }

    //更新玩家
    public void update() {
        BattleEntity.playerBattle.getComponent(PlayerComponent.class).update();
    }
}
