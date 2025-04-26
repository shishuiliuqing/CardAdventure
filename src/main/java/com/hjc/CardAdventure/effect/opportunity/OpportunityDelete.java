package com.hjc.CardAdventure.effect.opportunity;

import com.hjc.CardAdventure.effect.target.TargetedEffect;
import com.hjc.CardAdventure.pojo.Role;

import java.util.ArrayList;

//时机效果删除
public class OpportunityDelete extends TargetedEffect {
    public OpportunityDelete(Role from, String effect, Role to) {
        super(from, effect, to);
    }

    @Override
    public void action() {
        if(getFrom() == null || getTo() == null) return;
        ArrayList<String> effect = cutEffect(getEffect());
        //获取时机名字
        String name = getFirst(effect);
        Opportunity.deleteOpportunity(getTo(),name);

        continueAction(getFrom(),montage(effect),getTo());
    }

    @Override
    public String toString() {
        return "";
    }
}
