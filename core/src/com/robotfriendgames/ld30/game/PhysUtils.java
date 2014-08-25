package com.robotfriendgames.ld30.game;

import com.badlogic.gdx.math.Interpolation;

public class PhysUtils {
    public static float calcGravity(float height) {
        return Interpolation.exp5.apply(
                LD.settings.fullGravity,
                -LD.settings.fullGravity,
                height / LD.data.worldMaxHeight);
    }

    public static float calcJumpVel(float height) {
        if(height < LD.data.worldMidHeight) {
            return LD.settings.playerJumpVel;
        } else {
            return -LD.settings.playerJumpVel;
        }
        /*
        return Interpolation.linear.apply(
                LD.settings.playerJumpVel,
                -LD.settings.playerJumpVel,
                height / LD.data.worldMaxHeight);
        */
    }

    public static float calcJumpHeight(float height) {
        int gravDir = (height < LD.data.worldMidHeight) ? 1 : -1;
        float v0 = calcJumpVel(height);
        float num = -1 * v0 * v0;
        float den = 2f * PhysUtils.calcGravity(height) * gravDir;
        return num / den;
    }
}
