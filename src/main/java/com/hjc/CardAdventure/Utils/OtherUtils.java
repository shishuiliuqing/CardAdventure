package com.hjc.CardAdventure.Utils;

import java.util.ArrayList;
import java.util.Random;

public class OtherUtils {
    private OtherUtils() {
    }

    //概率集合
    private static final ArrayList<Integer> integers = new ArrayList<>();

    //概率成是否功触发器
    public static boolean isSuccess(int probability) {
        //初始化概率集合
        initIntS();

        //随机删除99个数
        return getLastNum() <= probability;
    }

    //初始化概率集合
    private static void initIntS() {
        integers.clear();
        for (int i = 0; i < 100; i++) {
            integers.add(i + 1);
        }
    }

    //随机删除概率集合的数字直至剩余1个
    private static int getLastNum() {
        Random r = new Random();
        while (integers.size() > 1) {
            integers.remove(r.nextInt(integers.size()));
        }
        return integers.get(0);
    }
}
