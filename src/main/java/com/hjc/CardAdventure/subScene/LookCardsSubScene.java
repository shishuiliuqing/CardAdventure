package com.hjc.CardAdventure.subScene;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.scene.SubScene;
import com.almasb.fxgl.texture.Texture;
import com.hjc.CardAdventure.CardAdventureApp;
import com.hjc.CardAdventure.Utils.EntityUtils;
import com.hjc.CardAdventure.pojo.card.Card;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;

import static com.hjc.CardAdventure.Global.GAME_SETTING.*;
import static com.hjc.CardAdventure.Global.*;

public class LookCardsSubScene extends SubScene {
    //要展示的牌堆
    public static ArrayList<Card> cards;
    //返回键
    private static final Texture back = FXGL.texture(getTextureAddress(BATTLE_ADDRESS,"back"), 200, 200);
    //牌堆类型
    public static String cardsType = "你好呀";
    //卡牌描述
    public static final Label LABEL = new Label();

    public LookCardsSubScene() {
        //卡牌描述移动
        LABEL.setFont(new Font("华文行楷", 20));
        LABEL.setTextFill(Color.WHITE);
        LABEL.setTranslateX(1350);
        LABEL.setTranslateY(70);
        LABEL.setWrapText(true);
        LABEL.setMaxSize(450, 800);
        getContentRoot().getChildren().add(LABEL);

        //牌堆类型文本
        Label label = new Label(cardsType);
        label.setWrapText(true);
        label.setMaxSize(200, 910);
        label.setTranslateX(10);
        label.setTranslateY(70);
        label.setFont(new Font("华文行楷", 200));
        getContentRoot().getChildren().add(label);

        //背景设置，不允许操作
        Rectangle rectangle = new Rectangle(APP_WITH, APP_HEIGHT, Color.rgb(80, 80, 80, 0.5));
        getContentRoot().getChildren().add(rectangle);

        Pane pane = new Pane();
        ScrollPane scrollPane = new ScrollPane(pane);
        scrollPane.setPrefSize(1000, APP_HEIGHT - 70);
        EntityUtils.nodeMove(scrollPane, (APP_WITH - 1000) * 1.0 / 2 - 200, 70);
        //配置滚动框样式
        scrollPane.getStylesheets().add(getClass().getClassLoader().getResource("scrollPane.css").toExternalForm());
        scrollPane.getStyleClass().add("scroll-pane");
        scrollPane.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        getContentRoot().getChildren().add(scrollPane);

        //设置pane的大小
        pane.setPrefSize(1000, 200 * (cards.size() / 5.0 + 1));
        //添加卡片
        for (int i = 0; i < cards.size(); i++) {
            Card card = cards.get(i);
            Pane cardPane = EntityUtils.createCard(card);
            EntityUtils.nodeMove(cardPane, 10 + (i % 5) * 200, 10 + (i / 5) * 200);
            pane.getChildren().add(cardPane);
        }

        //返回按钮设置
        back.setTranslateX(1700);
        back.setTranslateY(800);
        back.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            FXGL.getSceneService().popSubScene();
            LABEL.setText("");
        });
        getContentRoot().getChildren().add(back);
    }

//    //卡牌描述
//    private void describe(Card card) {
//        LABEL.setText(card.cardToString());
//    }
}
