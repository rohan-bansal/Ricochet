package me.rohanbansal.ricochet.tools;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import me.rohanbansal.ricochet.camera.CameraAction;

import java.util.ArrayList;

public class Actions {

    public static ArrayList<String> onFinish = new ArrayList<>();

    public static CameraAction zoomCameraTo(float zoom, float duration, Interpolation interp) {
        CameraAction action = new CameraAction();
        action.setAction("zoomTo");
        action.getZoomTo().put("zoom", zoom);
        action.getZoomTo().put("duration", duration);
        action.setZoomInterp(interp);
        return action;
    }

    public static CameraAction setCameraZoom(float zoom) {
        CameraAction action = new CameraAction();
        action.setAction("setZoom");
        action.setZoom(zoom);
        return action;
    }

    public static CameraAction cameraWait(int milliseconds) {
        CameraAction action = new CameraAction();
        action.setAction("wait");
        action.setWait(milliseconds);
        return action;
    }

    public static CameraAction cameraGlide(Vector2 position, float alpha, Interpolation interp) {
        CameraAction action = new CameraAction();
        action.setAction("glide");
        action.getGlideTo().put("location", position);
        action.getGlideTo().put("alpha", new Vector2(alpha, alpha));
        action.setGlideInterp(interp);
        return action;
    }

    public static CameraAction setOnFinish(String value) {
        CameraAction action = new CameraAction();
        action.setFinish(value);
        action.setAction("onFinish");

        return action;
    }
}
