package me.rohanbansal.ricochet.world;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import me.rohanbansal.ricochet.camera.CameraController;
import me.rohanbansal.ricochet.tools.ModifiedShapeRenderer;

public class WorldManager implements Disposable {

    private Vector2 gravity;
    private boolean sleep;
    private boolean renderingDebug = false;
    private float PPM;

    private int velocityIter = 6, positionIter = 2;

    private World world;
    private final Box2DDebugRenderer B2DR = new Box2DDebugRenderer();
    private ModifiedShapeRenderer sRenderer;

    /**
     * Initialize a worldmanager
     * @param gravity gravity of bodies
     * @param sleepObjects whether to save resources with unmoving bodies by putting them to sleep
     * @param pixelsPerMeter PPM to scale by
     */
    public WorldManager(Vector2 gravity, boolean sleepObjects, float pixelsPerMeter) {
        this(gravity, sleepObjects, null, pixelsPerMeter);
    }

    /**
     * Initialize a worldmanager
     * @param gravity gravity of bodies
     * @param sleepObjects whether to save resources with unmoving bodies by putting them to sleep
     * @param contactListener collision listener for bodies
     * @param pixelsPerMeter PPM to scale by
     */
    public WorldManager(Vector2 gravity, boolean sleepObjects, ContactListener contactListener, float pixelsPerMeter) {
        this.gravity = gravity;
        this.sleep = sleepObjects;
        this.PPM = pixelsPerMeter;

        sRenderer = new ModifiedShapeRenderer();

        world = new World(this.gravity, this.sleep);
        if(contactListener != null) {
            world.setContactListener(contactListener);
        }
    }

    /**
     * main update method, step the world forward
     * @param delta delta time
     * @param worldCamera camera to draw the debug renderer with
     */
    public void update(float delta, CameraController worldCamera) {
        if(renderingDebug) {
            B2DR.render(world, worldCamera.getCamera().combined);
        }
        world.step(delta, velocityIter, positionIter);
    }

    /**
     *
     * @return get the shaperenderer
     */
    public ModifiedShapeRenderer getShapeRenderer() {
        return sRenderer;
    }

    /**
     *
     * @return get the pixels per meter
     */
    public float getPPM() {
        return PPM;
    }

    /**
     *
     * @param velocityIter set the velocity iterations of the world step
     */
    // defaults to 4
    public void setVelocityIter(int velocityIter) {
        this.velocityIter = velocityIter;
    }

    /**
     *
     * @param positionIter set the position iterations of the world step
     */
    // defaults to 4
    public void setPositionIter(int positionIter) {
        this.positionIter = positionIter;
    }

    /**
     *
     * @return get the debug renderer
     */
    public Box2DDebugRenderer getB2DR() {
        return B2DR;
    }

    /**
     * enable the debug renderer
     */
    public void enableDebugRenderer() {
        renderingDebug = true;
    }

    /**
     * disable the debug renderer
     */
    public void disableDebugRenderer() {
        renderingDebug = false;
    }

    /**
     * set world gravity
     * @param gravity gravity to set
     */
    public void setGravity(Vector2 gravity) {
        this.gravity = gravity;
        world.setGravity(gravity);
    }

    /**
     * check if body sleep is activated
     * @return sleep value
     */
    public boolean isSleepActivated() {
        return sleep;
    }

    /**
     *
     * @return get the world instance
     */
    public World getWorld() {
        return world;
    }

    /**
     * dispose all world resources
     */
    @Override
    public void dispose() {
        world.dispose();
        B2DR.dispose();
    }
}
