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

    /**
     *
     * @return get finish trigger
     */
    public String getFinish() {
        return finish;
    }

    /**
     *
     * @param finish set finish trigger
     */
    public void setFinish(String finish) {
        this.finish = finish;
    }

    /**
     *
     * @return get current action
     */
    public String getAction() {
        return action;
    }

    /**
     *
     * @param doWith concurrent action
     * @return set concurrent action
     */
    public CameraAction doWith(CameraAction doWith) {
        this.doWith = doWith;
        return this;
    }

    /**
     *
     * @return get the concurrent action
     */
    public CameraAction getDoWith() {
        return doWith;
    }

    /**
     *
     * @param action action to be set
     * @return camera action created
     */
    public CameraAction setAction(String action) {
        this.action = action;

        return this;
    }

    /**
     *
     * @return get zoom data
     */
    public HashMap<String, Float> getZoomTo() {
        return zoomTo;
    }

    /**
     *
     * @return get glide data
     */
    public HashMap<String, Vector2> getGlideTo() {
        return glideTo;
    }

    /**
     *
     * @param zoom set camera zoom, no interpolation
     */
    public void setZoom(float zoom) {
        this.zoom = zoom;
    }

    /**
     *
     * @return get camera zoom
     */
    public float getZoom() {
        return zoom;
    }

    /**
     *
     * @param wait wait before the next action (milliseconds)
     */
    public void setWait(int wait) {
        this.wait = wait;
    }

    /**
     *
     * @return get glide interpolation type
     */
    public Interpolation getGlideInterp() {
        return glideInterp;
    }

    /**
     *
     * @param glideInterp set glide interpolation type
     */
    public void setGlideInterp(Interpolation glideInterp) {
        this.glideInterp = glideInterp;
    }

    /**
     *
     * @return get zoom interpolation type
     */
    public Interpolation getZoomInterp() {
        return zoomInterp;
    }

    /**
     *
     * @param zoomInterp set zoom interpolation type
     */
    public void setZoomInterp(Interpolation zoomInterp) {
        this.zoomInterp = zoomInterp;
    }

    /**
     *
     * @return get wait time (in milliseconds)
     */
    public int getWait() {
        return wait;
    }
}
