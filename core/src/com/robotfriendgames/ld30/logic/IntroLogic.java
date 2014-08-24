package com.robotfriendgames.ld30.logic;

import com.badlogic.gdx.utils.Array;
import com.robotfriendgames.ld30.components.Component;
import com.robotfriendgames.ld30.components.IntroControlComponent;
import com.robotfriendgames.ld30.data.GameState;
import com.robotfriendgames.ld30.game.GameEntity;
import com.robotfriendgames.ld30.game.LD30;

public class IntroLogic {
    private GameEntity introBg;

    public void update(float delta) {
        switch(LD30.data.gameState) {
        case INTRO_IN:
            introBg = LD30.entityFactory.makeIntroBackground();
            LD30.data.gameState = GameState.INTRO;
            break;
        case INTRO:
            Array<IntroControlComponent> array = LD30.componentPool.getActive(Component.Type.INTRO_CONTROL);
            for(int i = 0; i < array.size; i++) {
                IntroControlComponent icc = array.get(i);
                if(icc.hasUp && icc.parent == introBg) {
                    LD30.data.gameState = GameState.INTRO_OUT;
                }
            }
            break;
        case INTRO_OUT:
            LD30.entityPool.free(introBg);
            LD30.data.gameState = GameState.PLAY_IN;
            break;
        }
    }
}
