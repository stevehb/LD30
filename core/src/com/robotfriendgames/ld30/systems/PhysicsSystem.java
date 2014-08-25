package com.robotfriendgames.ld30.systems;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.robotfriendgames.ld30.comm.Message;
import com.robotfriendgames.ld30.comm.MessageReceiver;
import com.robotfriendgames.ld30.data.PlayerStates;
import com.robotfriendgames.ld30.game.LD;
import com.robotfriendgames.ld30.game.PhysUtils;

public class PhysicsSystem implements MessageReceiver {
    public static final String TAG = PhysicsSystem.class.getSimpleName();
    private static final Vector2 tmp = new Vector2(0, 0);

    private PlayerContactListener contactListener;
    //private PlayerContactFilter contactFilter;

    public PhysicsSystem() {
        contactListener = new PlayerContactListener();
        //contactFilter = new PlayerContactFilter();
    }

    public void start() {
        LD.data.world.setContactListener(contactListener);
        //LD.data.world.setContactFilter(contactFilter);
        LD.post.addReceiver(this);
    }

    public void update(float delta) {
        float grav = PhysUtils.calcGravity(LD.data.player.getY());
        LD.data.world.setGravity(tmp.set(0, grav));

        Vector2 pos = LD.data.getPlayerPos();
        float playerRotation = LD.data.player.getRotation();
        if(pos.y > LD.data.worldMidHeight && playerRotation < 90) {
            Body body = LD.data.getPlayerBody();
            body.setTransform(pos, 180 * MathUtils.degreesToRadians);
        }
        if(pos.y < LD.data.worldMidHeight && playerRotation > 90) {
            Body body = LD.data.getPlayerBody();
            body.setTransform(pos, 0);
        }
    }

    public void stop() {
        LD.data.world.setContactListener(null);
        //LD.data.world.setContactFilter(null);
        LD.post.removeReceiver(this);
    }

    @Override
    public void receive(Message msg) {
        switch(msg.type) {
        case PLAYER_INPUT_LEFT:
            movePlayer(-1);
            break;
        case PLAYER_INPUT_RIGHT:
            movePlayer(1);
            break;
        case PLAYER_INPUT_JUMP:
            jumpPlayer();
            break;
        case PLAYER_STATE:
            checkState((PlayerStates) msg.data);
            break;
        }
    }

    private void checkState(PlayerStates state) {
        // stop moving if dead
        if(state == PlayerStates.DEAD) {
            Body body = LD.data.getPlayerBody();
            body.setLinearVelocity(0, 0);
            body.setAngularVelocity(0);
        }
    }

    private void movePlayer(int dirMul) {
        PlayerStates playerState = LD.data.getPlayerState();
        if(playerState == PlayerStates.DEAD) {
            return;
        }

        // apply the physics of moving the player, and update state
        float forceMul = 1f;
        boolean playerJumping =
                playerState == PlayerStates.JUMP_RIGHT ||
                playerState == PlayerStates.JUMP_LEFT;
        if(playerJumping) {
            forceMul = 0.15f;
        }

        Body body = LD.data.getPlayerBody();
        Vector2 vel = LD.data.getPlayerVel();
        float newVelX = dirMul * forceMul * LD.settings.playerHorzVel;
        body.setLinearVelocity(newVelX, vel.y);
        setState(playerJumping, newVelX);
    }

    private void jumpPlayer() {
        PlayerStates playerState = LD.data.getPlayerState();
        if(playerState == PlayerStates.DEAD) {
            return;
        }

        boolean playerJumping =
                playerState == PlayerStates.JUMP_RIGHT ||
                playerState == PlayerStates.JUMP_LEFT;
        if(playerJumping) {
            return;
        }

        Body body = LD.data.getPlayerBody();
        Vector2 vel = LD.data.getPlayerVel();
        Vector2 pos = LD.data.getPlayerPos();
        body.setLinearVelocity(vel.x, PhysUtils.calcJumpVel(pos.y));
        setState(true, vel.x);
    }

    private void setState(boolean playerJumping, float velX) {
        if(!playerJumping && velX < 0) {
            LD.post.send(Message.Type.PLAYER_STATE, PlayerStates.GROUND_LEFT);
        } else if(!playerJumping) {
            LD.post.send(Message.Type.PLAYER_STATE, PlayerStates.GROUND_RIGHT);
        } else if(velX < 0) {
            LD.post.send(Message.Type.PLAYER_STATE, PlayerStates.JUMP_LEFT);
        } else {
            LD.post.send(Message.Type.PLAYER_STATE, PlayerStates.JUMP_RIGHT);
        }
    }
}
