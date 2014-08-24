package com.robotfriendgames.ld30.systems;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.robotfriendgames.ld30.game.LD;

public class CameraTrackSystem {
    public static final String TAG = CameraTrackSystem.class.getSimpleName();

    public void update(float delta) {
        float camMinY = LD.data.viewport.getWorldHeight() / 2;
        float camMaxY = LD.data.worldMaxHeight - camMinY;
        Vector2 pos = LD.data.getPlayerPos();
        float camY = MathUtils.clamp(pos.y, camMinY, camMaxY);
        LD.data.camera.position.set(LD.data.camera.position.x, camY, 0);
    }
}
