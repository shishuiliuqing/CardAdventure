package com.hjc.CardAdventure.effect.player;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.hjc.CardAdventure.Utils.CardsUtils;
import com.hjc.CardAdventure.component.card.CardComponent;
import com.hjc.CardAdventure.effect.basic.PauseEffect;
import com.hjc.CardAdventure.pojo.BattleInformation;
import com.hjc.CardAdventure.pojo.Role;
import com.hjc.CardAdventure.pojo.card.Card;
import javafx.animation.TranslateTransition;
import javafx.scene.text.Text;
import javafx.util.Duration;

import static com.hjc.CardAdventure.Global.CARD_USE.*;

//特殊打出效果
public class SpecialProduce extends PauseEffect {
    public SpecialProduce(Role from, String effect) {
        super(from, effect);
    }

    @Override
    public void action() {
        if (getFrom() == null) return;
        //牌堆为空
        if (findCards.isEmpty()) {
            TranslateTransition tt = new TranslateTransition(Duration.seconds(0.2), new Text());
            tt.setOnFinished(e -> BattleInformation.effectExecution());
            tt.play();
            return;
        }
        //加载最近卡牌地址
        int boxNum = CardsUtils.getNearFindCards();
        //加载对应卡牌
        Card card = findCards.get(boxNum);
        //删除对应位置
        findCards.remove(boxNum);
        //特殊使用打开
        specialProduce = true;
        //加载卡牌实体
        FXGL.spawn("card", new SpawnData().put("boxNum", boxNum).put("card", card));
    }

    @Override
    public String toString() {
        return "";
    }
}
