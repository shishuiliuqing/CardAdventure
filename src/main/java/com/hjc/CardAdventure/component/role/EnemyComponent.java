package com.hjc.CardAdventure.component.role;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.Texture;
import com.hjc.CardAdventure.Utils.EntityUtils;
import com.hjc.CardAdventure.entity.BattleEntity;
import com.hjc.CardAdventure.pojo.BattleInformation;
import com.hjc.CardAdventure.pojo.enemy.Enemy;

import static com.hjc.CardAdventure.Global.*;

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
}
