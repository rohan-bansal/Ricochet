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

    public WorldManager(Vector2 gravity, boolean sleepObjects, float pixelsPerMeter) {
        this(gravity, sleepObjects, null, pixelsPerMeter);
    }

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

    public void update(float delta, CameraController worldCamera) {
        if(renderingDebug) {
            B2DR.render(world, worldCamera.getCamera().combined);
        }
        world.step(delta, velocityIter, positionIter);
    }

    public ModifiedShapeRenderer getShapeRenderer() {
        return sRenderer;
    }

    public float getPPM() {
        return PPM;
    }

    // defaults to 4
    public void setVelocityIter(int velocityIter) {
        this.velocityIter = velocityIter;
    }

    // defaults to 4
    public void setPositionIter(int positionIter) {
        this.positionIter = positionIter;
    }

    public Box2DDebugRenderer getB2DR() {
        return B2DR;
    }

    public void enableDebugRenderer() {
        renderingDebug = true;
    }

    public void disableDebugRenderer() {
        renderingDebug = false;
    }

    public void setGravity(Vector2 gravity) {
        this.gravity = gravity;
        world.setGravity(gravity);
    }

    public boolean isSleepActivated() {
        return sleep;
    }

    public World getWorld() {
        return world;
    }

    @Override
    public void dispose() {
        world.dispose();
        B2DR.dispose();
    }
}
