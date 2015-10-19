package com.netgames.clashoffishes.engine.object;

/**
 * GameObject
 * abstract class that functions as the basic implementation for
 * all the GameObjects that are available in the game.
 * This class will primarily be used for fixed sprites.
 * 
 * @author Hein Dauven
 * @version %I%, %G%
 */
public abstract class GameObject {
    /**
     * The constructor of a GameObject object.
     */
    public GameObject() {
        
    }

    /**
     * The update method updates the position of the GameObject.
     */
    protected abstract void update();
}
