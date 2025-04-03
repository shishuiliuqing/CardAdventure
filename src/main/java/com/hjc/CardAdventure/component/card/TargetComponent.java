package com.hjc.CardAdventure.component.card;

import com.almasb.fxgl.entity.component.Component;
import com.hjc.CardAdventure.Utils.EntityUtils;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import static com.hjc.CardAdventure.Global.CARD_USE.*;
import static com.hjc.CardAdventure.Global.GAME_SETTING.*;
import static com.hjc.CardAdventure.Global.CARD_DISPLAY.*;
import static com.hjc.CardAdventure.Global.PLAYER.player;

public class TargetComponent extends Component {

    @Override
    public void onAdded() {
        //添加组件
        addComponent();
    }

    private void addComponent() {
        //创建指定目标标题
        Text targetText = EntityUtils.getText("<当前指定目标>",
                "华文行楷",22,
                Color.WHITE);
        EntityUtils.nodeMove(targetText,CARD_BOX_WIDTH + 10 + 15,APP_HEIGHT - CARD_BOX_HEIGHT + 28);
        entity.getViewComponent().addChild(targetText);

        //创建指定目标文本
        Rectangle targetReact = new Rectangle(CARD_BOX_HEIGHT - 16,30,Color.valueOf(player.getColorS()));
        Text text = EntityUtils.getText(targetJudgment(),
                "华文行楷",27,
                Color.BLACK);
        StackPane stackPane = new StackPane(targetReact);
        stackPane.getChildren().add(text);
        EntityUtils.nodeMove(stackPane,CARD_BOX_WIDTH + 18,APP_HEIGHT - CARD_BOX_HEIGHT + 37);
        entity.getViewComponent().addChild(stackPane);
    }

    //更新指定目标
    public void update() {
        entity.getViewComponent().clearChildren();
        onAdded();
    }

    //判断指定目标
    private String targetJudgment() {
        if (isAll) return "全体敌方目标";
        if (isRam) return "随机敌方目标";
        if (target == null) return "未指定目标";
        return target.getRoleName();
    }
}
