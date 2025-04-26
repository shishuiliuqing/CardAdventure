package com.hjc.CardAdventure.pojo.environment;

public enum Season {
    spring, summer, fall, winter;

    public String seasonToString() {
        if (this.equals(Season.spring)) {
            return "春";
        } else if (this.equals(Season.summer)) {
            return "夏";
        } else if (this.equals(Season.fall)) {
            return "秋";
        } else if (this.equals(Season.winter)) {
            return "冬";
        } else return "";
    }

    //获取英文地址
    public String getAddress() {
        return switch (this) {
            case spring -> "spring";
            case summer -> "summer";
            case fall -> "fall";
            case winter -> "winter";
        };
    }
}
