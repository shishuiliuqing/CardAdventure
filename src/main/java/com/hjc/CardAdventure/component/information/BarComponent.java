package com.hjc.CardAdventure.component.information;

import com.almasb.fxgl.entity.component.Component;
import com.hjc.CardAdventure.Utils.EntityUtils;
import com.hjc.CardAdventure.pojo.environment.InsideInformation;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static com.hjc.CardAdventure.Global.PLAYER.player;
import static com.hjc.CardAdventure.Global.GAME_SETTING.*;
import static com.hjc.CardAdventure.Global.BAR_HEIGHT;

public class BarComponent extends Component {

    @Override
    public void onAdded() {
        //创建信息框
        Rectangle informationBar = new Rectangle(APP_WITH,BAR_HEIGHT, Color.valueOf("#aec6cf"));
        entity.getViewComponent().addChild(informationBar);

        //名字文本
        Label nameText = EntityUtils.getLabel(220, 0,
                player.getName(),
                "微软雅黑",30,
                Color.BLACK);
        EntityUtils.nodeMove(nameText,10,10);
        entity.getViewComponent().addChild(nameText);

        //局内信息文本
        Label insideInformation = EntityUtils.getLabel(500, 0,
                InsideInformation.insideEnvironmentToString(),
                "微软雅黑", 30,
                Color.BLACK);
        EntityUtils.nodeMove(insideInformation,900,10);
        entity.getViewComponent().addChild(insideInformation);

        //当前行动对象框
        Rectangle nowAction = new Rectangle(50,50,Color.BLACK);
        EntityUtils.nodeMove(nowAction,1400,10);
        entity.getViewComponent().addChild(nowAction);
    }

    //更新局内信息
    public void update() {
        entity.getViewComponent().clearChildren();
        onAdded();
    }
}
