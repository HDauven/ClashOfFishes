package com.netgames.clashoffishes.engine.object;

import javafx.scene.image.Image;

/**
 * AnimatedObject
 * abstract class that functions as the basic implementation for
 * all the AnimtedObjects that are available in the game.
 * This class will primarily be used for motion sprites.
 *
 * @author Hein Dauven
 * @version %I%, %G%
 */
public abstract class AnimatedObject extends GameObject {

    /**
     * The constructor of an AnimatedObject object.
     * @param SVGData
     * @param xLocation
     * @param yLocation
     * @param spriteCels 
     */
    public AnimatedObject(String SVGData, double xLocation, double yLocation, Image... spriteCels) {
        super(SVGData, xLocation, yLocation, spriteCels);
    }
    
}
