package com.netgames.clashoffishes.engine.object;

import javafx.scene.image.Image;

/**
 * Static game objects that will be flipped around the X axis.
 *
 * @author Hein Dauven
 */
public class PropH extends GameObject {

    public PropH(String SVGData, double xLocation, double yLocation, Image... spriteCels) {
        super(SVGData, xLocation, yLocation, spriteCels);
        this.setIsFlipH(true);
        /* Flips the image around the Y axis */
        spriteFrame.setScaleX(-1);
        /* Alters the location of the node */
        spriteFrame.setTranslateX(xLocation);
        spriteFrame.setTranslateY(yLocation);
    }

    @Override
    public void update() {
        /* not implemented yet */
    }    
}
