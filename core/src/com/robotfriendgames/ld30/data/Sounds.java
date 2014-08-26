package com.robotfriendgames.ld30.data;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.ObjectMap;
import com.robotfriendgames.ld30.game.FileLoader;
import com.robotfriendgames.ld30.game.LD;

public class Sounds {
    public static final String TAG = Sounds.class.getSimpleName();

    @SuppressWarnings("unchecked")
    public static Sounds load() {
        // load json into map
        Json json = new Json();
        FileHandle file = FileLoader.load(Strings.SOUND_MAP_FILENAME);
        Sounds sounds = new Sounds();
        sounds.map = json.fromJson(ObjectMap.class, SoundData.class, file);

        // load sound with AssetManager
        for(SoundData sd : sounds.map.values()) {
            LD.assetManager.load(sd.file, Sound.class);
            LD.assetManager.update();
        }
        return sounds;
    }

    public static void initSounds() {
        for(SoundData sd : LD.sounds.map.values()) {
            sd.sound = LD.assetManager.get(sd.file, Sound.class);
        }
    }

    public ObjectMap<String, SoundData> map;
}
