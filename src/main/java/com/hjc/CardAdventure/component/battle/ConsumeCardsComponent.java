package com.hjc.CardAdventure.component.battle;

import com.almasb.fxgl.entity.component.Component;
import com.hjc.CardAdventure.Utils.EntityUtils;
import com.hjc.CardAdventure.pojo.BattleInformation;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

public class ConsumeCardsComponent extends Component {
    @Override
    public void onAdded() {
        //添加组件
        addComponent();
    }

    private void addComponent() {
        //生成圆心数字
        StackPane stackPane = EntityUtils.generateCircleNum(1825, 630,
                25, BattleInformation.CONSUME_CARDS.size(), "#7700bb",
                new Font("微软雅黑", 15));
        entity.getViewComponent().addChild(stackPane);
    }

    //更新方法
    private void update() {
        entity.getViewComponent().clearChildren();
        addComponent();
    }
}
