package com.hjc.CardAdventure.entityFactory;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.hjc.CardAdventure.component.role.*;


public class RoleEntityFactory implements EntityFactory {
    @Spawns("playerBattle")
    public Entity newPlayerBattle(SpawnData data) {
        return FXGL.entityBuilder(data)
                .with(new PlayerComponent())
                //.neverUpdated()
                .build();
    }

    @Spawns("enemy")
    public Entity newEnemy(SpawnData data) {
        return FXGL.entityBuilder(data)
                .with(new EnemyComponent())
                //.neverUpdated()
                .build();
    }
}
