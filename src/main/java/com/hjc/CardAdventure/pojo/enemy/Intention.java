package com.hjc.CardAdventure.pojo.enemy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Intention {
    //怪物意图编号
    private int num;
    //意图类型
    private IntentionType intentionType;
    //意图触发类型
    private IntentionGenerateType intentionGenerateType;
    //意图效果
    private ArrayList<String> effects;
    //连续发动次数
    private int consecutiveStarts;

    //发动中断器
    public static void interrupt(Intention intention) {
        intention.setConsecutiveStarts(0);
    }
}
