package me.rohanbansal.ricochet.camera;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import me.rohanbansal.ricochet.tools.Actions;

import java.util.ArrayList;

public class CameraController {

    private OrthographicCamera camera;
    private Viewport viewport;

    private boolean lerping = false;
    private float lerpValue = 0f;
    private float lerpSpeed = 0f;

    private float zoomToVal = 0;
    private boolean zooming = false;
    private long startTime = 0L;
    private long milliseconds = 0L;
    private float zoomOrigin;
    private float timeToZoomTarget = 0f;
    private float cameraZoomDuration = 0f;
    private Interpolation interp;
    private Vector3 glideTo = null;
    private float alpha = 0f;

    private boolean action_in_progress = false;

    private ArrayList<CameraAction> sequence = new ArrayList<>();
    private ArrayList<CameraAction> actionsToRemove = new ArrayList<>();


    public CameraController(boolean isBox2D, float PPM) {
        camera = new OrthographicCamera();
        if(!isBox2D) {
            viewport = new FitViewport(Gdx.graphics.getWidth() / PPM, Gdx.graphics.getHeight() / PPM, camera);
        } else {
            camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }
    }

    public CameraController(boolean isBox2D) {
        this(isBox2D, 1f);
    }

    public void attachCameraSequence(ArrayList<CameraAction> actionSequence) {
        this.sequence = actionSequence;
    }

    public void processCameraSequence() {
        if(sequence != null) {
            for(CameraAction action : sequence) {
                if(action.getAction().equals("setZoom")) {
                    if(!action_in_progress) {
                        getCamera().zoom = action.getZoom();
                        actionsToRemove.add(action);
                    }
                } else if(action.getAction().equals("zoomTo")) {
                    if(!action_in_progress) {
                        this.smoothZoomTo(action.getZoomTo().get("zoom"), action.getZoomTo().get("duration"), action.getZoomInterp());
                        actionsToRemove.add(action);
                    }
                } else if(action.getAction().equals("wait")) {
                    if(!action_in_progress) {
                        this.wait(action.getWait());
                        actionsToRemove.add(action);
                    }
                } else if(action.getAction().equals("glide")) {
                    if(!action_in_progress) {
                        smoothTranslateTo(action.getGlideTo().get("location"), action.getGlideTo().get("alpha").x, action.getGlideInterp());
                        actionsToRemove.add(action);
                    }
                } else if(action.getAction().equals("onFinish")) {
                    if(!action_in_progress) {
                        action_in_progress = true;
                        Actions.onFinish.add(action.getFinish());
                        action_in_progress = false;
                    }
                }
            }
        }

    }

    public void update() {
        camera.update();
        processCameraSequence();

        if(milliseconds > 0) {
            if(TimeUtils.timeSinceMillis(startTime) >= milliseconds) {
                milliseconds = 0;
                action_in_progress = false;
            }
        }

        if(glideTo != null) {
            action_in_progress = true;

            camera.position.interpolate(this.glideTo, alpha, interp);
            float distance = this.glideTo.dst(camera.position);
            if(distance <= 2f) {
                camera.position.set(glideTo.x, glideTo.y, 0);
                glideTo = null;
                action_in_progress = false;
            }
        }

        if(zooming) {
            if (timeToZoomTarget >= 0) {
                action_in_progress = true;
                timeToZoomTarget -= Gdx.graphics.getDeltaTime();
                float progress = timeToZoomTarget < 0 ? 1 : 1f - timeToZoomTarget / cameraZoomDuration;
                camera.zoom = interp.apply(zoomOrigin, zoomToVal, progress);
            } else {
                zooming = false;
                action_in_progress = false;
            }
        }

        if(actionsToRemove.size() > 0) {
            for(CameraAction x : actionsToRemove) {
                sequence.remove(x);
            }
            actionsToRemove.clear();
        }

    }

    public void wait(int milliseconds) {
        action_in_progress = true;
        this.milliseconds = milliseconds;
        startTime = TimeUtils.millis();
    }

    public void smoothZoomTo(float value, float duration, Interpolation interp) {
        action_in_progress = true;
        this.zoomToVal = value;
        this.zoomOrigin = camera.zoom;
        timeToZoomTarget = cameraZoomDuration = duration;
        this.zooming = true;
        this.interp = interp;
    }

    public void smoothTranslateTo(Vector2 position, float alpha, Interpolation interp) {
        action_in_progress = true;
        this.interp = interp;
        this.alpha = alpha;
        this.glideTo = new Vector3(position.x, position.y, 0);
        camera.unproject(glideTo);
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public void setViewportSize(int x, int y) {
        viewport.update(x, y);
    }
}
