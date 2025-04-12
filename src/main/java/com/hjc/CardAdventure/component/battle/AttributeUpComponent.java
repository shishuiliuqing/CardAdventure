package com.hjc.CardAdventure.component.battle;

import com.almasb.fxgl.entity.component.Component;
import com.hjc.CardAdventure.Utils.BattleUtils;
import com.hjc.CardAdventure.Utils.CardsUtils;
import com.hjc.CardAdventure.entity.BattleEntity;
import com.hjc.CardAdventure.pojo.BattleInformation;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import static com.hjc.CardAdventure.Global.PLAYER.player;

public class AttributeUpComponent extends Component {

    public static int attributeUP = 0;

    @Override
    public void onAdded() {
        if (attributeUP > 6) attributeUP = 5;
        double xMove = 600;
        double yMove = 650.0;
        double interval = 138;
        double bYMove = yMove - 50;
        //Rectangle powerR = new Rectangle(40, 40, Color.GREEN);
        //力量添加
        Label powerText = new Label("力量：" + player.getAttribute().getPower());
        powerText.setFont(new Font("华文行楷", 20));
        powerText.setTranslateX(xMove);
        powerText.setTranslateY(yMove);
        entity.getViewComponent().addChild(powerText);
        Button powerUp = new Button("+");
        powerUp.setTranslateX(xMove);
        powerUp.setTranslateY(bYMove);
        powerUp.setFocusTraversable(false);
        powerUp.setStyle("-fx-base: ORANGE");
        entity.getViewComponent().addChild(powerUp);

        //智力添加
        Label intelligenceText = new Label("智力：" + player.getAttribute().getIntelligence());
        intelligenceText.setFont(new Font("华文行楷", 20));
        intelligenceText.setTranslateX(xMove + interval * 1);
        intelligenceText.setTranslateY(yMove);
        entity.getViewComponent().addChild(intelligenceText);
        Button intelligenceUp = new Button("+");
        intelligenceUp.setTranslateX(xMove + interval * 1);
        intelligenceUp.setTranslateY(bYMove);
        intelligenceUp.setFocusTraversable(false);
        intelligenceUp.setStyle("-fx-base: ORANGE");
        entity.getViewComponent().addChild(intelligenceUp);

        //智力添加
        Label defenseText = new Label("防御：" + player.getAttribute().getDefense());
        defenseText.setFont(new Font("华文行楷", 20));
        defenseText.setTranslateX(xMove + interval * 2);
        defenseText.setTranslateY(yMove);
        entity.getViewComponent().addChild(defenseText);
        Button defenseUp = new Button("+");
        defenseUp.setTranslateX(xMove + interval * 2);
        defenseUp.setTranslateY(bYMove);
        defenseUp.setFocusTraversable(false);
        defenseUp.setStyle("-fx-base: ORANGE");
        entity.getViewComponent().addChild(defenseUp);

        //敏捷添加
        Label agilityText = new Label("敏捷：" + player.getAttribute().getAgility());
        agilityText.setFont(new Font("华文行楷", 20));
        agilityText.setTranslateX(xMove + interval * 3);
        agilityText.setTranslateY(yMove);
        entity.getViewComponent().addChild(agilityText);
        Button agilityUp = new Button("+");
        agilityUp.setTranslateX(xMove + interval * 3);
        agilityUp.setTranslateY(bYMove);
        agilityUp.setFocusTraversable(false);
        agilityUp.setStyle("-fx-base: ORANGE");
        entity.getViewComponent().addChild(agilityUp);

        //纯洁添加
        Label purityText = new Label("纯洁：" + player.getAttribute().getPurity());
        purityText.setFont(new Font("华文行楷", 20));
        purityText.setTranslateX(xMove + interval * 4);
        purityText.setTranslateY(yMove);
        entity.getViewComponent().addChild(purityText);
        Button purityUp = new Button("+");
        purityUp.setTranslateX(xMove + interval * 4);
        purityUp.setTranslateY(bYMove);
        purityUp.setFocusTraversable(false);
        purityUp.setStyle("-fx-base: ORANGE");
        entity.getViewComponent().addChild(purityUp);

        //速度添加
        Label speedText = new Label("速度：" + player.getAttribute().getSpeed());
        speedText.setFont(new Font("华文行楷", 20));
        speedText.setTranslateX(xMove + interval * 5);
        speedText.setTranslateY(yMove);
        entity.getViewComponent().addChild(speedText);
        Button speedUp = new Button("+");
        speedUp.setTranslateX(xMove + interval * 5);
        speedUp.setTranslateY(bYMove);
        speedUp.setFocusTraversable(false);
        speedUp.setStyle("-fx-base: ORANGE");
        entity.getViewComponent().addChild(speedUp);


        //提示操作框
        Label tip = new Label("请选择" + attributeUP + "个属性添加属性点");
        tip.setFont(new Font("华文行楷", 50));
        tip.setTextFill(Color.ORANGE);
        tip.setTranslateX(650);
        tip.setTranslateY(680);
        entity.getViewComponent().addChild(tip);

        //力量添加
        powerUp.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            //可添加属性点-1
            attributeUP--;
            //玩家力量属性+1
            player.getAttribute().setPower(player.getAttribute().getPower() + 1);
            //刷新显示
            powerText.setText("力量：" + player.getAttribute().getPower());
            //移除该按钮
            entity.getViewComponent().removeChild(powerUp);
            if (attributeUP == 0) {
                entity.removeFromWorld();
                BattleEntity.attribute.getComponent(AttributeComponent.class).update();
                CardsUtils.updateHands();
                actionOver();
            }
        });

        //智力添加
        intelligenceUp.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            //可添加属性点-1
            attributeUP--;
            //玩家力量属性+1
            player.getAttribute().setIntelligence(player.getAttribute().getIntelligence() + 1);
            //刷新显示
            intelligenceText.setText("智力：" + player.getAttribute().getIntelligence());
            //移除该按钮
            entity.getViewComponent().removeChild(intelligenceUp);
            if (attributeUP == 0) {
                entity.removeFromWorld();
                BattleEntity.attribute.getComponent(AttributeComponent.class).update();
                CardsUtils.updateHands();
                actionOver();
            }
        });

        //防御添加
        defenseUp.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            //可添加属性点-1
            attributeUP--;
            //玩家力量属性+1
            player.getAttribute().setDefense(player.getAttribute().getDefense() + 1);
            //刷新显示
            defenseText.setText("防御：" + player.getAttribute().getDefense());
            //移除该按钮
            entity.getViewComponent().removeChild(defenseUp);
            if (attributeUP == 0) {
                entity.removeFromWorld();
                BattleEntity.attribute.getComponent(AttributeComponent.class).update();
                CardsUtils.updateHands();
                actionOver();
            }
        });

        //敏捷添加
        agilityUp.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            //可添加属性点-1
            attributeUP--;
            //玩家力量属性+1
            player.getAttribute().setAgility(player.getAttribute().getAgility() + 1);
            //刷新显示
            agilityText.setText("敏捷：" + player.getAttribute().getAgility());
            //移除该按钮
            entity.getViewComponent().removeChild(agilityUp);
            if (attributeUP == 0) {
                entity.removeFromWorld();
                BattleEntity.attribute.getComponent(AttributeComponent.class).update();
                CardsUtils.updateHands();
                actionOver();
            }
        });

        //纯洁添加
        purityUp.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            //可添加属性点-1
            attributeUP--;
            //玩家力量属性+1
            player.getAttribute().setPurity(player.getAttribute().getPurity() + 1);
            //刷新显示
            purityText.setText("纯洁：" + player.getAttribute().getPurity());
            //移除该按钮
            entity.getViewComponent().removeChild(purityUp);
            if (attributeUP == 0) {
                entity.removeFromWorld();
                BattleEntity.attribute.getComponent(AttributeComponent.class).update();
                CardsUtils.updateHands();
                actionOver();
            }
        });

        //速度添加
        speedUp.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            //可添加属性点-1
            attributeUP--;
            //玩家力量属性+1
            player.getAttribute().setSpeed(player.getAttribute().getSpeed() + 1);
            //刷新显示
            speedUp.setText("速度：" + player.getAttribute().getSpeed());
            //移除该按钮
            entity.getViewComponent().removeChild(speedUp);
            //刷新行动队列
            BattleInformation.sort(BattleInformation.NEXT_ACTION);
            BattleEntity.actionBox.getComponent(ActionComponent.class).update();
            if (attributeUP == 0) {
                entity.removeFromWorld();
                BattleEntity.attribute.getComponent(AttributeComponent.class).update();
                CardsUtils.updateHands();
                actionOver();
            }
        });
    }

    private void actionOver() {
        BattleUtils.actionOver();
        BattleInformation.effectExecution();
    }
}
