package com.hjc.CardAdventure.component.camp;

import com.almasb.fxgl.entity.component.Component;
import com.hjc.CardAdventure.Utils.CampUtils;
import com.hjc.CardAdventure.entity.CampEntity;
import com.hjc.CardAdventure.pojo.event.Event;
import javafx.scene.paint.Color;

import static com.hjc.CardAdventure.Global.GAME_SETTING.APP_WITH;
import static com.hjc.CardAdventure.Global.CONFIGURATION.eventConfiguration;

public class AdventureComponent extends Component {

    @Override
    public void onAdded() {
        addUI();

        entity.getViewComponent().addOnClickHandler(e -> goAdventure());
    }

    private void goAdventure() {
        //获取探险事件
        Event.event = Event.getEvent(eventConfiguration.randomAdventureEvent());
        //删除营地实体
        CampEntity.clearCampEntities();
        //加载事件
        Event.displayEvent();
    }

    private void addUI() {
        CampUtils.createButton(entity, APP_WITH / 2.0 + 50, 800, "adventure", "探险", Color.ORANGE);
    }
}
