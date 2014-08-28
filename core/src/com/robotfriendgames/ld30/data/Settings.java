package com.robotfriendgames.ld30.data;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.ObjectMap;
import com.robotfriendgames.ld30.game.FileLoader;

public class Settings {
    public static Settings load() {
        Json json = new Json();
        FileHandle file = FileLoader.load(Strings.SETTINGS_FILENAME);
        Settings settings = json.fromJson(Settings.class, file);
        return settings;
    }

    public float fullGravity;

    public float playerHorzVel;
    public float playerJumpVel;

    public float playerLandingMaxVel;
    public float playerCollisionMaxVel;

    public float platformSpacing;

    public float worldToPixels;
    public float pixelsToWorld;

    public float winWaitDelay;

    public String[] platforms;

    public String[] backgroundTiles;

    public ObjectMap<String, PhysicsData> physDataMap;
}
