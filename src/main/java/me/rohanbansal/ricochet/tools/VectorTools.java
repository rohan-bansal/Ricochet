package me.rohanbansal.ricochet.tools;

import com.badlogic.gdx.math.Vector2;

public class VectorTools {

    /**
     * scale a vector
     * @param a value to be scaled by
     * @param vec vector to be scaled
     * @return return scaled vector
     */
    public static Vector2 mult(float a, Vector2 vec) {
        return new Vector2(vec.x * a, vec.y * a);
    }
}

