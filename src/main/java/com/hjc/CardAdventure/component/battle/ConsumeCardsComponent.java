package com.hjc.CardAdventure.component.battle;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.hjc.CardAdventure.Utils.EntityUtils;
import com.hjc.CardAdventure.component.information.TipBarComponent;
import com.hjc.CardAdventure.pojo.BattleInformation;
import com.hjc.CardAdventure.subScene.LookCardsSubScene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

public class ConsumeCardsComponent extends Component {
    @Override
    public void onAdded() {
        //添加组件
        addComponent();

        entity.getViewComponent().addEventHandler(MouseEvent.MOUSE_ENTERED, e->lookInformation());
        entity.getViewComponent().addOnClickHandler(e->lookCards());
    }

    private void addComponent() {
        //生成圆心数字
        StackPane stackPane = EntityUtils.generateCircleNum(1825, 630,
                25, BattleInformation.CONSUME_CARDS.size(), "#7700bb",
                new Font("微软雅黑", 15));
        entity.getViewComponent().addChild(stackPane);
    }

    //消耗牌堆信息
    private void lookInformation() {
        TipBarComponent.update("消耗牌堆，牌数：" + BattleInformation.CONSUME_CARDS.size());
    }

    //查看消耗牌堆
    private void lookCards() {
        LookCardsSubScene.cards = BattleInformation.CONSUME_CARDS;
        LookCardsSubScene.cardsType = "消耗区";
        FXGL.getSceneService().pushSubScene(new LookCardsSubScene());
    }

    //更新方法
    private void update() {
        entity.getViewComponent().clearChildren();
        addComponent();
    }
}
