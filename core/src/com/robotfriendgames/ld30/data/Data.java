package com.robotfriendgames.ld30.data;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.robotfriendgames.ld30.components.Component;
import com.robotfriendgames.ld30.components.PhysicsComponent;
import com.robotfriendgames.ld30.components.PlayerStateComponent;
import com.robotfriendgames.ld30.game.GameEntity;
import com.robotfriendgames.ld30.game.LD;

public class Data {
    public GameStates gameState;
    public RenderLevel renderLevel;

    public OrthographicCamera camera;
    public Viewport viewport;

    public float worldWidth, worldHeight;
    public float worldMaxHeight;
    public float worldMidHeight;

    public World world = new World(new Vector2(0, LD.settings.fullGravity), true);
    public GameEntity[] players;
    public GameEntity player;
    public GameEntity[] platforms;
    public int safePlatformIdx = -1;

    public Body getPlayerBody() {
        PhysicsComponent pc = (PhysicsComponent) player.getComponent(Component.Type.PHYSICS);
        return pc.body;
    }

    public Vector2 getPlayerPos() {
        PhysicsComponent pc = (PhysicsComponent) player.getComponent(Component.Type.PHYSICS);
        return pc.body.getPosition();
    }

    public Vector2 getPlayerVel() {
        PhysicsComponent pc = (PhysicsComponent) player.getComponent(Component.Type.PHYSICS);
        return pc.body.getLinearVelocity();
    }

    public PlayerStates getPlayerState() {
        PlayerStateComponent psc = (PlayerStateComponent) player.getComponent(Component.Type.PLAYER_STATE);
        return psc.playerState;
    }
}
