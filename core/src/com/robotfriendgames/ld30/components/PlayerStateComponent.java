package com.robotfriendgames.ld30.components;

import com.robotfriendgames.ld30.comm.Message;
import com.robotfriendgames.ld30.data.PlayerStates;
import com.robotfriendgames.ld30.game.GameEntity;
import com.robotfriendgames.ld30.game.LD;

public class PlayerStateComponent extends Component {
    public PlayerStates playerState;
    public boolean inContact;

    public PlayerStateComponent() {
        super(Type.PLAYER_STATE);
    }

    public static PlayerStateComponent apply(GameEntity parent) {
        PlayerStateComponent psc = LD.componentPool.obtain(Type.PLAYER_STATE);
        psc.setParent(parent);
        psc.inContact = false;
        LD.post.addReceiver(psc);
        LD.post.send(Message.Type.PLAYER_STATE, PlayerStates.STILL);
        return psc;
    }

    @Override
    public void receive(Message msg) {
        switch(msg.type) {
        case PLAYER_CONTACT:
            updateContact(msg.data);
            break;
        case PLAYER_STATE:
            updateState((PlayerStates) msg.data);
            break;
        }
    }

    @Override
    public void reset() {
        super.reset();
        LD.post.removeReceiver(this);
        inContact = false;
        playerState = PlayerStates.STILL;
    }

    private void updateContact(Object obj) {
        if(obj == null) {
            inContact = false;
        } else {
            inContact = true;
        }
    }

    private void updateState(PlayerStates playerState) {
        this.playerState = playerState;
    }
}
