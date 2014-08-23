package com.robotfriendgames.ld30.pools;

import com.robotfriendgames.ld30.game.GameEntity;

public class EntityPool extends BasePool<GameEntity> {

    public EntityPool() {
        super(GameEntity.class);
    }

    @Override
    public GameEntity obtain() {
        GameEntity entity = super.obtain();
        return entity;
    }
}
