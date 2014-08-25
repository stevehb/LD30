package com.robotfriendgames.ld30.components;

import com.badlogic.gdx.Gdx;
import com.robotfriendgames.ld30.comm.Message;
import com.robotfriendgames.ld30.data.PlayerStates;
import com.robotfriendgames.ld30.game.GameEntity;
import com.robotfriendgames.ld30.game.LD;

public class PlayerStateComponent extends Component {
    public static final String TAG = PlayerStateComponent.class.getSimpleName();

    public PlayerStates playerState;

    public PlayerStateComponent() {
        super(Type.PLAYER_STATE);
    }

    public static PlayerStateComponent apply(GameEntity parent) {
        PlayerStateComponent psc = LD.componentPool.obtain(Type.PLAYER_STATE);
        psc.setParent(parent);
        psc.playerState = PlayerStates.STILL;
        LD.post.addReceiver(psc);
        return psc;
    }

    @Override
    public void reset() {
        super.reset();
        LD.post.removeReceiver(this);
        playerState = PlayerStates.STILL;
    }

    @Override
    public void receive(Message msg) {
        switch(msg.type) {
        case PLAYER_STATE:
            updateState((PlayerStates) msg.data);
            updateSprite();
            break;
        }
    }

    private void updateState(PlayerStates state) {
        if(playerState == PlayerStates.DEAD && state != PlayerStates.STILL) {
            return;
        }
        playerState = state;
    }

    private void updateSprite() {
        // switch actively displayed sprite according to player state
        for(int i = 0; i < PlayerStates.states.length; i++) {
            PlayerStates state = PlayerStates.states[i];
            if(playerState == state) {
                if(playerState == PlayerStates.STILL) {
                    Gdx.app.log(TAG, "making STILL visible");
                }
                LD.data.players[playerState.getIdx()].makeVisible();
            } else {
                if(playerState.getIdx() != state.getIdx()) {
                    LD.data.players[state.getIdx()].makeInvisible();
                }
            }
        }
    }
}
