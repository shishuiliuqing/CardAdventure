package com.hjc.CardAdventure.component.card;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.hjc.CardAdventure.CardAdventureApp;
import com.hjc.CardAdventure.Global;
import com.hjc.CardAdventure.Utils.EntityUtils;
import com.hjc.CardAdventure.component.battle.AbandonCardsComponent;
import com.hjc.CardAdventure.component.battle.ConsumeCardsComponent;
import com.hjc.CardAdventure.component.battle.DrawCardsComponent;
import com.hjc.CardAdventure.component.battle.SumCardsComponent;
import com.hjc.CardAdventure.component.information.TipBarComponent;
import com.hjc.CardAdventure.effect.Effect;
import com.hjc.CardAdventure.effect.opportunity.Opportunity;
import com.hjc.CardAdventure.effect.opportunity.OpportunityType;
import com.hjc.CardAdventure.entity.BattleEntity;
import com.hjc.CardAdventure.pojo.BattleInformation;
import com.hjc.CardAdventure.pojo.card.Card;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.util.ArrayList;

import static com.hjc.CardAdventure.Global.CARD_DISPLAY.*;
import static com.hjc.CardAdventure.Global.GAME_SETTING.*;
import static com.hjc.CardAdventure.Global.CARD_USE.*;
import static com.hjc.CardAdventure.Global.PLAYER.player;

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
    public int boxNum;
    //当前卡牌ui
    Pane pane;

    //相对牌框移动距离
    private static final double X_TO_BOX = 30 / PROPORTION;
    private static final double Y_TO_BOX = 70 / PROPORTION;

//    @Override
//    public void onUpdate(double tpf) {
//        //非卡牌使用阶段
//        if (!isUse) return;
//        //卡牌正在使用
//        if (isUsing) return;
//        //若为攻击卡，触发攻击后效果
//        if (isAttack) {
//            Opportunity.launchOpportunity(player, OpportunityType.PHY_ATTACK_END);
//            isAttack = false;
//        }
//        System.out.println(card);
//        //执行卡牌放下效果--目标指定刷新
//        card.putDown();
//        //卡牌使用结束
//        isUse = false;
//        isUsing = false;
//        //如果当前行动仍然是玩家，可以继续行动
//        if (BattleInformation.nowAction == player) selectable = true;
//    }

    @Override
    public void onAdded() {
        this.boxNum = entity.getInt("boxNum");
        this.card = entity.getObject("card");
        //System.out.println(card);

        //手牌区添加此牌
        HAND_CARDS.add(this);
        //绘制卡牌
        addUI();

        entity.getViewComponent().addOnClickHandler(e -> select());
        entity.getViewComponent().addEventHandler(MouseEvent.MOUSE_ENTERED, e -> lookInformation());
    }

    //查看卡牌信息
    private void lookInformation() {
        TipBarComponent.update(card.cardDescription());
    }


    //卡牌ui绘制
    private void addUI() {
        Pane pane = EntityUtils.createCard(card);
        EntityUtils.nodeMove(pane, CARD_BOX_HEIGHT + 10 + boxNum * CARD_BOX_WIDTH + X_TO_BOX, APP_HEIGHT - CARD_BOX_HEIGHT + Y_TO_BOX - (isSelected() ? Y_MOVE_SELECTED : 0));
        entity.getViewComponent().addChild(pane);
        this.pane = pane;
    }

    //选择卡牌
    public void select() {
        //卡牌不在手牌区,不允许选择
        if (!isHand()) return;

        double paneY = APP_HEIGHT - CARD_BOX_HEIGHT + Y_TO_BOX;

        if (isSelected() && specialProduce) {
            //点击后下移
            pane.setTranslateY(paneY);
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
            pane.setTranslateY(paneY);
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
        pane.setTranslateY(paneY - Y_MOVE_SELECTED);

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
            pane.setTranslateY(paneY);
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

    //执行卡牌效果
    public void action() {
        //如果是特殊使用，特殊使用结束
        specialProduce = false;
        //当前行动卡牌
        actionCard = this;
        //将该牌移至中央
        pane.setLayoutX(APP_WITH / 2.0 - (199.0 + CARD_BOX_WIDTH * boxNum + X_TO_BOX + 1));
        pane.setLayoutY(-290);
        //更新抽牌区状态
        DrawCardsComponent.CARD_BOX_STATUS[boxNum - 1] = 0;
        //手牌区删除此牌
        HAND_CARDS.remove(this);
        //设置当前使用的卡牌
        usingCard = card;
        //卡牌正在使用
        isUsing = true;
//        //进入卡牌使用阶段
//        isUse = true;
        //运行卡牌效果
        card.action();
        //启动线程,卡牌正在使用
        //usingThread.start();
        Platform.runLater(Global.CARD_USE::run);
    }

    //判断此牌是否在手牌区
    private boolean isHand() {
        for (CardComponent handCard : HAND_CARDS) {
            if (handCard == this) return true;
        }
        return false;
    }

    //卡牌消失特效,并置入3大牌堆
    public void disappear(ArrayList<Card> cards) {
        //牌堆置入此牌
        cards.add(card);
        ScaleTransition st = new ScaleTransition(Duration.seconds(0.3), pane);
        st.setToX(0.2);
        st.setToY(0.2);
        st.setOnFinished(e -> {
            goToCards(cards, pane.getTranslateX() + pane.getLayoutX(), pane.getTranslateY() + pane.getLayoutY());
            entity.removeFromWorld();

        });
        st.play();
    }

    //置入牌堆
    private void goToCards(ArrayList<Card> cards, double x, double y) {
        //生成一个圆
        Circle circle = new Circle(20, Color.valueOf(card.getColorS()));
        double cx = x + CARD_WIDTH / 2;
        double cy = y + CARD_HEIGHT / 2;
        circle.setCenterX(cx);
        circle.setCenterY(cy);
        Entity c = FXGL.entityBuilder().view(circle).buildAndAttach();
        //生成圆圈移动动画
        double targetCircleX;
        double targetCircleY;
        if (cards == BattleInformation.ABANDON_CARDS) {
            //外框偏移量
            double outXMove = APP_WITH - CARD_BOX_WIDTH;
            double outYMove = APP_HEIGHT - CARD_BOX_HEIGHT;
            //内框偏移量
            double inXMove = outXMove + 30 / PROPORTION;
            double inYMove = outYMove + 75 / PROPORTION;
            //圆心所在位置
            targetCircleX = inXMove + CARD_WIDTH / 2;
            targetCircleY = inYMove + CARD_HEIGHT / 2;
        } else if (cards == BattleInformation.DRAW_CARDS) {
            targetCircleX = 30 / PROPORTION + CARD_WIDTH / 2;
            targetCircleY = APP_HEIGHT - CARD_BOX_HEIGHT + 75 / PROPORTION + CARD_HEIGHT / 2;
        } else {
            targetCircleX = 1825;
            targetCircleY = 630;
        }

        TranslateTransition tt = new TranslateTransition(Duration.seconds(0.5), circle);
        tt.setToX(targetCircleX - cx);
        tt.setToY(targetCircleY - cy);
        tt.setOnFinished(e -> {
            c.removeFromWorld();
            if (cards == BattleInformation.DRAW_CARDS)
                BattleEntity.drawCards.getComponent(DrawCardsComponent.class).update();
            else if (cards == BattleInformation.ABANDON_CARDS)
                BattleEntity.abandonCards.getComponent(AbandonCardsComponent.class).update();
            else BattleEntity.consumeCards.getComponent(ConsumeCardsComponent.class).update();
        });
        tt.play();
    }

    //判断当前卡牌是否被选择
    private boolean isSelected() {
        for (CardComponent cardComponent : CARD_COMPONENTS) {
            if (cardComponent == this) return true;
        }
        return false;
    }

    //更新卡牌
    public void update() {
        entity.getViewComponent().clearChildren();
        addUI();
    }
}
