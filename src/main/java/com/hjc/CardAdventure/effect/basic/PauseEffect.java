package com.hjc.CardAdventure.effect.basic;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.hjc.CardAdventure.effect.Effect;
import com.hjc.CardAdventure.pojo.BattleInformation;
import com.hjc.CardAdventure.pojo.Role;
import javafx.animation.TranslateTransition;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.ArrayList;

//暂停效果
public class PauseEffect extends Effect {
    public PauseEffect(Role from, String effect) {
        super(from, effect);
    }

    @Override
    public void action() {
        ArrayList<String> effect = Effect.cutEffect(getEffect());
        int value = Effect.changeToInt(Effect.getFirst(effect));
        if (value == 999) return;
        Text text = new Text();
        Entity entity = FXGL.entityBuilder().view(text).buildAndAttach();
        TranslateTransition tt = new TranslateTransition(Duration.seconds(value / 10.0), text);
        tt.setOnFinished(e -> {
            entity.removeFromWorld();
            BattleInformation.effectExecution();
        });
        tt.play();
    }

    @Override
    public String toString() {
        return "暂停";
    }
}
