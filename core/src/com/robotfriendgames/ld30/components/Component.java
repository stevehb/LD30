package com.robotfriendgames.ld30.components;

import com.badlogic.gdx.utils.Pool;
import com.robotfriendgames.ld30.comm.Message;
import com.robotfriendgames.ld30.comm.MessageReceiver;
import com.robotfriendgames.ld30.game.GameEntity;

public class Component implements MessageReceiver, Pool.Poolable {
    public enum Type {
        HORZ_WRAP(HorzWrapComponent.class),
        INTRO_CONTROL(IntroControlComponent.class),
        PHYSICS(PhysicsComponent.class),
        PLAYER_CONTROL(PlayerInputComponent.class),
        PLAYER_STATE(PlayerStateComponent.class);

        private Class<? extends Component> componentClass;

        Type(Class<? extends Component> c) {
            this.componentClass = c;
        }

        public Class<? extends Component> get() {
            return this.componentClass;
        }
    }

    final public Type type;
    public GameEntity parent;

    public Component(Component.Type type) {
        this.type = type;
    }

    public void setParent(GameEntity parent) {
        this.parent = parent;
        parent.addComponent(this);
    }

    public void update(float delta) { }

    @Override
    public void receive(Message msg) { }

    @Override
    public void reset() {
        parent = null;
    }

    public GameEntity getParent() {
        return parent;
    }
}
