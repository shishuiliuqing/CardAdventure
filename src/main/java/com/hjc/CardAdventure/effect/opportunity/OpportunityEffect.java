package com.hjc.CardAdventure.effect.opportunity;

import com.hjc.CardAdventure.effect.target.TargetedEffect;
import com.hjc.CardAdventure.pojo.Role;

import java.util.ArrayList;

//时机效果
public class OpportunityEffect extends TargetedEffect {
    public OpportunityEffect(Role from, String effect, Role to) {
        super(from, effect, to);
    }

    @Override
    public void action() {
        if (getFrom() == null || getTo() == null) return;
        //获取效果序列
        ArrayList<String> effect = cutEffect(getEffect());
        //获取时机名字
        String name = getFirst(effect);
        //获取时机
        OpportunityType opportunityType = OpportunityType.parse(getFirst(effect));
        //获得状态
        OpportunityStatus opportunityStatus = OpportunityStatus.getInstance(getFirst(effect));
        //获取回合数
        int rounds = changeToInt(getFirst(effect));
        //获取次数
        int num = changeToInt(getFirst(effect));
        //获取层数
        int layer = changeToInt(getFirst(effect));
        //判断是否可堆叠
        boolean stackable = getFirst(effect).equals("YES");
        //时机创建
        Opportunity opportunity = new Opportunity(name, opportunityType, opportunityStatus, rounds, num, layer, stackable, montage(effect), null);
        //目标添加时机
        Opportunity.addOpportunity(getTo(), opportunity);
    }

    @Override
    public String toString() {
        //获取效果序列
        ArrayList<String> effect = cutEffect(getEffect());
        //获取时机名字
        String name = getFirst(effect);
        //获取时机
        getFirst(effect);
        //获取时机状态
        getFirst(effect);
        //获取回合数
        int rounds = changeToInt(getFirst(effect));
        //获取次数
        int num = changeToInt(getFirst(effect));
        //获取层数
        int layer = changeToInt(getFirst(effect));
        //判断是否可堆叠
        boolean stackable = getFirst(effect).equals("YES");
        return "获得\"" + name + " " + (stackable ? layer : num) + "\"";
    }
}
