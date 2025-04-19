package com.hjc.CardAdventure.pojo;

import com.hjc.CardAdventure.Utils.EffectUtils;
import com.hjc.CardAdventure.Utils.EntityUtils;
import com.hjc.CardAdventure.effect.basic.PauseEffect;
import com.hjc.CardAdventure.effect.opportunity.Opportunity;
import com.hjc.CardAdventure.pojo.attribute.Attribute;

import java.util.ArrayList;

public interface Role {
    //角色行动
    void action();

    //获取角色名字
    String getRoleName();

    //获取生命值
    int getRoleBlood();

    //获取最大生命值
    int getRoleMaxBlood();

    //受到物理伤害
    void phyHurt(int value);

    //特殊受伤效果
    void specialHurt(HurtType hurtType, int value);

    //失去生命
    void lossBlood(int value);

    //获取角色护盾
    int getRoleArmor();

    //设置角色护盾
    void setRoleArmor(int armor);

    //获取属性
    Attribute getRoleAttribute();

    //获取角色的时机效果
    ArrayList<Opportunity> getRoleOpportunities();

    //死亡
    void die();

    //特殊伤害受伤特效播放
    static void specialHurtEffect(Role role, HurtType hurtType) {
        if (hurtType == HurtType.FIRE) {
            BattleInformation.insetEffect(new PauseEffect(null, "10"));
            EffectUtils.displayEffect("fireDamage", 23, 1, 1, role, 40, 0);
        }
        BattleInformation.effectExecution();
    }

}
