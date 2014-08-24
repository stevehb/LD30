package com.robotfriendgames.ld30.data;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.ObjectMap;

public class Settings {
    public static Settings load() {
        Json json = new Json();
        FileHandle file = Gdx.files.getFileHandle(Strings.SETTINGS_FILENAME, Files.FileType.Internal);
        Settings settings = json.fromJson(Settings.class, file);
        return settings;
    }

    public float fullGravity;
    public float terminalVel;

    public float playerHorzAccel;
    public float playerHorzMaxVel;
    public float playerJumpAccel;

    public float worldToPixels;
    public float pixelsToWorld;

    public ObjectMap<String, PhysicsData> physDataMap;
}
