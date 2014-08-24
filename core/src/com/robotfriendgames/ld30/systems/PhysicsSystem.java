package com.robotfriendgames.ld30.systems;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.Array;
import com.robotfriendgames.ld30.components.Component;
import com.robotfriendgames.ld30.components.PhysicsComponent;
import com.robotfriendgames.ld30.game.LD30;

public class PhysicsSystem {
    public static final String TAG = PhysicsSystem.class.getSimpleName();

    private class ContactListener implements com.badlogic.gdx.physics.box2d.ContactListener {
        @Override
        public void beginContact(Contact contact) {
            Array<PhysicsComponent> array = LD30.componentPool.getActive(Component.Type.PHYSICS);
            for(int i = 0; i < array.size; i++) {
                PhysicsComponent pc = array.get(i);
                if(pc.fixture == contact.getFixtureA() || pc.fixture == contact.getFixtureB()) {
                    pc.inContact = true;
                }
            }
        }

        @Override
        public void endContact(Contact contact) {
            Array<PhysicsComponent> array = LD30.componentPool.getActive(Component.Type.PHYSICS);
            for(int i = 0; i < array.size; i++) {
                PhysicsComponent pc = array.get(i);
                if(pc.fixture == contact.getFixtureA() || pc.fixture == contact.getFixtureB()) {
                    pc.inContact = false;
                }
            }
        }

        @Override
        public void preSolve(Contact contact, Manifold oldManifold) {

        }

        @Override
        public void postSolve(Contact contact, ContactImpulse impulse) {

        }
    }

    public PhysicsSystem() {
        LD30.world.setContactListener(new ContactListener());
    }

    public void update(float delta) {

    }
}
