package com.robotfriendgames.ld30.game;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class FileLoader {
    private static Files.FileType[] typeOrder;

    static {
        typeOrder = new Files.FileType[] {
                Files.FileType.Absolute,
                Files.FileType.External,
                Files.FileType.Local,
                Files.FileType.Internal,
                Files.FileType.Classpath
        };
    }

    public static FileHandle load(String filename) {
        FileHandle fileHandle = null;
        for(Files.FileType type : typeOrder) {
            try {
                fileHandle = Gdx.files.getFileHandle(filename, type);
                if(fileHandle != null && fileHandle.exists()) {
                    return fileHandle;
                }
            } catch(GdxRuntimeException e) { }
        }
        return fileHandle;
    }
}
