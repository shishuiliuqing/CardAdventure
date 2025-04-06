package com.hjc.CardAdventure.component.card;

import com.almasb.fxgl.entity.component.Component;
import com.hjc.CardAdventure.Utils.EntityUtils;
import com.hjc.CardAdventure.component.battle.SumCardsComponent;
import com.hjc.CardAdventure.entity.BattleEntity;
import com.hjc.CardAdventure.pojo.BattleInformation;
import com.hjc.CardAdventure.pojo.card.Card;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

import static com.hjc.CardAdventure.Global.CARD_DISPLAY.*;
import static com.hjc.CardAdventure.Global.GAME_SETTING.*;
import static com.hjc.CardAdventure.Global.CARD_USE.*;

public class CardComponent extends Component {
    //被选择时移动的距离
    private static final double Y_MOVE_SELECTED = 50;
    //当前手牌
    public static final ArrayList<CardComponent> HAND_CARDS = new ArrayList<>();
    //当前被选择的卡
    public static final ArrayList<CardComponent> CARD_COMPONENTS = new ArrayList<>();
    //当前卡牌
    private Card card;
    //卡牌所在位置
    private int boxNum;


    //相对牌框移动距离
    private static final double X_TO_BOX = 30 / PROPORTION;
    private static final double Y_TO_BOX = 70 / PROPORTION;

    @Override
    public void onAdded() {
        this.boxNum = entity.getInt("boxNum");
        this.card = entity.getObject("card");

        //手牌区添加此牌
        HAND_CARDS.add(this);
        //绘制卡牌
        addUI();

        entity.getViewComponent().addOnClickHandler(e->select());
    }


    //卡牌ui绘制
    private void addUI() {
        Pane pane = EntityUtils.createCard(card);
        EntityUtils.nodeMove(pane, CARD_BOX_HEIGHT + 10 + boxNum * CARD_BOX_WIDTH + X_TO_BOX, APP_HEIGHT - CARD_BOX_HEIGHT + Y_TO_BOX - (isSelected() ? Y_MOVE_SELECTED : 0));
        entity.getViewComponent().addChild(pane);
    }

    //选择卡牌
    public void select() {
        if (isSelected() && specialProduce) {
            //点击后下移
            entity.translateY(Y_MOVE_SELECTED);
            //选择区移除
            CARD_COMPONENTS.remove(this);
            //触发牌的放下效果
            card.putDown();
            //使用按钮变暗
            BattleEntity.produce.getComponent(ProduceComponent.class).update(false);
            //特殊时候结束
            specialProduce = false;
            //恢复效果执行状态
            BattleInformation.effectExecution();
        }
        //非选卡状态
        if (!selectable) return;

        //被选择时
        if (isSelected()) {
            //点击后下移
            entity.translateY(Y_MOVE_SELECTED);
            //选择区移除
            CARD_COMPONENTS.remove(this);
            //如果为弃牌状态
            if (isAbandon) {
                //弃牌按钮变暗
                BattleEntity.abandon.getComponent(AbandonComponent.class).update(false);
                //如果为出牌状态
            } else {
                //触发牌的放下效果
                card.putDown();
                //使用按钮变暗
               BattleEntity.produce.getComponent(ProduceComponent.class).update(false);
            }

            return;
        }

        //该牌上移
        entity.translateY(-1 * Y_MOVE_SELECTED);

        //如果是弃牌
        if (isAbandon) {
            //等于弃牌数，将第一张选择的牌下移
            if (CARD_COMPONENTS.size() == needAbandon) {
                //牌下移
                CARD_COMPONENTS.get(0).select();
                //添加该牌
                CARD_COMPONENTS.add(this);
                //弃牌键亮起
                BattleEntity.abandon.getComponent(AbandonComponent.class).update(true);
                //结束
                return;
            }


            //添加该牌
            CARD_COMPONENTS.add(this);
            //牌数满足，弃牌键亮起
            if (CARD_COMPONENTS.size() == needAbandon) {
                BattleEntity.abandon.getComponent(AbandonComponent.class).update(true);
            }
            return;
        }


        //出牌阶段
        //当前出牌数为0，不允许选择牌
        if (remainingProduce == 0 && !specialProduce) {
            //该牌强制下移
            entity.translateY(Y_MOVE_SELECTED);
            SumCardsComponent.warn();
            return;
        }

        if (!CARD_COMPONENTS.isEmpty()) {
            CARD_COMPONENTS.get(0).select();
            //CARD_COMPONENTS.remove(0);
            CARD_COMPONENTS.add(this);
            card.isSelected();
            BattleEntity.produce.getComponent(ProduceComponent.class).update(true);
            return;
        }

        //添加此牌
        CARD_COMPONENTS.add(this);
        card.isSelected();
        BattleEntity.produce.getComponent(ProduceComponent.class).update(true);
    }

    //判断当前卡牌是否被选择
    private boolean isSelected() {
        for (CardComponent cardComponent : CARD_COMPONENTS) {
            if (cardComponent == this) return true;
        }
        return false;
    }
}
