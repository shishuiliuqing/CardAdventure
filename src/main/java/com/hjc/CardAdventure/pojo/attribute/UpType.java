package com.hjc.CardAdventure.pojo.attribute;

public enum UpType {
    POWER_UP,
    INTELLIGENCE_UP,
    DEFENSE_UP,
    AGILITY_UP,
    PURITY_UP,
    SPEED_UP;

    public static UpType getInstance(String type) {
        return switch (type) {
            case "P" -> POWER_UP;
            case "I" -> INTELLIGENCE_UP;
            case "D" -> DEFENSE_UP;
            case "A" -> AGILITY_UP;
            case "U" -> PURITY_UP;
            default -> SPEED_UP;
        };
    }
}
