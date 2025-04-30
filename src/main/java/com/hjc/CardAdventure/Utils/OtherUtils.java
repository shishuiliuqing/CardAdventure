package com.hjc.CardAdventure.Utils;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.hjc.CardAdventure.entity.CampEntity;
import com.hjc.CardAdventure.entity.InformationEntity;
import com.hjc.CardAdventure.pojo.environment.InsideInformation;
import javafx.animation.ScaleTransition;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Random;

import static com.hjc.CardAdventure.Global.GAME_SETTING.*;

public class OtherUtils {
    private OtherUtils() {
    }

    //概率集合
    private static final ArrayList<Integer> integers = new ArrayList<>();

    //概率成是否功触发器
    public static boolean isSuccess(int probability) {
        //初始化概率集合
        initIntS();

        //随机删除99个数
        return getLastNum() <= probability;
    }

    //初始化概率集合
    private static void initIntS() {
        integers.clear();
        for (int i = 0; i < 100; i++) {
            integers.add(i + 1);
        }
    }

    //随机删除概率集合的数字直至剩余1个
    private static int getLastNum() {
        Random r = new Random();
        while (integers.size() > 1) {
            integers.remove(r.nextInt(integers.size()));
        }
        return integers.get(0);
    }

    //进入下一个时间状态
    public static void nextTime() {
        //透明背景，不允许点击
        Rectangle background = new Rectangle(APP_WITH, APP_HEIGHT, Color.rgb(0, 0, 0, 0));
        Entity entity = FXGL.entityBuilder().view(background).buildAndAttach();
        //加载动画
        Rectangle load = new Rectangle(1, 1, Color.BLACK);
        EntityUtils.nodeMove(load, (APP_WITH - 1) / 2.0, (APP_HEIGHT - 1) / 2.0);
        entity.getViewComponent().addChild(load);

        //变大动画
        ScaleTransition st = new ScaleTransition(Duration.seconds(1), load);
        st.setToX(APP_WITH);
        st.setToY(APP_HEIGHT);
        st.setOnFinished(e -> {
            //进入下一时间状态
            InsideInformation.turnTimeStatus();
            //移除所有实体
            ArrayList<Entity> entities = FXGL.getGameWorld().getEntities();
            while (!entities.isEmpty()) {
                entities.get(0).removeFromWorld();
            }
//            FXGL.getGameWorld().removeEntities(entities);
            //初始化信息栏
            InformationEntity.initInformationEntities();
            //初始化营地
            CampEntity.initCampEntities();
        });
        st.play();
    }
}
