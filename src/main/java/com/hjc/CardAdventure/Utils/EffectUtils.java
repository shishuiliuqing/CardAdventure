package com.hjc.CardAdventure.Utils;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.hjc.CardAdventure.pojo.Role;
import com.hjc.CardAdventure.pojo.enemy.Enemy;
import com.hjc.CardAdventure.pojo.player.Player;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class EffectUtils {
    private EffectUtils() {
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

        //文本放大动画
        ScaleTransition st = new ScaleTransition(Duration.seconds(0.5), stackPane);
        st.setToX(0.9);
        st.setToY(0.9);

        ParallelTransition pt = new ParallelTransition(ft, st);
        pt.setOnFinished(e -> entity.removeFromWorld());
        pt.play();
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
