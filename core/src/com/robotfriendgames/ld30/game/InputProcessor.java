package com.robotfriendgames.ld30.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.robotfriendgames.ld30.comm.Message;
import com.robotfriendgames.ld30.components.Component;
import com.robotfriendgames.ld30.components.PhysicsComponent;
import com.robotfriendgames.ld30.data.KeyState;
import com.robotfriendgames.ld30.data.Settings;

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
        LD.post.send(Message.Type.INPUT, tmp);
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(keycode == Input.Keys.R) {
            LD.settings = Settings.load();
            LD.data.world.setGravity(new Vector2(0f, LD.settings.fullGravity));
            Array<PhysicsComponent> array = LD.componentPool.getActive(Component.Type.PHYSICS);
            for(int i = 0; i < array.size; i++) {
                array.get(i).copySettingsData();
                array.get(i).body.setActive(true);
            }
            return true;
        }

        tmp.key = keycode;
        tmp.pressed = false;
        tmp.eventTime = Gdx.input.getCurrentEventTime();
        LD.post.send(Message.Type.INPUT, tmp);
        return true;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        tmp.key = button;
        tmp.pressed = true;
        tmp.eventTime = Gdx.input.getCurrentEventTime();
        LD.post.send(Message.Type.INPUT, tmp);
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        tmp.key = button;
        tmp.pressed = false;
        tmp.eventTime = Gdx.input.getCurrentEventTime();
        LD.post.send(Message.Type.INPUT, tmp);
        return true;
    }
}
