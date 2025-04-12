package com.hjc.CardAdventure.configuration;

import com.hjc.CardAdventure.pojo.environment.TimeStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Random;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeasonMonsterPool {
    //弱怪池
    private TimeMonsterPool weak;
    //强怪池
    private TimeMonsterPool strong;

    //按条件获得非精英/特殊怪物组合
    public ArrayList<String> generateLittleMonsterPool(int day, TimeStatus timeStatus) {
        //前三天为弱怪池
        ArrayList<ArrayList<String>> monsterPool;
        if ((day - 1) % 6 + 1 <= 3) {
            monsterPool = weak.getTimeMonsterPool(timeStatus);
            //后三天为强怪池
        } else {
            monsterPool = strong.getTimeMonsterPool(timeStatus);
        }
        return randomMonster(monsterPool);
    }

    //随机从怪池选择一个怪物组合
    private ArrayList<String> randomMonster(ArrayList<ArrayList<String>> monsterPool) {
        Random r = new Random();
        return monsterPool.get(r.nextInt(monsterPool.size()));
    }
}
