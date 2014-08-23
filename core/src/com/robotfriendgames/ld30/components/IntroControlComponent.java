package com.robotfriendgames.ld30.components;

import com.robotfriendgames.ld30.comm.Message;
import com.robotfriendgames.ld30.data.KeyState;
import com.robotfriendgames.ld30.game.GameEntity;
import com.robotfriendgames.ld30.game.LD30;

public class IntroControlComponent extends Component {
    public boolean hasUp;

    public IntroControlComponent() {
        super(Type.INTRO_CONTROL);
    }

    public static IntroControlComponent apply(GameEntity parent) {
        IntroControlComponent pcc = LD30.componentPool.obtain(Type.INTRO_CONTROL);
        pcc.setParent(parent);
        LD30.post.addReceiver(pcc);
        return pcc;
    }

    @Override
    public void reset() {
        super.reset();
        hasUp = false;
        LD30.post.removeReceiver(this);
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
