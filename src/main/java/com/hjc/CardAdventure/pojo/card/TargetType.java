package com.hjc.CardAdventure.pojo.card;

public enum TargetType {
    INDIVIDUAL, OWN, RANDOMIZED, ALL;

    public String getTargetString() {
        if (this == INDIVIDUAL) {
            return "单一敌方目标";
        } else if (this == OWN) {
            return "自身";
        } else if (this == RANDOMIZED) {
            return "随机一位敌人";
        } else return "敌方全体目标";
    }
}
