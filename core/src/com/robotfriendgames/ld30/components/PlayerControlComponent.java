package com.robotfriendgames.ld30.components;

import com.badlogic.gdx.Input;
import com.robotfriendgames.ld30.comm.Message;
import com.robotfriendgames.ld30.data.KeyState;
import com.robotfriendgames.ld30.game.GameEntity;
import com.robotfriendgames.ld30.game.LD30;

public class PlayerControlComponent extends Component {
    public KeyState left, right, space, mouse;

    public PlayerControlComponent() {
        super(Component.Type.PLAYER_CONTROL);
        left = new KeyState();
        right = new KeyState();
        space = new KeyState();
        mouse = new KeyState();
    }

    public static PlayerControlComponent apply(GameEntity parent) {
        PlayerControlComponent pcc = LD30.componentPool.obtain(Component.Type.PLAYER_CONTROL);
        pcc.setParent(parent);
        LD30.post.addReceiver(pcc);
        return pcc;
    }

    @Override
    public void reset() {
        super.reset();
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
        switch(state.key) {
        case Input.Keys.LEFT:
            left.set(state);
            break;
        case Input.Keys.RIGHT:
            right.set(state);
            break;
        case Input.Keys.SPACE:
            space.set(state);
            break;
        case Input.Buttons.LEFT:
        case Input.Buttons.MIDDLE:
        case Input.Buttons.RIGHT:
            mouse.set(state);
            break;
        }
    }
}
