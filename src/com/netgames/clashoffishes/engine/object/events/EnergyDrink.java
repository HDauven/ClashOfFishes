package com.netgames.clashoffishes.engine.object.events;

import com.netgames.clashoffishes.engine.object.GameObject;
import javafx.scene.image.Image;

/**
 * 
 * @author Hein Dauven
 */
public class EnergyDrink extends GameObject implements RandomEvent {

    /** 
     * 
     * @param SVGData
     * @param xLocation
     * @param yLocation
     * @param spriteCels 
     */
    public EnergyDrink(int id, double xLocation, double yLocation, Image... spriteCels) {
        super(id, "M 4,00 L 4,0 0,19 0,139 16,148 64,148 78,139 78,18 75,0 Z", xLocation, yLocation, spriteCels);
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
        // empty
    }
}
