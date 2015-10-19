package com.netgames.clashoffishes.engine.object;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.SVGPath;

/**
 * GameObject
 * abstract class that functions as the basic implementation for
 * all the GameObjects that are available in the game.
 * This class will primarily be used for fixed sprites.
 * 
 * @author Hein Dauven
 * @version %I%, %G%
 */
public abstract class GameObject {
    protected List<Image> imageStates = new ArrayList<>();
    protected ImageView spriteFrame;
    protected SVGPath spriteBound;
    protected double iX;
    protected double iY;
    protected double pX;
    protected double pY;
    protected boolean isAlive;
    protected boolean isFixed;
    protected boolean isBonus;
    protected boolean hasValue;
    protected boolean isFlipV;
    protected boolean isFlipH;
    
    /**
     * The constructor of a GameObject object.
     * @param SVGData
     * @param xLocation
     * @param yLocation
     * @param spriteCels
     */
    public GameObject(String SVGData, double xLocation, 
                        double yLocation, Image... spriteCels) {
        this.spriteBound = new SVGPath();
        this.spriteBound.setContent(SVGData);
        this.spriteFrame = new ImageView(spriteCels[0]);
        this.imageStates.addAll(Arrays.asList(spriteCels));
        this.iX = xLocation;
        this.iY = yLocation;
        this.pX = 0;
        this.pY = 0;
        this.isAlive = false;
        this.isFixed = false;
        this.isBonus = false;
        this.hasValue = false;
        this.isFlipV = false;
        this.isFlipH = false;
    }

    /**
     * The update method updates the position of the GameObject.
     */
    protected abstract void update();
}
