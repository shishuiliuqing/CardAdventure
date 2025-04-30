package com.hjc.CardAdventure.component.camp;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.hjc.CardAdventure.Global;
import com.hjc.CardAdventure.Utils.CampUtils;
import com.hjc.CardAdventure.component.information.TipBarComponent;
import com.hjc.CardAdventure.configuration.MonsterPool;
import com.hjc.CardAdventure.effect.Effect;
import com.hjc.CardAdventure.effect.basic.PauseEffect;
import com.hjc.CardAdventure.entity.BattleEntity;
import com.hjc.CardAdventure.entity.CampEntity;
import com.hjc.CardAdventure.pojo.BattleInformation;
import com.hjc.CardAdventure.pojo.enemy.EnemyType;
import com.hjc.CardAdventure.pojo.environment.InsideInformation;
import com.hjc.CardAdventure.pojo.environment.TimeStatus;
import javafx.animation.ScaleTransition;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import static com.hjc.CardAdventure.Global.GAME_SETTING.*;

public class BattleComponent extends Component {

    @Override
    public void onAdded() {
        addUI();

        entity.getViewComponent().addOnClickHandler(e -> goBattle());
        entity.getViewComponent().addEventHandler(MouseEvent.MOUSE_ENTERED, e -> lookInformation());
    }

    private void lookInformation() {
        String text = "战斗" + Effect.NEW_LINE;
        text += "寻找怪物（非精英，非boss），并开展战斗，获得卡牌，经验，金币等奖励！" + Effect.NEW_LINE;
        text += "只有fw才会选择休息的，我必须立刻战斗爽！\n\n          --by某位不愿意透露姓名的开发者";
        TipBarComponent.update(text);
    }

    private void addUI() {
        CampUtils.createButton(entity, APP_WITH / 2.0 - 250, 800, "battle", "战斗", Color.RED);
    }

    private void goBattle() {
        InsideInformation.day = 3;
        InsideInformation.timeStatus = TimeStatus.EVENING;
        //获得怪池
        MonsterPool.enemyType = EnemyType.LITTLE_MONSTER;
        MonsterPool.monsterPool = Global.CONFIGURATION.seasonMonsterPool.generateLittleMonsterPool(InsideInformation.day, InsideInformation.timeStatus);

        //加载动画
        Rectangle rectangle = new Rectangle(1, 1, Color.BLACK);
        rectangle.setTranslateX((APP_WITH - 1) / 2.0);
        rectangle.setTranslateY((APP_HEIGHT + 70 - 1) / 2.0);
        Entity load = FXGL.entityBuilder().view(rectangle).buildAndAttach();


        ScaleTransition st = new ScaleTransition(Duration.seconds(0.5), rectangle);
        st.setToX(APP_WITH);
        st.setToY(APP_HEIGHT - 70);

        st.setOnFinished(e -> {
            //移除营地实体
            CampEntity.clearCampEntities();
            //移除加载动画
            load.removeFromWorld();
            //初始化战斗实体
            BattleEntity.initBattleEntities();
            //进行战斗
            BattleInformation.battle();
            BattleInformation.insetEffect(new PauseEffect(null,"10"));
            BattleInformation.effectExecution();
        });
        st.play();
    }
}
