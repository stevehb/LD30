package com.robotfriendgames.ld30.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.robotfriendgames.ld30.components.Component;
import com.robotfriendgames.ld30.components.PhysicsComponent;
import com.robotfriendgames.ld30.components.PlayerControlComponent;
import com.robotfriendgames.ld30.game.GameEntity;
import com.robotfriendgames.ld30.game.LD30;

public class PlayerLogic {
    public static final String TAG = PlayerLogic.class.getSimpleName();

    private GameEntity player;
    private boolean isFlipped;

    public PlayerLogic(GameEntity player) {
        this.player = player;
    }

    public void update(float delta) {
        PlayerControlComponent pcc = (PlayerControlComponent) player.getComponent(Component.Type.PLAYER_CONTROL);
        PhysicsComponent pc = (PhysicsComponent) player.getComponent(Component.Type.PHYSICS);
        Vector2 vel = pc.body.getLinearVelocity();
        Vector2 pos = pc.body.getPosition();

        boolean underHorzMaxVel = Math.abs(vel.x) < LD30.settings.playerHorzMaxVel;
        if(pcc.left.pressed && underHorzMaxVel) {
            pc.body.applyLinearImpulse(-LD30.settings.playerHorzAccel, 0, pos.x, pos.y, true);
        }
        if(pcc.right.pressed && underHorzMaxVel) {
            pc.body.applyLinearImpulse(LD30.settings.playerHorzAccel, 0, pos.x, pos.y, true);
        }
        if(pcc.space.pressed && pc.inContact) {
            pc.body.applyLinearImpulse(0, LD30.settings.playerJumpAccel, pos.x, pos.y, true);
            pcc.space.pressed = false;
        }

        //Gdx.app.log(TAG, "tracking body=(" + (int)pos.x + "," + (int)pos.y + ") vel=(" + (int)vel.x + "," + (int)vel.y + ")");

        if(!isFlipped && vel.x < 1) {
            Gdx.app.log(TAG, "reversing X to left");
            player.flip(true, false);
            isFlipped = true;
        }
        if(isFlipped && vel.x > 1) {
            Gdx.app.log(TAG, "reversing X to right");
            player.flip(true, false);
            isFlipped = false;
        }

    }

}
