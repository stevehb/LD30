package com.robotfriendgames.ld30.pools;

import com.robotfriendgames.ld30.comm.Message;

public class MessagePool extends BasePool<Message> {
    public MessagePool() {
        super(Message.class);
    }

    public Message obtain(Message.Type type, Object data) {
        Message msg = super.obtain();
        msg.type = type;
        msg.data = data;
        msg.terminate = false;
        return msg;
    }
}
