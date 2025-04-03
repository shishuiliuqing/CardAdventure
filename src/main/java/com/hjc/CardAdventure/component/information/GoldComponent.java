package com.hjc.CardAdventure.component.information;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.Texture;
import com.hjc.CardAdventure.Utils.EntityUtils;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import static com.hjc.CardAdventure.Global.*;
import static com.hjc.CardAdventure.Global.PLAYER.*;

public class GoldComponent extends Component {
    @Override
    public void onAdded() {
        //金币图片
        Texture goldTexture = FXGL.texture(getTextureAddress(INFORMATION_ADDRESS,"gold"),35,35);
        EntityUtils.nodeMove(goldTexture,460,15);
        entity.getViewComponent().addChild(goldTexture);

        //金币数字文本
        Label label = EntityUtils.getLabel(100,0,
                String.valueOf(gold),
                "微软雅黑",25,
                Color.BLACK);
        EntityUtils.nodeMove(label,505,15);
        entity.getViewComponent().addChild(label);
    }

    public void update() {
        entity.getViewComponent().clearChildren();
        onAdded();
    }
}
