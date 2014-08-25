package com.robotfriendgames.ld30.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.robotfriendgames.ld30.comm.Message;
import com.robotfriendgames.ld30.comm.MessageSender;
import com.robotfriendgames.ld30.components.Component;
import com.robotfriendgames.ld30.data.AnimationData;
import com.robotfriendgames.ld30.data.ObjectType;
import com.robotfriendgames.ld30.data.RenderLevel;

public class GameEntity extends Sprite implements MessageSender, Pool.Poolable {
    public static final String TAG = GameEntity.class.getSimpleName();

    public ObjectType type;
    protected Array<Component> components;
    protected Array<Action> actions;
    protected AnimationData animData;
    protected float animElapsed;
    protected int frameIdx;
    protected boolean animFinished;
    protected boolean isVisible;
    protected RenderLevel renderLevel;

    public GameEntity() {
        components = new Array<Component>(false, 16, Component.class);
        actions = new Array<Action>(false, 8, Action.class);
    }

    public void update(float delta) {
        // update actions (from libgdx's Actor.java)
        for(int i = 0; i < actions.size; i++) {
            Action action = actions.get(i);
            if(action.act(delta) && i < actions.size) {
                Action current = actions.get(i);
                int actionIndex = current == action ? i : actions.indexOf(action, true);
                if(actionIndex != -1) {
                    actions.removeIndex(actionIndex);
                    action.setActor(null);
                    i--;
                }
            }
        }

        // update components
        for(int i = 0; i < components.size; i++) {
            Component c = components.get(i);
            c.update(delta);
        }

        // update animation
        if(animData != null && animData.isAnim && isVisible) {
            animElapsed += delta;
            float frameDelay = 1f / animData.fps;

            if(animElapsed > frameDelay) {
                frameIdx++;
                animElapsed -= frameDelay;
            }
            if(frameIdx >= animData.frames.length) {
                frameIdx = animData.loop ? 0 : frameIdx - 1;
                animFinished = true;
            }
            TextureRegion region = animData.frames[frameIdx];
            float width = region.getRegionWidth() * LD.settings.pixelsToWorld;
            float height = region.getRegionHeight() * LD.settings.pixelsToWorld;
            setSize(width, height);
        }
    }

    @Override
    public void draw(Batch batch) {
        if(animData != null && LD.data.renderLevel == renderLevel) {
            setRegion(animData.frames[frameIdx]);
            super.draw(batch);
        }
    }

    @Override
    public void reset() {
        animData = null;
        animElapsed = 0;
        frameIdx = 0;
        animFinished = false;
        isVisible = true;
        renderLevel = null;
        setScale(1);
        setColor(Color.WHITE);
        setOrigin(0, 0);
        setRotation(0);
        clearActions();
        clearComponents();
    }

    public void makeInvisible() {
        Color c = getColor();
        c.a = 0;
        setColor(c);
        isVisible = false;
    }

    public void makeVisible() {
        Color c = getColor();
        c.a = 1;
        setColor(c);
        isVisible = true;
    }

    public void setAnimData(AnimationData animData) {
        this.animData = animData;
        TextureRegion region = this.animData.frames[frameIdx];
        float width = region.getRegionWidth() * LD.settings.pixelsToWorld;
        float height = region.getRegionHeight() * LD.settings.pixelsToWorld;
        setSize(width, height);
        setOrigin(getWidth() / 2f, getHeight() / 2f);
    }

    public AnimationData getAnimData() {
        return animData;
    }

    public boolean isAnimFinished() {
        return animFinished;
    }

    public void resetAnim() {
        animElapsed = 0;
        frameIdx = 0;
        animFinished = false;
    }

    public void setRenderLevel(RenderLevel renderLevel) {
        this.renderLevel = renderLevel;
    }

    public RenderLevel getRenderLevel() {
        return renderLevel;
    }

    public void addAction(Action action) {
        actions.add(action);
    }

    public void removeAction(Action action) {
        if(actions.removeValue(action, true)) {
            action.setActor(null);
        }
    }

    public Action[] getActions() {
        return actions.items;
    }

    public void clearActions() {
        for (int i = actions.size - 1; i >= 0; i--) {
            actions.get(i).setActor(null);
        }
        actions.clear();
    }

    public void addComponent(Component component) {
        components.add(component);
    }

    public void removeComponent(Component component) {
        components.removeValue(component, true);
        LD.componentPool.free(component);
    }

    public Component getComponent(Component.Type type) {
        for(int i = 0; i < components.size; i++) {
            Component c = components.get(i);
            if(c.type == type) {
                return c;
            }
        }
        return null;
    }

    public Component[] getComponents() {
        return components.items;
    }

    public void clearComponents() {
        for(int i = 0; i < components.size; i++) {
            Component c = components.get(i);
            LD.componentPool.free(c);
        }
        components.clear();
    }

    @Override
    public void send(Message.Type type, Object data) {
        Message msg = LD.messagePool.obtain(type, data);
        for(int i = 0; i < components.size; i++) {
            Component c = components.get(i);
            c.receive(msg);
        }
        LD.messagePool.free(msg);
    }
}
