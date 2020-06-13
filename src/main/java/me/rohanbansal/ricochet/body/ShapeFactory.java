package me.rohanbansal.ricochet.body;

import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import me.rohanbansal.ricochet.world.WorldManager;

public class ShapeFactory {

    private ShapeFactory() {}

    /**
     * Creates a basic collideable rectangle body
     * @param world world to create the body in
     * @param position location of the body
     * @param size size of the body (e.g. [32, 64])
     * @param type type of body - static, dynamic, kinematic
     * @param density density of the body
     * @param sensor whether the body is a sensor - this means it will not be collideable, but can still detect collisions
     * @return returns the processed body
     */
    public static Body createRectBody(WorldManager world, Vector2 position, Vector2 size, BodyDef.BodyType type, float density, boolean sensor) {

        final BodyDef bdef = new BodyDef();
        bdef.position.set(position.x / world.getPPM(), position.y / world.getPPM());
        bdef.type = type;

        final Body body = world.getWorld().createBody(bdef);
        final PolygonShape shape = new PolygonShape();
        shape.setAsBox(size.x / world.getPPM(), size.y / world.getPPM());
        final FixtureDef fdef = new FixtureDef();
        fdef.shape = shape;
        fdef.density = density;
        fdef.isSensor = sensor;

        body.createFixture(fdef);
        shape.dispose();

        return body;
    }

    /**
     * Generate a rectangle body from a TiledMap rect object
     * @param rectObject the rect from the tiledmap to be processed
     * @param world the world to create the body in
     * @param type the type of body - static, dynamic, or kinematic
     * @param sensor whether the body is a sensor - this means it will not be collideable, but can still detect collisions
     * @return return the processed body
     */
    public static Body createRectangleFromMap(RectangleMapObject rectObject, WorldManager world, BodyDef.BodyType type, boolean sensor) {

        Rectangle rect = rectObject.getRectangle();

        final BodyDef bdef = new BodyDef();
        bdef.position.set(rect.getX() / world.getPPM(), rect.getY() / world.getPPM());
        bdef.type = type;

        final Body body = world.getWorld().createBody(bdef);
        final PolygonShape shape = new PolygonShape();
        shape.setAsBox(rect.getWidth() / world.getPPM(), rect.getHeight() / world.getPPM());
        final FixtureDef fdef = new FixtureDef();
        fdef.shape = shape;

        fdef.isSensor = sensor;

        body.createFixture(fdef);
        shape.dispose();

        return body;
    }

    /**
     * Creates a basic collideable polygon body object
     * @param world Wold to be created in
     * @param vertices a list of vertices (clockwise)
     * @param type type of body - static, dynamic, or kinematic
     * @param density density og body
     * @param sensor whether the body is a sensor - this means it will not be collideable, but can still detect collisions
     * @return return the processed polygon body
     */
    public static Body createPolyBody(WorldManager world, final float[] vertices, BodyDef.BodyType type, float density, boolean sensor) {

        PolygonShape polygon = new PolygonShape();
        float[] worldVertices = new float[vertices.length];

        for (int i = 0; i < vertices.length; ++i) {
            worldVertices[i] = vertices[i] / world.getPPM();
        }

        polygon.set(worldVertices);

        final BodyDef bdef = new BodyDef();
        bdef.type = type;
        Body body = world.getWorld().createBody(bdef);
        final FixtureDef fdef = new FixtureDef();

        fdef.isSensor = sensor;
        fdef.density = density;

        fdef.shape = polygon;
        body.createFixture(fdef);
        polygon.dispose();

        return body;
    }

    /**
     * Generate a polygon body from a TiledMap rect object
     * @param polygonObject the polygon from the tiledmap to be processed
     * @param world world to create body in
     * @param type type of body - static, kinematic, or dynamic
     * @param sensor whether the body is a sensor - this means it will not be collideable, but can still detect collisions
     * @return return the processed body
     */
    public static Body createPolygonFromMap(PolygonMapObject polygonObject, WorldManager world, BodyDef.BodyType type, boolean sensor) {
        PolygonShape polygon = new PolygonShape();
        float[] vertices = polygonObject.getPolygon().getTransformedVertices();

        float[] worldVertices = new float[vertices.length];

        for (int i = 0; i < vertices.length; ++i) {
            worldVertices[i] = vertices[i] / world.getPPM();
        }

        polygon.set(worldVertices);

        final BodyDef bdef = new BodyDef();
        bdef.type = type;
        Body body = world.getWorld().createBody(bdef);
        final FixtureDef fdef = new FixtureDef();

        fdef.isSensor = sensor;

        fdef.shape = polygon;
        body.createFixture(fdef);
        polygon.dispose();

        return body;
    }
}
