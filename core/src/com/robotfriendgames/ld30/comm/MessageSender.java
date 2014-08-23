package com.robotfriendgames.ld30.comm;

public interface MessageSender {
    public void send(Message.Type type, Object data);
}
