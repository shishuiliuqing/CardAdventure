package com.hjc.CardAdventure;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.localization.Language;
import com.hjc.CardAdventure.entity.BattleEntity;
import com.hjc.CardAdventure.entity.CampEntity;
import com.hjc.CardAdventure.entity.InformationEntity;
import com.hjc.CardAdventure.entityFactory.*;
import com.hjc.CardAdventure.pojo.BattleInformation;
import com.hjc.CardAdventure.pojo.environment.InsideInformation;
import com.hjc.CardAdventure.pojo.player.Player;
import javafx.scene.paint.Color;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.hjc.CardAdventure.Global.GAME_SETTING.APP_HEIGHT;
import static com.hjc.CardAdventure.Global.GAME_SETTING.APP_WITH;
import static com.hjc.CardAdventure.Global.*;
import static com.hjc.CardAdventure.Global.CONFIGURATION.*;

public class CardAdventureApp extends GameApplication {

    //游戏设置
    @Override
    protected void initSettings(GameSettings gameSettings) {
        //设置主菜单是否出现
        gameSettings.setMainMenuEnabled(false);
        //设置宽
        gameSettings.setWidth(APP_WITH);
        //设置高
        gameSettings.setHeight(APP_HEIGHT);
        //设置版本号
        gameSettings.setVersion("0.1");
        //设置标题
        gameSettings.setTitle("卡牌冒险");
        //设置图标
        gameSettings.setAppIcon(getTextureAddress(ENEMY_IMG_ADDRESS,"forestWolf"));
        //设置中文
        gameSettings.getSupportedLanguages().add(Language.CHINESE);
        gameSettings.setDefaultLanguage(Language.CHINESE);
    }

    @Override
    protected void initGame() {
        //设置游戏背景颜色
        getGameScene().setBackgroundColor(Color.GRAY);

        //添加实体工厂
        getGameWorld().addEntityFactory(new InformationEntityFactory());
        getGameWorld().addEntityFactory(new CampEntityFactory());
        getGameWorld().addEntityFactory(new BattleEntityFactory());
        getGameWorld().addEntityFactory(new CardEntityFactory());
        getGameWorld().addEntityFactory(new RoleEntityFactory());

        //初始化环境
        InsideInformation.generateInsideEnvironment();

        PLAYER.initPlayer();

        //初始化局内信息实体
        InformationEntity.initInformationEntities();
        //初始化营地
        //CampEntity.initCampEntities();
        //初始化战斗实体
        BattleEntity.initBattleEntities();
        BattleInformation.battle();
    }

    //启动类
    public static void main(String[] args) {
        launch(args);
    }
}
