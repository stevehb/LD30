package com.robotfriendgames.ld30.logic;

import com.robotfriendgames.ld30.data.GameState;
import com.robotfriendgames.ld30.game.LD30;

public class OutroLogic {
    public void update(float delta) {
        switch(LD30.data.gameState) {
        case OUTRO_IN:
            LD30.data.gameState = GameState.OUTRO;
            break;
        case OUTRO:
            break;
        case OUTRO_OUT:
            LD30.data.gameState = GameState.INTRO_IN;
            break;
        }
    }
}
