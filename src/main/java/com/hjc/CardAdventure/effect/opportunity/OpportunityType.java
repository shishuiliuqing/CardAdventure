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
    //进行物理攻击后
    PHY_ATTACK_END,
    //造成物理伤害后
    PHY_DAMAGE_END,
    //受到物理伤害后
    PHY_HURT,
    //受到特殊伤害后
    SPECIAL_HURT,
    //失去生命时
    LOSS_BLOOD,
    //受到物理伤害未失去生命时
    DEFENSE_PHY_HURT,
    //物理伤害攻击造成生命损失后
    PHY_DAMAGE_SUCCESS,
    //使用牌后
    USE_CARD_END,
    //其他,仅占位效果，不触发
    OTHER;

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
            case "PHY_DAMAGE_END" -> PHY_DAMAGE_END;
            case "PHY_HURT" -> PHY_HURT;
            case "SPECIAL_HURT" -> SPECIAL_HURT;
            case "DEFENSE_PHY_HURT" -> DEFENSE_PHY_HURT;
            case "PHY_DAMAGE_SUCCESS" -> PHY_DAMAGE_SUCCESS;
            case "LOSS_BLOOD" -> LOSS_BLOOD;
            case "USE_CARD_END" -> USE_CARD_END;
            case "OTHER" -> OTHER;
            default -> null;
        };
    }

    //根据时机类型获取名称
    public static String getTypeName(OpportunityType opportunityType) {
        return switch (opportunityType) {
            case ROUND_BEGIN -> "大回合开始";
            case OWN_ROUND_BEGIN -> "自身回合开始";
            case OWN_ROUND_END -> "自身回合结束";
            case GAME_BEGIN -> "游戏开始";
            case DRAW_STAGE -> "抽牌阶段";
            case ABANDON_STAGE -> "弃牌阶段";
            case PHY_ATTACK_END -> "物理攻击后";
            case PHY_DAMAGE_END -> "造成物理伤害后";
            case PHY_HURT -> "受到物理伤害后";
            case PRODUCE_STAGE_END -> "出牌阶段后";
            case SPECIAL_HURT -> "因非物理伤害失去生命时";
            case DEFENSE_PHY_HURT -> "受到物理伤害未失去生命时";
            case PHY_DAMAGE_SUCCESS -> "成功造成物理伤害时";
            case LOSS_BLOOD -> "失去生命时";
            case USE_CARD_END -> "使用牌后";
            case OTHER -> "其他";
        };
    }
}
