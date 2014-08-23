package com.robotfriendgames.ld30.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.robotfriendgames.ld30.comm.Message;
import com.robotfriendgames.ld30.data.KeyState;

public class InputProcessor extends InputAdapter {
    public static final String TAG = InputProcessor.class.getSimpleName();
    private KeyState tmp;

    public InputProcessor() {
        tmp = new KeyState();
    }

    @Override
    public boolean keyDown(int keycode) {
        tmp.key = keycode;
        tmp.pressed = true;
        tmp.eventTime = Gdx.input.getCurrentEventTime();
        LD30.post.send(Message.Type.INPUT, tmp);
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        tmp.key = keycode;
        tmp.pressed = false;
        tmp.eventTime = Gdx.input.getCurrentEventTime();
        LD30.post.send(Message.Type.INPUT, tmp);
        return true;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        tmp.key = button;
        tmp.pressed = true;
        tmp.eventTime = Gdx.input.getCurrentEventTime();
        LD30.post.send(Message.Type.INPUT, tmp);
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        tmp.key = button;
        tmp.pressed = false;
        tmp.eventTime = Gdx.input.getCurrentEventTime();
        LD30.post.send(Message.Type.INPUT, tmp);
        return true;
    }
}
