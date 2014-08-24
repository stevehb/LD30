package com.robotfriendgames.ld30.factory;

import com.badlogic.gdx.Gdx;
import com.robotfriendgames.ld30.components.HorzWrapComponent;
import com.robotfriendgames.ld30.components.IntroControlComponent;
import com.robotfriendgames.ld30.components.PhysicsComponent;
import com.robotfriendgames.ld30.components.PlayerControlComponent;
import com.robotfriendgames.ld30.data.RenderLevel;
import com.robotfriendgames.ld30.game.GameEntity;
import com.robotfriendgames.ld30.game.LD30;

public class EntityFactory {
    public static final String TAG = EntityFactory.class.getSimpleName();

    public GameEntity makeIntroBackground() {
        GameEntity entity = LD30.entityPool.obtain();
        entity.setAnimData(LD30.images.map.get("introBackground"));
        entity.setRenderLevel(RenderLevel.BACKGROUND);
        IntroControlComponent.apply(entity);
        return entity;
    }

    public GameEntity makeGameBackground() {
        GameEntity entity = LD30.entityPool.obtain();
        entity.setAnimData(LD30.images.map.get("gameBackground"));
        entity.setRenderLevel(RenderLevel.BACKGROUND);
        return entity;
    }

    public GameEntity makeGround() {
        GameEntity entity = LD30.entityPool.obtain();
        PhysicsComponent.apply(entity, "floor");
        return entity;
    }

    public GameEntity makePlayer() {
        GameEntity entity = LD30.entityPool.obtain();
        entity.setAnimData(LD30.images.map.get("player"));
        entity.setRenderLevel(RenderLevel.FOREGROUND);
        PlayerControlComponent.apply(entity);
        Gdx.app.log(TAG, "making player");
        PhysicsComponent pc = PhysicsComponent.apply(entity, "player");
        pc.copyBodyData();
        pc.body.setActive(true);
        Gdx.app.log(TAG, "end making player");
        HorzWrapComponent.apply(entity);
        return entity;
    }
}
