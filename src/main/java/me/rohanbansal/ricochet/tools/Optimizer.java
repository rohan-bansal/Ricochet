package me.rohanbansal.ricochet.tools;

import me.rohanbansal.ricochet.body.SpriteContainer;
import me.rohanbansal.ricochet.camera.CameraController;
import me.rohanbansal.ricochet.effects.EffectCreator;
import me.rohanbansal.ricochet.map.MapManager;
import me.rohanbansal.ricochet.text.TextDrawer;
import me.rohanbansal.ricochet.world.WorldManager;
import org.lwjgl.Sys;

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
    private ObjCombination<EffectCreator, Integer> effectFactory;
    private ObjCombination<MapManager, Integer> mapManager;
    private ObjCombination<WorldManager, Integer> worldManager;
    private PriorityQueue<Object> updateStack = new PriorityQueue<>();
    private ArrayList<Objects>  objsToOptimize = new ArrayList<>();


    public void addObject(Object obj) {

    }

    public void setOptimize(Objects... objects) {
        if(objects.length > 0) {
            objsToOptimize.addAll(Arrays.asList(objects));
        }
    }

    public void optimizeAndUpdate() {
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

//        if(updateStack.peek() instanceof ObjCombination) {
//            if(((ObjCombination) updateStack.peek()).obj1 instanceof Boolean) {
//                System.out.println("here");
//            }
//        }

    }

    public void useTextDrawer(int... priority) {
        if(priority.length > 0) {
            this.textDrawer = new ObjCombination<>(true, priority[0]);
        } else {
            this.textDrawer = new ObjCombination<>(true, -1);
        }
    }

    public void setEffectCreator(EffectCreator factory, int... priority) {
        if(priority.length > 0) {
            this.effectFactory = new ObjCombination<>(factory, priority[0]);
        } else {
            this.effectFactory = new ObjCombination<>(factory, -1);
        }
    }

    public void setMapManager(MapManager manager, int... priority) {
        if(priority.length > 0) {
            this.mapManager = new ObjCombination<>(manager, priority[0]);
        } else {
            this.mapManager = new ObjCombination<>(manager, -1);
        }
    }

    public void setWorldManager(WorldManager manager, int... priority) {
        if(priority.length > 0) {
            this.worldManager = new ObjCombination<>(manager, priority[0]);
        } else {
            this.worldManager = new ObjCombination<>(manager, -1);
        }
    }

    public void addSpriteContainer(SpriteContainer container, int... priority) {
        if(priority.length > 0) {
            this.sprites.add(new ObjCombination<>(container, priority[0]));
        } else {
            this.sprites.add(new ObjCombination<>(container, -1));
        }
    }

    public void addCamera(CameraController camera, int... priority) {
        if(priority.length > 0) {
            this.cameras.add(new ObjCombination<>(camera, priority[0]));
        } else {
            this.cameras.add(new ObjCombination<>(camera, -1));
        }
    }
}
