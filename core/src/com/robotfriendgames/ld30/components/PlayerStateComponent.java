package com.robotfriendgames.ld30.components;

import com.robotfriendgames.ld30.data.PlayerState;
import com.robotfriendgames.ld30.game.GameEntity;
import com.robotfriendgames.ld30.game.LD30;

public class PlayerStateComponent extends Component {
    public PlayerState playerState;

    public PlayerStateComponent() {
        super(Type.PLAYER_STATE);
    }

    public static PlayerStateComponent apply(GameEntity parent) {
        PlayerStateComponent psc = LD30.componentPool.obtain(Type.PLAYER_STATE);
        psc.setParent(parent);
        return psc;
    }

    @Override
    public void reset() {
        super.reset();
        playerState = PlayerState.QUICK;
    }
}
