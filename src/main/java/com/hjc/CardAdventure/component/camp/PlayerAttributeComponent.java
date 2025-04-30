package com.hjc.CardAdventure.component.camp;

import com.almasb.fxgl.entity.component.Component;
import com.hjc.CardAdventure.CardAdventureApp;
import com.hjc.CardAdventure.effect.Effect;
import com.hjc.CardAdventure.pojo.player.Level;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

import static com.hjc.CardAdventure.Global.GAME_SETTING.APP_WITH;
import static com.hjc.CardAdventure.Global.PLAYER.player;

public class PlayerAttributeComponent extends Component {
    @Override
    public void onAdded() {
        Label label = new Label(Level.getLV(player.getExperience()) + "      EXP：" + Level.experienceDisplay(player.getExperience()) + Effect.NEW_LINE + "当前玩家属性：\n" + player.getAttribute().displayAttribute() + Effect.NEW_LINE);
        label.setMaxSize(600, 400);
        label.setTranslateX(APP_WITH - 600 - 10);
        label.setTranslateY(170);
        label.setFont(new Font("微软雅黑", 35));

        entity.getViewComponent().addChild(label);
    }
}
