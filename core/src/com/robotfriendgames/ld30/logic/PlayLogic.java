package com.robotfriendgames.ld30.logic;

import com.robotfriendgames.ld30.data.GameState;
import com.robotfriendgames.ld30.game.LD30;

public class PlayLogic {
    public void update(float delta) {
        switch(LD30.data.gameState) {
        case PLAY_IN:
            LD30.data.gameState = GameState.PLAY;
            break;
        case PLAY:
            break;
        case PLAY_OUT:
            LD30.data.gameState = GameState.OUTRO_IN;
            break;
        }
    }
}
