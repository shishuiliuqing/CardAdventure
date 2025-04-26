package com.hjc.CardAdventure.Utils;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.texture.Texture;
import com.hjc.CardAdventure.pojo.BattleInformation;
import com.hjc.CardAdventure.pojo.Role;
import com.hjc.CardAdventure.pojo.card.Card;
import com.hjc.CardAdventure.pojo.enemy.Enemy;
import com.hjc.CardAdventure.pojo.player.Player;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.ArrayList;

import static com.hjc.CardAdventure.Global.*;
import static com.hjc.CardAdventure.Global.CARD_DISPLAY.*;
import static com.hjc.CardAdventure.Global.GAME_SETTING.APP_HEIGHT;
import static com.hjc.CardAdventure.Global.GAME_SETTING.APP_WITH;

public class EffectUtils {
    private EffectUtils() {
    }

    //牌堆卡牌转移特效
    public static void CircleToCards(double fromX, double fromY, double toX, double toY, Color color, ArrayList<Card> cards) {
        //生成一个圆
        Circle circle = new Circle(20, color);
        circle.setCenterX(fromX);
        circle.setCenterY(fromY);
        Entity c = FXGL.entityBuilder().view(circle).buildAndAttach();

        TranslateTransition tt = new TranslateTransition(Duration.seconds(0.3), circle);
        tt.setToX(toX - fromX);
        tt.setToY(toY - fromY);
        tt.setOnFinished(e -> {
            c.removeFromWorld();
            CardsUtils.updateCards(cards);
        });
        tt.play();
    }

    //获取牌堆位置
    public static double[] getCardsLocal(ArrayList<Card> cards) {
        double[] location = new double[2];
        if (cards == BattleInformation.ABANDON_CARDS) {
            //外框偏移量
            double outXMove = APP_WITH - CARD_BOX_WIDTH;
            double outYMove = APP_HEIGHT - CARD_BOX_HEIGHT;
            //内框偏移量
            double inXMove = outXMove + 30 / PROPORTION;
            double inYMove = outYMove + 75 / PROPORTION;
            //圆心所在位置
            location[0] = inXMove + CARD_WIDTH / 2;
            location[1] = inYMove + CARD_HEIGHT / 2;
        } else if (cards == BattleInformation.DRAW_CARDS) {
            location[0] = 30 / PROPORTION + CARD_WIDTH / 2;
            location[1] = APP_HEIGHT - CARD_BOX_HEIGHT + 75 / PROPORTION + CARD_HEIGHT / 2;
        } else {
            location[0] = 1825;
            location[1] = 630;
        }
        return location;
    }

    //效果文字展示之效果结束
    public static void textEffectEnd(String effectName, Role role) {
        //获取角色位置
        double[] location = getLocal(role);

        //实体创建
        Entity entity = FXGL.entityBuilder().buildAndAttach();
        Rectangle rectangle = new Rectangle(200, 70, Color.rgb(0, 0, 0, 0));
        StackPane stackPane = new StackPane(rectangle);
        //文本创建
        Text text = EntityUtils.getText(effectName + "效果结束",
                "华文行楷", 40,
                Color.WHITE);
        stackPane.getChildren().add(text);
        EntityUtils.nodeMove(stackPane, location[0] + 20, location[1] + 20);
        entity.getViewComponent().addChild(stackPane);

        //创建文本消失动画
        FadeTransition ft = new FadeTransition(Duration.seconds(0.5), stackPane);
        ft.setToValue(0);

        //文本缩小动画
        ScaleTransition st = new ScaleTransition(Duration.seconds(0.5), stackPane);
        st.setToX(0.9);
        st.setToY(0.9);

        ParallelTransition pt = new ParallelTransition(ft, st);
        pt.setOnFinished(e -> entity.removeFromWorld());
        pt.play();
    }

    //效果文字展示之文字放大
    public static void textBigger(String content, Role role, Color color) {
        //获取角色位置
        double[] location = getLocal(role);

        //实体创建
        Entity entity = FXGL.entityBuilder().buildAndAttach();
        Rectangle rectangle = new Rectangle(200, 70, Color.rgb(0, 0, 0, 0));
        StackPane stackPane = new StackPane(rectangle);
        //文本创建
        Text text = EntityUtils.getText(content,
                "华文行楷", 30,
                color);
        stackPane.getChildren().add(text);
        EntityUtils.nodeMove(stackPane, location[0] + 20, location[1] + 20);
        entity.getViewComponent().addChild(stackPane);

        //创建文本消失动画
        FadeTransition ft = new FadeTransition(Duration.seconds(0.5), stackPane);
        ft.setToValue(0);

        //文本缩小动画
        ScaleTransition st = new ScaleTransition(Duration.seconds(0.5), stackPane);
        st.setToX(1.8);
        st.setToY(1.8);

        ParallelTransition pt = new ParallelTransition(ft, st);
        pt.setOnFinished(e -> entity.removeFromWorld());
        pt.play();
    }

    //数值效果展示
    public static void displayValue(int value, Role role, Color color, String operator) {
        //获取角色位置
        double[] location = getLocal(role);

        //实体创建
        Entity entity = FXGL.entityBuilder().buildAndAttach();
        Rectangle rectangle = new Rectangle(200, 70, Color.rgb(0, 0, 0, 0));
        StackPane stackPane = new StackPane(rectangle);
        //文本创建
        Text text = EntityUtils.getText(operator + " " + value,
                "华文琥珀", 50,
                color);
        stackPane.getChildren().add(text);
        EntityUtils.nodeMove(stackPane, location[0] + 100, location[1] + 20);
        entity.getViewComponent().addChild(stackPane);

        //创建文本消失动画
        FadeTransition ft = new FadeTransition(Duration.seconds(0.5), stackPane);
        ft.setToValue(0);

        //创建文本移动动画
        TranslateTransition tt = new TranslateTransition(Duration.seconds(0.5), stackPane);
        tt.setToY(200);

        ParallelTransition pt = new ParallelTransition(ft, tt);
        pt.setOnFinished(e -> entity.removeFromWorld());
        pt.play();
    }

    //获得护盾
    public static void getArmor(Role role) {
        double[] location = getLocal(role);
        //创建动画实体
        Entity entity = FXGL.entityBuilder().buildAndAttach();
        Texture armorTexture = FXGL.texture(getTextureAddress(EFFECT_IMG_ADDRESS, "armor"), 180, 180);
        EntityUtils.nodeMove(armorTexture, location[0] + 30, location[1] - 50);
        entity.getViewComponent().addChild(armorTexture);

        //透明动画
        FadeTransition ft = new FadeTransition(Duration.seconds(0.2), armorTexture);
        ft.setToValue(0);

        //移动动画
        TranslateTransition tt = new TranslateTransition(Duration.seconds(0.2), armorTexture);
        tt.setToY(location[1]);

        ParallelTransition pt = new ParallelTransition(ft, tt);
        pt.setOnFinished(e -> {
            //entity.removeFromWorld();
        });
        pt.play();
    }

    //图片放大后透明动画
    public static void amplifyAndDisappear(Role role, double x, double y, String textureAddress) {
        double[] location = getLocal(role);

        //获取图片
        //创建实体
        Entity entity = FXGL.entityBuilder().buildAndAttach();
        Texture texture = FXGL.texture(getTextureAddress(EFFECT_IMG_ADDRESS, textureAddress), 180, 180);
        texture.setScaleX(0);
        texture.setScaleY(0);
        EntityUtils.nodeMove(texture, location[0] + x, location[1] + y);
        entity.getViewComponent().addChild(texture);

        //放大动画
        ScaleTransition st = new ScaleTransition(Duration.seconds(0.2), texture);
        st.setToX(1);
        st.setToY(1);

        //透明动画
        FadeTransition ft = new FadeTransition(Duration.seconds(0.2), texture);
        ft.setToValue(0.5);

        ParallelTransition pt = new ParallelTransition(st, ft);
        pt.setOnFinished(e -> entity.removeFromWorld());
        pt.play();
    }

    //特效展示
    public static void displayEffect(String pictureName, int num, double seconds, double scale, Role role, double x, double y) {
        //获取角色地址
        double[] location = getLocal(role);

        AnimationChannel ac = new AnimationChannel(FXGL.image(getTextureAddress(EFFECT_IMG_ADDRESS, pictureName)), Duration.seconds(seconds), num);
        AnimatedTexture at = new AnimatedTexture(ac);
        at.setScaleX(scale);
        at.setScaleY(scale);
        EntityUtils.nodeMove(at, location[0] + x, location[1] + y);
        //实体创建
        Entity entity = FXGL.entityBuilder().view(at).buildAndAttach();

        at.setOnCycleFinished(entity::removeFromWorld);
        at.play();
    }

    //获取角色位置
    private static double[] getLocal(Role role) {
        double[] doubles = new double[2];
        if (role instanceof Player) {
            doubles[0] = 450;
            doubles[1] = 300;
        } else {
            Enemy enemy = (Enemy) role;
            doubles[0] = 550 + 215 * enemy.getLocation();
            doubles[1] = 300;
        }

        return doubles;
    }
}
