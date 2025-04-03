package com.hjc.CardAdventure.configuration;

import com.hjc.CardAdventure.pojo.environment.TimeStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeMonsterPool {
    //白天
    private ArrayList<ArrayList<String>> day;
    //下午
    private ArrayList<ArrayList<String>> afternoon;
    //晚上
    private ArrayList<ArrayList<String>> evening;

    //返回对应时间的怪池
    public ArrayList<ArrayList<String>> getTimeMonsterPool(TimeStatus timeStatus) {
        return switch (timeStatus) {
            case DAY -> day;
            case AFTERNOON -> afternoon;
            case EVENING -> evening;
        };
    }
}