package com.hjc.CardAdventure.pojo.event;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.texture.Texture;
import com.hjc.CardAdventure.Utils.EntityUtils;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

import static com.hjc.CardAdventure.Global.GAME_SETTING.*;
import static com.hjc.CardAdventure.Global.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    //当前执行事件
    public static Event event;
    //事件实体
    public static Entity entity;

    //图片地址
    private String img;
    //标题
    private String title;
    //文本
    private String text;
    //事件选项
    private ArrayList<Option> options;

    //根据文件名获取事件
    public static Event getEvent(String name) {
        return FXGL.getAssetLoader().loadJSON(getJsonAddress(EVENT_ADDRESS, name), Event.class).get();
    }

    //展示事件
    public static void displayEvent() {
        //更新事件实体
        updateEventEntity();
        //初始化背景框
        initBackBox();
        //初始化图片
        initPicture();
        //初始化标题
        initTitle();
        //初始化文本
        initText();
        //初始化选项
        initOptions();
    }

    //初始化背景框
    private static void initBackBox() {
        //背景
        Rectangle r = new Rectangle(APP_WITH, APP_HEIGHT - BAR_HEIGHT, Color.rgb(0, 0, 0, 0.9));
        EntityUtils.nodeMove(r, 0, 70);
        entity.getViewComponent().addChild(r);

        //背景框
        Texture backBox = FXGL.texture(getTextureAddress(EVENT_IMG_ADDRESS, "textBox"), APP_WITH - 250, APP_HEIGHT - 100);
        EntityUtils.nodeMove(backBox, 125, 70);
        entity.getViewComponent().addChild(backBox);
    }

    //初始图片
    private static void initPicture() {
        Texture picture = FXGL.texture(getTextureAddress(EVENT_PIC_ADDRESS, event.img), 720, 720);
        EntityUtils.nodeMove(picture, 190, 147);
        entity.getViewComponent().addChild(picture);
    }

    //初始化标题
    private static void initTitle() {
        Rectangle r = new Rectangle(770, 70, Color.rgb(0, 0, 0, 0));
        StackPane stackPane = new StackPane(r);
        EntityUtils.nodeMove(stackPane, 945, 145);
        entity.getViewComponent().addChild(stackPane);

        Text text = EntityUtils.getText(event.title,
                "华文行楷", 50,
                Color.WHITE);
        stackPane.getChildren().add(text);
    }

    //初始化文本
    private static void initText() {
        Label label = EntityUtils.getLabel(770, 720, event.text,
                "华文行楷", 40,
                Color.WHITE);
        EntityUtils.nodeMove(label, 945, 215);
        entity.getViewComponent().addChild(label);
    }

    //初始化选项
    private static void initOptions() {
        for (Option option : event.options) {
            Option.displayOption(entity, option);
        }
    }

    //更新事件实体
    private static void updateEventEntity() {
        if (entity != null) entity.removeFromWorld();
        entity = FXGL.entityBuilder().buildAndAttach();
    }
}
