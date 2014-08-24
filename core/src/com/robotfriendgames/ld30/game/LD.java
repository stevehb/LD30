package com.robotfriendgames.ld30.game;

import com.badlogic.gdx.assets.AssetManager;
import com.robotfriendgames.ld30.comm.PostOffice;
import com.robotfriendgames.ld30.data.Data;
import com.robotfriendgames.ld30.data.Images;
import com.robotfriendgames.ld30.data.Settings;
import com.robotfriendgames.ld30.factory.EntityFactory;
import com.robotfriendgames.ld30.pools.ActionPool;
import com.robotfriendgames.ld30.pools.ComponentPool;
import com.robotfriendgames.ld30.pools.EntityPool;
import com.robotfriendgames.ld30.pools.MessagePool;

public class LD {
    public static Images images;
    public static Settings settings;
    public static Data data;

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
