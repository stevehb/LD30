package com.robotfriendgames.ld30.game;

import com.robotfriendgames.ld30.comm.PostOffice;
import com.robotfriendgames.ld30.pools.ActionPool;
import com.robotfriendgames.ld30.pools.ComponentPool;
import com.robotfriendgames.ld30.pools.EntityPool;
import com.robotfriendgames.ld30.pools.MessagePool;

public class LD30 {
    public static PostOffice post = new PostOffice();

    public static ActionPool actionPool = new ActionPool();
    public static ComponentPool componentPool = new ComponentPool();
    public static EntityPool entityPool = new EntityPool();
    public static MessagePool messagePool = new MessagePool();
}
