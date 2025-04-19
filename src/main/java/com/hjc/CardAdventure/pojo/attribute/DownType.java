package com.hjc.CardAdventure.pojo.attribute;

public enum DownType {
    POWER_DOWN,
    INTELLIGENCE_DOWN,
    DEFENSE_DOWN,
    AGILITY_DOWN,
    PURITY_DOWN,
    SPEED_DOWN;

    public static DownType getInstance(String type) {
        return switch (type) {
            case "P" -> POWER_DOWN;
            case "I" -> INTELLIGENCE_DOWN;
            case "D" -> DEFENSE_DOWN;
            case "A" -> AGILITY_DOWN;
            case "U" -> PURITY_DOWN;
            default -> SPEED_DOWN;
        };
    }
}
