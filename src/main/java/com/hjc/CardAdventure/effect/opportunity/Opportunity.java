package com.hjc.CardAdventure.effect.opportunity;

import com.hjc.CardAdventure.Utils.EffectUtils;
import com.hjc.CardAdventure.effect.Effect;
import com.hjc.CardAdventure.pojo.BattleInformation;
import com.hjc.CardAdventure.pojo.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Opportunity {
    //效果名称
    private String name;
    //触发时机
    private OpportunityType opportunityType;
    //可执行回合
    private int rounds;
    //可触发次数
    private int num;
    //层数
    private int layer;
    //是否可叠加
    private boolean stackable;
    //触发执行效果
    private String effect;
    //时机结束触发效果
    private String endEffect;

    //向某个角色添加某个时机效果
    public static void addOpportunity(Role role, Opportunity opportunity) {
        ArrayList<Opportunity> opportunities = role.getRoleOpportunities();
        for (Opportunity o : opportunities) {
            //效果名字相同
            if (o.getName().equals(opportunity.getName())) {
                //可叠加
                if (opportunity.stackable) {
                    o.setLayer(o.getLayer() + opportunity.getLayer());
                    return;
                }

                //永久生效效果
                if (o.getNum() == 999) return;
                //仅添加次数
                o.setNum(o.getNum() + opportunity.getNum());
                return;
            }
        }

        //未有此效果，直接添加
        opportunities.add(opportunity);
    }

    //某个时机到来，触发时机效果
    public static void launchOpportunity(Role role, OpportunityType opportunityType) {
        ArrayList<Opportunity> opportunities = role.getRoleOpportunities();
        int n = 0;
        //满足发动时机的，发动效果
        while (n < opportunities.size()) {
            Opportunity opportunity = opportunities.get(n);
            if (opportunity.opportunityType == opportunityType) {
                //发动次数-1
                if (opportunity.num != 999) opportunity.num--;
                //执行效果
                if (opportunity.effect != null) {
                    //将数值替换为层数
                    String effect = opportunity.effect.replace("SValueS", String.valueOf(opportunity.getLayer()));
                    //解析效果
                    Effect result = Effect.parse(role, effect, null);
                    //执行效果
                    if (result != null) BattleInformation.insetEffect(result);
                }


                //发动次数为0，执行结束效果
                if (opportunity.num == 0) {
                    if (opportunity.endEffect != null) {
                        String effect = opportunity.endEffect.replace("SValueS", String.valueOf(opportunity.getLayer()));
                        //解析效果
                        Effect result = Effect.parse(role, effect, null);
                        //执行效果
                        if (result != null) BattleInformation.insetEffect(result);
                        //效果结束文字展示
                        EffectUtils.textEffectEnd(opportunity.getName(), role);
                    }
                    //删除该时机
                    opportunities.remove(n);
                    continue;
                }
            }
            n++;
        }
    }

    //回合时机触发器，用于检查回合开始效果
    public static void roundLaunch(Role role) {
        //触发该角色所有回合开始效果
        launchOpportunity(role, OpportunityType.ROUND_BEGIN);

        int n = 0;
        ArrayList<Opportunity> opportunities = role.getRoleOpportunities();
        while (n < opportunities.size()) {
            Opportunity opportunity = opportunities.get(n);
            //与回合无关效果
            if (opportunity.rounds == 0) {
                n++;
                continue;
            }
            //与回合有关效果
            opportunity.rounds--;
            //效果到期，触发结束效果
            if (opportunity.rounds == 0) {
                //移除该时机效果
                opportunities.remove(opportunity);
                //触发结束效果
                if (opportunity.endEffect != null) {
                    String effect = opportunity.endEffect.replace("SValueS", String.valueOf(opportunity.getLayer()));
                    //解析效果
                    Effect result = Effect.parse(role, effect, null);
                    //执行效果
                    if (result != null) BattleInformation.insetEffect(result);
                    //效果结束文字展示
                    EffectUtils.textEffectEnd(opportunity.getName(), role);
                }
                continue;
            }

            n++;
        }
    }
}
