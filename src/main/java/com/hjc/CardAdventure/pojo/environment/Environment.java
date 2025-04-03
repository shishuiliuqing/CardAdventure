package com.hjc.CardAdventure.pojo.environment;

//环境
public enum Environment {
    FOREST, DESERT, GRASSLAND, WETLAND;

    public String environmentToString() {
        if (this.equals(Environment.FOREST)) {
            return "森林";
        } else if (this.equals(Environment.DESERT)) {
            return "沙漠";
        } else if (this.equals(Environment.GRASSLAND)) {
            return "草地";
        } else if (this.equals(Environment.WETLAND)) {
            return "湿地";
        } else return "";
    }
}
