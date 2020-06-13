package me.rohanbansal.ricochet.tools;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class ModifiedShapeRenderer extends ShapeRenderer {

    /**
     * draw a rounded rectangle
     * @param x position x
     * @param y position y
     * @param width width of rectangle
     * @param height height of rectangle
     * @param radius radius or curve at corner
     */
    public void roundedRect(float x, float y, float width, float height, float radius){

        super.rect(x + radius, y + radius, width - 2*radius, height - 2*radius);

        super.rect(x + radius, y, width - 2*radius, radius);
        super.rect(x + width - radius, y + radius, radius, height - 2*radius);
        super.rect(x + radius, y + height - radius, width - 2*radius, radius);
        super.rect(x, y + radius, radius, height - 2*radius);

        super.arc(x + radius, y + radius, radius, 180f, 90f);
        super.arc(x + width - radius, y + radius, radius, 270f, 90f);
        super.arc(x + width - radius, y + height - radius, radius, 0f, 90f);
        super.arc(x + radius, y + height - radius, radius, 90f, 90f);
    }
}
