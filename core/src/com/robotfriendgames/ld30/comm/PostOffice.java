package com.robotfriendgames.ld30.comm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.robotfriendgames.ld30.game.LD30;

public class PostOffice implements MessageSender {
    private Array<MessageReceiver> register;

    public PostOffice() {
        register = new Array<MessageReceiver>(false, 64, MessageReceiver.class);
    }

    public void addReceiver(MessageReceiver receiver) {
        register.add(receiver);
    }

    public void removeReceiver(MessageReceiver receiver) {
        boolean removed = register.removeValue(receiver, true);
        if(!removed) {
            Gdx.app.error("GDX", "WARNING: no receiver[" + receiver + "] found to be removed");
        }
    }

    @Override
    public void send(Message.Type type, Object data) {
        Message msg = LD30.messagePool.obtain(type, data);
        int regCount = register.size;
        for(int i = 0; i < Math.min(regCount, register.size) && !msg.terminate; i++) {
            MessageReceiver recv = register.get(i);
            recv.receive(msg);
        }
        LD30.messagePool.free(msg);
    }
}
