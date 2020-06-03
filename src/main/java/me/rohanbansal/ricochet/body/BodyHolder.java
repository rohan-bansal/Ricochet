package me.rohanbansal.ricochet.body;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
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

    public BodyHolder(Body body) {
        this.body = body;
        id = -1;
    }

    public BodyHolder(WorldManager world, Vector2 position, Vector2 size, BodyDef.BodyType type, float density, boolean sensor, int id) {
        body = ShapeFactory.createRectangle(world, position, size, type, density, sensor);
        this.id = id;
    }

    public void update(final float delta, CameraController camera, WorldManager manager, World world) {
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

    public void setDrift(float drift) {
        this.drift = drift;
    }

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

    public void killDrift() {
        body.setLinearVelocity(forwardSpeed);
    }
}
