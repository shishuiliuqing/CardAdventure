package com.hjc.CardAdventure.entity;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.hjc.CardAdventure.pojo.environment.InsideInformation;

public class InformationEntity {
    //信息框
    public static Entity informationBar;
    //玩家血量
    public static Entity playerBlood;
    //金币
    public static Entity gold;
//    //药水
//    public static Entity medicine;
    //提示文本框
    public static Entity tipBar;

    //初始化局内信息实体
    public static void initInformationEntities() {
        informationBar = FXGL.spawn("informationBar");
        playerBlood = FXGL.spawn("playerBlood");
        gold = FXGL.spawn("gold");
        tipBar = FXGL.spawn("tipBar");
        //System.out.println(InsideInformation.insideEnvironmentToString());
    }
}
