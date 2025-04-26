package com.hjc.CardAdventure.component.camp;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.Texture;
import com.hjc.CardAdventure.Global;
import com.hjc.CardAdventure.Utils.EntityUtils;
import com.hjc.CardAdventure.component.information.TipBarComponent;
import com.hjc.CardAdventure.pojo.BattleInformation;
import com.hjc.CardAdventure.subScene.LookCardsSubScene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

import static com.hjc.CardAdventure.Global.*;
import static com.hjc.CardAdventure.Global.CARD_DISPLAY.*;
import static com.hjc.CardAdventure.Global.CARD_DISPLAY.CARD_HEIGHT;
import static com.hjc.CardAdventure.Global.PLAYER.*;

public class CardsComponent extends Component {
    @Override
    public void onAdded() {
        //添加总牌堆UI
        addUI();

        entity.getViewComponent().addOnClickHandler(e -> lookCards());
        entity.getViewComponent().addEventHandler(MouseEvent.MOUSE_ENTERED, e -> lookInformation());
    }

    private void lookCards() {
        LookCardsSubScene.cards = cards;
        LookCardsSubScene.cardsType = "总牌堆";
        FXGL.getSceneService().pushSubScene(new LookCardsSubScene());
    }

    private void lookInformation() {
        TipBarComponent.update("总牌堆，牌数：" + cards.size());
    }

    //添加总牌堆UI
    private void addUI() {
        //牌框图片
        Texture cardBox = FXGL.texture(getTextureAddress(BATTLE_ADDRESS, "cardBox"), CARD_BOX_WIDTH, CARD_BOX_HEIGHT);
        EntityUtils.nodeMove(cardBox, 0, Global.GAME_SETTING.APP_HEIGHT - CARD_BOX_HEIGHT);
        entity.getViewComponent().addChild(cardBox);

        //牌背
        Texture cardBack = FXGL.texture(getTextureAddress(CARD_BACK_ADDRESS, "cardBack" + EntityUtils.parseColor(player.getColorS())), CARD_WIDTH, CARD_HEIGHT);
        EntityUtils.nodeMove(cardBack, 30 / PROPORTION + 1, GAME_SETTING.APP_HEIGHT - CARD_BOX_HEIGHT + 75 / PROPORTION);
        entity.getViewComponent().addChild(cardBack);

        //牌数
        double cXMove = 30 / PROPORTION + CARD_WIDTH / 2;
        double cYMove = GAME_SETTING.APP_HEIGHT - CARD_BOX_HEIGHT + 75 / PROPORTION + CARD_HEIGHT / 2;
        StackPane stackPane = EntityUtils.generateCircleNum(cXMove, cYMove,
                25, cards.size(), player.getColorS(),
                new Font("微软雅黑", 15));
        entity.getViewComponent().addChild(stackPane);
    }
}
