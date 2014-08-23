package com.robotfriendgames.ld30.comm;

import com.badlogic.gdx.utils.Pool;

public class Message implements Pool.Poolable {
    public enum Type {
        INPUT,
        UPDATE_PULSE;
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
