package com.robotfriendgames.ld30.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.robotfriendgames.ld30.comm.PostOffice;
import com.robotfriendgames.ld30.data.Data;
import com.robotfriendgames.ld30.data.Images;
import com.robotfriendgames.ld30.data.Settings;
import com.robotfriendgames.ld30.factory.EntityFactory;
import com.robotfriendgames.ld30.pools.*;

public class LD30 {
    public static Images images;
    public static Settings settings;
    public static Data data;

    public static World world;

    public static PostOffice post;

    public static ActionPool actionPool;
    public static ComponentPool componentPool;
    public static EntityPool entityPool;
    public static MessagePool messagePool;

    public static AssetManager assetManager;

    public static EntityFactory entityFactory;

    public static void init() {
        assetManager = new AssetManager();

        images = Images.load();
        settings = Settings.load();
        data = new Data();

        world = new World(new Vector2(0, LD30.settings.fullGravity), true);

        post = new PostOffice();

        actionPool = new ActionPool();
        componentPool = new ComponentPool();
        entityPool = new EntityPool();
        messagePool = new MessagePool();

        entityFactory = new EntityFactory();

        assetManager.finishLoading();
        Images.initAnimations();
    }
}
