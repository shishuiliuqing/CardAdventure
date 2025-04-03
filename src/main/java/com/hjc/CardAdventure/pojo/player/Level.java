package com.hjc.CardAdventure.pojo.player;

public enum Level {
    //2次出牌，初始为1级
    LV_1,
    //3次出牌，升级（2点属性点添加）
    LV_2,
    //4次出牌，升级（2点属性点添加）
    LV_3,
    //4次出牌，升级（祝福选取，纹章选取），预计第一场景度过
    LV_4,

    //5次出牌，升级（2点属性点添加）
    LV_5,
    //6次出牌，升级（2点属性点添加）
    LV_6,
    //6次出牌，升级（祝福选取，纹章选取），预计第二场景度过
    LV_7,

    //7次出牌，升级（3点属性点添加）
    LV_8,
    //8次出牌，升级（3点属性点添加），
    LV_9,
    //9次出牌，升级（祝福选取，纹章选取）预计第三场景度过
    LV_10,

    //10次出牌，全部属性点提升1级
    LV_11;

    //等级计算器
    public static Level getLV(int experience) {
        if (experience >= 850) return LV_11;
        if (experience >= 700) return LV_10;
        if (experience >= 550) return LV_9;
        if (experience >= 400) return LV_8;
        if (experience >= 300) return LV_7;
        if (experience >= 200) return LV_6;
        if (experience >= 120) return LV_5;
        if (experience >= 70) return LV_4;
        if (experience >= 40) return LV_3;
        if (experience >= 10) return LV_2;
        return LV_1;
    }

    //等级经验显示器
    public static String experienceDisplay(int experience) {
        if (experience >= 850) return experience + " / " + 9999;
        if (experience >= 700) return experience + " / " + 850;
        if (experience >= 550) return experience + " / " + 700;
        if (experience >= 400) return experience + " / " + 550;
        if (experience >= 300) return experience + " / " + 400;
        if (experience >= 200) return experience + " / " + 300;
        if (experience >= 120) return experience + " / " + 200;
        if (experience >= 70) return experience + " / " + 120;
        if (experience >= 40) return experience + " / " + 70;
        if (experience >= 10) return experience + " / " + 40;
        return experience + " / " + 10;
    }

    //获取升级到下一等级需要达到的经验
    public static int nextExperience(int experience) {
        if (experience >= 850) return 9999;
        if (experience >= 700) return 850;
        if (experience >= 550) return 700;
        if (experience >= 400) return 550;
        if (experience >= 300) return 400;
        if (experience >= 200) return 300;
        if (experience >= 120) return 200;
        if (experience >= 70) return 120;
        if (experience >= 40) return 70;
        if (experience >= 10) return 40;
        return 10;
    }

    //等级获取出牌数
    public static int playedCardNum(Level level) {
        return switch (level) {
            case LV_1 -> 2;
            case LV_2 -> 3;
            case LV_3, LV_4 -> 4;
            case LV_5 -> 5;
            case LV_6, LV_7 -> 6;
            case LV_8 -> 7;
            case LV_9 -> 8;
            case LV_10 -> 9;
            case LV_11 -> 10;
        };
    }

    //升级奖励判断
    public static int[] upgrade(int experience) {
        int[] reward = new int[3];
        if (experience >= 850) {
            reward[0] = 5;
        } else if (experience >= 700) {
            reward[1] = 1;
            reward[2] = 1;
        } else if (experience >= 400) {
            reward[0] = 3;
        } else if (experience >= 300) {
            reward[1] = 1;
            reward[2] = 1;
        } else if (experience >= 120) {
            reward[0] = 2;
        } else if (experience >= 70) {
            reward[1] = 1;
            reward[2] = 1;
        } else if (experience >= 10) {
            reward[0] = 2;
        }
        return reward;
    }
}
