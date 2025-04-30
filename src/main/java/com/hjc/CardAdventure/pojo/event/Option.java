package com.hjc.CardAdventure.pojo.event;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.texture.Texture;
import com.hjc.CardAdventure.Utils.EntityUtils;
import com.hjc.CardAdventure.Utils.OtherUtils;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

import static com.hjc.CardAdventure.Global.*;
import static com.hjc.CardAdventure.effect.Effect.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Option {
    //选项编号
    private int num;
    //选项文本
    private String text;
    //选项效果
    ArrayList<String> effects;

    //选项解析
    public static void parseAndAction(String effect) {
        //获取效果序列
        ArrayList<String> result = cutEffect(effect);
        //获取第一操作数
        String operation = getFirst(result);
        switch (operation) {
            //返回营地
            case "BACK" -> {
                OtherUtils.nextTime();
            }
        }
    }

    //展示选项
    public static void displayOption(Entity entity, Option option) {
        Pane pane = new Pane();
        getOptionPane(pane, option, false);

        //添加监听事件
        pane.setOnMouseEntered(e -> {
            getOptionPane(pane, option, true);
        });
        pane.setOnMouseExited(e -> {
            getOptionPane(pane, option, false);
        });

        entity.getViewComponent().addChild(pane);
    }

    //选项创建
    private static void getOptionPane(Pane pane, Option option, boolean isSelect) {
        //创建容器
        pane.getChildren().clear();
        pane.setPrefSize(770, 50);

        //加载背景框
        Texture backBox = FXGL.texture(getTextureAddress(EVENT_IMG_ADDRESS, isSelect ? "optionBoxT" : "optionBoxF"), 770, 50);
        pane.getChildren().add(backBox);

        //加载文本
        Text t = EntityUtils.getText(option.text,
                "微软雅黑", 25,
                Color.WHITE);
        EntityUtils.nodeMove(t, 130, 35);
        pane.getChildren().add(t);
//        double x = t.getBoundsInLocal().getWidth();
//        System.out.println(x);
        EntityUtils.nodeMove(pane, 945, 800 - 60 * option.num);
    }
}
