package com.robotfriendgames.ld30.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.robotfriendgames.ld30.comm.Message;
import com.robotfriendgames.ld30.comm.MessageReceiver;
import com.robotfriendgames.ld30.comm.MessageSender;
import com.robotfriendgames.ld30.components.Component;

public class GameEntity extends Sprite implements MessageSender, Pool.Poolable {
    protected Array<Component> components;
    protected Array<Action> actions;

    public GameEntity() {
        components = new Array<Component>(false, 16, Component.class);
    }

    public void update(float delta) {

    }

    public void render() {

    }

    @Override
    public void reset() {
        setScale(1);
        setColor(Color.WHITE);
        setOrigin(0, 0);
        setRotation(0);
        clearActions();
        clearComponents();
    }

    public void clearActions() {
        for (int i = actions.size - 1; i >= 0; i--) {
            actions.get(i).setActor(null);
        }
        actions.clear();
    }

    public void clearComponents() {
        for(int i = 0; i < components.size; i++) {
            Component c = components.get(i);
            LD30.componentPool.free(c);
        }
        components.clear();
    }

    @Override
    public void send(Message.Type type, Object data) {
        Message msg = LD30.messagePool.obtain(type, data);
        for(int i = 0; i < components.size; i++) {
            Component c = components.get(i);
            if(c instanceof MessageReceiver) {
                ((MessageReceiver)c).receive(msg);
            }
        }
        LD30.messagePool.free(msg);
    }
}
