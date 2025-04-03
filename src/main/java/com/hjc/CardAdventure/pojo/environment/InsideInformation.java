package com.hjc.CardAdventure.pojo.environment;

import lombok.Data;

import java.util.Random;

@Data
//局内信息
public class InsideInformation {
    //环境生成队列
    public static Environment[] ENVIRONMENTS;
    //季节生成队列
    public static Season[] SEASONS;
    //天数（只有24天）
    public static int day;
    //时间状态（早上，下午，晚上）
    public static TimeStatus timeStatus;


    //将局内信息转化为字符串
    public static String insideEnvironmentToString() {
        int stage = (day - 1) / 6;
        return ENVIRONMENTS[stage].environmentToString() + "（" + SEASONS[stage].seasonToString() + "）" +
                "      第" + (day >= 10 ? "" : "0") + day + "天（" + timeStatus.timeStatusToString() + "）";
    }

    //初始化并随机生成局内信息
    public static void generateInsideEnvironment() {
        //获取所有环境
        ENVIRONMENTS = Environment.values();
        //获取所有季节
        SEASONS = Season.values();

        Random r = new Random();
        //随机打乱环境
        for (int i = 0; i < ENVIRONMENTS.length; i++) {
            int random = r.nextInt(ENVIRONMENTS.length);
            Environment t = ENVIRONMENTS[i];
            ENVIRONMENTS[i] = ENVIRONMENTS[random];
            ENVIRONMENTS[random] = t;
        }
        //随机打乱季节
        for (int i = 0; i < SEASONS.length; i++) {
            int random = r.nextInt(SEASONS.length);
            Season t = SEASONS[i];
            SEASONS[i] = SEASONS[random];
            SEASONS[random] = t;
        }
        //重置天数
        day = 1;
        //重置时间状态
        timeStatus = TimeStatus.DAY;
    }

    //时间状态更新
    public static void turnTimeStatus() {
        timeStatus = TimeStatus.turn(timeStatus);
        if (timeStatus == TimeStatus.DAY) day++;
    }
}
