package com.robotfriendgames.ld30.data;

public enum PlayerStates {
    STILL(PlayerSprites.GROUND_RIGHT),
    GROUND_RIGHT(PlayerSprites.GROUND_RIGHT),
    GROUND_LEFT(PlayerSprites.GROUND_LEFT),
    JUMP_RIGHT(PlayerSprites.JUMP_RIGHT),
    JUMP_LEFT(PlayerSprites.JUMP_LEFT),
    DEAD(PlayerSprites.EXPLOSION);

    private int idx;
    public static PlayerStates[] states = PlayerStates.values();

    PlayerStates(int idx) {
        this.idx = idx;
    }

    public int getIdx() {
        return idx;
    }
}
