package com.robotfriendgames.ld30.factory;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.robotfriendgames.ld30.components.HorzWrapComponent;
import com.robotfriendgames.ld30.components.IntroControlComponent;
import com.robotfriendgames.ld30.components.PhysicsComponent;
import com.robotfriendgames.ld30.components.PlayerInputComponent;
import com.robotfriendgames.ld30.components.PlayerStateComponent;
import com.robotfriendgames.ld30.data.ObjectType;
import com.robotfriendgames.ld30.data.RenderLevel;
import com.robotfriendgames.ld30.game.GameEntity;
import com.robotfriendgames.ld30.game.LD;
import com.robotfriendgames.ld30.game.PhysUtils;

public class EntityFactory {
    public static final String TAG = EntityFactory.class.getSimpleName();

    public GameEntity makeIntroBackground() {
        GameEntity entity = LD.entityPool.obtain();
        entity.setAnimData(LD.images.map.get("introBackground"));
        entity.setRenderLevel(RenderLevel.BACKGROUND);
        IntroControlComponent.apply(entity);
        return entity;
    }

    public GameEntity makeGameBackground() {
        GameEntity entity = LD.entityPool.obtain();
        entity.setAnimData(LD.images.map.get("gameBackground"));
        entity.setRenderLevel(RenderLevel.BACKGROUND);
        LD.data.worldMaxHeight = entity.getHeight();
        LD.data.worldMidHeight = LD.data.worldMaxHeight / 2;
        return entity;
    }

    public GameEntity makeGround() {
        GameEntity entity = LD.entityPool.obtain();
        PhysicsComponent.apply(entity, "floor", ObjectType.START);
        return entity;
    }

    public GameEntity makePlayer() {
        GameEntity entity = LD.entityPool.obtain();
        entity.setAnimData(LD.images.map.get("player"));
        entity.setRenderLevel(RenderLevel.FOREGROUND);
        PlayerInputComponent.apply(entity);
        PlayerStateComponent.apply(entity);
        PhysicsComponent pc = PhysicsComponent.apply(entity, "player", ObjectType.PLAYER);
        pc.body.setActive(true);
        HorzWrapComponent.apply(entity);
        return entity;
    }

    public GameEntity[] makePlatforms() {
        Array<GameEntity> platforms = new Array<GameEntity>(false, 256, GameEntity.class);
        float workingHeight = LD.data.player.getY();
        workingHeight += PhysUtils.calcJumpHeight(workingHeight);
        while(workingHeight < LD.data.worldMaxHeight) {
            platforms.add(makePlatform(workingHeight));

            if(platforms.size > LD.data.worldMaxHeight) {
                throw new RuntimeException("too many platforms: " + platforms.size);
            }

            workingHeight += (PhysUtils.calcJumpHeight(workingHeight) * LD.settings.platformSpacing);
        }
        return platforms.items;
    }

    private GameEntity makePlatform(float y) {
        float vertPercent = y / LD.data.worldMidHeight;
        vertPercent = (vertPercent > 1f) ? vertPercent - 1 : vertPercent;
        vertPercent = 1f - vertPercent;
        int platformSizeIdx = (int)(vertPercent * LD.settings.platforms.length);

        GameEntity entity = LD.entityPool.obtain();
        entity.setAnimData(LD.images.map.get(LD.settings.platforms[platformSizeIdx]));
        entity.setRenderLevel(RenderLevel.MIDGROUND);
        PhysicsComponent pc = PhysicsComponent.apply(entity, "platform", ObjectType.PLATFORM);

        float horzSpace = LD.data.worldWidth - pc.size.x;
        float x = MathUtils.random(0, horzSpace) + (pc.size.x / 2);
        pc.body.setTransform(x, y, 0);
        pc.copyBodyData();
        return entity;
    }
}
