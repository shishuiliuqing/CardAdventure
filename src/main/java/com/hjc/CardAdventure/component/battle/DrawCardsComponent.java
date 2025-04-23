package com.hjc.CardAdventure.component.battle;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.Texture;
import com.hjc.CardAdventure.Utils.EntityUtils;
import com.hjc.CardAdventure.component.information.TipBarComponent;
import com.hjc.CardAdventure.pojo.BattleInformation;
import com.hjc.CardAdventure.pojo.card.Card;
import com.hjc.CardAdventure.subScene.LookCardsSubScene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

import static com.hjc.CardAdventure.Global.PLAYER.player;
import static com.hjc.CardAdventure.Global.CARD_DISPLAY.*;
import static com.hjc.CardAdventure.Global.*;

public class DrawCardsComponent extends Component {

    //牌框状态
    public static final int[] CARD_BOX_STATUS = new int[10];

    @Override
    public void onAdded() {
        //添加组件
        addComponent();

        entity.getViewComponent().addEventHandler(MouseEvent.MOUSE_ENTERED, e->lookInformation());
        entity.getViewComponent().addOnClickHandler(e->lookCards());
//        entity.getViewComponent().addOnClickHandler(e->draw());
    }

    //添加组件
    public void addComponent() {
        //牌框图片
        Texture cardBox = FXGL.texture(getTextureAddress(BATTLE_ADDRESS, "cardBox"), CARD_BOX_WIDTH, CARD_BOX_HEIGHT);
        EntityUtils.nodeMove(cardBox, 0, GAME_SETTING.APP_HEIGHT - CARD_BOX_HEIGHT);
        entity.getViewComponent().addChild(cardBox);

        //牌背
        Texture cardBack = FXGL.texture(getTextureAddress(CARD_BACK_ADDRESS, "cardBack" + EntityUtils.parseColor(player.getColorS())), CARD_WIDTH, CARD_HEIGHT);
        EntityUtils.nodeMove(cardBack, 30 / PROPORTION + 1, GAME_SETTING.APP_HEIGHT - CARD_BOX_HEIGHT + 75 / PROPORTION);
        entity.getViewComponent().addChild(cardBack);

        //牌数
        double cXMove = 30 / PROPORTION + CARD_WIDTH / 2;
        double cYMove = GAME_SETTING.APP_HEIGHT - CARD_BOX_HEIGHT + 75 / PROPORTION + CARD_HEIGHT / 2;
        StackPane stackPane = EntityUtils.generateCircleNum(cXMove, cYMove,
                25, BattleInformation.DRAW_CARDS.size(), player.getColorS(),
                new Font("微软雅黑", 15));
        entity.getViewComponent().addChild(stackPane);
    }

    //更新方法
    public void update() {
        entity.getViewComponent().clearChildren();
        addComponent();
    }

    //抽牌堆信息
    private void lookInformation() {
        TipBarComponent.update("抽牌堆，剩余牌数：" + BattleInformation.DRAW_CARDS.size());
    }

    //查看抽牌堆
    public void lookCards() {
        LookCardsSubScene.cards = BattleInformation.DRAW_CARDS;
        LookCardsSubScene.cardsType = "抽牌区";
        FXGL.getSceneService().pushSubScene(new LookCardsSubScene());
    }

    //抽牌
    public int draw() {
        int boxNum = nearEmptyBox();
        if (boxNum == -1) return 0;
        //抽牌堆没牌
        if (BattleInformation.DRAW_CARDS.isEmpty()) {
            return 1;
        }

        //更新抽牌堆
        Card card = BattleInformation.DRAW_CARDS.get(0);
        BattleInformation.DRAW_CARDS.remove(0);
        update();
        FXGL.spawn("draw", new SpawnData()
                .put("boxNum", boxNum)
                .put("card", card)
        );
        CARD_BOX_STATUS[boxNum - 1] = 1;
        return 0;
    }

    //获取最近的空选牌框
    public static int nearEmptyBox() {
        for (int i = 0; i < CARD_BOX_STATUS.length; i++) {
            if (CARD_BOX_STATUS[i] == 0) {
                //CARD_BOX_STATUS[i] = 1;
                return i + 1;
            }
        }
        return -1;
    }
}
