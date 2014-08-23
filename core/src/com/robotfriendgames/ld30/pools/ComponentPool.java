package com.robotfriendgames.ld30.pools;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.robotfriendgames.ld30.components.Component;

public class ComponentPool {
    private ObjectMap<Component.Type, BasePool<? extends Component>> pools;

    public ComponentPool() {
        pools = new ObjectMap<Component.Type, BasePool<? extends Component>>();
        for(Component.Type t : Component.Type.values()) {
            addPool(t);
        }
    }

    private <T extends Component> void addPool(Component.Type type) {
        pools.put(type, new BasePool<T>((Class<T>) type.get()));
    }

    public <T extends Component> T obtain(Component.Type type) {
        BasePool<T> pool = (BasePool<T>) pools.get(type);
        T comp = pool.obtain();
        return comp;
    }

    public <T extends Component> void free(T obj) {
        BasePool<T> pool = (BasePool<T>) pools.get(obj.type);
        pool.free(obj);
    }

    public <T extends Component> Array<T> getActive(Component.Type type) {
        return (Array<T>) pools.get(type).getActive();
    }

    public <T extends Component> int countActive(Component.Type type) {
        Array<? extends Component> array = pools.get(type).getActive();
        return array.size;
    }
}
