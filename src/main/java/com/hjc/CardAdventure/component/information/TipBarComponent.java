package com.hjc.CardAdventure.component.information;

import com.almasb.fxgl.entity.component.Component;
import com.hjc.CardAdventure.Global;
import com.hjc.CardAdventure.Utils.EntityUtils;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class TipBarComponent extends Component {
    //提示文本框
    public static final TextArea TIP_BAR = new TextArea();
    private static final double X_MOVE = 20;
    private static final double Y_MOVE = Global.BAR_HEIGHT + 100;


    @Override
    public void onAdded() {
        //不可编辑
        TIP_BAR.setEditable(false);
        //自动换行
        TIP_BAR.setWrapText(true);
        //设置字体
        TIP_BAR.setFont(new Font("微软雅黑", 20));
        //设置文本框背景
        TIP_BAR.setStyle("-fx-control-inner-background: #aec6cf;-fx-background-color: rgba(0,0,0,0);-fx-text-fill: black;-fx-border-color: transparent");
        //设置大小
        TIP_BAR.setPrefSize(400, 500);
        //移动文本框位置
        EntityUtils.nodeMove(TIP_BAR,X_MOVE,Y_MOVE);
        //添加文本框
        entity.getViewComponent().addChild(TIP_BAR);
    }

    //文本更新
    public static void update(String tipText) {
        if (TIP_BAR.getText().equals(tipText)) return;
        TIP_BAR.clear();
        TIP_BAR.setText(tipText);
    }
}
