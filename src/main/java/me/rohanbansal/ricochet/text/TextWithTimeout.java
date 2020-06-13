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

    /**
     * Stores text with timeout information
     * @param batch batch to drawn text on
     * @param text string of text
     * @param x position x
     * @param y positon y
     * @param color color of text
     * @param scale size of text
     * @param secTimeout how long to draw text for
     */
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