package com.netgames.clashoffishes.engine.object;

import javafx.scene.image.Image;

/**
 * Static game objects that will be used as-is.
 *
 * @author Hein Dauven
 */
public class Prop extends GameObject {

    public Prop(int id, String SVGData, double xLocation, double yLocation, Image... spriteCels) {
        super(id, SVGData, xLocation, yLocation, spriteCels);
        spriteFrame.setTranslateX(xLocation);
        spriteFrame.setTranslateY(yLocation);
    }

    @Override
    public void update() {
        /* not implemented yet */
    }   
}