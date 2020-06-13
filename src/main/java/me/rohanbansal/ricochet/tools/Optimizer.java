package me.rohanbansal.ricochet.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import me.rohanbansal.ricochet.body.SpriteContainer;
import me.rohanbansal.ricochet.camera.CameraController;
import me.rohanbansal.ricochet.effects.EffectCreator;
import me.rohanbansal.ricochet.map.MapManager;
import me.rohanbansal.ricochet.text.TextDrawer;
import me.rohanbansal.ricochet.world.WorldManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

public class Optimizer implements Disposable {

    public enum Objects {
        TEXT, EFFECTS, MAP, WORLD, SPRITES, CAMERAS
    }

    private ArrayList<ObjCombination<SpriteContainer, Integer>> sprites = new ArrayList<>();
    private ArrayList<ObjCombination<CameraController, Integer>> cameras = new ArrayList<>();
    private ObjCombination<Boolean, Integer> textDrawer;
    private ObjCombination<Boolean, Integer> effectFactory;
    private ObjCombination<MapManager, Integer> mapManager;
    private ObjCombination<WorldManager, Integer> worldManager;
    private PriorityQueue<Object> updateStack = new PriorityQueue<>();
    private ArrayList<Objects>  objsToOptimize = new ArrayList<>();


    /**
     * Optimize and update only the methods specified below
     * @param objects which objects to update - text, effects, map, world, sprites, or cameras
     */
    public void setOptimize(Objects... objects) {
        if(objects.length > 0) {
            objsToOptimize.addAll(Arrays.asList(objects));
        }
    }

    /**
     * main update method.
     * it first sorts optimizeable objects by priority in a queue, then polls each one and updates
     * @param worldCam world camera to update with
     * @param hudCam heads up camera to update with
     * @param batch spritebatch to draw with
     */
    public void optimizeAndUpdate(CameraController worldCam, CameraController hudCam, SpriteBatch batch) {

        for(Objects obj : objsToOptimize) {
            if(updateStack.size() < 6) {
                if(obj == Objects.TEXT) {
                    updateStack.add(textDrawer);
                }
                if(obj == Objects.EFFECTS) {
                    updateStack.add(effectFactory);
                }
                if(obj == Objects.CAMERAS) {
                    updateStack.addAll(cameras);
                }
                if(obj == Objects.SPRITES) {
                    updateStack.addAll(cameras);
                }
                if(obj == Objects.MAP) {
                    updateStack.add(mapManager);
                }
                if(obj == Objects.WORLD) {
                    updateStack.add(worldManager);
                }
            }
        }

        for(int i = 0; i < updateStack.size() + 1; i++) {
            ObjCombination process = (ObjCombination) updateStack.poll();
            assert process != null;
            if (process.identifier.equals("text")) {
                TextDrawer.update(hudCam);
            } else if (process.identifier.equals("effect")) {
                EffectCreator.render(worldCam, batch);
            } else if (process.identifier.equals("map")) {
                ((MapManager) process.obj1).update(worldCam);
            } else if (process.identifier.equals("world")) {
                ((WorldManager) process.obj1).update(Gdx.graphics.getDeltaTime(), worldCam);
            } else if (process.identifier.equals("sprites")) {
                batch.setProjectionMatrix(worldCam.getCamera().combined);
                batch.begin();
                ((SpriteContainer) process.obj1).update(batch, worldCam);
                batch.end();
            } else if (process.identifier.equals("cameras")) {
                ((CameraController) process.obj1).update();
            }
        }
    }

    /**
     * use the text drawer in update
     * @param priority priority relative to others
     */
    public void useTextDrawer(int... priority) {
        if(priority.length > 0) {
            this.textDrawer = new ObjCombination<>(true, priority[0], "text");
        } else {
            this.textDrawer = new ObjCombination<>(true, -1, "text");
        }
    }

    /**
     * use effect creator in update
     * @param priority priority relative to others
     */
    public void useEffectCreator(int... priority) {
        if(priority.length > 0) {
            this.effectFactory = new ObjCombination<>(true, priority[0], "effect");
        } else {
            this.effectFactory = new ObjCombination<>(true, -1, "effect");
        }
    }

    /**
     * set mapmanager to update
     * @param manager mapmanager to update
     * @param priority priority relative to others
     */
    public void setMapManager(MapManager manager, int... priority) {
        if(priority.length > 0) {
            this.mapManager = new ObjCombination<>(manager, priority[0], "map");
        } else {
            this.mapManager = new ObjCombination<>(manager, -1, "map");
        }
    }

    /**
     * set worldmanager to update
     * @param manager worldmanager to update
     * @param priority priority relative to others
     */
    public void setWorldManager(WorldManager manager, int... priority) {
        if(priority.length > 0) {
            this.worldManager = new ObjCombination<>(manager, priority[0], "world");
        } else {
            this.worldManager = new ObjCombination<>(manager, -1, "world");
        }
    }

    /**
     * add a spritecontainer to the list to update
     * @param container spritecontainer to add
     * @param priority priority relative to others
     */
    public void addSpriteContainer(SpriteContainer container, int... priority) {
        if(priority.length > 0) {
            this.sprites.add(new ObjCombination<>(container, priority[0], "sprites"));
        } else {
            this.sprites.add(new ObjCombination<>(container, -1, "sprites"));
        }
    }

    /**
     * add a camera to the list to update
     * @param camera camera to add
     * @param priority priority relative to others
     */
    public void addCamera(CameraController camera, int... priority) {
        if(priority.length > 0) {
            this.cameras.add(new ObjCombination<>(camera, priority[0], "cameras"));
        } else {
            this.cameras.add(new ObjCombination<>(camera, -1, "cameras"));
        }
    }

    /**
     * dispose all
     */
    @Override
    public void dispose() {
        cameras.clear();
        for(ObjCombination combination : sprites) {
            ((SpriteContainer) combination.obj1).dispose();
        }
        sprites.clear();
        worldManager = null;
        mapManager = null;
        EffectCreator.disposeAtlas();
    }
}
