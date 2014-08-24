package com.robotfriendgames.ld30.logic;

import com.robotfriendgames.ld30.data.GameState;
import com.robotfriendgames.ld30.game.GameEntity;
import com.robotfriendgames.ld30.game.LD30;
import com.robotfriendgames.ld30.systems.PhysicsSystem;

public class PlayLogic {
    public static final String TAG = PlayLogic.class.getSimpleName();

    private GameEntity gameBg;
    private GameEntity player;
    private PlayerLogic playerLogic;
    private PhysicsSystem physicsSystem;

    public void update(float delta) {
        switch(LD30.data.gameState) {
        case PLAY_IN:
            gameBg = LD30.entityFactory.makeGameBackground();
            player = LD30.entityFactory.makePlayer();
            playerLogic = new PlayerLogic(player);
            physicsSystem = new PhysicsSystem();
            LD30.entityFactory.makeGround();
            LD30.data.gameState = GameState.PLAY;
            break;
        case PLAY:
            playerLogic.update(delta);
            physicsSystem.update(delta);
            break;
        case PLAY_OUT:
            LD30.data.gameState = GameState.OUTRO_IN;
            break;
        }
    }
}
