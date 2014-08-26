package com.robotfriendgames.ld30.pools;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ReflectionPool;

public class BasePool<T> extends ReflectionPool<T> {
    private Array<T> active;

    public BasePool(Class<T> type) {
        super(type);
        active = new Array<T>(false, 64, type);
    }

    @Override
    public T obtain() {
        T obj = super.obtain();
        active.add(obj);
        return obj;
    }

    @Override
    public void free(T obj) {
        active.removeValue(obj, true);
        super.free(obj);
    }

    public void freeAll() {
        super.freeAll(active);
    }

    public Array<T> getActive() {
        return active;
    }
}
