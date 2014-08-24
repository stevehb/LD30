package com.robotfriendgames.ld30.components;

import com.robotfriendgames.ld30.comm.Message;
import com.robotfriendgames.ld30.data.KeyState;
import com.robotfriendgames.ld30.game.GameEntity;
import com.robotfriendgames.ld30.game.LD;

public class IntroControlComponent extends Component {
    public boolean hasUp;

    public IntroControlComponent() {
        super(Type.INTRO_CONTROL);
    }

    public static IntroControlComponent apply(GameEntity parent) {
        IntroControlComponent icc = LD.componentPool.obtain(Type.INTRO_CONTROL);
        icc.setParent(parent);
        LD.post.addReceiver(icc);
        return icc;
    }

    @Override
    public void reset() {
        super.reset();
        hasUp = false;
        LD.post.removeReceiver(this);
    }

    @Override
    public void receive(Message msg) {
        switch(msg.type) {
        case INPUT:

            storeInput((KeyState) msg.data);
            break;
        }
    }

    private void storeInput(KeyState state) {
        if(!state.pressed) {
            hasUp = true;
        }
    }
}
