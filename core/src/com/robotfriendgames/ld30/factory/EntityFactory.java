package com.robotfriendgames.ld30.factory;

import com.robotfriendgames.ld30.components.IntroControlComponent;
import com.robotfriendgames.ld30.data.AnimationData;
import com.robotfriendgames.ld30.data.RenderLevel;
import com.robotfriendgames.ld30.game.GameEntity;
import com.robotfriendgames.ld30.game.LD30;

public class EntityFactory {
    public GameEntity makeIntroBackground() {
        GameEntity entity = LD30.entityPool.obtain();
        AnimationData ad = LD30.images.map.get("introBackground");
        entity.setAnimData(ad);
        entity.setRenderLevel(RenderLevel.BACKGROUND);
        IntroControlComponent.apply(entity);
        return entity;
    }

    public GameEntity makePlayer() {
        return null;
    }
}
