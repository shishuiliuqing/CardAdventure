package com.hjc.CardAdventure.component.card;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.Texture;
import com.hjc.CardAdventure.Utils.EntityUtils;
import com.hjc.CardAdventure.entity.BattleEntity;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import static com.hjc.CardAdventure.Global.CARD_USE.produce;
import static com.hjc.CardAdventure.Global.GAME_SETTING.*;
import static com.hjc.CardAdventure.Global.*;

public class ProduceComponent extends Component {
    @Override
    public void onAdded() {
        //创建使用按钮
        Texture button = FXGL.texture(produce ? getTextureAddress(BATTLE_ADDRESS, "buttonLight") : getTextureAddress(BATTLE_ADDRESS, "buttonDark"), 270 / 2.0, 98 / 2.0);
        EntityUtils.nodeMove(button, APP_WITH - 150, APP_HEIGHT - 250);
        entity.getViewComponent().addChild(button);

        //创建使用文本
        Text use = EntityUtils.getText("使    用",
                "华文行楷", 29,
                Color.BLACK);
        EntityUtils.nodeMove(use, APP_WITH - 150 + 25, APP_HEIGHT - 250 + 35);
        entity.getViewComponent().addChild(use);
    }

    //更新方法
    public void update(boolean isLight) {
        entity.removeFromWorld();
        produce = isLight;
        BattleEntity.produce = FXGL.spawn("produce");
    }
}
