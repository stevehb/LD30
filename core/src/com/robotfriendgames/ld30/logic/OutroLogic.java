package com.robotfriendgames.ld30.logic;

import com.robotfriendgames.ld30.data.GameStates;
import com.robotfriendgames.ld30.game.LD;

public class OutroLogic {
    public void update(float delta) {
        switch(LD.data.gameState) {
        case OUTRO_IN:
            LD.data.gameState = GameStates.OUTRO;
            break;
        case OUTRO:
            break;
        case OUTRO_OUT:
            LD.data.gameState = GameStates.INTRO_IN;
            break;
        }
    }
}
