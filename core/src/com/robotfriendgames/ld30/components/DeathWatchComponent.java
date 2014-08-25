package com.robotfriendgames.ld30.components;

import com.robotfriendgames.ld30.comm.Message;
import com.robotfriendgames.ld30.data.PlayerSprites;
import com.robotfriendgames.ld30.game.GameEntity;
import com.robotfriendgames.ld30.game.LD;

public class DeathWatchComponent extends Component {
    public static final String TAG = DeathWatchComponent.class.getSimpleName();

    public DeathWatchComponent() {
        super(Type.DEATH_WATCH);
    }

    public static DeathWatchComponent apply(GameEntity parent) {
        DeathWatchComponent dwc = LD.componentPool.obtain(Type.DEATH_WATCH);
        dwc.setParent(parent);
        return dwc;
    }

    public void update(float delta) {
        if(parent == LD.data.players[PlayerSprites.EXPLOSION] && parent.isAnimFinished()) {
            LD.post.send(Message.Type.PLAYER_RESET, null);
        }
    }

    @Override
    public void reset() {
        super.reset();
    }
}
