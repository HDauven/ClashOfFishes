package com.netgames.clashoffishes.engine.object;

import javafx.scene.image.Image;

/**
 * AnimatedObject
 * abstract class that functions as the basic implementation for
 * all the AnimtedObjects that are available in the game.
 * This class will primarily be used for motion sprites.
 *
 * @author Hein Dauven
 * @version %I%, %G%
 */
public abstract class AnimatedObject extends GameObject {
    protected double vX;
    protected double vY;
    protected double lifeSpan;
    protected double damage;
    protected double offsetX;
    protected double offsetY;
    protected double boundScale;
    protected double boundRot;
    protected double friction;
    protected double gravity;
    protected double bounce;
    
    /**
     * The constructor of an AnimatedObject object.
     * @param SVGData
     * @param xLocation
     * @param yLocation
     * @param spriteCels 
     */
    public AnimatedObject(String SVGData, double xLocation, double yLocation, Image... spriteCels) {
        super(SVGData, xLocation, yLocation, spriteCels);
        lifeSpan = 1000;
        vX = 0;
        vY = 0;
        damage = 0;
        offsetX = 0;
        offsetY = 0;
        boundScale = 0;
        boundRot = 0;
        friction = 0;
        gravity = 0;
        bounce = 0;
    }
    
    /**
     * The update method updates the position of the AnimatedObject.
     */
    @Override
    public abstract void update(); 
    
    /**
     * The collide method checks whether an AnimatedObject has collided
     * with a GameObject.
     * @param object A GameObject that is currently in game.
     * @return Whether the collision is true or false.
     */
    protected abstract boolean collide(GameObject object);
    
    /**
     * 
     * @return 
     */
    public double getvX() {
        return vX;
    }

    /**
     * 
     * @param vX 
     */
    public void setvX(double vX) {
        this.vX = vX;
    }

    /**
     * 
     * @return 
     */
    public double getvY() {
        return vY;
    }

    /**
     * 
     * @param vY 
     */
    public void setvY(double vY) {
        this.vY = vY;
    }
    
    /**
     * 
     * @return 
     */
    public double getLifeSpan() {
        return lifeSpan;
    }

    /**
     * 
     * @param lifeSpan 
     */
    public void setLifeSpan(double lifeSpan) {
        this.lifeSpan = lifeSpan;
    }

    /**
     * 
     * @return 
     */
    public double getDamage() {
        return damage;
    }

    /**
     * 
     * @param damage 
     */
    public void setDamage(double damage) {
        this.damage = damage;
    }
    
    /**
     * 
     * @return 
     */
    public double getOffsetX() {
        return offsetX;
    }

    /**
     * 
     * @param offsetX 
     */
    public void setOffsetX(double offsetX) {
        this.offsetX = offsetX;
    }

    /**
     * 
     * @return 
     */
    public double getOffsetY() {
        return offsetY;
    }

    /**
     * 
     * @param offsetY 
     */
    public void setOffsetY(double offsetY) {
        this.offsetY = offsetY;
    }
    
    /**
     * 
     * @return 
     */
    public double getBoundScale() {
        return boundScale;
    }

    /**
     * 
     * @param boundScale 
     */
    public void setBoundScale(double boundScale) {
        this.boundScale = boundScale;
    }

    /**
     * 
     * @return 
     */
    public double getBoundRot() {
        return boundRot;
    }

    /**
     * 
     * @param boundRot 
     */
    public void setBoundRot(double boundRot) {
        this.boundRot = boundRot;
    }
    
    /**
     * 
     * @return 
     */
    public double getFriction() {
        return friction;
    }

    /**
     * 
     * @param friction 
     */
    public void setFriction(double friction) {
        this.friction = friction;
    }

    /**
     * 
     * @return 
     */
    public double getGravity() {
        return gravity;
    }

    /**
     * 
     * @param gravity 
     */
    public void setGravity(double gravity) {
        this.gravity = gravity;
    }

    /**
     * 
     * @return 
     */
    public double getBounce() {
        return bounce;
    }
    
    /**
     * 
     * @param bounce 
     */
    public void setBounce(double bounce) {
        this.bounce = bounce;
    }
}
