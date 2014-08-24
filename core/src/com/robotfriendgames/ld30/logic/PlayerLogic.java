package com.robotfriendgames.ld30.logic;

import com.robotfriendgames.ld30.comm.Message;
import com.robotfriendgames.ld30.components.Component;
import com.robotfriendgames.ld30.components.PlayerInputComponent;
import com.robotfriendgames.ld30.components.PlayerStateComponent;
import com.robotfriendgames.ld30.game.LD;

public class PlayerLogic {
    public static final String TAG = PlayerLogic.class.getSimpleName();

    public void update(float delta) {
        PlayerInputComponent pic = (PlayerInputComponent) LD.data.player.getComponent(Component.Type.PLAYER_CONTROL);
        PlayerStateComponent psc = (PlayerStateComponent) LD.data.player.getComponent(Component.Type.PLAYER_STATE);
        if(pic.left.pressed) {
            LD.post.send(Message.Type.PLAYER_MOVE_LEFT, null);
        }
        if(pic.right.pressed) {
            LD.post.send(Message.Type.PLAYER_MOVE_RIGHT, null);
        }
        if(pic.space.pressed && psc.inContact) {
            LD.post.send(Message.Type.PLAYER_JUMP, null);
        }
    }
}
