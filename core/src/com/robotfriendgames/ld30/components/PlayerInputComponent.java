package com.robotfriendgames.ld30.components;

import com.badlogic.gdx.Input;
import com.robotfriendgames.ld30.comm.Message;
import com.robotfriendgames.ld30.data.KeyState;
import com.robotfriendgames.ld30.game.GameEntity;
import com.robotfriendgames.ld30.game.LD;

public class PlayerInputComponent extends Component {
    public KeyState left, right, space, mouse;

    public PlayerInputComponent() {
        super(Component.Type.PLAYER_CONTROL);
        left = new KeyState();
        right = new KeyState();
        space = new KeyState();
        mouse = new KeyState();
    }

    public static PlayerInputComponent apply(GameEntity parent) {
        PlayerInputComponent pic = LD.componentPool.obtain(Component.Type.PLAYER_CONTROL);
        pic.setParent(parent);
        LD.post.addReceiver(pic);
        return pic;
    }

    @Override
    public void reset() {
        super.reset();
        left.reset();
        right.reset();
        space.reset();
        mouse.reset();
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
