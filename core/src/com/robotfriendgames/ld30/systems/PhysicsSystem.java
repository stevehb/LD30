package com.robotfriendgames.ld30.systems;

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
    private PlayerContactFilter contactFilter;

    public PhysicsSystem() {
        contactListener = new PlayerContactListener();
        contactFilter = new PlayerContactFilter();
    }

    public void start() {
        LD.data.world.setContactListener(contactListener);
        LD.data.world.setContactFilter(contactFilter);
        LD.post.addReceiver(this);
    }

    public void update(float delta) {
        float grav = PhysUtils.calcGravity(LD.data.player.getY());
        LD.data.world.setGravity(tmp.set(0, grav));
    }

    public void stop() {
        LD.data.world.setContactListener(null);
        LD.data.world.setContactFilter(null);
        LD.post.removeReceiver(this);
    }

    @Override
    public void receive(Message msg) {
        switch(msg.type) {
        case PLAYER_MOVE_LEFT:
            movePlayer(-1);
            break;
        case PLAYER_MOVE_RIGHT:
            movePlayer(1);
            break;
        case PLAYER_JUMP:
            jumpPlayer();
            break;
        }
    }

    private void movePlayer(int dirMul) {
        Body body = LD.data.getPlayerBody();
        Vector2 vel = LD.data.getPlayerVel();
        Vector2 pos = LD.data.getPlayerPos();
        if(Math.abs(vel.x) < LD.settings.playerHorzMaxVel) {
            body.applyForce(dirMul * LD.settings.playerHorzForce, 0, pos.x, pos.y, true);
            //body.applyLinearImpulse(dirMul * LD.settings.playerHorzForce, 0, pos.x, pos.y, true);
        }

        if(vel.x < 0) {
            LD.post.send(Message.Type.PLAYER_STATE, PlayerStates.MOVE_LEFT);
        } else {
            LD.post.send(Message.Type.PLAYER_STATE, PlayerStates.MOVE_RIGHT);
        }
    }

    private void jumpPlayer() {
        Body body = LD.data.getPlayerBody();
        Vector2 vel = LD.data.getPlayerVel();
        Vector2 pos = LD.data.getPlayerPos();
        body.setLinearVelocity(vel.x, PhysUtils.calcJumpVel(pos.y));
        LD.post.send(Message.Type.PLAYER_STATE, PlayerStates.JUMPING);
    }
}
