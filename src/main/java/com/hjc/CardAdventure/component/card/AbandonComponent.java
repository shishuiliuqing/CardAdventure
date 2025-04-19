package com.hjc.CardAdventure.component.card;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.Texture;
import com.hjc.CardAdventure.Utils.BattleUtils;
import com.hjc.CardAdventure.Utils.EntityUtils;
import com.hjc.CardAdventure.component.battle.AttributeUpComponent;
import com.hjc.CardAdventure.component.battle.DrawCardsComponent;
import com.hjc.CardAdventure.entity.BattleEntity;
import com.hjc.CardAdventure.pojo.BattleInformation;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.Arrays;

import static com.hjc.CardAdventure.Global.BATTLE_ADDRESS;
import static com.hjc.CardAdventure.Global.CARD_USE.*;
import static com.hjc.CardAdventure.Global.GAME_SETTING.APP_HEIGHT;
import static com.hjc.CardAdventure.Global.GAME_SETTING.APP_WITH;
import static com.hjc.CardAdventure.Global.getTextureAddress;


public class AbandonComponent extends Component {

    //弃置文本实体
    private static Entity tipEntity;

    @Override
    public void onAdded() {
        //创建使用按钮
        Texture button = FXGL.texture(abandon ? getTextureAddress(BATTLE_ADDRESS, "buttonLight") : getTextureAddress(BATTLE_ADDRESS, "buttonDark"), 270 / 2.0, 98 / 2.0);
        EntityUtils.nodeMove(button, APP_WITH - 150, APP_HEIGHT - 309);
        entity.getViewComponent().addChild(button);

        //创建使用文本
        Text abandonText = EntityUtils.getText("弃    置",
                "华文行楷", 29,
                Color.BLACK);
        EntityUtils.nodeMove(abandonText, APP_WITH - 150 + 25, APP_HEIGHT - 309 + 35);
        entity.getViewComponent().addChild(abandonText);

        entity.getViewComponent().addOnClickHandler(e -> abandon());
    }

    //弃牌
    public void abandonJudge() {
        //可以选牌
        selectable = true;
        //将选择的牌全部放下
        while (!CardComponent.CARD_COMPONENTS.isEmpty()) {
            CardComponent cardComponent = CardComponent.CARD_COMPONENTS.get(0);
            cardComponent.select();
        }

        //不允许选牌
        selectable = false;
        //不用弃牌
        if (needAbandon == 0 && isAbandonOver) {
            attributeUpStage();
            return;
        }

        //若需要弃牌数大于手牌数
        if (needAbandon >= CardComponent.HAND_CARDS.size()) {
            //全部弃置
            while (!CardComponent.HAND_CARDS.isEmpty()) {
                CardComponent cardComponent = CardComponent.HAND_CARDS.get(0);
                CardComponent.HAND_CARDS.remove(0);
                cardComponent.disappear(BattleInformation.ABANDON_CARDS);
            }
            //清除手牌区状态
            Arrays.fill(DrawCardsComponent.CARD_BOX_STATUS, 0);
            //如果是弃牌阶段
            if (isAbandonOver) {
                attributeUpStage();
            }
            return;
        }

        //允许选牌
        selectable = true;
        //自己选择弃牌
        isAbandon = true;
        //生成弃牌文本
        generateTip();
    }

    //选择弃置
    private void abandon() {
        //未亮时不允许点击
        if (!abandon) return;
        //取消弃牌
        isAbandon = false;
        //不允许选牌
        selectable = false;
        //弃牌键变暗
        update(false);

        while (!CardComponent.CARD_COMPONENTS.isEmpty()) {
            CardComponent cardComponent = CardComponent.CARD_COMPONENTS.get(0);
            cardComponent.disappear(BattleInformation.ABANDON_CARDS);
            //选择牌区删除此牌
            CardComponent.CARD_COMPONENTS.remove(0);
            //手牌区删除此牌
            CardComponent.HAND_CARDS.remove(cardComponent);
            //更新抽牌区状态
            DrawCardsComponent.CARD_BOX_STATUS[cardComponent.boxNum - 1] = 0;
        }

        //删除文本
        if (tipEntity != null) tipEntity.removeFromWorld();
        //若为回合结束
        if (isAbandonOver) {
            attributeUpStage();
        } else {
            selectable = true;
        }
    }

    private void generateTip() {
        Text text = EntityUtils.getText("请选择弃置" + needAbandon + "张牌",
                "华文行楷", 50,
                Color.WHITE);
        EntityUtils.nodeMove(text, APP_WITH * 1.0 / 2 - 150, APP_HEIGHT * 1.0 - 300);
        tipEntity = FXGL.entityBuilder().view(text).buildAndAttach();
    }

    //进入属性添加阶段
    private void attributeUpStage() {
        if (remainingProduce > 0) {
            AttributeUpComponent.attributeUP = remainingProduce;
            FXGL.entityBuilder().with(new AttributeUpComponent()).buildAndAttach();
        } else {
            BattleUtils.actionOver();
            BattleInformation.effectExecution();
        }
    }

    //更新
    public void update(boolean isLight) {
        abandon = isLight;
        entity.removeFromWorld();
        BattleEntity.abandon = FXGL.spawn("abandonJudge");
    }
}
