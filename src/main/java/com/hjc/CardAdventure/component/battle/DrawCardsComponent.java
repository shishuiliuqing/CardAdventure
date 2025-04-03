package com.hjc.CardAdventure.component.battle;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.Texture;
import com.hjc.CardAdventure.Utils.EntityUtils;
import com.hjc.CardAdventure.pojo.BattleInformation;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

import static com.hjc.CardAdventure.Global.PLAYER.player;
import static com.hjc.CardAdventure.Global.CARD_DISPLAY.*;
import static com.hjc.CardAdventure.Global.*;

public class DrawCardsComponent extends Component {

    //牌框状态
    public static final int[] CARD_BOX_STATUS = new int[10];

    @Override
    public void onAdded() {
        //添加组件
        addComponent();
    }

    //添加组件
    public void addComponent() {
        //牌框图片
        Texture cardBox = FXGL.texture(getTextureAddress(BATTLE_ADDRESS, "cardBox"), CARD_BOX_WIDTH, CARD_BOX_HEIGHT);
        EntityUtils.nodeMove(cardBox, 0, GAME_SETTING.APP_HEIGHT - CARD_BOX_HEIGHT);
        entity.getViewComponent().addChild(cardBox);

        //牌背
        Texture cardBack = FXGL.texture(getTextureAddress(CARD_BACK_ADDRESS, "cardBack" + EntityUtils.parseColor(player.getColorS())), CARD_WIDTH, CARD_HEIGHT);
        EntityUtils.nodeMove(cardBack, 30 / PROPORTION + 1, GAME_SETTING.APP_HEIGHT - CARD_BOX_HEIGHT + 75 / PROPORTION);
        entity.getViewComponent().addChild(cardBack);

        //牌数
        double cXMove = 30 / PROPORTION + CARD_WIDTH / 2;
        double cYMove = GAME_SETTING.APP_HEIGHT - CARD_BOX_HEIGHT + 75 / PROPORTION + CARD_HEIGHT / 2;
        StackPane stackPane = EntityUtils.generateCircleNum(cXMove, cYMove,
                25, BattleInformation.DRAW_CARDS.size(), player.getColorS(),
                new Font("微软雅黑", 15));
        entity.getViewComponent().addChild(stackPane);
    }

    //更新方法
    public void update() {
        entity.getViewComponent().clearChildren();
        addComponent();
    }
}
