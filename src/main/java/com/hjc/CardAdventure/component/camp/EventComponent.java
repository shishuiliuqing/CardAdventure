package com.hjc.CardAdventure.component.camp;

import com.almasb.fxgl.entity.component.Component;
import com.hjc.CardAdventure.Utils.CampUtils;
import javafx.scene.paint.Color;

import static com.hjc.CardAdventure.Global.GAME_SETTING.APP_WITH;

public class EventComponent extends Component {

    @Override
    public void onAdded() {
        addUI();
    }

    private void addUI() {
        CampUtils.createButton(entity, APP_WITH / 2.0 + 350, 800, "event", "事件", Color.BLUE);
    }
}
