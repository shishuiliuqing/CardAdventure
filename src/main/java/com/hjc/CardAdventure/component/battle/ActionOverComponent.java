package com.hjc.CardAdventure.component.battle;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.hjc.CardAdventure.Utils.EntityUtils;
import com.hjc.CardAdventure.component.card.AbandonComponent;
import com.hjc.CardAdventure.component.card.CardComponent;
import com.hjc.CardAdventure.component.information.TipBarComponent;
import com.hjc.CardAdventure.effect.basic.PauseEffect;
import com.hjc.CardAdventure.effect.opportunity.Opportunity;
import com.hjc.CardAdventure.effect.opportunity.OpportunityType;
import com.hjc.CardAdventure.entity.BattleEntity;
import com.hjc.CardAdventure.pojo.BattleInformation;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import static com.hjc.CardAdventure.Global.*;
import static com.hjc.CardAdventure.Global.CARD_USE.*;

public class ActionOverComponent extends Component {

    private static AnimatedTexture animatedTexture;

    @Override
    public void onAdded() {
        //加载沙漏序列帧图片
        AnimationChannel ac = new AnimationChannel(FXGL.image(getTextureAddress(BATTLE_ADDRESS, "hourglass")), Duration.seconds(1), 62);
        //生成组件
        animatedTexture = new AnimatedTexture(ac);
        animatedTexture.setScaleX(0.2);
        animatedTexture.setScaleY(0.2);
        EntityUtils.nodeMove(animatedTexture, 1480, -340);
        entity.getViewComponent().addChild(animatedTexture);

        //生成隐形矩形
        Rectangle rectangle = new Rectangle(70, 70, Color.rgb(0, 0, 0, 0));
        EntityUtils.nodeMove(rectangle, GAME_SETTING.APP_WITH - 70, 0);
        entity.getViewComponent().addChild(rectangle);

        entity.getViewComponent().addEventHandler(MouseEvent.MOUSE_ENTERED, e -> lookInformation());
        entity.getViewComponent().addOnClickHandler(e -> onOver());
    }

    //结束按钮查看
    private void lookInformation() {
        TipBarComponent.update("结束回合");
    }

    //结束动画
    public void onOver() {
        if (isPlayer) {
            //非玩家回合
            isPlayer = false;
            //不可选牌
            selectable = false;
            //弃牌后回合结束
            isAbandonOver = true;
            //可保留牌的数量
            int reserveNum = PLAYER.player.getAttribute().getAgility() / 5;
            //需要弃牌数
            needAbandon = Math.max(CardComponent.HAND_CARDS.size() - reserveNum, 0);
            //执行弃牌
            BattleEntity.abandon.getComponent(AbandonComponent.class).abandonJudge();
        }
    }

    //回合结束
    public void over() {
        //若为玩家，初始化玩家所有用牌属性
        if (BattleInformation.nowAction == PLAYER.player) {
            initCardUse();
        }
        //执行动画
        animatedTexture.play();
        //暂停1秒
        BattleInformation.EFFECTS.add(new PauseEffect(null,"10"));
        //删除当前行动者
        BattleInformation.THIS_ACTION.remove(0);
        //更新行动序列
        BattleEntity.actionBox.getComponent(ActionComponent.class).update();
        //下一个角色行动
        BattleInformation.battle();
    }
}
