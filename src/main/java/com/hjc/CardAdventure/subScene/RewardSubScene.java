package com.hjc.CardAdventure.subScene;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.scene.SubScene;
import com.almasb.fxgl.texture.Texture;
import com.hjc.CardAdventure.Utils.EntityUtils;
import com.hjc.CardAdventure.Utils.OtherUtils;
import com.hjc.CardAdventure.component.information.GoldComponent;
import com.hjc.CardAdventure.entity.CampEntity;
import com.hjc.CardAdventure.entity.InformationEntity;
import com.hjc.CardAdventure.pojo.Reward;
import com.hjc.CardAdventure.pojo.card.Card;
import com.hjc.CardAdventure.pojo.card.CardQuality;
import com.hjc.CardAdventure.pojo.environment.InsideInformation;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.ArrayList;

import static com.hjc.CardAdventure.Global.GAME_SETTING.*;
import static com.hjc.CardAdventure.Global.*;
import static com.hjc.CardAdventure.Global.CARD_DISPLAY.*;

public class RewardSubScene extends SubScene {
    public static Reward reward;

    public RewardSubScene() {
        //灰色透明背景，禁止操作
        Rectangle background = new Rectangle(APP_WITH, APP_HEIGHT, Color.rgb(80, 80, 80, 0.7));
        getContentRoot().getChildren().add(background);

        //前进按钮设置
        Texture forward = FXGL.texture(getTextureAddress(SUB_SCENE_IMG_ADDRESS, "forward"), 200, 200);
        EntityUtils.nodeMove(forward, 1700, 800);
        forward.setOnMouseClicked(e -> {
            goForward();
        });
        getContentRoot().getChildren().add(forward);

        //获得奖励文本
        Label rewardText = EntityUtils.getLabel(500, 50, "获 得 奖 励 !",
                "华文行楷", 83,
                Color.YELLOW);
        EntityUtils.nodeMove(rewardText, (APP_WITH - 500) / 2.0, 70);
        getContentRoot().getChildren().add(rewardText);

        //奖励框
        Texture box = FXGL.texture(getTextureAddress(SUB_SCENE_IMG_ADDRESS, "rewardBox"), 600, 200);
        EntityUtils.nodeMove(box, (APP_WITH - 600) / 2.0, 170);
        getContentRoot().getChildren().add(box);
        //透明动画
        FadeTransition ft = new FadeTransition(Duration.seconds(0.5), box);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.setOnFinished(e -> {
            //矩形背景框
            Rectangle rBox = new Rectangle(600, 1, Color.rgb(255, 255, 255, 0.9));
            EntityUtils.nodeMove(rBox, (APP_WITH - 600) / 2.0, 250);
            getContentRoot().getChildren().add(rBox);

            //移动动画
            TranslateTransition tt = new TranslateTransition(Duration.seconds(0.5), rBox);
            tt.setToY(600);

            //规模变化动画
            ScaleTransition st = new ScaleTransition(Duration.seconds(0.5), rBox);
            st.setToY(700);

            //组合动画
            ParallelTransition pt = new ParallelTransition(tt, st);
            pt.setOnFinished(e1 -> initReward());
            pt.play();
        });
        ft.play();
    }

    //前进
    private void goForward() {
        FXGL.getSceneService().popSubScene();

        if (reward.getExperience() == 0) {
            //进入下一时间状态
            OtherUtils.nextTime();
        } else {
            //获得经验
            ExperienceSubScene.flag = 0;
            FXGL.getSceneService().pushSubScene(new ExperienceSubScene(reward.getExperience()));
        }
    }

    //展示奖励
    private void initReward() {
        //清除奖励框内容
        for (int i = 5; i < getContentRoot().getChildren().size(); ) {
            getContentRoot().getChildren().remove(i);
        }

        //位置
        int location = 0;

        //初始化卡牌奖励
        location = initRewardCards(location);
        //初始化金币
        location = initRewardGold(location);
    }

    //生成褐色背景框
    private Pane getBackPane(int location) {
        //生成矩形褐色背景框
        Rectangle rBox = new Rectangle(560, 70, Color.valueOf("#F18E69"));
        //生成容器
        Pane pane = new Pane(rBox);
        pane.setPrefSize(560, 70);
        EntityUtils.nodeMove(pane, (APP_WITH - 560) / 2.0, 260 + 80 * location);
        getContentRoot().getChildren().add(pane);
        //添加鼠标进入选择
        pane.setOnMouseEntered(e -> {
            Rectangle r = (Rectangle) pane.getChildren().get(0);
            r.setFill(Color.RED);
        });
        //添加鼠标离开选择
        pane.setOnMouseExited(e -> {
            Rectangle r = (Rectangle) pane.getChildren().get(0);
            r.setFill(Color.valueOf("#F18E69"));
        });
        return pane;
    }

    //展示卡牌奖励
    private int initRewardCards(int location) {
        if (reward.getCards() == null) return location;
        for (int i = 0; i < reward.getCards().size(); i++) {
            ArrayList<Card> cards = reward.getCards().get(i);

            Pane pane = getBackPane(location);
            //牌背颜色决定
            String cardBackColor = switch (judgeCardsQuality(cards)) {
                case 0 -> "cardBackWhite";
                case 1 -> "cardBackBlueC";
                default -> "cardYellow";
            };
            //生成牌背
            Texture cardBack = FXGL.texture(getTextureAddress(CARD_BACK_ADDRESS, cardBackColor), 40, 60);
            EntityUtils.nodeMove(cardBack, 5, 5);
            pane.getChildren().add(cardBack);
            //生成文本
            Text text = EntityUtils.getText("可从多张卡中选择一张加入牌组",
                    "华文行楷", 35,
                    Color.WHITE);
            EntityUtils.nodeMove(text, 55, 45);
            pane.getChildren().add(text);
            //添加鼠标点击选择
            pane.setOnMouseClicked(e -> selectCards(cards));

            location++;
        }

        return location;
    }

    //展示金币奖励
    private int initRewardGold(int location) {
        if (reward.getGolds() == null) return location;

        for (int i = 0; i < reward.getGolds().size(); i++) {
            int gold = reward.getGolds().get(i);

            Pane pane = getBackPane(location);
            //获得金币图片
            Texture goldTexture = FXGL.texture(getTextureAddress(INFORMATION_ADDRESS, "gold"), 60, 60);
            EntityUtils.nodeMove(goldTexture, 5, 5);
            pane.getChildren().add(goldTexture);
            //金币数量文本
            Text goldText = EntityUtils.getText(String.valueOf(gold),
                    "华文行楷", 35,
                    Color.YELLOW);
            EntityUtils.nodeMove(goldText, 80, 45);
            pane.getChildren().add(goldText);
            //点击后获得金币
            pane.setOnMouseClicked(e -> {
                PLAYER.gold += gold;
                InformationEntity.gold.getComponent(GoldComponent.class).update();
                reward.getGolds().remove(Integer.valueOf(gold));
                initReward();
            });

            location++;
        }

        return location;
    }

    //选择卡牌
    private void selectCards(ArrayList<Card> cards) {
        //生成一个大矩形框，不允许其他操作
        Rectangle r = new Rectangle(APP_WITH, APP_HEIGHT - 70, Color.rgb(0, 0, 0, 0.8));
        r.setY(70);
        getContentRoot().getChildren().add(r);

        //卡牌文本信息描述
        Label label = EntityUtils.getLabel(450, 800, "",
                "华文行楷", 20,
                Color.WHITE);
        EntityUtils.nodeMove(label, 1500, 70);
        getContentRoot().getChildren().add(label);

        //装载所有卡牌的容器
        Pane pane = new Pane();
        pane.setMaxSize(CARD_WIDTH + (cards.size() - 1) * 280, CARD_HEIGHT);

        for (int i = 0; i < cards.size(); i++) {
            Card card = cards.get(i);
            //获得卡牌实体
            Pane cardPane = EntityUtils.createCard(card);
            //卡牌描述查看
            cardPane.setOnMouseEntered(e ->
                    label.setText(card.cardDescription())
            );
            //移动卡牌实体
            EntityUtils.nodeMove(cardPane, 280 * i, 0);
            //添加点击后获得
            cardPane.setOnMouseClicked(e -> {
                PLAYER.cards.add(card);
                reward.getCards().remove(cards);
                initReward();
            });
            //容器添加卡牌
            pane.getChildren().add(cardPane);
        }

        //居中容器
        Rectangle rectangle = new Rectangle(APP_WITH, CARD_HEIGHT, Color.rgb(0, 0, 0, 0));
        StackPane stackPane = new StackPane(rectangle);
        stackPane.setTranslateY(300);
        stackPane.getChildren().add(pane);
        getContentRoot().getChildren().add(stackPane);

        //生成关闭按钮
        Texture close = FXGL.texture(getTextureAddress(BATTLE_ADDRESS, "buttonDark"), 270 / 1.5, 98 / 1.5);
        StackPane stackPane1 = new StackPane(close);
        EntityUtils.nodeMove(stackPane1, (APP_WITH - 270 / 1.5) / 2, 600);
        Text text = EntityUtils.getText("关闭",
                "华文行楷", 50,
                Color.WHITE);
        stackPane1.getChildren().add(text);
        //添加点击
        stackPane1.setOnMouseClicked(e -> initReward());
        //添加变色
        stackPane1.setOnMouseEntered(e -> {
            stackPane1.getChildren().remove(0);
            stackPane1.getChildren().add(0, close.multiplyColor(Color.RED));
        });
        stackPane1.setOnMouseExited(e -> {
            stackPane1.getChildren().remove(0);
            stackPane1.getChildren().add(0, close);
        });
        getContentRoot().getChildren().add(stackPane1);
    }

    //判断卡组品质
    private static int judgeCardsQuality(ArrayList<Card> cards) {
        //判断有无白卡
        for (Card card : cards) {
            if (card.getCardQuality() == CardQuality.WHITE) return 0;
        }

        //判断有无蓝卡
        for (Card card : cards) {
            if (card.getCardQuality() == CardQuality.BLUE) return 1;
        }

        return 2;
    }
}
