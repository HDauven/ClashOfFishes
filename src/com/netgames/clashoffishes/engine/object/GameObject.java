package com.netgames.clashoffishes.engine.object;

import com.netgames.clashoffishes.engine.GameMode;
import com.netgames.clashoffishes.engine.math.Vector2;
import javafx.scene.canvas.GraphicsContext;

/**
 * Created by bram on 1/10/15.
 */
public abstract class GameObject {
    private Vector2 position;
    private Vector2 velocity;

    public GameObject(int x, int y) {
        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);
    }

    protected abstract void update();

    protected abstract void render(GraphicsContext ctx);

    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getVelocity() {
        return velocity;
    }
}
