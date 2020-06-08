package me.rohanbansal.ricochet.camera;


import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;

import java.util.HashMap;

public class CameraAction {

    private HashMap<String, Float> zoomTo = new HashMap<String, Float>() {{
       put("zoom", 0f);
       put("duration", 0f);
    }};
    private HashMap<String, Vector2> glideTo = new HashMap<String, Vector2>() {{
        put("location", new Vector2());
        put("alpha", new Vector2());
    }};

    private float zoom;
    private int wait;
    private Interpolation glideInterp;
    private Interpolation zoomInterp;

    private CameraAction doWith;

    private String finish;

    private String action = "";

    public String getFinish() {
        return finish;
    }

    public void setFinish(String finish) {
        this.finish = finish;
    }

    public String getAction() {
        return action;
    }

    public CameraAction doWith(CameraAction doWith) {
        this.doWith = doWith;
        return this;
    }

    public CameraAction getDoWith() {
        return doWith;
    }

    public CameraAction setAction(String action) {
        this.action = action;

        return this;
    }

    public HashMap<String, Float> getZoomTo() {
        return zoomTo;
    }

    public HashMap<String, Vector2> getGlideTo() {
        return glideTo;
    }

    public void setZoom(float zoom) {
        this.zoom = zoom;
    }

    public float getZoom() {
        return zoom;
    }

    public void setWait(int wait) {
        this.wait = wait;
    }

    public Interpolation getGlideInterp() {
        return glideInterp;
    }

    public void setGlideInterp(Interpolation glideInterp) {
        this.glideInterp = glideInterp;
    }

    public Interpolation getZoomInterp() {
        return zoomInterp;
    }

    public void setZoomInterp(Interpolation zoomInterp) {
        this.zoomInterp = zoomInterp;
    }

    public int getWait() {
        return wait;
    }
}
