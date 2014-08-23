package com.robotfriendgames.ld30.data;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

public class Settings {
    public static Settings load() {
        Json json = new Json();
        FileHandle file = Gdx.files.getFileHandle(Strings.SETTINGS_FILENAME, Files.FileType.Internal);
        Settings settings = json.fromJson(Settings.class, file);
        return settings;
    }

    public float fullGravity;
}
