package com.hjc.CardAdventure.pojo;

public enum HurtType {
    //普通掉血
    NULL,
    //火焰伤害
    FIRE;


    //获取伤害类型
    public static HurtType getHurtType(String type) {
        return switch (type) {
            case "FIRE" -> FIRE;
            default -> NULL;
        };
    }
}
