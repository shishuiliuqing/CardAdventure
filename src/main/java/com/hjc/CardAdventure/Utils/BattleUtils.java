package com.hjc.CardAdventure.Utils;

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
}
