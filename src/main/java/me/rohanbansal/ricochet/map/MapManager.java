package me.rohanbansal.ricochet.map;

import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import me.rohanbansal.ricochet.camera.CameraController;
import me.rohanbansal.ricochet.world.WorldManager;

public class MapManager {

    private TiledMapRenderer tiledMapRenderer;
    private MapLoader loader;

    /**
     * Handle the maploader and tiledmap update methods with a mapmanager
     * @param loader maploader, with map loaded
     * @param world world loaded
     */
    public MapManager(MapLoader loader, WorldManager world) {
        tiledMapRenderer = new OrthogonalTiledMapRenderer(loader.getMap(), 1/world.getPPM());

        this.loader = loader;
    }

    /**
     * Main update method
     * @param cam camera to draw map on
     */
    public void update(CameraController cam) {
        tiledMapRenderer.setView(cam.getCamera());
        tiledMapRenderer.render();
    }
}
