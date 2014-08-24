package com.robotfriendgames.ld30.logic;

import com.badlogic.gdx.utils.Array;
import com.robotfriendgames.ld30.components.Component;
import com.robotfriendgames.ld30.components.IntroControlComponent;
import com.robotfriendgames.ld30.data.GameStates;
import com.robotfriendgames.ld30.game.GameEntity;
import com.robotfriendgames.ld30.game.LD;

public class IntroLogic {
    private GameEntity introBg;

    public void update(float delta) {
        switch(LD.data.gameState) {
        case INTRO_IN:
            introBg = LD.entityFactory.makeIntroBackground();
            LD.data.gameState = GameStates.INTRO;
            break;
        case INTRO:
            Array<IntroControlComponent> array = LD.componentPool.getActive(Component.Type.INTRO_CONTROL);
            for(int i = 0; i < array.size; i++) {
                IntroControlComponent icc = array.get(i);
                if(icc.hasUp && icc.parent == introBg) {
                    LD.data.gameState = GameStates.INTRO_OUT;
                }
            }
            break;
        case INTRO_OUT:
            LD.entityPool.free(introBg);
            LD.data.gameState = GameStates.PLAY_IN;
            break;
        }
    }
}
