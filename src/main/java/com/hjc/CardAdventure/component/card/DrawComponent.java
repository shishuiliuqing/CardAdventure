package com.hjc.CardAdventure.component.card;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.Texture;
import com.hjc.CardAdventure.Utils.EntityUtils;
import com.hjc.CardAdventure.pojo.card.Card;

import static com.hjc.CardAdventure.Global.CARD_DISPLAY.*;
import static com.hjc.CardAdventure.Global.*;
import static com.hjc.CardAdventure.Global.PLAYER.player;
import static com.hjc.CardAdventure.Global.GAME_SETTING.*;

public class DrawComponent extends Component {
    //要移动到的牌框
    private int boxNum;
    //当前卡牌
    private Card card;
    //x需要移动的距离
    private double xNeedMove;
    //x已移动的距离
    private double xMove = 0;

    @Override
    public void onUpdate(double tpf) {
        //抽牌移动效果
        if (xMove < xNeedMove) {
            entity.translateX(xNeedMove * tpf * 2);
            xMove += xNeedMove * tpf * 2;
        } else {
            entity.removeFromWorld();
        }
    }

    @Override
    public void onAdded() {
        //获取要移动到的牌框
        boxNum = entity.getInt("boxNum");
        //当前卡牌
        card = entity.getObject("card");
        //需要移动的距离
        xNeedMove = CARD_BOX_HEIGHT + 10 + CARD_BOX_WIDTH * boxNum;

        //创建卡牌背面
        String cardBackName = "cardBack" + EntityUtils.parseColor(player.getColorS());
        Texture drawCards = FXGL.texture(getTextureAddress(CARD_BACK_ADDRESS, cardBackName), CARD_WIDTH, CARD_HEIGHT);
        EntityUtils.nodeMove(drawCards, 30 / PROPORTION + 1, APP_HEIGHT - CARD_BOX_HEIGHT + 75 / PROPORTION);
        entity.getViewComponent().addChild(drawCards);
    }

    @Override
    public void onRemoved() {
        FXGL.spawn("card", new SpawnData()
                .put("boxNum", boxNum)
                .put("card", card)
        );
    }
}
