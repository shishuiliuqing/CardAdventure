package com.hjc.CardAdventure.effect.opportunity;

public enum OpportunityType {
    //游戏开始
    GAME_BEGIN,
    //回合开始
    ROUND_BEGIN,
    //自身回合开始
    OWN_ROUND_BEGIN,
    //自身回合结束
    OWN_ROUND_END,
    //抽牌阶段
    DRAW_STAGE,
    //出牌阶段后
    PRODUCE_STAGE_END,
    //弃牌阶段后
    ABANDON_STAGE,
    //物理攻击后
    PHY_ATTACK_END;

    //根据名称获取时机效果
    public static OpportunityType parse(String type) {
        return switch (type) {
            case "GAME_BEGIN" -> GAME_BEGIN;
            case "ROUND_BEGIN" -> ROUND_BEGIN;
            case "OWN_ROUND_BEGIN" -> OWN_ROUND_BEGIN;
            case "OWN_ROUND_END" -> OWN_ROUND_END;
            case "DRAW_STAGE" -> DRAW_STAGE;
            case "PRODUCE_STAGE_END" -> PRODUCE_STAGE_END;
            case "ABANDON_STAGE" -> ABANDON_STAGE;
            case "PHY_ATTACK_END" -> PHY_ATTACK_END;
            default -> null;
        };
    }
}
