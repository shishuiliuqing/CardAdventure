package com.hjc.CardAdventure.component.information;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.Texture;
import com.hjc.CardAdventure.Utils.EntityUtils;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import static com.hjc.CardAdventure.Global.*;
public class BloodComponent extends Component {

    @Override
    public void onAdded() {
        //血条图片
        Texture boolTexture = FXGL.texture(getTextureAddress(INFORMATION_ADDRESS,"playerBlood"),30,30);
        EntityUtils.nodeMove(boolTexture,270,15);
        entity.getViewComponent().addChild(boolTexture);

        //血量文本显示
        Label label = EntityUtils.getLabel(100,0,
                PLAYER.player.getBlood() + " / " + PLAYER.player.getMaxBlood(),
                "微软雅黑",25,
                Color.BLACK);
        EntityUtils.nodeMove(label,315,15);
        entity.getViewComponent().addChild(label);
    }

    //更新血量
    public void update() {
        entity.getViewComponent().clearChildren();
        onAdded();
    }
}
