package com.hjc.CardAdventure.configuration;

import com.almasb.fxgl.dsl.FXGL;
import com.hjc.CardAdventure.pojo.enemy.Enemy;
import com.hjc.CardAdventure.pojo.enemy.EnemyType;
import com.hjc.CardAdventure.pojo.enemy.Intention;
import com.hjc.CardAdventure.pojo.environment.InsideInformation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static com.hjc.CardAdventure.Global.*;

//怪池用于生成怪物
public class MonsterPool {
    //敌人类型，用于决定获得经验
    public static EnemyType enemyType;
    //当前怪池
    public static ArrayList<String> monsterPool;

    //将怪物组合创建成对象
    public static ArrayList<Enemy> getEnemies(int day) {
        ArrayList<Enemy> enemies = new ArrayList<>();
        for (String monster : monsterPool) {
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

    //获取当前怪物的卡牌奖励
    public static String getCardReward() {
        Random random = new Random();
        ArrayList<String> cards = new ArrayList<>();
        //从每一个怪物中获取一张卡牌
        for (String enemy : monsterPool) {
            EnemyCards enemyCards = EnemyCards.getEnemyCards(enemy);
            String card = enemyCards.getByDay(InsideInformation.day);
            cards.add(card);
        }
        //从获取的卡牌中随机一张卡牌
        return cards.get(random.nextInt(cards.size()));
    }
}
