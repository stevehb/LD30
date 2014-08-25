package com.robotfriendgames.ld30.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.robotfriendgames.ld30.comm.Message;
import com.robotfriendgames.ld30.data.ObjectType;
import com.robotfriendgames.ld30.data.PlayerStates;
import com.robotfriendgames.ld30.game.GameEntity;
import com.robotfriendgames.ld30.game.LD;

public class PlayerContactListener implements ContactListener {
    public  static final String TAG = PlayerContactListener.class.getSimpleName();

    @Override
    public void beginContact(Contact contact) { }

    @Override
    public void endContact(Contact contact) { }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        PlayerStates playerState = LD.data.getPlayerState();
        if(playerState == PlayerStates.DEAD) {
            return;
        }

        // turn off contacts for platforms
        GameEntity a = (GameEntity) contact.getFixtureA().getBody().getUserData();
        GameEntity b = (GameEntity) contact.getFixtureB().getBody().getUserData();

        boolean playerPlatform =
                (a == LD.data.player && b.type == ObjectType.PLATFORM) ||
                (a.type == ObjectType.PLATFORM && b == LD.data.player);
        if(!playerPlatform) {
            contact.setEnabled(true);
            return;
        }

        GameEntity player = (a.type == ObjectType.PLAYER) ? a : b;
        GameEntity platform = (a.type == ObjectType.PLATFORM) ? a : b;

        float playerContactY, platformContactY;
        boolean setContact;
        if(player.getY() < LD.data.worldMidHeight) {
            playerContactY = player.getY();
            platformContactY = platform.getY() + platform.getHeight();
            setContact = playerContactY > platformContactY;
        } else {
            playerContactY = player.getY() + player.getHeight();
            platformContactY = platform.getY();
            setContact = playerContactY < platformContactY;
        }
        contact.setEnabled(setContact);
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        PlayerStates playerState = LD.data.getPlayerState();
        if(playerState == PlayerStates.DEAD) {
            return;
        }

        GameEntity platform = getPlatform(contact.getFixtureA(), contact.getFixtureB());

        // test for player
        boolean isPlayer =
                contact.getFixtureA().getBody().getUserData() == LD.data.player ||
                contact.getFixtureB().getBody().getUserData() == LD.data.player;
        if(!isPlayer) {
            return;
        }

        // test for death landing
        float maxImpulse = 0;
        float[] normalImpulses =  impulse.getNormalImpulses();
        for(int i = 0; i < impulse.getCount(); i++) {
            if(normalImpulses[i] > maxImpulse) {
                maxImpulse = normalImpulses[i];
            }
        }
        int playerRotation = ((int)(LD.data.player.getRotation())) % 360;
        boolean tooHardAngle =
                maxImpulse > LD.settings.playerCollisionMaxVel &&
                (playerRotation > 45 || playerRotation < 315);
        boolean tooHardStraight =
                maxImpulse > (LD.settings.playerCollisionMaxVel * 1.25f) &&
                (playerRotation < 45 && playerRotation > 315);
        if(tooHardAngle || tooHardStraight) {
            Gdx.app.log(TAG, "player dead: angle=" + playerRotation + ", impulse=" + maxImpulse);
            LD.post.send(Message.Type.PLAYER_STATE, PlayerStates.DEAD);
            return;
        }

        // test for safe landing
        Vector2 vel = LD.data.getPlayerVel();
        if(Math.abs(vel.y) < LD.settings.playerLandingMaxVel) {
            if(vel.x < 0) {
                LD.post.send(Message.Type.PLAYER_STATE, PlayerStates.GROUND_LEFT);
            } else {
                LD.post.send(Message.Type.PLAYER_STATE, PlayerStates.GROUND_RIGHT);
            }
            setSafePlatform(platform);
        } else {
            if(vel.x < 0) {
                LD.post.send(Message.Type.PLAYER_STATE, PlayerStates.JUMP_LEFT);
            } else {
                LD.post.send(Message.Type.PLAYER_STATE, PlayerStates.JUMP_RIGHT);
            }
        }
    }

    private GameEntity getPlatform(Fixture a, Fixture b) {
        GameEntity platform = null;
        ObjectType aType = ((GameEntity) a.getBody().getUserData()).type;
        ObjectType bType = ((GameEntity) b.getBody().getUserData()).type;
        if(aType == ObjectType.PLATFORM) {
            platform = (GameEntity)a.getBody().getUserData();
        } else if(bType == ObjectType.PLATFORM) {
            platform = (GameEntity)b.getBody().getUserData();
        }
        return platform;
    }

    private void setSafePlatform(GameEntity platform) {
        for(int i = 0; i < LD.data.platforms.length; i++) {
            if(LD.data.platforms[i] == platform) {
                LD.data.safePlatformIdx = i;
                return;
            }
        }
        LD.data.safePlatformIdx = -1;
    }
}
