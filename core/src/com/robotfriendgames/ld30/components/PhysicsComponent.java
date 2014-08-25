package com.robotfriendgames.ld30.components;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.robotfriendgames.ld30.comm.Message;
import com.robotfriendgames.ld30.data.PhysicsData;
import com.robotfriendgames.ld30.game.GameEntity;
import com.robotfriendgames.ld30.game.LD;

public class PhysicsComponent extends Component {
    public static final String TAG = PhysicsComponent.class.getSimpleName();
    public String physDataName;
    public Body body;
    public Fixture fixture;
    public Vector2 size;

    public PhysicsComponent() {
        super(Type.PHYSICS);
        size = new Vector2();
    }

    public static PhysicsComponent apply(GameEntity parent, String physDataName) {
        PhysicsComponent pc = LD.componentPool.obtain(Type.PHYSICS);
        pc.setParent(parent);
        pc.physDataName = physDataName;
        PhysicsData pd = LD.settings.physDataMap.get(pc.physDataName);
        pc.createBody(pd);
        pc.body.setUserData(parent);
        LD.post.addReceiver(pc);
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
        size.set(Vector2.Zero);
        LD.post.removeReceiver(this);
    }

    @Override
    public void receive(Message msg) {
    }

    public void copyBodyData() {
        // copy from box2d to GameEntity
        float x = body.getPosition().x - (parent.getWidth() / 2);
        float y = body.getPosition().y - (parent.getHeight() / 2);
        parent.setPosition(x, y);
        parent.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
    }

    public void resetData() {
        PhysicsData pd = LD.settings.physDataMap.get(physDataName);
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
        }
        body = LD.data.world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        if(physData.size != null) {
            shape.setAsBox(physData.size.x, physData.size.y);
            size.set(physData.size.x * 2, physData.size.y * 2);
        } else {
            shape.setAsBox(parent.getWidth() / 2, parent.getHeight() / 2);
            size.set(parent.getWidth(), parent.getHeight());
        }

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = physData.density;
        fixtureDef.friction = physData.friction;
        fixtureDef.restitution = physData.restitution;
        fixture = body.createFixture(fixtureDef);

        shape.dispose();
    }
}
