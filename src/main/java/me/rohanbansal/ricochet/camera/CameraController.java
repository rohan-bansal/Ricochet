package me.rohanbansal.ricochet.camera;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class CameraController {

    private OrthographicCamera camera;
    private Viewport viewport;

    private boolean lerping = false;
    private float lerpValue = 0f;
    private float lerpSpeed = 0f;

    private boolean lerpingT = false;
    private Vector3 lerpValueT = null;
    private float lerpSpeedT = 0f;

    public CameraController(boolean isUI, float PPM) {
        camera = new OrthographicCamera();
        if(!isUI) {
            viewport = new FitViewport(Gdx.graphics.getWidth() / PPM, Gdx.graphics.getHeight() / PPM, camera);
        } else {
            camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }
    }

    public CameraController(boolean isUI) {
        this(isUI, 1f);
    }

    public void update() {
        camera.update();

        if(lerping) {
            if(Math.abs(lerpValue - camera.zoom) <= 0.1f) {
                lerping = false;
            } else {
                camera.zoom = MathUtils.lerp(camera.zoom, lerpValue, lerpSpeed);
            }
        }
        if(lerpingT) {
            if(camera.position.dst(lerpValueT) <= 0.2f) {
                lerpingT = false;
            } else {
                camera.position.lerp(lerpValueT, lerpSpeedT);
            }
        }
    }

    public void smoothZoomTo(float value, float speed) {
        lerping = true;
        lerpValue = value;
        lerpSpeed = speed;
    }

    public void smoothTranslateTo(Vector2 position, float alpha) {
        lerpingT = true;
        lerpValueT = new Vector3(position.x, position.y, 0);
        lerpSpeedT = alpha;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public void setViewportSize(int x, int y) {
        viewport.update(x, y);
    }
}
