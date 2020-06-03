package me.rohanbansal.ricochet.text;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TextWithTimeout {
    public SpriteBatch batch;
    public String text;
    public float x, y;
    public Color color;
    public float scale;
    public long startedTime;
    public float goalTime;

    public TextWithTimeout(SpriteBatch batch, String text, float x, float y, Color color, float scale, float secTimeout) {
        this.batch = batch;
        this.text = text;
        this.x = x;
        this.y = y;
        this.color = color;
        this.scale = scale;
        this.goalTime = secTimeout;
    }
}