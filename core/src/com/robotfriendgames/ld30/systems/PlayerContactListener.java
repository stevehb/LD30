package com.robotfriendgames.ld30.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.Array;
import com.robotfriendgames.ld30.comm.Message;
import com.robotfriendgames.ld30.components.Component;
import com.robotfriendgames.ld30.components.PhysicsComponent;
import com.robotfriendgames.ld30.game.LD;

public class PlayerContactListener implements ContactListener {
    public  static final String TAG = PlayerContactListener.class.getSimpleName();

    @Override
    public void beginContact(Contact contact) {
        Array<PhysicsComponent> array = LD.componentPool.getActive(Component.Type.PHYSICS);
        for(int i = 0; i < array.size; i++) {
            PhysicsComponent pc = array.get(i);
            if(pc.fixture == contact.getFixtureA() || pc.fixture == contact.getFixtureB()) {
                LD.post.send(Message.Type.PLAYER_CONTACT, this);
            }
        }
    }

    @Override
    public void endContact(Contact contact) {
        Array<PhysicsComponent> array = LD.componentPool.getActive(Component.Type.PHYSICS);
        for(int i = 0; i < array.size; i++) {
            PhysicsComponent pc = array.get(i);
            if(pc.fixture == contact.getFixtureA() || pc.fixture == contact.getFixtureB()) {
                LD.post.send(Message.Type.PLAYER_CONTACT, null);
            }
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) { }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        float maxImpulse = 0, avgImpulse = 0;
        float[] normalImpulses =  impulse.getNormalImpulses();
        for(int i = 0; i < impulse.getCount(); i++) {
            avgImpulse += normalImpulses[i];
            if(normalImpulses[i] > maxImpulse) {
                maxImpulse = normalImpulses[i];
            }
        }
        avgImpulse /= impulse.getCount();
        if(maxImpulse > LD.settings.playerCollisionMaxVel) {
            Gdx.app.log(TAG, "strong contact: " + avgImpulse + ", max=" + maxImpulse);
        }
    }
}
