package com.netgames.clashoffishes.engine.object.events;

import com.netgames.clashoffishes.engine.object.AnimatedObject;
import javafx.scene.image.Image;

/**
 *
 * @author Hein Dauven
 */
public class FishHook extends AnimatedObject implements RandomEvent
{

    private double xLocation;
    private double yLocation;
    private boolean lowestEntered;
    int i;

    /**
     *
     * @param SVGData
     * @param xLocation
     * @param yLocation
     * @param spriteCels
     */
    public FishHook(String SVGData, double xLocation, double yLocation, Image... spriteCels)
    {
        super(SVGData, xLocation, yLocation, spriteCels);
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
    public void update()
    {
        if (!lowestEntered)
        {
            spriteFrame.setTranslateY(yLocation = yLocation + vY);
            spriteBound.setTranslateY(yLocation);
            if(yLocation == 300)
            {
                lowestEntered = true;
            }
        }
        if (lowestEntered)
        {
            i++;
            vY = 0;
        }
        if (i > 240)
        {
            //Is sowieso 300 volgens mij
            if (yLocation == 300)
            {
                vY = -1;
                lowestEntered = false;
            }
        }
    }

    public double getYLocation()
    {
        return this.yLocation;
    }
}
