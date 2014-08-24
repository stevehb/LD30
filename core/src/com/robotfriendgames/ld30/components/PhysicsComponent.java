package com.robotfriendgames.ld30.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.*;
import com.robotfriendgames.ld30.comm.Message;
import com.robotfriendgames.ld30.data.PhysicsData;
import com.robotfriendgames.ld30.game.GameEntity;
import com.robotfriendgames.ld30.game.LD30;

public class PhysicsComponent extends Component {
    public static final String TAG = PhysicsComponent.class.getSimpleName();
    public String physDataName;
    public Body body;
    public Fixture fixture;
    public boolean inContact;

    public PhysicsComponent() {
        super(Type.PHYSICS);
    }

    public static PhysicsComponent apply(GameEntity parent, String physDataName) {
        PhysicsComponent pc = LD30.componentPool.obtain(Type.PHYSICS);
        pc.setParent(parent);
        pc.physDataName = physDataName;
        PhysicsData pd = LD30.settings.physDataMap.get(pc.physDataName);
        pc.createBody(pd);
        LD30.post.addReceiver(pc);
        return pc;
    }

    @Override
    public void update(float delta) {
        copyBodyData();
    }

    @Override
    public void reset() {
        super.reset();
        physDataName = null;
        body = null;
        fixture = null;
        inContact = false;
        LD30.post.removeReceiver(this);
    }

    @Override
    public void receive(Message msg) {
    }

    public void copyBodyData() {
        float x = body.getPosition().x * LD30.settings.worldToPixels;
        float y = body.getPosition().y * LD30.settings.worldToPixels;
        parent.setPosition(x - (parent.getWidth() / 2), y - (parent.getHeight() / 2));
        parent.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
    }

    public void copySettingsData() {
        PhysicsData pd = LD30.settings.physDataMap.get(physDataName);
        fixture.setDensity(pd.density);
        fixture.setFriction(pd.friction);
        fixture.setRestitution(pd.restitution);
        if(pd.position != null) {
            body.setTransform(pd.position.x, pd.position.y, 0);
        }
    }

    private void createBody(PhysicsData physData) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.valueOf(physData.bodyType);
        if(physData.position != null) {
            bodyDef.position.set(physData.position);
            Gdx.app.log(TAG, "set body position to (" + physData.position.x + "," + physData.position.y + ")");
        }
        body = LD30.world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        if(physData.size != null) {
            shape.setAsBox(physData.size.x, physData.size.y);
        } else {
            float x = (parent.getWidth() * LD30.settings.pixelsToWorld) / 2f;
            float y = (parent.getHeight() * LD30.settings.pixelsToWorld) / 2f;
            shape.setAsBox(x, y);
            Gdx.app.log(TAG, "set body size to [" + x + "," + y + "]");
        }

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = physData.density;
        fixtureDef.friction = physData.friction;
        fixtureDef.restitution = physData.restitution;
        fixture = body.createFixture(fixtureDef);


        Gdx.app.log(TAG, "mass=" + body.getMass());
        shape.dispose();
    }
}
