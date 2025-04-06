package com.hjc.CardAdventure.component.battle;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.hjc.CardAdventure.Utils.BattleUtils;
import com.hjc.CardAdventure.Utils.EntityUtils;
import javafx.animation.FadeTransition;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import static com.hjc.CardAdventure.Global.CARD_USE.remainingProduce;

public class SumCardsComponent extends Component {

    @Override
    public void onAdded() {
        //刷新出牌数
        shuffleNum();
        //添加组件
        addComponent();
    }

    private void addComponent() {
        //生成剩余出牌数文本
        Text text = EntityUtils.getText("<当前剩余出牌数：" + remainingProduce + (remainingProduce >= 10 ? "" : " ") + ">",
                "华文行楷", 30,
                Color.WHITE);
        EntityUtils.nodeMove(text, 25, 770);
        entity.getViewComponent().addChild(text);
    }

    private void shuffleNum() {
        remainingProduce = BattleUtils.mathProduce();
    }

    //更新实体
    public void update() {
        entity.getViewComponent().clearChildren();
        addComponent();
    }

    //更新出牌数
    public void shuffleAndUpdate() {
        shuffleNum();
        update();
    }

    //出牌数警告
    public static void warn() {
        //生成剩余出牌数文本
        Text text = EntityUtils.getText("<当前剩余出牌数：0 >",
                "华文行楷",30,
                Color.RED);
        EntityUtils.nodeMove(text,25,770);
        Entity warn = FXGL.entityBuilder().view(text).buildAndAttach();

        FadeTransition ft = new FadeTransition(Duration.seconds(1), text);
        ft.setToValue(0);
        ft.setOnFinished(e -> {
            warn.removeFromWorld();
        });
        ft.play();
    }
}
