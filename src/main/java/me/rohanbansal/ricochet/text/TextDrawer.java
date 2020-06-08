package me.rohanbansal.ricochet.text;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import me.rohanbansal.ricochet.camera.CameraController;

import java.util.ArrayList;

public class TextDrawer {

    private static BitmapFont drawer = new BitmapFont();
    private static ArrayList<Text> drawCalls;
    private static ArrayList<TextWithTimeout> drawTimeout;

    static {
        drawer.getData().setScale(0.6f);
        drawCalls = new ArrayList<>();
        drawTimeout = new ArrayList<>();
    }

    /**
     *
     * @param fontFilePath the internal path to the font file (.ttf, .fnt)
     */
    public static void setFont(String fontFilePath) {
        drawer = new BitmapFont(Gdx.files.internal(fontFilePath));
    }

    /**
     * Draws text when called, but requires continuous calls to keep the text there
     * @param batch spritebatch used to draw
     * @param text  characters to render
     * @param x x position
     * @param y y position
     * @param color color of text to be drawn
     * @param scale size of text to be drawn
     */
    public static void drawText(SpriteBatch batch, String text, float x, float y, Color color, float scale) {
        drawCalls.add(new Text(batch, text, x, y, color, scale));
    }

    /**
     * Draws text for a specific amount of time
     * @param batch spritebatch used to draw
     * @param text characters to render
     * @param x x position
     * @param y y position
     * @param color color of text to be drawn
     * @param scale size of text to be drawn
     * @param secTimeout time in seconds for how long the text should be drawn for
     */
    public static void drawTimeoutText(SpriteBatch batch, String text, float x, float y, Color color, float scale, float secTimeout) {
        drawTimeout.add(new TextWithTimeout(batch, text, x, y, color, scale, secTimeout));
    }

    /**
     * For use with a GlyphLayout, so that the GlyphLayout processes the text with the correct scale
     * @param scale size of text for use with layout
     * @return returns the bitmapfont object
     */
    public static BitmapFont getScaledDrawer(float scale) {
        drawer.getData().setScale(scale);
        return drawer;
    }

    /**
     * Necessary update method to place in the main render loop that draws all pending text in the stack
     * @param camera camera to draw on
     */
    public static void update(CameraController camera) {
        if(drawCalls.size() > 0) {
            for(int i = 0; i < drawCalls.size(); i++) {
                drawCalls.get(i).batch.setProjectionMatrix(camera.getCamera().combined);
                drawCalls.get(i).batch.begin();
                drawer.setColor(drawCalls.get(i).color);
                drawer.getData().setScale(drawCalls.get(i).scale);
                drawer.draw(drawCalls.get(i).batch, drawCalls.get(i).text, drawCalls.get(i).x, drawCalls.get(i).y);
                drawCalls.get(i).batch.end();
                drawCalls.remove(i);
            }
        }
        if(drawTimeout.size() > 0) {
            for(int i = 0; i < drawTimeout.size(); i++) {
                if(drawTimeout.get(i).startedTime == 0f) {
                    drawTimeout.get(i).startedTime = System.currentTimeMillis();
                }
                if(((System.currentTimeMillis() - drawTimeout.get(i).startedTime) / 1000f) > drawTimeout.get(i).goalTime) {
                    drawTimeout.remove(i);
                    break;
                }
                drawTimeout.get(i).batch.setProjectionMatrix(camera.getCamera().combined);
                drawTimeout.get(i).batch.begin();
                drawer.setColor(drawTimeout.get(i).color);
                drawer.getData().setScale(drawTimeout.get(i).scale);
                drawer.draw(drawTimeout.get(i).batch, drawTimeout.get(i).text, drawTimeout.get(i).x, drawTimeout.get(i).y);
                drawTimeout.get(i).batch.end();
            }
        }
    }
}

