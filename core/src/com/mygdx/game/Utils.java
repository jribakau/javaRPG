package com.mygdx.game;

public class Utils {
    public static float normalize(float dx, float dy) {
        return (float) Math.sqrt(dx * dx + dy * dy);
    }
}
