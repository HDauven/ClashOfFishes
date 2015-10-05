package com.netgames.clashoffishes.engine.object.events;

import com.netgames.clashoffishes.engine.object.GameObject;
import javafx.scene.canvas.GraphicsContext;

/**
 * Created by bram on 1/10/15.
 */
public class JellyFish extends GameObject implements RandomEvent {

    public JellyFish(int x, int y) {
        super(x, y);
    }

    @Override
    protected void update() {

    }

    @Override
    protected void render(GraphicsContext ctx) {

    }
}
