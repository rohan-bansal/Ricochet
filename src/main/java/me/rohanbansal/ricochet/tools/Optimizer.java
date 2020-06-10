package me.rohanbansal.ricochet.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import me.rohanbansal.ricochet.body.SpriteContainer;
import me.rohanbansal.ricochet.camera.CameraController;
import me.rohanbansal.ricochet.effects.EffectCreator;
import me.rohanbansal.ricochet.map.MapManager;
import me.rohanbansal.ricochet.text.TextDrawer;
import me.rohanbansal.ricochet.world.WorldManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

public class Optimizer {

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



    public void setOptimize(Objects... objects) {
        if(objects.length > 0) {
            objsToOptimize.addAll(Arrays.asList(objects));
        }
    }

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
                    updateStack.add(cameras);
                }
                if(obj == Objects.SPRITES) {
                    updateStack.add(sprites);
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
            Object process = updateStack.poll();
            if(process instanceof ObjCombination) {
                if(((ObjCombination) process).identifier.equals("text")) {
                    TextDrawer.update(hudCam);
                } else if(((ObjCombination) process).identifier.equals("effect")) {
                    EffectCreator.render(batch);
                } else if(((ObjCombination) process).identifier.equals("map")) {
                    ((MapManager) ((ObjCombination) process).obj1).update(worldCam);
                } else if(((ObjCombination) process).identifier.equals("world")) {
                    ((WorldManager) ((ObjCombination) process).obj1).update(Gdx.graphics.getDeltaTime(), worldCam);
                }
            } else if(process instanceof ArrayList) {
                if(((ObjCombination) (((ArrayList) process).get(0))).identifier.equals("sprites")) {
                    for(Object container : ((ArrayList) process)) {
                        ((SpriteContainer) container).update(batch, worldCam);
                    }
                } else if(((ObjCombination) (((ArrayList) process).get(0))).identifier.equals("cameras")) {
                    for(Object container : ((ArrayList) process)) {
                        ((CameraController) container).update();
                    }
                }
            }
        }
    }

    public void useTextDrawer(int... priority) {
        if(priority.length > 0) {
            this.textDrawer = new ObjCombination<>(true, priority[0], "text");
        } else {
            this.textDrawer = new ObjCombination<>(true, -1, "text");
        }
    }

    public void useEffectCreator(int... priority) {
        if(priority.length > 0) {
            this.effectFactory = new ObjCombination<>(true, priority[0], "effect");
        } else {
            this.effectFactory = new ObjCombination<>(true, -1, "effect");
        }
    }

    public void setMapManager(MapManager manager, int... priority) {
        if(priority.length > 0) {
            this.mapManager = new ObjCombination<>(manager, priority[0], "map");
        } else {
            this.mapManager = new ObjCombination<>(manager, -1, "map");
        }
    }

    public void setWorldManager(WorldManager manager, int... priority) {
        if(priority.length > 0) {
            this.worldManager = new ObjCombination<>(manager, priority[0], "world");
        } else {
            this.worldManager = new ObjCombination<>(manager, -1, "world");
        }
    }

    public void addSpriteContainer(SpriteContainer container, int... priority) {
        if(priority.length > 0) {
            this.sprites.add(new ObjCombination<>(container, priority[0], "sprites"));
        } else {
            this.sprites.add(new ObjCombination<>(container, -1, "sprites"));
        }
    }

    public void addCamera(CameraController camera, int... priority) {
        if(priority.length > 0) {
            this.cameras.add(new ObjCombination<>(camera, priority[0], "cameras"));
        } else {
            this.cameras.add(new ObjCombination<>(camera, -1, "cameras"));
        }
    }
}
