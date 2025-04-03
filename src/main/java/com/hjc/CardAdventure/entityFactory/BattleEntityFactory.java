package com.hjc.CardAdventure.entityFactory;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.hjc.CardAdventure.component.battle.*;

public class BattleEntityFactory implements EntityFactory{
    //选择框
    @Spawns("cardSelectionBox")
    public Entity newCardSelectionBox(SpawnData data) {
        return   FXGL.entityBuilder(data)
                .with(new CardSelectBoxComponent())
                .neverUpdated()
                .build();
    }


    //抽牌区
    @Spawns("drawCards")
    public Entity newDrawCards(SpawnData data) {
        return FXGL.entityBuilder(data)
                .with(new DrawCardsComponent())
                .neverUpdated()
                .build();
    }

    //弃牌区
    @Spawns("abandonCards")
    public Entity newAbandonCards(SpawnData data) {
        return FXGL.entityBuilder(data)
                .with(new AbandonCardsComponent())
                .neverUpdated()
                .build();
    }

    //消耗牌区
    @Spawns("consumeCards")
    public Entity newConsumeCards(SpawnData data) {
        return FXGL.entityBuilder(data)
                .with(new ConsumeCardsComponent())
                .neverUpdated()
                .build();
    }

    //人物属性栏
    @Spawns("attribute")
    public Entity newAttribute(SpawnData data) {
        return FXGL.entityBuilder(data)
                .with(new AttributeComponent())
                .neverUpdated()
                .build();
    }

    //计牌器
    @Spawns("sumProduce")
    public Entity newProduce(SpawnData data) {
        return FXGL.entityBuilder(data)
                .with(new SumCardsComponent())
                .neverUpdated()
                .build();
    }

    //回合结束
    @Spawns("actionOver")
    public Entity newActionOver(SpawnData data) {
        return FXGL.entityBuilder(data)
                .with(new ActionOverComponent())
                //.neverUpdated()
                .build();
    }


    //行动顺序框
    @Spawns("actionBox")
    public Entity newAction(SpawnData data) {
        return FXGL.entityBuilder(data)
                .with(new ActionComponent())
                .neverUpdated()
                .build();
    }
}
