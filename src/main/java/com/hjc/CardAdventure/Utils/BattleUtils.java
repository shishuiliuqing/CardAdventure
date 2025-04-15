package com.hjc.CardAdventure.Utils;

import com.hjc.CardAdventure.effect.basic.ActionOver;
import com.hjc.CardAdventure.effect.basic.PauseEffect;
import com.hjc.CardAdventure.pojo.BattleInformation;

import static com.hjc.CardAdventure.Global.PLAYER.player;
import static com.hjc.CardAdventure.pojo.player.Level.*;

public class BattleUtils {
    private BattleUtils() {
    }

    //计算人物出牌数
    public static int mathProduce() {
        int num = playedCardNum(getLV(player.getExperience()));
        return num;
    }


    //回合结束
    public static void actionOver() {
        BattleInformation.EFFECTS.add(new PauseEffect(null, "10"));
        BattleInformation.EFFECTS.add(new ActionOver(BattleInformation.nowAction, ""));
        //BattleInformation.effectExecution();
    }
}
