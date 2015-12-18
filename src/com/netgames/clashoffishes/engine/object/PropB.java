package com.netgames.clashoffishes.engine.object;

import javafx.scene.image.Image;

/**
 * Static game objects that will be flipped and mirrored.
 *
 * @author Hein Dauven
 */
public class PropB extends GameObject {

    public PropB(int id, String SVGData, double xLocation, double yLocation, Image... spriteCels) {
        super(id, SVGData, xLocation, yLocation, spriteCels);
        this.setIsFlipH(true);
        this.setIsFlipV(true);
        /* Flips the image around the X & Y axis */
        spriteFrame.setScaleX(-1);
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
