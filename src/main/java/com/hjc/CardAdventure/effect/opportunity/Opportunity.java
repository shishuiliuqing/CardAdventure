package com.hjc.CardAdventure.effect.opportunity;

import com.hjc.CardAdventure.Utils.EffectUtils;
import com.hjc.CardAdventure.effect.Effect;
import com.hjc.CardAdventure.pojo.BattleInformation;
import com.hjc.CardAdventure.pojo.Role;
import javafx.scene.paint.Color;
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
    //时机效果状态
    private OpportunityStatus opportunityStatus;
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

    //特殊效果
    //private static final String SPECIAL_OPPORTUNITY = "寻回/抽";

    //向某个角色添加某个时机效果
    public static void addOpportunity(Role role, Opportunity opportunity) {
        ArrayList<Opportunity> opportunities = role.getRoleOpportunities();
//        //特殊效果，直接添加
//        if (SPECIAL_OPPORTUNITY.contains(opportunity.name)) {
//            opportunities.add(opportunity);
//            return;
//        }
        //非特殊效果
        for (Opportunity o : opportunities) {
            //效果名字相同
            if (o.getName().equals(opportunity.getName())) {
                //可叠加
                if (opportunity.stackable) {
                    o.setLayer(o.getLayer() + opportunity.getLayer());
                    if (!NO_DISPLAY.contains(opportunity.name)) {
                        EffectUtils.textBigger("获得" + opportunity.name + "效果", role, Color.WHITE);
                    }
                    return;
                }

                //永久生效效果
                if (o.getNum() == 999) return;
                //仅添加次数
                o.setNum(o.getNum() + opportunity.getNum());
                if (!NO_DISPLAY.contains(opportunity.name)) {
                    EffectUtils.textBigger("获得" + opportunity.name + "效果", role,Color.WHITE);
                }
                return;
            }
        }

        //未有此效果，直接添加
        opportunities.add(opportunity);
        if (!NO_DISPLAY.contains(opportunity.name)) {
            EffectUtils.textBigger("获得" + opportunity.name + "效果", role,Color.WHITE);
        }
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
                    //若有@，将@替换为S，即效果含有时机效果
                    effect = effect.replace("@", "S");
                    //解析效果
                    Effect result = Effect.parse(role, effect, null);
                    //执行效果
                    if (result != null) {
                        BattleInformation.insetEffect(result);
                        //result.action();
                        BattleInformation.effectExecution();
                    }
                }


                //发动次数为0，执行结束效果
                if (opportunity.num == 0) {
                    endEffectAction(role, opportunity);
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
                endEffectAction(role, opportunity);
                continue;
            }

            n++;
        }
    }

    //执行结束后效果
    private static void endEffectAction(Role role, Opportunity opportunity) {
        if (opportunity.endEffect != null) {
            String effect = opportunity.endEffect.replace("SValueS", String.valueOf(opportunity.getLayer()));
            //解析效果
            Effect result = Effect.parse(role, effect, null);
            //执行效果
            if (result != null) {
                BattleInformation.insetEffect(result);
                //result.action();
                BattleInformation.effectExecution();
            }
            //效果结束文字展示
            EffectUtils.textEffectEnd(opportunity.getName(), role);
        }
    }

    //删除某角色的时机效果
    public static void deleteOpportunity(Role role, String name) {
        ArrayList<Opportunity> opportunities = role.getRoleOpportunities();
        for (Opportunity opportunity : opportunities) {
            if (opportunity.name.equals(name)) {
                opportunities.remove(opportunity);
                return;
            }
        }
    }

    //查看某角色是否拥有某种时机
    public static boolean exist(Role role, String opportunityName) {
        ArrayList<Opportunity> opportunities = role.getRoleOpportunities();
        for (Opportunity opportunity : opportunities) {
            if (opportunity.name.equals(opportunityName)) return true;
        }
        return false;
    }

    private static final String NO_DISPLAY = "寻回/抽;无";

    //展示时机效果
    public static String displayOpportunities(ArrayList<Opportunity> opportunities) {
        StringBuilder sb = new StringBuilder();
        for (Opportunity opportunity : opportunities) {
            String name = opportunity.name;
            if (NO_DISPLAY.contains(name)) continue;
            sb.append(name).append(" ");
        }
        sb.append(Effect.NEW_LINE);
        return sb.toString();
    }

    //时机序列效果详情
    public static String detailOpportunities(Role role) {
        ArrayList<Opportunity> opportunities = role.getRoleOpportunities();
        StringBuilder sb = new StringBuilder();
        for (Opportunity opportunity : opportunities) {
            sb.append(detailOpportunity(role, opportunity));
        }
        return sb.toString();
    }

    //展示某个时机效果的详情
    private static String detailOpportunity(Role role, Opportunity opportunity) {
        if (NO_DISPLAY.contains(opportunity.name)) return "";
        StringBuilder sb = new StringBuilder();
        sb.append(opportunity.name).append(" ");
        //层数显示
        if (opportunity.stackable) {
            sb.append(opportunity.layer).append("层");
        } else if (opportunity.num != 999) {
            sb.append(opportunity.num).append("层");
        }
        sb.append("\n");

        //回合数展示
        if (opportunity.rounds != 0) {
            sb.append("持续").append(opportunity.rounds).append("回合\n");
        } else if (opportunity.num == 999) {
            sb.append("永久效果\n");
        }

        //详情效果展示
        String detail = Effect.effectDetail(opportunity.name);
        if (detail.isEmpty()) {
            sb.append(OpportunityType.getTypeName(opportunity.opportunityType)).append(",");
            if (opportunity.effect == null) {
                Effect endEffect = Effect.parse(role, opportunity.endEffect.replace("SValueS", String.valueOf(opportunity.layer)), null);
                sb.append(endEffect.toString());
            } else {
                Effect thisEffect = Effect.parse(role, opportunity.effect.replace("SValueS", String.valueOf(opportunity.layer)), null);
                sb.append(thisEffect.toString());
            }
            sb.append(Effect.NEW_LINE);
        } else {
            sb.append(detail).append(Effect.NEW_LINE);
        }

        return sb.toString();
    }
}
