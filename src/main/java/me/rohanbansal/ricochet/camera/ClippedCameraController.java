package me.rohanbansal.ricochet.camera;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class ClippedCameraController extends CameraController {

    private Vector2 worldDimensions;

    /**
     * Bounded camera controller that cannot move outside of set dimensions, including zoom
     * @param isBox2D using box2d
     * @param worldDimensions dimensions of world
     */
    public ClippedCameraController(boolean isBox2D, Vector2 worldDimensions) {
        super(isBox2D, 1f);
        this.worldDimensions = worldDimensions;
    }

    /**
     * updated render method to calculate bounds
     */
    @Override
    public void update() {
        super.update();
        calculateBounds();
    }

    /**
     * calculate bounds function to take into accound world dimensions and zoom ratios
     */
    private void calculateBounds() {

        float worldWidth = worldDimensions.x;
        float worldHeight = worldDimensions.y;
        float zoom = getCamera().zoom;
        float zoomedHalfWorldWidth = zoom * getCamera().viewportWidth / 2;
        float zoomedHalfWorldHeight = zoom * getCamera().viewportHeight / 2;

        float minX = zoomedHalfWorldWidth;
        float maxX = worldWidth - zoomedHalfWorldWidth;

        float minY = zoomedHalfWorldHeight;
        float maxY = worldHeight - zoomedHalfWorldHeight;

        getCamera().position.x = MathUtils.clamp(getCamera().position.x, minX, maxX);
        getCamera().position.y = MathUtils.clamp(getCamera().position.y, minY, maxY);
    }

    /**
     * convert screen position to world position
     * @param cam camera to be used
     * @return vector2 converted to world position
     */
    public static Vector2 mouseScreenToWorld(CameraController cam) {
        Vector3 vec = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        cam.getCamera().unproject(vec);
        return new Vector2(vec.x, vec.y);
    }

    /**
     * get world dimensions
     * @return vector2 of dimensions
     */
    public Vector2 getWorldDimensions() {
        return worldDimensions;
    }

    /**
     * set world dimensions
     * @param worldDimensions vector2 of dimensions
     */
    public void setWorldDimensions(Vector2 worldDimensions) {
        this.worldDimensions = worldDimensions;
    }
}