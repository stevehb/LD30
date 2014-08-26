package com.robotfriendgames.ld30.systems;

import com.robotfriendgames.ld30.comm.Message;
import com.robotfriendgames.ld30.comm.MessageReceiver;
import com.robotfriendgames.ld30.data.SoundData;
import com.robotfriendgames.ld30.game.LD;

public class SoundSystem implements MessageReceiver {
    public static final String TAG = SoundSystem.class.getSimpleName();

    public void start() {
        LD.post.addReceiver(this);
    }

    @Override
    public void receive(Message msg) {
        switch(msg.type) {
        case PLAY_SOUND:
            playSound((String) msg.data);
            break;
        }
    }

    public void stop() {
        LD.post.removeReceiver(this);
    }

    private void playSound(String name) {
        SoundData sd = LD.sounds.map.get(name);
        sd.sound.play(sd.volume);
    }
}
