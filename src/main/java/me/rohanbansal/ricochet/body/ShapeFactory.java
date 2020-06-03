package me.rohanbansal.ricochet.body;

import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import me.rohanbansal.ricochet.world.WorldManager;

public class ShapeFactory {

    private ShapeFactory() {}

    public static Body createRectangle(WorldManager world, Vector2 position, Vector2 size, BodyDef.BodyType type, float density, boolean sensor) {

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

    public static Body createPolygon(WorldManager world, final float[] vertices, BodyDef.BodyType type, float density, boolean sensor) {

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
