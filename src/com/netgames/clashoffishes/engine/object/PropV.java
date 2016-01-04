package com.netgames.clashoffishes.engine.object;

import javafx.scene.image.Image;

/**
 * Static game objects that will be flipped around the Y axis.
 *
 * @author Hein Dauven
 */
public class PropV extends GameObject {

    public PropV(int id, String SVGData, double xLocation, double yLocation, Image... spriteCels) {
        super(id, SVGData, xLocation, yLocation, spriteCels);
        this.setIsFlipV(true);
        /* Flips the image around the X axis */
        spriteFrame.setScaleY(-1);
        /* Alters the location of the node */
        spriteFrame.setTranslateX(xLocation);
        spriteFrame.setTranslateY(yLocation);
    }

    @Override
    public void update() {
        /* not implemented yet */
    }
}
