package me.rohanbansal.ricochet.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class ModifiedStage extends Stage {


    /**
     * Upgraded version of Scene2D stage with support for adding multiple actors at once, and removing focus from widgets when touched elsewhere
     * @param fitViewport Stage default viewport
     * @param batch SpriteBatch for stage to use
     */
    public ModifiedStage(FitViewport fitViewport, SpriteBatch batch) {
        super(fitViewport, batch);
    }

    /**
     * Add multiple actors at once
     * @param actors actors to add
     */
    public void addActors(Actor... actors) {
        if(actors.length > 0) {
            for(Actor actor : actors) {
                addActor(actor);
            }
        }
    }

    /**
     * Override touch down function in superclass to disable focus
     * @param screenX inputX
     * @param screenY inputY
     * @param pointer pointer
     * @param button button clicked
     * @return boolean
     */
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        super.unfocusAll();
        Gdx.input.setOnscreenKeyboardVisible(false);
        return super.touchDown(screenX, screenY, pointer, button);
    }
}