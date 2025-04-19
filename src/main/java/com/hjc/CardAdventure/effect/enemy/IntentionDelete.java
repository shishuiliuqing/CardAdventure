package com.hjc.CardAdventure.effect.enemy;

import com.hjc.CardAdventure.Global;
import com.hjc.CardAdventure.effect.Effect;
import com.hjc.CardAdventure.effect.opportunity.Opportunity;
import com.hjc.CardAdventure.effect.opportunity.OpportunityType;
import com.hjc.CardAdventure.pojo.Role;
import com.hjc.CardAdventure.pojo.enemy.Enemy;

//删除当前意图
public class IntentionDelete extends Effect {
    public IntentionDelete(Role from, String effect) {
        super(from, effect);
    }

    @Override
    public void action() {
        if (getFrom() == null) return;
        Enemy enemy = (Enemy) getFrom();
        //敌人回合结束，若为攻击意图，触发攻击后效果
        if (Global.CARD_USE.isAttack) {
            Opportunity.launchOpportunity(enemy, OpportunityType.PHY_ATTACK_END);
        }
        //删除意图
        enemy.setNowIntention(null);
        enemy.update();
    }

    @Override
    public String toString() {
        return "意图删除";
    }
}
