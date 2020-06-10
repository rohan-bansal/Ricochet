package me.rohanbansal.ricochet.body;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.utils.Disposable;
import me.rohanbansal.ricochet.camera.CameraController;
import me.rohanbansal.ricochet.world.WorldManager;

public class SpriteContainer extends BodyHolder implements Disposable {

    private static int id = 1;
    private WorldManager world;
    private Texture frame;
    private Vector2 size;

    private TextureAtlas atlas;

    public SpriteContainer(WorldManager world, Vector2 position, Vector2 size, BodyDef.BodyType type, String spritePath) {
        this(world, position, size, type, 1f, false, spritePath);
    }

    public SpriteContainer(WorldManager world, Vector2 position, Vector2 size, BodyDef.BodyType type, float density, boolean passThrough, String spritePath) {
        super(world, position, size, type, density, passThrough, SpriteContainer.id);

        this.world = world;
        this.frame = new Texture(Gdx.files.internal(spritePath));
        this.size = new Vector2(size);

        SpriteContainer.id++;
    }

    public void disableRotation() {
        getBody().setFixedRotation(true);
    }

    public void update(SpriteBatch batch, CameraController camera) {

        batch.setProjectionMatrix(camera.getCamera().combined);
        batch.begin();
        batch.draw(frame, getBody().getPosition().x - size.x / world.getPPM() / 2,
                getBody().getPosition().y - size.y / world.getPPM() / 2,
                size.x / world.getPPM(),
                size.y / world.getPPM());
        batch.end();
    }

    public Rectangle getRectangle() {
        return new Rectangle(getBody().getPosition().x - size.x / 2 / world.getPPM(),
                getBody().getPosition().y - size.y / 2 / world.getPPM(),
                size.x / world.getPPM(),
                size.y / world.getPPM());
    }

    @Override
    public void dispose() {
        atlas.dispose();
    }
}
