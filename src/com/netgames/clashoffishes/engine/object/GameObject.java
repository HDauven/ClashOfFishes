package com.netgames.clashoffishes.engine.object;

import com.netgames.clashoffishes.engine.GameMode;
import com.netgames.clashoffishes.engine.math.Vector2;
import javafx.scene.canvas.GraphicsContext;

/**
 * GameObject
 * abstract class that functions as the basic implementation for
 * all the GameObjects that are available in the game.
 * 
 * @author Bram Hoendervangers
 * @author Hein Dauven
 * @version 1.0
 */
public abstract class GameObject {
    /** The position of a GameObject */
    private Vector2 position;
    /** The velocity of a GameObject */
    private Vector2 velocity;

    /**
     * The constructor of a GameObject object.
     * @param x x coordinate of the GameObject object.
     * @param y y coordinate of the GameObject object.
     */
    public GameObject(int x, int y) {
        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);
    }

    /**
     * The update method updates the position of the GameObject.
     */
    protected abstract void update();

    /**
     * The render method renders the GameObject on its actual position.
     * @param ctx draw call for the GameObject.
     */
    protected abstract void render(GraphicsContext ctx);

    /**
     * Gets the current position of the GameObject.
     * @return position data of the GameObject
     */
    public Vector2 getPosition() {
        return position;
    }

    /**
     * Get the current velocity of the GameObject.
     * @return velocity data of the GameObject
     */
    public Vector2 getVelocity() {
        return velocity;
    }
}
