package com.robotfriendgames.ld30.comm;

import com.badlogic.gdx.utils.Pool;

public class Message implements Pool.Poolable {
    public enum Type {
        INPUT,
        PLAY_SOUND,
        PLAYER_INPUT_LEFT, PLAYER_INPUT_RIGHT, PLAYER_INPUT_JUMP,
        PLAYER_RESET, PLAYER_STATE,
        WIN_WAIT_START, WIN_WAIT_STOP, WIN_WAIT_END;
    }
    public Message.Type type;
    public Object data;
    public boolean terminate;

    @Override
    public String toString() {
        return "Message[" + type + "]";
    }

    @Override
    public void reset() {
        type = null;
        data = null;
        terminate = false;
    }
}
