package com.robotfriendgames.ld30.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.robotfriendgames.ld30.game.GameEntity;
import com.robotfriendgames.ld30.game.LD;

public class HorzWrapComponent extends Component {
    public static final String TAG = HorzWrapComponent.class.getSimpleName();

    public HorzWrapComponent() {
        super(Type.HORZ_WRAP);
    }

    public static HorzWrapComponent apply(GameEntity parent) {
        HorzWrapComponent hc = LD.componentPool.obtain(Type.HORZ_WRAP);
        hc.setParent(parent);
        return hc;
    }

    public void update(float delta) {
        PhysicsComponent pc = (PhysicsComponent) parent.getComponent(Component.Type.PHYSICS);
        Vector2 pos = pc.body.getPosition();
        float worldWidth = Gdx.graphics.getWidth() * LD.settings.pixelsToWorld;

        if(pos.x < 0) {
            pc.body.setTransform(worldWidth, pos.y, pc.body.getAngle());
        }
        if(pos.x > worldWidth) {
            pc.body.setTransform(0, pos.y, pc.body.getAngle());
        }


    }

    @Override
    public void reset() {
        super.reset();
    }
}
