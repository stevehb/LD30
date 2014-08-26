package com.robotfriendgames.ld30.logic;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.robotfriendgames.ld30.comm.Message;
import com.robotfriendgames.ld30.data.GameStates;
import com.robotfriendgames.ld30.data.PlayerSprites;
import com.robotfriendgames.ld30.game.LD;
import com.robotfriendgames.ld30.systems.CameraTrackSystem;
import com.robotfriendgames.ld30.systems.PhysicsSystem;
import com.robotfriendgames.ld30.systems.WinWaitSystem;

public class PlayLogic {
    public static final String TAG = PlayLogic.class.getSimpleName();

    private CameraTrackSystem cameraTrackSystem;
    private PlayerLogic playerLogic;
    private PhysicsSystem physicsSystem;
    private WinWaitSystem winWaitSystem;

    public void update(float delta) {
        switch(LD.data.gameState) {
        case PLAY_IN:
            resetData();
            LD.data.world = new World(new Vector2(0, LD.settings.fullGravity), true);
            LD.entityFactory.makeGameBackground();
            LD.entityFactory.makeStartGround();
            LD.entityFactory.makeEndGround();
            LD.entityFactory.makePlayers();
            LD.data.player = LD.data.players[PlayerSprites.PHYS];
            LD.data.platforms = LD.entityFactory.makePlatforms();

            cameraTrackSystem = new CameraTrackSystem();
            playerLogic = new PlayerLogic();
            playerLogic.start();
            physicsSystem = new PhysicsSystem();
            physicsSystem.start();
            winWaitSystem = new WinWaitSystem();
            winWaitSystem.start();

            LD.post.send(Message.Type.PLAY_SOUND, "playButton");
            LD.data.gameState = GameStates.PLAY;
            break;
        case PLAY:
            cameraTrackSystem.update(delta);
            playerLogic.update(delta);
            physicsSystem.update(delta);
            winWaitSystem.update(delta);
            break;
        case PLAY_OUT:
            physicsSystem.stop();
            playerLogic.stop();
            winWaitSystem.stop();
            LD.entityPool.freeAll();
            LD.data.world.dispose();
            LD.data.world = null;
            resetData();
            LD.data.gameState = GameStates.OUTRO_IN;
            break;
        }
    }

    private void resetData() {
        LD.data.player = null;
        LD.data.players = null;
        LD.data.platforms = null;
        LD.data.safePlatformIdx = -1;
    }
}
