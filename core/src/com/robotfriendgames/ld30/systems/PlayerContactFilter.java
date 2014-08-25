package com.robotfriendgames.ld30.systems;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.ContactFilter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.robotfriendgames.ld30.data.ObjectType;
import com.robotfriendgames.ld30.game.LD;

public class PlayerContactFilter implements ContactFilter {
    @Override
    public boolean shouldCollide(Fixture fixtureA, Fixture fixtureB) {
        ObjectType objA = (ObjectType) fixtureA.getBody().getUserData();
        ObjectType objB = (ObjectType) fixtureB.getBody().getUserData();

        boolean playerPlatform =
                (objA == ObjectType.PLAYER && objB == ObjectType.PLATFORM) ||
                (objA == ObjectType.PLATFORM && objB == ObjectType.PLAYER);
        if(!playerPlatform) {
            return true;
        }

        Vector2 vel = LD.data.getPlayerVel();
        Vector2 pos = LD.data.getPlayerPos();
        boolean playerVelUp = vel.y > 0;
        boolean playerPosLow = pos.y < LD.data.worldMidHeight;
        if(playerVelUp && playerPosLow) {
            return false;
        }
        if(!playerVelUp && !playerPosLow) {
            return false;
        }
        return true;
    }
}
