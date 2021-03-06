package com.robotfriendgames.ld30.data;

public class KeyState {
    public int key;
    public boolean pressed;
    public long eventTime;

    @Override
    public String toString() {
        return "KeyState{" +
                "key=" + key +
                ", pressed=" + pressed +
                ", eventTime=" + eventTime +
                '}';
    }

    public void set(KeyState state) {
        key = state.key;
        pressed = state.pressed;
        eventTime = state.eventTime;
    }

    public void reset() {
        key = -1;
        pressed = false;
        eventTime = -1;
    }
}
