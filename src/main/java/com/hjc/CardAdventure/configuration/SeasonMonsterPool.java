package com.hjc.CardAdventure.configuration;

import com.almasb.fxgl.dsl.FXGL;
import com.hjc.CardAdventure.pojo.environment.InsideInformation;
import com.hjc.CardAdventure.pojo.environment.TimeStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Random;

import static com.hjc.CardAdventure.Global.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeasonMonsterPool {
    //弱怪池
    private TimeMonsterPool weak;
    //中怪池
    private TimeMonsterPool medium;
    //强怪池
    private TimeMonsterPool strong;

    //按条件获得非精英/特殊怪物组合
    public ArrayList<String> generateLittleMonsterPool(int day, TimeStatus timeStatus) {
        //前两天为弱怪池
        ArrayList<ArrayList<String>> monsterPool;
        if ((day - 1) % 6 + 1 <= 2) {
            monsterPool = weak.getTimeMonsterPool(timeStatus);

        }
        //中两天为中怪池
        else if ((day - 1) % 6 + 1 <= 4) {
            monsterPool = medium.getTimeMonsterPool(timeStatus);
        }
        //后两天为强怪池
        else {
            monsterPool = strong.getTimeMonsterPool(timeStatus);
        }
        return randomMonster(monsterPool);
    }

    //随机从怪池选择一个怪物组合
    private ArrayList<String> randomMonster(ArrayList<ArrayList<String>> monsterPool) {
        Random r = new Random();
        return monsterPool.get(r.nextInt(monsterPool.size()));
    }

    //根据环境条件获得怪池
    public static SeasonMonsterPool getInstance() {
        String season = InsideInformation.getSeason().getAddress();
        String environment = InsideInformation.getEnvironment().getAddress();
        return FXGL.getAssetLoader().loadJSON(getJsonAddress(MONSTER_POOL_ADDRESS, environment + "/" + season), SeasonMonsterPool.class).get();
    }
}
