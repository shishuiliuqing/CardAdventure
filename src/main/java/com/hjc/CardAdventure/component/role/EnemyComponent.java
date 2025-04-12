package com.hjc.CardAdventure.component.role;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.Texture;
import com.hjc.CardAdventure.Utils.EntityUtils;
import com.hjc.CardAdventure.component.card.TargetComponent;
import com.hjc.CardAdventure.component.information.TipBarComponent;
import com.hjc.CardAdventure.effect.Effect;
import com.hjc.CardAdventure.entity.BattleEntity;
import com.hjc.CardAdventure.pojo.BattleInformation;
import com.hjc.CardAdventure.pojo.enemy.Enemy;
import javafx.scene.input.MouseEvent;

import static com.hjc.CardAdventure.Global.*;
import static com.hjc.CardAdventure.Global.CARD_USE.*;

public class EnemyComponent extends Component {
    //怪物
    private Enemy enemy;
    //所在索引
    private int location;
    //是否被选择
    private boolean isSelected = false;
    //敌人图片
    private Texture enemyImg;

    @Override
    public void onAdded() {
        //获得敌人所在位置
        int index = entity.getInt("index");
        enemy = BattleInformation.ENEMIES.get(index);
        location = enemy.getLocation();
        //添加其他组件
        addComponent();
        //加载图片
        addEnemy();
        //加载意图
        addIntention();

        entity.getViewComponent().addEventHandler(MouseEvent.MOUSE_ENTERED, e -> lookInformation());
        entity.getViewComponent().addOnClickHandler(e -> target());
    }


    //非人物组件
    private void addComponent() {
        EntityUtils.createRole(entity, isSelected, 550 + location * 215, 300.0, 120.0, enemy);
    }

    //加载图片
    private void addEnemy() {
        enemyImg = FXGL.texture(getTextureAddress(ENEMY_IMG_ADDRESS, enemy.getImg()), enemy.getWidth(), enemy.getHeight());
        EntityUtils.nodeMove(enemyImg, 550 + location * 215 + enemy.getX(), 300 + enemy.getY());
        entity.getViewComponent().addChild(enemyImg);
    }

    //加载意图
    private void addIntention() {
        EntityUtils.displayIntention(enemy, entity, 550 + location * 215 + enemy.getX() + enemy.getWidth() / 2, 300 + enemy.getY());
    }

    private void lookInformation() {
        TipBarComponent.update("当前位置：" + enemy.getLocation() + "号位" + Effect.NEW_LINE + enemy);
    }


    //选择该目标
    private void target() {
        if (!needTarget) return;
        if (isAll) return;
        if (isRam) return;
        if (enemy == target) return;
        //删除上一个指定
        for (int i = 0; i < BattleEntity.enemies.length; i++) {
            Entity e = BattleEntity.enemies[i];
            if (e == null) continue;
            if (e.getComponent(EnemyComponent.class).isSelected) {
                e.getComponent(EnemyComponent.class).update(false);
            }
        }
        target = enemy;
        BattleEntity.target.getComponent(TargetComponent.class).update();
        this.update(true);
    }

    //更新目标指示
    public void update(boolean isSelected) {
        this.isSelected = isSelected;
        update();
    }

    //更新敌人
    public void update() {
        entity.getViewComponent().clearChildren();
        addComponent();
        addEnemy();
        addIntention();
    }
}
