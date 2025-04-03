package com.hjc.CardAdventure.configuration;

import com.almasb.fxgl.dsl.FXGL;
import com.hjc.CardAdventure.pojo.enemy.Enemy;
import com.hjc.CardAdventure.pojo.enemy.EnemyType;
import com.hjc.CardAdventure.pojo.enemy.Intention;
import com.hjc.CardAdventure.pojo.environment.InsideInformation;
import com.hjc.CardAdventure.pojo.environment.TimeStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static com.hjc.CardAdventure.Global.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeasonMonsterPool {
    //当前生成的怪物序列
    public static ArrayList<String> enemyNames;

    //弱怪池
    private TimeMonsterPool weak;
    //强怪池
    private TimeMonsterPool strong;

    //将怪物组合创建成对象
    public ArrayList<Enemy> getEnemies(ArrayList<String> monsters, int day) {
        ArrayList<Enemy> enemies = new ArrayList<>();
        for (String monster : monsters) {
            int x = (day - 1) % 6 + 1;
            String enemyName = monster + x;
            Enemy enemy = FXGL.getAssetLoader().loadJSON(getJsonAddress(ENEMY_ADDRESS, enemyName), Enemy.class).get();
            String stage = String.valueOf((InsideInformation.day - 1) / 6 + 1);
            //System.out.println(MONSTER_INTENTION_ADDRESS + monster + stage + ".json");
            Intention[] intentions = FXGL.getAssetLoader().loadJSON(getJsonAddress(INTENTION_ADDRESS, enemyName), Intention[].class).get();
            ArrayList<Intention> intentionArrayList = new ArrayList<>(Arrays.asList(intentions));
            enemy.setIntentions(intentionArrayList);
            enemies.add(enemy);
        }
        return enemies;
    }

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
        enemyNames = monsterPool.get(r.nextInt(monsterPool.size()));
        return enemyNames;
    }
}
