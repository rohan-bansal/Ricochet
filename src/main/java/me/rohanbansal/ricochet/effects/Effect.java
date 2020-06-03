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
    private float scale;
    public CameraController camera;

    public Effect(float frame_speed, TextureAtlas animAtlas, Vector2 position, boolean looping, float scale, CameraController cam) {
        animation = new Animation<>(frame_speed, animAtlas.getRegions());

        this.position = position;
        this.looping = looping;
        this.scale = scale;
        this.camera = cam;
    }

    public void render(CameraController camera, SpriteBatch batch) {
        stateTime += Gdx.graphics.getDeltaTime();
        currentFrame = animation.getKeyFrame(stateTime, looping);
        if(animation.isAnimationFinished(stateTime) && !looping) {
            EffectFactory.deleteEffect(this);
        }

        batch.setProjectionMatrix(camera.getCamera().combined);
        batch.begin();

        batch.draw(currentFrame, position.x, position.y, currentFrame.getRegionWidth() * scale, currentFrame.getRegionHeight() * scale);

        batch.end();
    }
}
