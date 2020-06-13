package me.rohanbansal.ricochet.text;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Text {
    public SpriteBatch batch;
    public String text;
    public float x, y;
    public Color color;
    public float scale;

    /**
     * Stores text information
     * @param batch spritebatch used to draw
     * @param text text string
     * @param x position x
     * @param y position y
     * @param color color of text
     * @param scale size of text
     */
    public Text(SpriteBatch batch, String text, float x, float y, Color color, float scale) {
        this.batch = batch;
        this.text = text;
        this.x = x;
        this.y = y;
        this.color = color;
        this.scale = scale;
    }
}