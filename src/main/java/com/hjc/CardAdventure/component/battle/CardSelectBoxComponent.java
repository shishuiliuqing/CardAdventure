package com.hjc.CardAdventure.component.battle;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.Texture;
import com.hjc.CardAdventure.Utils.EntityUtils;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static com.hjc.CardAdventure.Global.GAME_SETTING.*;
import static com.hjc.CardAdventure.Global.CARD_DISPLAY.*;
import static com.hjc.CardAdventure.Global.*;

public class CardSelectBoxComponent extends Component {

    @Override
    public void onAdded() {
        //出牌区边框
        Rectangle box = new Rectangle(APP_WITH,CARD_BOX_HEIGHT + 5, Color.BLACK);
        EntityUtils.nodeMove(box,0,APP_HEIGHT - CARD_BOX_HEIGHT - 5);
        entity.getViewComponent().addChild(box);

        //人物属性区边框
        Texture playerAttributeBox = FXGL.texture(getTextureAddress(BATTLE_ADDRESS,"playerAttributeBox"),CARD_BOX_HEIGHT,CARD_BOX_HEIGHT);
        EntityUtils.nodeMove(playerAttributeBox,CARD_BOX_WIDTH + 10,APP_HEIGHT - CARD_BOX_HEIGHT);
        entity.getViewComponent().addChild(playerAttributeBox);

        //出牌区界面
        //第一个选择框
        Texture cardBox = FXGL.texture(getTextureAddress(BATTLE_ADDRESS,"cardBox"),CARD_BOX_WIDTH,CARD_BOX_HEIGHT);
        EntityUtils.nodeMove(cardBox,CARD_BOX_WIDTH + 10 + CARD_BOX_HEIGHT,APP_HEIGHT - CARD_BOX_HEIGHT);
        entity.getViewComponent().addChild(cardBox);

        //第二到十选择框
        for(int i = 2; i <= 10 ; i++) {
            Texture cardBoxCopy = cardBox.copy();
            EntityUtils.nodeMove(cardBoxCopy,10 + CARD_BOX_HEIGHT + CARD_BOX_WIDTH * i,APP_HEIGHT - CARD_BOX_HEIGHT);
            entity.getViewComponent().addChild(cardBoxCopy);
        }
    }
}
