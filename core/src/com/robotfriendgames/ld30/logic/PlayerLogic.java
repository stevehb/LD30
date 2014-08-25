package com.robotfriendgames.ld30.logic;

import com.badlogic.gdx.physics.box2d.Body;
import com.robotfriendgames.ld30.comm.Message;
import com.robotfriendgames.ld30.comm.MessageReceiver;
import com.robotfriendgames.ld30.components.Component;
import com.robotfriendgames.ld30.components.PhysicsComponent;
import com.robotfriendgames.ld30.components.PlayerInputComponent;
import com.robotfriendgames.ld30.components.PlayerStateComponent;
import com.robotfriendgames.ld30.data.PlayerSprites;
import com.robotfriendgames.ld30.data.PlayerStates;
import com.robotfriendgames.ld30.game.GameEntity;
import com.robotfriendgames.ld30.game.LD;

public class PlayerLogic implements MessageReceiver {
    public static final String TAG = PlayerLogic.class.getSimpleName();

    public void start() {
        LD.post.addReceiver(this);
    }

    public void update(float delta) {
        copyPhysToDisplay();

        // turn user input into player actions
        PlayerInputComponent pic = (PlayerInputComponent) LD.data.player.getComponent(Component.Type.PLAYER_CONTROL);
        PlayerStateComponent psc = (PlayerStateComponent) LD.data.player.getComponent(Component.Type.PLAYER_STATE);
        if(pic.left.pressed) {
            LD.post.send(Message.Type.PLAYER_INPUT_LEFT, null);
        }
        if(pic.right.pressed) {
            LD.post.send(Message.Type.PLAYER_INPUT_RIGHT, null);
        }
        if(pic.space.pressed) {
            LD.post.send(Message.Type.PLAYER_INPUT_JUMP, null);
        }
    }

    public void stop() {
        LD.post.removeReceiver(this);
    }

    @Override
    public void receive(Message msg) {
        switch(msg.type) {
        case PLAYER_RESET:
            resetPlayer();
            break;
        }
    }

    private void copyPhysToDisplay() {
        // copy from phys player to other visible player sprites
        GameEntity phys = LD.data.players[PlayerSprites.PHYS];
        for(int i = 0; i < PlayerStates.states.length; i++) {
            if(i == PlayerSprites.PHYS) {
                continue;
            }
            PlayerStates state = PlayerStates.states[i];
            GameEntity sprite = LD.data.players[state.getIdx()];
            // calc X and Y to center visible sprites not the size of phys (like explosion)
            float x = phys.getX() + (phys.getWidth() / 2) - (sprite.getWidth() / 2);
            float y = phys.getY() + (phys.getHeight() / 2) - (sprite.getHeight() / 2);
            sprite.setPosition(x, y);
            sprite.setRotation(phys.getRotation());
        }

    }

    private void resetPlayer() {
        PhysicsComponent pc = (PhysicsComponent) LD.data.player.getComponent(Component.Type.PHYSICS);
        pc.resetData();
        LD.data.players[PlayerSprites.EXPLOSION].resetAnim();
        if(LD.data.safePlatformIdx != -1) {
            GameEntity platform = LD.data.platforms[LD.data.safePlatformIdx];
            float startX = platform.getX() + (platform.getWidth() / 2);
            float startY = platform.getY() + platform.getHeight() + LD.data.player.getHeight() / 2;
            // set starting position, plus extra Y to prevent fall through
            Body body = LD.data.getPlayerBody();
            body.setTransform(startX, startY + LD.settings.playerLandingMaxVel, 0);
        }
        LD.post.send(Message.Type.PLAYER_STATE, PlayerStates.STILL);
    }
}
