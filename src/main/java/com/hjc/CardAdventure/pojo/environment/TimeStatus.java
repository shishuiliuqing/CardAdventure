package com.hjc.CardAdventure.pojo.environment;

public enum TimeStatus {
    DAY, AFTERNOON, EVENING;

    public String timeStatusToString() {
        if (this.equals(TimeStatus.DAY)) {
            return "早上";
        } else if (this.equals(AFTERNOON)) {
            return "下午";
        } else if (this.equals(EVENING)) {
            return "晚上";
        } else return "";
    }

    //时间状态轮转
    public static TimeStatus turn(TimeStatus timeStatus) {
        //若白天转到下午
        if (timeStatus.equals(TimeStatus.DAY)) return TimeStatus.AFTERNOON;
        //若下午转到晚上
        if (timeStatus.equals(TimeStatus.AFTERNOON)) return TimeStatus.EVENING;
        //若晚上转白天
        return TimeStatus.DAY;
    }
}
