package com.robotfriendgames.ld30.logic;

import com.badlogic.gdx.utils.Array;
import com.robotfriendgames.ld30.comm.Message;
import com.robotfriendgames.ld30.components.Component;
import com.robotfriendgames.ld30.components.IntroControlComponent;
import com.robotfriendgames.ld30.data.GameStates;
import com.robotfriendgames.ld30.game.GameEntity;
import com.robotfriendgames.ld30.game.LD;

public class OutroLogic {
    private GameEntity outroBg;

    public void update(float delta) {
        switch(LD.data.gameState) {
        case OUTRO_IN:
            outroBg = LD.entityFactory.makeOutroBackground();
            LD.data.camera.position.set(LD.data.worldWidth / 2, LD.data.worldHeight / 2, 0);
            outroBg.setPosition(0, 0);
            LD.post.send(Message.Type.PLAY_SOUND, "playButton");
            LD.data.gameState = GameStates.OUTRO;
            break;
        case OUTRO:
            Array<IntroControlComponent> array = LD.componentPool.getActive(Component.Type.INTRO_CONTROL);
            for(int i = 0; i < array.size; i++) {
                IntroControlComponent icc = array.get(i);
                if(icc.hasUp && icc.parent == outroBg) {
                    LD.data.gameState = GameStates.OUTRO_OUT;
                }
            }
            break;
        case OUTRO_OUT:
            LD.entityPool.free(outroBg);
            LD.data.gameState = GameStates.INTRO_IN;
            break;
        }
    }
}
