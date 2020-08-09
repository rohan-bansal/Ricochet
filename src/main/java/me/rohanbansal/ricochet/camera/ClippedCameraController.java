package me.rohanbansal.ricochet.camera;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class ClippedCameraController extends CameraController {

    public ClippedCameraController(boolean isBox2D) {
        super(isBox2D, 1f);
    }

    public void calculateBounds(Vector2 worldDimensions) {

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

    public static Vector2 mouseScreenToWorld(ClippedCameraController cam) {
        Vector3 vec = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        cam.getCamera().unproject(vec);
        return new Vector2(vec.x, vec.y);
    }

}