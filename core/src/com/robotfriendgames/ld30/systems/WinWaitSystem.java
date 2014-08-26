package com.robotfriendgames.ld30.systems;

import com.robotfriendgames.ld30.comm.Message;
import com.robotfriendgames.ld30.comm.MessageReceiver;
import com.robotfriendgames.ld30.data.GameStates;
import com.robotfriendgames.ld30.game.LD;

public class WinWaitSystem implements MessageReceiver {
    public static final String TAG = WinWaitSystem.class.getSimpleName();

    private float accumTime;
    private boolean counting;

    public void start() {
        LD.post.addReceiver(this);
    }

    public void update(float delta) {
        if(counting) {
            accumTime += delta;
        }
        if(accumTime >= LD.settings.winWaitDelay) {
            LD.data.gameState = GameStates.PLAY_OUT;
            LD.post.send(Message.Type.WIN_WAIT_END, null);
        }
    }

    @Override
    public void receive(Message msg) {
        switch(msg.type) {
        case WIN_WAIT_START:
            accumTime = 0;
            counting = true;
            break;
        case WIN_WAIT_STOP:
            accumTime = 0;
            counting = false;
            break;
        }
    }

    public void stop() {
        LD.post.removeReceiver(this);
    }
}
