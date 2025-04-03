package com.hjc.CardAdventure.component.card;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.Texture;
import com.hjc.CardAdventure.Utils.EntityUtils;
import com.hjc.CardAdventure.entity.BattleEntity;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import static com.hjc.CardAdventure.Global.BATTLE_ADDRESS;
import static com.hjc.CardAdventure.Global.CARD_USE.abandon;
import static com.hjc.CardAdventure.Global.GAME_SETTING.APP_HEIGHT;
import static com.hjc.CardAdventure.Global.GAME_SETTING.APP_WITH;
import static com.hjc.CardAdventure.Global.getTextureAddress;


public class AbandonComponent extends Component {
    @Override
    public void onAdded() {
        //创建使用按钮
        Texture button = FXGL.texture(abandon ? getTextureAddress(BATTLE_ADDRESS, "buttonLight") : getTextureAddress(BATTLE_ADDRESS, "buttonDark"), 270 / 2.0, 98 / 2.0);
        EntityUtils.nodeMove(button, APP_WITH - 150, APP_HEIGHT - 309);
        entity.getViewComponent().addChild(button);

        //创建使用文本
        Text abandonText = EntityUtils.getText("弃    置",
                "华文行楷", 29,
                Color.BLACK);
        EntityUtils.nodeMove(abandonText, APP_WITH - 150 + 25, APP_HEIGHT - 309 + 35);
        entity.getViewComponent().addChild(abandonText);
    }

    //更新
    public void update(boolean isLight) {
        abandon = isLight;
        entity.removeFromWorld();
        BattleEntity.abandon = FXGL.spawn("abandon");
    }
}
