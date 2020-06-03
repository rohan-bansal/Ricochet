package me.rohanbansal.ricochet.tools;

import com.badlogic.gdx.math.Vector2;

public class VectorTools {

    public static Vector2 mult(float a, Vector2 vec) {
        return new Vector2(vec.x * a, vec.y * a);
    }
}

