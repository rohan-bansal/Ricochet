package me.rohanbansal.ricochet;

import me.rohanbansal.ricochet.camera.CameraController;
import me.rohanbansal.ricochet.effects.EffectCreator;
import me.rohanbansal.ricochet.text.TextDrawer;
import me.rohanbansal.ricochet.tools.Optimizer;

public class Test {

    static Optimizer optimizer;

    public static void main(String[] args) {
        optimizer = new Optimizer();
        optimizer.setOptimize(Optimizer.Objects.EFFECTS, Optimizer.Objects.TEXT);
        optimizer.setEffectCreator(new EffectCreator(), 1);
        optimizer.useTextDrawer(2);

        optimizer.optimizeAndUpdate();
    }
}
