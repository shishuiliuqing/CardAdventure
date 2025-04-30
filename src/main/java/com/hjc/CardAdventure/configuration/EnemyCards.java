package com.hjc.CardAdventure.configuration;

import com.almasb.fxgl.dsl.FXGL;
import com.hjc.CardAdventure.pojo.card.Card;
import com.hjc.CardAdventure.pojo.enemy.Enemy;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Random;

import static com.hjc.CardAdventure.Global.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnemyCards {
    //1阶段
    private ArrayList<String> stageOne;
    //2阶段
    private ArrayList<String> stageTwo;
    //3阶段
    private ArrayList<String> stageThree;
    //4阶段
    private ArrayList<String> stageFour;

    //随机获得怪物的一张卡牌（根据天数）
    public String getByDay(int day) {
        Random r = new Random();
        ArrayList<String> cards = byDay(day);
        System.out.println(cards);
        return cards.get(r.nextInt(cards.size()));
    }

    //根据天数获得对应阶段的数组
    private ArrayList<String> byDay(int day) {
        return switch ((day - 1) / 6 + 1) {
            case 1 -> stageOne;
            case 2 -> stageTwo;
            case 3 -> stageThree;
            default -> stageFour;
        };
    }

    //根据怪物名字获取怪物卡组
    public static EnemyCards getEnemyCards(String enemy) {
        //System.out.println(enemy);
        return FXGL.getAssetLoader().loadJSON(getJsonAddress(ENEMY_CARDS_ADDRESS, enemy), EnemyCards.class).get();
    }
}
