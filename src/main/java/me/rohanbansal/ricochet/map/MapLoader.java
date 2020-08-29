package me.rohanbansal.ricochet.map;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import me.rohanbansal.ricochet.body.ShapeFactory;
import me.rohanbansal.ricochet.world.WorldManager;

import java.util.ArrayList;

public class MapLoader implements Disposable {


    private WorldManager world;
    private TiledMap map;

    private ArrayList<Body> wallList = new ArrayList<>();

    /**
     * initialize a maploader instance
     * @param world world to create bodies in
     */
    public MapLoader(WorldManager world) {
        this.world = world;
    }

    /**
     * Load a tiledMap from assets into the world
     * @param mapName path to map
     * @param collisionIdentifier string identifier used for the collision layer
     * @return return the new maploader instance
     */
    public MapLoader loadMap(String mapName, String collisionIdentifier) {
        map = new TmxMapLoader().load(mapName);

        Array<MapObject> walls = map.getLayers().get(collisionIdentifier).getObjects().getByType(MapObject.class);

        for(MapObject wall : new Array.ArrayIterator<>(walls)) {
            if(wall instanceof RectangleMapObject) {
                RectangleMapObject rectWall = (RectangleMapObject) wall;
                Body b = ShapeFactory.createRectangleFromMap(rectWall, world, BodyDef.BodyType.StaticBody, false);
                wallList.add(b);
            } else if(wall instanceof PolygonMapObject) {
                PolygonMapObject polWall = (PolygonMapObject) wall;
                Body b = ShapeFactory.createPolygonFromMap(polWall, world, BodyDef.BodyType.StaticBody, false);
                wallList.add(b);
            }
        }

        return this;
    }

    /**
     * get list of walls processed from map
     * @return list of walls
     */
    public ArrayList<Body> getWallList() {
        return wallList;
    }

    /** 
     * get the pure map instance
     * @return
     */
    public TiledMap getMap() {
        return map;
    }

    /**
     * dispose the map
     */
    @Override
    public void dispose() {
        map.dispose();
    }
}
