package com.hjc.CardAdventure.component.battle;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.hjc.CardAdventure.Global;
import com.hjc.CardAdventure.Utils.EntityUtils;
import com.hjc.CardAdventure.component.information.TipBarComponent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import static com.hjc.CardAdventure.Global.*;
import static com.hjc.CardAdventure.Global.CARD_USE.*;

public class ActionOverComponent extends Component {

    private static AnimatedTexture animatedTexture;

    @Override
    public void onAdded() {
        //加载沙漏序列帧图片
        AnimationChannel ac = new AnimationChannel(FXGL.image(getTextureAddress(BATTLE_ADDRESS, "hourglass")), Duration.seconds(1), 62);
        //生成组件
        animatedTexture = new AnimatedTexture(ac);
        animatedTexture.setScaleX(0.2);
        animatedTexture.setScaleY(0.2);
        EntityUtils.nodeMove(animatedTexture, 1480, -340);
        entity.getViewComponent().addChild(animatedTexture);

        //生成隐形矩形
        Rectangle rectangle = new Rectangle(70, 70, Color.rgb(0, 0, 0, 0));
        EntityUtils.nodeMove(rectangle, GAME_SETTING.APP_WITH - 70, 0);
        entity.getViewComponent().addChild(rectangle);

        entity.getViewComponent().addEventHandler(MouseEvent.MOUSE_ENTERED, e -> lookInformation());
        entity.getViewComponent().addOnClickHandler(e -> onOver());
    }

    //结束按钮查看
    private void lookInformation() {
        TipBarComponent.update("结束回合");
    }

    //结束动画
    public void onOver() {
        if (isPlayer) {
            //动画播放
            animatedTexture.play();
        }
    }
}
