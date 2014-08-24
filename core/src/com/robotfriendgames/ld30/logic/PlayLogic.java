package com.robotfriendgames.ld30.logic;

import com.robotfriendgames.ld30.data.GameStates;
import com.robotfriendgames.ld30.game.GameEntity;
import com.robotfriendgames.ld30.game.LD;
import com.robotfriendgames.ld30.systems.CameraTrackSystem;
import com.robotfriendgames.ld30.systems.PhysicsSystem;

public class PlayLogic {
    public static final String TAG = PlayLogic.class.getSimpleName();

    private CameraTrackSystem cameraTrackSystem;
    private PlayerLogic playerLogic;
    private PhysicsSystem physicsSystem;

    public void update(float delta) {
        switch(LD.data.gameState) {
        case PLAY_IN:
            LD.entityFactory.makeGameBackground();
            LD.entityFactory.makeGround();
            LD.data.player = LD.entityFactory.makePlayer();

            cameraTrackSystem = new CameraTrackSystem();
            playerLogic = new PlayerLogic();
            physicsSystem = new PhysicsSystem();
            physicsSystem.start();

            LD.data.gameState = GameStates.PLAY;
            break;
        case PLAY:
            cameraTrackSystem.update(delta);
            playerLogic.update(delta);
            physicsSystem.update(delta);
            break;
        case PLAY_OUT:
            physicsSystem.stop();
            cameraTrackSystem = null;
            for(int i = 0; i <= LD.entityPool.getActive().size; i++) {
                GameEntity entity = LD.entityPool.getActive().get(i);
                LD.entityPool.free(entity);
            }
            LD.data.player = null;
            LD.data.gameState = GameStates.OUTRO_IN;
            break;
        }
    }
}
