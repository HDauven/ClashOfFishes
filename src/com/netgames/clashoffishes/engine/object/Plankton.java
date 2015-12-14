package com.netgames.clashoffishes.engine.object;

import javafx.scene.image.Image;

/**
 * 
 * @author Hein Dauven
 */
public class Plankton extends GameObject implements NPC {

    /**
     * 
     * @param SVGData
     * @param xLocation
     * @param yLocation
     * @param spriteCels 
     */
    public Plankton(int id, String SVGData, double xLocation, double yLocation, Image... spriteCels) {
        super(id, SVGData, xLocation, yLocation, spriteCels);
        spriteFrame.setTranslateX(xLocation);
        spriteFrame.setTranslateY(yLocation);
        isFixed  = false;
        hasValue = true;
        isBonus  = true;
    }

    /**
     * 
     */
    @Override
    protected void update() {
        //  empty
    }
}
