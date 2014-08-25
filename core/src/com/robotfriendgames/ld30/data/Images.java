package com.robotfriendgames.ld30.data;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.ObjectMap;
import com.robotfriendgames.ld30.game.FileLoader;
import com.robotfriendgames.ld30.game.LD;

public class Images {
    public static final String TAG = Images.class.getSimpleName();

    @SuppressWarnings("unchecked")
    public static Images load() {
        // load json into map
        Json json = new Json();
        FileHandle file = FileLoader.load(Strings.IMAGE_MAP_FILENAME);
        Images images = new Images();
        images.map = json.fromJson(ObjectMap.class, AnimationData.class, file);

        // load images with AssetManager
        for(AnimationData ad : images.map.values()) {
            LD.assetManager.load(ad.image, Texture.class);
            LD.assetManager.update();
        }
        return images;
    }

    public static void initAnimations() {
        for(AnimationData ad : LD.images.map.values()) {
            Texture texture = LD.assetManager.get(ad.image, Texture.class);
            int frameCount = 1;
            if(ad.isAnim) {
                frameCount = texture.getWidth() / texture.getHeight();
            }
            int frameWidth = texture.getWidth() / frameCount;
            ad.frames = new TextureRegion[frameCount];
            for(int i = 0; i < frameCount; i++) {
                ad.frames[i] = new TextureRegion(texture, frameWidth * i, 0, frameWidth, texture.getHeight());
            }
        }
    }

    public ObjectMap<String, AnimationData> map;
}
