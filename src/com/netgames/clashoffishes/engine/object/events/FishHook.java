package com.netgames.clashoffishes.engine.object.events;

import com.netgames.clashoffishes.engine.object.AnimatedObject;
import javafx.scene.image.Image;

/**
 *
 * @author Hein Dauven
 */
public class FishHook extends AnimatedObject implements RandomEvent {

    private double xLocation;
    private double yLocation;
    private boolean lowestEntered;
    int i;

    /**
     *
     * @param xLocation
     * @param yLocation
     * @param spriteCels
     */
    public FishHook(int id, double xLocation, double yLocation, Image... spriteCels) {
        super(id, "M 27.00,919.50"
                + "           C 27.00,919.50 0.00,956.50 0.00,956.50"
                + "             0.00,956.50 0.00,997.50 0.00,997.50"
                + "             0.00,997.50 37.50,997.50 37.50,997.50"
                + "             37.50,997.50 39.50,919.50 39.50,919.50 Z", xLocation, yLocation, spriteCels);
        this.xLocation = xLocation;
        this.yLocation = yLocation;
        spriteFrame.setTranslateX(xLocation);
        spriteFrame.setTranslateY(yLocation);
        isFixed = false;
        hasValue = true;
        isBonus = true;
        lowestEntered = false;
        i = 0;
        super.setvY(1);
    }

    @Override
    public void update() {
        if (!lowestEntered) {
            spriteFrame.setTranslateY(yLocation = yLocation + vY);
            if (yLocation == -300) {
                lowestEntered = true;
            }
        }
        if (lowestEntered) {
            i++;
            vY = 0;
        }
        if (i > 240) {
            //Is sowieso 300 volgens mij
            if (yLocation == -300) {
                vY = -1;
                lowestEntered = false;
            }
        }
    }
}
