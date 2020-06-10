package me.rohanbansal.ricochet.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import me.rohanbansal.ricochet.camera.CameraController;

import java.util.ArrayList;
import java.util.HashMap;

public class EffectCreator {

    private static HashMap<String, Effect> loadedEffects = new HashMap<>();

    private static TextureAtlas atlas;
    private static ArrayList<Effect> effects = new ArrayList<>();
    private static ArrayList<Effect> effectRemoveQueue = new ArrayList<>();

    public static void loadEffect(String effectName, String textureAtlasPackPath, float frameSpeed) {
        atlas = new TextureAtlas(Gdx.files.internal(textureAtlasPackPath));
        Effect particle = new Effect(frameSpeed, atlas, null, false, 1, null);
        loadedEffects.put(effectName, particle);
    }

    public static Effect createEffect(String effectName, Vector2 position, boolean looping, float scale, CameraController camera) {
        Effect particle = null;

        for(String name : loadedEffects.keySet()) {
            if(name.equals(effectName)) {
                particle = loadedEffects.get(name).duplicateWith(position, looping, scale, camera);
                effects.add(particle);
            }
        }

        return particle;
    }

    public static void render(SpriteBatch batch) {
        for(Effect f : effects) {
            f.render(f.camera, batch);
        }

        for(Effect ef : effectRemoveQueue) {
            effects.remove(ef);
        }

        effectRemoveQueue.clear();
    }

    public static void deleteEffect(Effect effect) {
        effectRemoveQueue.add(effect);
    }

    public static void disposeAtlas() {
        atlas.dispose();
    }
}
