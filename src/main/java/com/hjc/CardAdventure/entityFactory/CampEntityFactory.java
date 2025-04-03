package com.hjc.CardAdventure.entityFactory;

import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.hjc.CardAdventure.component.camp.*;

public class CampEntityFactory implements EntityFactory {

    //火堆图片实体
    @Spawns("fire")
    public Entity newFire(SpawnData data) {
        return FXGL.entityBuilder(data)
                .with(new FireComponent())
                .neverUpdated()
                .build();
    }

    //总牌堆实体
    @Spawns("cards")
    public Entity newCards(SpawnData data) {
        return FXGL.entityBuilder(data)
                .with(new CardsComponent())
                .neverUpdated()
                .build();
    }

    @Spawns("playerAttribute")
    public Entity newPlayerAttribute(SpawnData data) {
        return FXGL.entityBuilder(data)
                .with(new PlayerAttributeComponent())
                .neverUpdated()
                .build();
    }

    //休息按钮实体
    @Spawns("rest")
    public Entity newRest(SpawnData data) {
        return FXGL.entityBuilder(data)
                .with(new RestComponent())
                .neverUpdated()
                .build();
    }

    //战斗按钮实体
    @Spawns("battle")
    public Entity newBattle(SpawnData data) {
        return FXGL.entityBuilder(data)
                .with(new BattleComponent())
                .neverUpdated()
                .build();
    }

    //冒险按钮实体
    @Spawns("adventure")
    public Entity newAdventure(SpawnData data) {
        return FXGL.entityBuilder(data)
                .with(new AdventureComponent())
                .neverUpdated()
                .build();
    }

    @Spawns("event")
    public Entity newEvent(SpawnData data) {
        return FXGL.entityBuilder(data)
                .with(new EventComponent())
                .neverUpdated()
                .build();
    }
}
