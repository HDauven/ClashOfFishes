package com.netgames.clashoffishes.engine.object;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.SVGPath;

/**
 * GameObject abstract class that functions as the basic implementation for all
 * the GameObjects that are available in the game. This class will primarily be
 * used for fixed sprites.
 *
 * @author Hein Dauven
 * @version %I%, %G%
 */
public abstract class GameObject {

    private int id;
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
     *
     * @param SVGData
     * @param xLocation
     * @param yLocation
     * @param spriteCels
     */
    public GameObject(int id, String SVGData, double xLocation,
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
        id = id;
    }

    /**
     *
     * @return
     */
    public List<Image> getImageStates() {
        return imageStates;
    }

    /**
     *
     * @param imageStates
     */
    public void setImageStates(List<Image> imageStates) {
        this.imageStates = imageStates;
    }

    /**
     *
     * @return
     */
    public ImageView getSpriteFrame() {
        return spriteFrame;
    }

    /**
     *
     * @param spriteFrame
     */
    public void setSpriteFrame(ImageView spriteFrame) {
        this.spriteFrame = spriteFrame;
    }

    /**
     *
     * @return
     */
    public SVGPath getSpriteBound() {
        return spriteBound;
    }

    /**
     *
     * @param spriteBound
     */
    public void setSpriteBound(SVGPath spriteBound) {
        this.spriteBound = spriteBound;
    }

    /**
     *
     * @return
     */
    public double getiX() {
        return iX;
    }

    /**
     *
     * @param iX
     */
    public void setiX(double iX) {
        this.iX = iX;
    }

    /**
     *
     * @return
     */
    public double getiY() {
        return iY;
    }

    /**
     *
     * @param iY
     */
    public void setiY(double iY) {
        this.iY = iY;
    }

    /**
     *
     * @return
     */
    public double getpX() {
        return pX;
    }

    /**
     *
     * @param pX
     */
    public void setpX(double pX) {
        this.pX = pX;
    }

    /**
     *
     * @return
     */
    public double getpY() {
        return pY;
    }

    /**
     *
     * @param pY
     */
    public void setpY(double pY) {
        this.pY = pY;
    }

    /**
     *
     * @return
     */
    public boolean isAlive() {
        return isAlive;
    }

    /**
     *
     * @param isAlive
     */
    public void setIsAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    /**
     *
     * @return
     */
    public boolean isFixed() {
        return isFixed;
    }

    /**
     *
     * @param isFixed
     */
    public void setIsFixed(boolean isFixed) {
        this.isFixed = isFixed;
    }

    /**
     *
     * @return
     */
    public boolean isBonus() {
        return isBonus;
    }

    /**
     *
     * @param isBonus
     */
    public void setIsBonus(boolean isBonus) {
        this.isBonus = isBonus;
    }

    /**
     *
     * @return
     */
    public boolean hasValue() {
        return hasValue;
    }

    /**
     *
     * @param hasValue
     */
    public void setHasValue(boolean hasValue) {
        this.hasValue = hasValue;
    }

    /**
     *
     * @return
     */
    public boolean isFlipV() {
        return isFlipV;
    }

    /**
     *
     * @param isFlipV
     */
    public void setIsFlipV(boolean isFlipV) {
        this.isFlipV = isFlipV;
    }

    /**
     *
     * @return
     */
    public boolean isFlipH() {
        return isFlipH;
    }

    /**
     *
     * @param isFlipH
     */
    public void setIsFlipH(boolean isFlipH) {
        this.isFlipH = isFlipH;
    }

    /**
     * The update method updates the position of the GameObject.
     */
    protected abstract void update();

    public int getID() {
        return this.id;
    }
}
