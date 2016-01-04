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
    public Seaweed(int id, double xLocation, double yLocation, Image... spriteCels) {
        super(id, "M 66.00,11.00"
                + "           C 66.00,11.00 11.00,192.00 11.00,192.00"
                + "             11.00,192.00 4.00,285.00 4.00,285.00"
                + "             4.00,285.00 12.00,453.00 12.00,453.00"
                + "             12.00,453.00 31.00,545.00 31.00,545.00"
                + "             31.00,545.00 149.00,525.00 149.00,525.00"
                + "             149.00,525.00 193.00,389.00 193.00,389.00"
                + "             193.00,389.00 186.00,148.00 186.00,148.00"
                + "             186.00,148.00 125.00,58.00 125.00,58.00", xLocation, yLocation, spriteCels);
        spriteFrame.setTranslateX(xLocation);
        spriteFrame.setTranslateY(yLocation);
        isFixed = false;
        hasValue = true;
        isBonus = true;
    }

    /**
     *
     */
    @Override
    protected void update() {
        // empty
    }

}
