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

    /**
     * Load an effect into memory
     * @param effectName name of effect, to be referenced later
     * @param textureAtlasPackPath path to the effect textureatlas resource file
     * @param frameSpeed speed of effect playback, should be ideally within a range of 0 to 1
     */
    public static void loadEffect(String effectName, String textureAtlasPackPath, float frameSpeed) {
        atlas = new TextureAtlas(Gdx.files.internal(textureAtlasPackPath));
        Effect particle = new Effect(frameSpeed, atlas, null, false, 1);
        loadedEffects.put(effectName, particle);
    }

    /**
     * Create a loaded effect at a specified position
     * @param effectName name of effect that is loaded into memory
     * @param position position of effect
     * @param looping whether the effect should loop
     * @param scale size of effect
     * @return return the crated effect
     */
    public static Effect createEffect(String effectName, Vector2 position, boolean looping, float scale) {
        Effect particle = null;

        for(String name : loadedEffects.keySet()) {
            if(name.equals(effectName)) {
                particle = loadedEffects.get(name).duplicateWith(position, looping, scale);
                effects.add(particle);
            }
        }

        return particle;
    }

    /**
     * Main render method
     * @param camera Camera to draw on
     * @param batch SpriteBatch to draw with
     */
    public static void render(CameraController camera, SpriteBatch batch) {
        for(Effect f : effects) {
            f.render(camera, batch);
        }

        for(Effect ef : effectRemoveQueue) {
            effects.remove(ef);
        }

        effectRemoveQueue.clear();
    }

    /**
     * Delete the specified effect with the object
     * @param effect effect to be disposed
     */
    public static void deleteEffect(Effect effect) {
        effectRemoveQueue.add(effect);
    }

    /**
     * Delete the specified effect with the string name
     * @param effect effect to be disposed
     */
    public static void deleteEffect(String effect) {
        effectRemoveQueue.add(loadedEffects.get(effect));
    }

    /**
     * dispose the texureatlas used to create the effects
     */
    public static void disposeAtlas() {
        atlas.dispose();
    }
}
