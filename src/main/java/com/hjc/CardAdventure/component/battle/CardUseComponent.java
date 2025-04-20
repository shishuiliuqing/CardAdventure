package com.hjc.CardAdventure.component.battle;

import com.almasb.fxgl.entity.component.Component;

import static com.hjc.CardAdventure.Global.CARD_USE.*;

public class CardUseComponent extends Component {
    @Override
    public void onUpdate(double tpf) {
        if (!isUse) return;
        if (isUsing) return;
        run();
        entity.removeFromWorld();
    }
}
