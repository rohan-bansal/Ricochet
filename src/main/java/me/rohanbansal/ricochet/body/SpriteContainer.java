package me.rohanbansal.ricochet.body;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.utils.Disposable;
import me.rohanbansal.ricochet.world.WorldManager;

public class SpriteContainer extends BodyHolder implements Disposable {

    private static int id = 1;

    public SpriteContainer(Body body) {
        super(body);
    }

    public SpriteContainer(WorldManager world, Vector2 position, Vector2 size, BodyDef.BodyType type) {
        super(world, position, size, type, 1f, false, SpriteContainer.id);

        SpriteContainer.id++;
    }

    public SpriteContainer(WorldManager world, Vector2 position, Vector2 size, BodyDef.BodyType type, float density, boolean passThrough) {
        super(world, position, size, type, density, passThrough, SpriteContainer.id);

        SpriteContainer.id++;
    }

    @Override
    public void dispose() {

    }
}
