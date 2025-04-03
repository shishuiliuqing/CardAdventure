package com.hjc.CardAdventure.component.battle;

import com.almasb.fxgl.entity.component.Component;
import com.hjc.CardAdventure.CardAdventureApp;
import com.hjc.CardAdventure.Utils.EntityUtils;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.text.Font;

import static com.hjc.CardAdventure.Global.GAME_SETTING.*;
import static com.hjc.CardAdventure.Global.CARD_DISPLAY.*;
import static com.hjc.CardAdventure.Global.PLAYER.player;

public class AttributeComponent extends Component {
    @Override
    public void onAdded() {
        //创建属性文本
        TextArea attributeText = new TextArea("你当前的属性\n" + player.getAttribute().displayAttribute());
        double scaleBig = 8;
        attributeText.setPrefSize(CARD_BOX_HEIGHT - 2 * scaleBig, CARD_BOX_HEIGHT - 2 * scaleBig - 80);
        EntityUtils.nodeMove(attributeText,CARD_BOX_WIDTH + 10 + scaleBig,APP_HEIGHT - CARD_BOX_HEIGHT + scaleBig + 70);
        attributeText.setFont(new Font("华文仿宋", 15.4));
        attributeText.setEditable(false);
        entity.getViewComponent().addChild(attributeText);
    }

    //更新属性
    public void update() {
        entity.getViewComponent().clearChildren();
        onAdded();
    }
}
