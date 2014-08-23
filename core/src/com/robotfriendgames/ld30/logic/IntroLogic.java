package com.robotfriendgames.ld30.logic;

import com.badlogic.gdx.utils.Array;
import com.robotfriendgames.ld30.components.Component;
import com.robotfriendgames.ld30.components.IntroControlComponent;
import com.robotfriendgames.ld30.data.GameState;
import com.robotfriendgames.ld30.game.GameEntity;
import com.robotfriendgames.ld30.game.LD30;

public class IntroLogic {
    public void update(float delta) {
        switch(LD30.data.gameState) {
        case INTRO_IN:
            GameEntity intro = LD30.entityFactory.makeIntroBackground();
            LD30.data.entities.add(intro);
            LD30.data.gameState = GameState.INTRO;
            break;
        case INTRO:
            Array<IntroControlComponent> array = LD30.componentPool.getActive(Component.Type.INTRO_CONTROL);
            IntroControlComponent icc = null;
            if(array.size > 0) {
                icc = array.first();
            }
            if(icc != null && icc.hasUp) {
                LD30.data.gameState = GameState.INTRO_OUT;
            }
            break;
        case INTRO_OUT:
            LD30.data.gameState = GameState.PLAY_IN;
            break;
        }
    }
}
