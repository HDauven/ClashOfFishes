package com.netgames.clashoffishes.engine.object.events;

import com.netgames.clashoffishes.engine.object.GameObject;
import javafx.scene.image.Image;

/**
 * 
 * @author Hein Dauven
 */
public class Seaweed extends GameObject implements RandomEvent {

    /**
     * 
     * @param SVGData
     * @param xLocation
     * @param yLocation
     * @param spriteCels 
     */
    public Seaweed(String SVGData, double xLocation, double yLocation, Image... spriteCels) {
        super(SVGData, xLocation, yLocation, spriteCels);
        hasValue = true;
        isBonus  = true;
    }

    /**
     * 
     */
    @Override
    protected void update() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
