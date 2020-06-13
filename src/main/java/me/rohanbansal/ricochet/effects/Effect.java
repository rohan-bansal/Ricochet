package me.rohanbansal.ricochet.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import me.rohanbansal.ricochet.camera.CameraController;

public class Effect {

    private Animation<TextureAtlas.AtlasRegion> animation;
    private Vector2 position;
    private float stateTime = 0f;
    private TextureAtlas.AtlasRegion currentFrame;
    private boolean looping;
    private float scale, frame_speed;
    private TextureAtlas animAtlas;

    /**
     * Initialize a new effect, usually done by the EffectCreator
     * @param frame_speed how fast to play the effect
     * @param animAtlas the textureatlas with effect data loaded
     * @param position position to load effect
     * @param looping loop the effect
     * @param scale effect size
     */
    public Effect(float frame_speed, TextureAtlas animAtlas, Vector2 position, boolean looping, float scale) {
        animation = new Animation<>(frame_speed, animAtlas.getRegions());

        this.position = position;
        this.looping = looping;
        this.scale = scale;
        this.frame_speed = frame_speed;
        this.animAtlas = animAtlas;
    }

    /**
     * Main render method, run by EffectCreator
     * @param camera camera to draw on
     * @param batch SpriteBatch to draw with
     */
    public void render(CameraController camera, SpriteBatch batch) {
        stateTime += Gdx.graphics.getDeltaTime();
        currentFrame = animation.getKeyFrame(stateTime, looping);
        if(animation.isAnimationFinished(stateTime) && !looping) {
            EffectCreator.deleteEffect(this);
        }

        batch.setProjectionMatrix(camera.getCamera().combined);
        batch.begin();

        batch.draw(currentFrame, position.x, position.y, currentFrame.getRegionWidth() * scale, currentFrame.getRegionHeight() * scale);

        batch.end();
    }

    /**
     * Duplicate an effect with new parameters
     * @param position new position
     * @param looping new looping value (true, false)
     * @param scale size of effect
     * @return return the processed effect
     */
    public Effect duplicateWith(Vector2 position, boolean looping, float scale) {
        return new Effect(frame_speed, animAtlas, position, looping, scale);
    }
}
