package com.hjc.CardAdventure.effect.opportunity;

public enum OpportunityStatus {
    //正面
    POSITIVE,
    //正面触发
    POSITIVE_END,
    //负面
    NEGATIVE,
    //消极触发
    NEGATIVE_END;

    //根据string获得状态
    public static OpportunityStatus getInstance(String status) {
        return switch (status) {
            case "POSITIVE_END" -> POSITIVE_END;
            case "NEGATIVE" -> NEGATIVE;
            case "NEGATIVE_END" -> NEGATIVE_END;
            default -> POSITIVE;
        };
    }
}
