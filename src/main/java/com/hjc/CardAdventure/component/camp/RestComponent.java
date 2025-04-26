package com.hjc.CardAdventure.component.camp;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.hjc.CardAdventure.CardAdventureApp;
import com.hjc.CardAdventure.Utils.CampUtils;
import com.hjc.CardAdventure.component.information.BarComponent;
import com.hjc.CardAdventure.component.information.BloodComponent;
import com.hjc.CardAdventure.component.information.TipBarComponent;
import com.hjc.CardAdventure.effect.Effect;
import com.hjc.CardAdventure.entity.CampEntity;
import com.hjc.CardAdventure.entity.InformationEntity;
import com.hjc.CardAdventure.pojo.environment.InsideInformation;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import static com.hjc.CardAdventure.Global.GAME_SETTING.APP_HEIGHT;
import static com.hjc.CardAdventure.Global.GAME_SETTING.APP_WITH;
import static com.hjc.CardAdventure.Global.CAMP.*;
import static com.hjc.CardAdventure.Global.PLAYER.player;

public class RestComponent extends Component {

    @Override
    public void onAdded() {
        addUI();

        entity.getViewComponent().addOnClickHandler(e -> goRest());
        entity.getViewComponent().addEventHandler(MouseEvent.MOUSE_ENTERED,e->lookInformation());
    }

    private void lookInformation() {
        String text = "休息" + Effect.NEW_LINE;
        text += "恢复1/3的生命值" + Effect.NEW_LINE;
        text += "年轻就是好，一天到晚想睡就睡\n\n          --by某位不愿意透露姓名的开发者";
        TipBarComponent.update(text);
    }

    //进行回血
    private void goRest() {
        //回血
        rest();

        //透明全屏背景，防止玩家继续点击操作
        Rectangle back = new Rectangle(APP_WITH, APP_WITH, Color.rgb(0, 0, 0, 0));
        Entity rest = FXGL.entityBuilder().view(back).buildAndAttach();

        Rectangle rectangle = new Rectangle(APP_WITH, APP_HEIGHT - 70);
        rectangle.setTranslateY(70);
        rest.getViewComponent().addChild(rectangle);

        FadeTransition ft = new FadeTransition(Duration.seconds(1), rectangle);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.setOnFinished(e -> {
            rest.removeFromWorld();
            //不允许休息
            canRest = false;
            //更新玩家血量
            InformationEntity.playerBlood.getComponent(BloodComponent.class).update();
            //时间状态更新
            InsideInformation.turnTimeStatus();
            //更新信息栏
            InformationEntity.informationBar.getComponent(BarComponent.class).update();
            //删除营地
            CampEntity.clearCampEntities();
            //更新营地
            CampEntity.initCampEntities();
            //张眼动画
            openEye();
        });
        ft.play();
    }

    //回血
    private void rest() {
        int value = player.getMaxBlood() / 3;
        player.setBlood(Math.min(player.getMaxBlood(), player.getBlood() + value));
    }

    //张眼动画
    private void openEye() {
        Rectangle back = new Rectangle(APP_WITH, APP_WITH, Color.rgb(0, 0, 0, 0));
        Entity rest = FXGL.entityBuilder().view(back).buildAndAttach();

        Rectangle rectangle = new Rectangle(APP_WITH, APP_HEIGHT - 70);
        rectangle.setTranslateY(70);
        rest.getViewComponent().addChild(rectangle);

        ScaleTransition st = new ScaleTransition(Duration.seconds(0.5), rectangle);
        st.setToX(0);
        st.setToY(0);

        FadeTransition ft = new FadeTransition(Duration.seconds(0.5), rectangle);
        ft.setToValue(0);

        ParallelTransition pt = new ParallelTransition(st, ft);
        pt.setOnFinished(e -> rest.removeFromWorld());
        pt.play();
    }

    //添加按钮
    private void addUI() {
        CampUtils.createButton(entity, APP_WITH / 2.0 - 550, 800, "rest", "休息", Color.GREEN);
    }
}
