package com.robotfriendgames.ld30.factory;

import com.badlogic.gdx.Gdx;
import com.robotfriendgames.ld30.components.*;
import com.robotfriendgames.ld30.data.RenderLevel;
import com.robotfriendgames.ld30.game.GameEntity;
import com.robotfriendgames.ld30.game.LD;

public class EntityFactory {
    public static final String TAG = EntityFactory.class.getSimpleName();

    public GameEntity makeIntroBackground() {
        GameEntity entity = LD.entityPool.obtain();
        entity.setAnimData(LD.images.map.get("introBackground"));
        entity.setRenderLevel(RenderLevel.BACKGROUND);
        IntroControlComponent.apply(entity);

        Gdx.app.log(TAG, "introBackground=[" + entity.getWidth() + "," + entity.getHeight() + "]");

        return entity;
    }

    public GameEntity makeGameBackground() {
        GameEntity entity = LD.entityPool.obtain();
        entity.setAnimData(LD.images.map.get("gameBackground"));
        entity.setRenderLevel(RenderLevel.BACKGROUND);
        LD.data.worldMaxHeight = entity.getHeight();

        Gdx.app.log(TAG, "gameBackground=[" + entity.getWidth() + "," + entity.getHeight() + "]");
        Gdx.app.log(TAG, "worldMaxHeight=" + LD.data.worldMaxHeight);

        return entity;
    }

    public GameEntity makeGround() {
        GameEntity entity = LD.entityPool.obtain();
        PhysicsComponent pc = PhysicsComponent.apply(entity, "floor");
        Gdx.app.log(TAG, "floor=[" + entity.getWidth() + "," + entity.getHeight() + "]");
        return entity;
    }

    public GameEntity makePlayer() {
        GameEntity entity = LD.entityPool.obtain();
        entity.setAnimData(LD.images.map.get("player"));
        entity.setRenderLevel(RenderLevel.FOREGROUND);
        PlayerInputComponent.apply(entity);
        PlayerStateComponent.apply(entity);
        PhysicsComponent pc = PhysicsComponent.apply(entity, "player");
        pc.body.setActive(true);
        HorzWrapComponent.apply(entity);
        Gdx.app.log(TAG, "player=[" + entity.getWidth() + "," + entity.getHeight() + "]");
        return entity;
    }
}
