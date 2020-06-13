package me.rohanbansal.ricochet.body;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import me.rohanbansal.ricochet.camera.CameraController;
import me.rohanbansal.ricochet.tools.VectorTools;
import me.rohanbansal.ricochet.world.WorldManager;

public abstract class BodyHolder {

    private static final float DRIFT_OFFSET = 1.0f;

    protected Vector2 forwardSpeed;
    protected Vector2 lateralSpeed;

    private final Body body;
    private float drift = 1;

    private int id;

    /**
     * Initialize a body holder with an existing body
     * @param body Body to initialize with
     */
    public BodyHolder(Body body) {
        this.body = body;
        id = -1;
    }

    /**
     * Initialize a body holder with body parameters
     * @param world world to be created in
     * @param position relative coordinates for the starting position
     * @param size size of the body, in width/height format (e.g. [32, 64])
     * @param type type of body - static, dynamic, or kinematic
     * @param density density of body to be created
     * @param sensor whether the body is a sensor - this means it will not be collideable, but can still detect collisions
     * @param id id of the body holder, determined by the program - this is usually generated
     */
    public BodyHolder(WorldManager world, Vector2 position, Vector2 size, BodyDef.BodyType type, float density, boolean sensor, int id) {
        body = ShapeFactory.createRectBody(world, position, size, type, density, sensor);
        this.id = id;
    }

    /**
     * Main superclass update method.
     * @param delta the program delta time
     * @param camera camera whose viewport will be used to draw the body
     * @param manager world the body is in
     */
    public void update(final float delta, CameraController camera, WorldManager manager) {
        if(drift < 1) {
            forwardSpeed = getForwardVelocity();
            lateralSpeed = getLateralVelocity();
            if(lateralSpeed.len() < DRIFT_OFFSET && id > 1) {
                killDrift();
            } else {
                handleDrift();
            }
        }
    }

    /**
     * Set the drift of the body - can be used in vehicle processing
     * @param drift a number that should be anywhere from 0 to 10. 10 is almost no friction, and vice versa.
     */
    public void setDrift(float drift) {
        this.drift = drift;
    }

    /**
     * Get the body
     * @return return the body
     */
    public Body getBody() {
        return body;
    }

    private void handleDrift() {
        Vector2 forwardSpeed = getForwardVelocity();
        Vector2 lateralSpeed = getLateralVelocity();
        body.setLinearVelocity(forwardSpeed.x + lateralSpeed.x * drift, forwardSpeed.y + lateralSpeed.y * drift);
    }

    private Vector2 getLocalVelocity() {
        return body.getLocalVector(body.getLinearVelocityFromLocalPoint(new Vector2(0, 0)));
    }

    private Vector2 getForwardVelocity() {
        Vector2 currentNormal = body.getWorldVector(new Vector2(0, 1));
        float dotProduct = currentNormal.dot(body.getLinearVelocity());
        return VectorTools.mult(dotProduct, currentNormal);
    }

    private Vector2 getLateralVelocity() {
        Vector2 currentNormal = body.getWorldVector(new Vector2(1, 0));
        float dotProduct = currentNormal.dot(body.getLinearVelocity());
        return VectorTools.mult(dotProduct, currentNormal);
    }

    /**
     * Kill the current drift by setting the linear velocity of the body to it's forward velocity, cancelling perpendicular
     */
    public void killDrift() {
        body.setLinearVelocity(forwardSpeed);
    }
}

