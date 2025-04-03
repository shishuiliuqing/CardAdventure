package com.hjc.CardAdventure.entityFactory;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.hjc.CardAdventure.component.information.*;

public class InformationEntityFactory implements EntityFactory {

    //提示文本框
    @Spawns("tipBar")
    public Entity newTipBar(SpawnData data) {
        return FXGL.entityBuilder(data)
                .with(new TipBarComponent())
                .neverUpdated()
                .build();
    }

    //游戏信息栏
    @Spawns("informationBar")
    public Entity newInformationBar(SpawnData data) {
        return FXGL.entityBuilder(data)
                .with(new BarComponent())
                .neverUpdated()
                .build();
    }

    //人物血量显示
    @Spawns("playerBlood")
    public Entity newPlayerBlood(SpawnData data) {
        return FXGL.entityBuilder(data)
                .with(new BloodComponent())
                .neverUpdated()
                .build();
    }

    //金币显示
    @Spawns("gold")
    public Entity newGold(SpawnData data) {
        return FXGL.entityBuilder(data)
                .with(new GoldComponent())
                .neverUpdated()
                .build();
    }

//    //药水显示
//    @Spawns("medicine")
//    public Entity newMedicine(SpawnData data) {
//        return FXGL.entityBuilder(data)
//                .with(new MedicineComponent())
//                .neverUpdated()
//                .build();
//    }
}
