package me.rohanbansal.ricochet.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class InterpTools {

    /**
     * An interpolation method that can be used on actors to slide them in from different directions.
     * @param actor actor to slide in
     * @param location location (left, right, top, down)
     * @param duration duration of slide-in
     * @param interp interpolation method to be used
     * @param offset offset of actor off the screen
     * @param runnable on-finish runnable
     */
    public static void slideIn(Actor actor, String location, float duration, Interpolation interp, int offset, Runnable... runnable) {
        Vector2 actorXY = new Vector2(actor.getX(), actor.getY());

        Runnable toRun = null;
        if(runnable.length > 0) {
            toRun = runnable[0];
        }

        if(location.equals("left")) {
            actor.setPosition(-offset, actorXY.y);
            if(toRun != null) {
                actor.addAction(sequence(moveTo(actorXY.x, actorXY.y, duration, interp), run(toRun)));
            } else {
                actor.addAction(moveTo(actorXY.x, actorXY.y, duration, interp));
            }
        } else if(location.equals("right")) {
            actor.setPosition(Gdx.graphics.getWidth() + offset, actorXY.y);
            if(toRun != null) {
                actor.addAction(sequence(moveTo(actorXY.x, actorXY.y, duration, interp), run(toRun)));
            } else {
                actor.addAction(moveTo(actorXY.x, actorXY.y, duration, interp));
            }
        } else if(location.equals("top")) {
            actor.setPosition(actorXY.x, Gdx.graphics.getHeight() + offset);
            if(toRun != null) {
                actor.addAction(sequence(moveTo(actorXY.x, actorXY.y, duration, interp), run(toRun)));
            } else {
                actor.addAction(moveTo(actorXY.x, actorXY.y, duration, interp));
            }
        } else if(location.equals("down")) {
            actor.setPosition(actorXY.x, -offset);
            if(toRun != null) {
                actor.addAction(sequence(moveTo(actorXY.x, actorXY.y, duration, interp), run(toRun)));
            } else {
                actor.addAction(moveTo(actorXY.x, actorXY.y, duration, interp));
            }
        }

        if(runnable.length > 0) {
            actor.addAction(run(runnable[0]));
        }
    }

    /**
     * An interpolation method that can be used on actors to slide them off the screen from different directions.
     * @param actor actor to slide in
     * @param location location (left, right, top, down)
     * @param duration duration of slide-out
     * @param interp interpolation method to be used
     * @param offset offset of actor off the screen
     * @param runnable on-finish runnable
     */
    public static void slideOut(Actor actor, String location, float duration, Interpolation interp, int offset, Runnable... runnable) {
        Vector2 actorXY = new Vector2(actor.getX(), actor.getY());

        Runnable toRun = null;
        if(runnable.length > 0) {
            toRun = runnable[0];
        }

        if(location.equals("left")) {
            if(toRun != null) {
                actor.addAction(sequence(moveTo(-offset, actorXY.y, duration, interp), run(toRun)));
            } else {
                actor.addAction(moveTo(-offset, actorXY.y, duration, interp));
            }
        } else if(location.equals("right")) {
            if(toRun != null) {
                actor.addAction(sequence(moveTo(Gdx.graphics.getWidth() + offset, actorXY.y, duration, interp), run(toRun)));
            } else {
                actor.addAction(moveTo(Gdx.graphics.getWidth() + offset, actorXY.y, duration, interp));
            }
        } else if(location.equals("top")) {
            if(toRun != null) {
                actor.addAction(sequence(moveTo(actorXY.x, Gdx.graphics.getHeight() + offset, duration, interp), run(toRun)));
            } else {
                actor.addAction(moveTo(actorXY.x, Gdx.graphics.getHeight() + offset, duration, interp));
            }
        } else if(location.equals("down")) {
            if(toRun != null) {
                actor.addAction(sequence(moveTo(actorXY.x, -offset, duration, interp), run(toRun)));
            } else {
                actor.addAction(moveTo(actorXY.x, -offset, duration, interp));
            }        }
    }

    /**
     * Slide in multiple actors at once, option to have a delay in between to stagger
     * @param location (left, right, top, down)
     * @param duration length of total slide-in
     * @param interp interpolation method to be used
     * @param offset offset off the screen
     * @param delay delay between actor interps
     * @param actors actors to be used
     */
    public static void sequenceSlideIn(String location, float duration, Interpolation interp, int offset, float delay, Actor... actors) {

        float currentDelay = 0f;

        for(int x = 0; x < actors.length; x++) {
            Actor actor = actors[x];

            if(x != 0) {
                currentDelay += delay;
            }

            Vector2 actorXY = new Vector2(actor.getX(), actor.getY());

            if(location.equals("left")) {
                actor.setPosition(-offset, actorXY.y);
                actor.addAction(sequence(delay(currentDelay), moveTo(actorXY.x, actorXY.y, duration, interp)));
            } else if(location.equals("right")) {
                actor.setPosition(Gdx.graphics.getWidth() + offset, actorXY.y);
                actor.addAction(sequence(delay(currentDelay), moveTo(actorXY.x, actorXY.y, duration, interp)));
            } else if(location.equals("top")) {
                actor.setPosition(actorXY.x, Gdx.graphics.getHeight() + offset);
                actor.addAction(sequence(delay(currentDelay), moveTo(actorXY.x, actorXY.y, duration, interp)));
            } else if(location.equals("down")) {
                actor.setPosition(actorXY.x, -offset);
                actor.addAction(sequence(delay(currentDelay), moveTo(actorXY.x, actorXY.y, duration, interp)));
            }
        }
    }

    /**
     * Slide out multiple actors at once, option to have a delay in between to stagger
     * @param location (left, right, top, down)
     * @param duration length of total slide-out
     * @param interp interpolation method to be used
     * @param offset offset off the screen
     * @param delay delay between actor interps
     * @param actors actors to be used
     */
    public static void sequenceSlideOut(String location, float duration, Interpolation interp, int offset, float delay, Actor... actors) {

        float currentDelay = 0f;

        for(int x = 0; x < actors.length; x++) {
            Actor actor = actors[x];

            if(x != 0) {
                currentDelay += delay;
            }

            Vector2 actorXY = new Vector2(actor.getX(), actor.getY());

            if(location.equals("left")) {
                actor.addAction(sequence(delay(currentDelay), moveTo(-offset, actorXY.y, duration, interp)));
            } else if(location.equals("right")) {
                actor.addAction(sequence(delay(currentDelay), moveTo(Gdx.graphics.getWidth() + offset, actorXY.y, duration, interp)));
            } else if(location.equals("top")) {
                actor.addAction(sequence(delay(currentDelay), moveTo(actorXY.x, Gdx.graphics.getHeight() + offset, duration, interp)));
            } else if(location.equals("down")) {
                actor.addAction(sequence(delay(currentDelay), moveTo(actorXY.x, -offset, duration, interp)));
            }
        }
    }
}
