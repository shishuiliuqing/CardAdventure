package com.hjc.CardAdventure.component.battle;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.hjc.CardAdventure.CardAdventureApp;
import com.hjc.CardAdventure.Utils.EntityUtils;
import com.hjc.CardAdventure.component.information.TipBarComponent;
import com.hjc.CardAdventure.entityFactory.CardEntityFactory;
import com.hjc.CardAdventure.pojo.BattleInformation;
import com.hjc.CardAdventure.subScene.LookCardsSubScene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import static com.hjc.CardAdventure.Global.GAME_SETTING.*;
import static com.hjc.CardAdventure.Global.CARD_DISPLAY.*;
import static com.hjc.CardAdventure.Global.PLAYER.player;

public class AbandonCardsComponent extends Component {
    @Override
    public void onAdded() {
        //添加组件
        addComponent();

        entity.getViewComponent().addEventHandler(MouseEvent.MOUSE_ENTERED,e->lookInformation());
        entity.getViewComponent().addOnClickHandler(e->lookCards());
    }

    private void addComponent() {
        //外框偏移量
        double outXMove = APP_WITH - CARD_BOX_WIDTH;
        double outYMove = APP_HEIGHT - CARD_BOX_HEIGHT;
        //内框偏移量
        double inXMove = outXMove + 30 / PROPORTION;
        double inYMove = outYMove + 75 / PROPORTION;
        //圆心所在位置
        double cXMove = inXMove + CARD_WIDTH / 2;
        double cYMove = inYMove + CARD_HEIGHT / 2;


        //外框
        Rectangle outBox = new Rectangle(CARD_BOX_WIDTH, CARD_BOX_HEIGHT, Color.valueOf(player.getColorS()));
        EntityUtils.nodeMove(outBox, outXMove, outYMove);
        entity.getViewComponent().addChild(outBox);

        //内框
        Rectangle inBox = new Rectangle(CARD_WIDTH, CARD_HEIGHT, Color.GRAY);
        EntityUtils.nodeMove(inBox, inXMove, inYMove);
        entity.getViewComponent().addChild(inBox);

        //圆心数字
        StackPane stackPane = EntityUtils.generateCircleNum(cXMove, cYMove,
                25, BattleInformation.ABANDON_CARDS.size(), player.getColorS(),
                new Font("微软雅黑", 15));
        entity.getViewComponent().addChild(stackPane);
    }

    //弃牌堆信息
    private void lookInformation() {
        TipBarComponent.update("弃牌堆，牌数：" + BattleInformation.ABANDON_CARDS.size());
    }

    //查看弃牌堆
    private void lookCards() {
        LookCardsSubScene.cards = BattleInformation.ABANDON_CARDS;
        LookCardsSubScene.cardsType = "弃牌区";
        FXGL.getSceneService().pushSubScene(new LookCardsSubScene());
    }

    //更新方法
    public void update() {
        entity.getViewComponent().clearChildren();
        addComponent();
    }
}
