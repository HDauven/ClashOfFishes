package com.netgames.clashoffishes.engine.object;

import static com.netgames.clashoffishes.engine.GameManager.WIDTH;
import static com.netgames.clashoffishes.engine.GameManager.HEIGHT;
import com.netgames.clashoffishes.engine.GameManager;
import javafx.scene.image.Image;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.Shape;

/**
 * Class that represents an actual in-game player.
 * 
 * @author Hein Dauven
 */
public class Player extends AnimatedObject {
    protected GameManager gameManager;
    protected static final double SPRITE_PIXELS_X = 81;
    protected static final double SPRITE_PIXELS_Y = 81;
    protected static final double rightBoundary   = (WIDTH/2 - SPRITE_PIXELS_X/2);
    protected static final double leftBoundary    = -(WIDTH/2 - SPRITE_PIXELS_X/2);
    protected static final double bottomBoundary  = (HEIGHT/2 - SPRITE_PIXELS_Y/2);
    protected static final double topBoundary     = -(HEIGHT/2 - SPRITE_PIXELS_Y/2);
    boolean animator = false;
    int framecounter = 0;
    int runningspeed = 6;
    
    /**
     * 
     * @param manager
     * @param SVGData
     * @param xLocation
     * @param yLocation
     * @param spriteCels 
     */
    public Player(GameManager manager,String SVGData, double xLocation, double yLocation, Image... spriteCels) {
        super(SVGData, xLocation, yLocation, spriteCels);
        gameManager = manager;
        vX = 2;
        vY = 2;
    }

    /**
     * 
     */
    @Override
    public void update() {
        // TODO implement the update method for a player
        setXYLocation();
        setBoundaries();
        setImageState();
        movePlayer(iX, iY);
        playAudioClip();
        checkCollision();
    }
    
    /**
     * 
     */
    private void setXYLocation() {
        if (gameManager.isRight()) { iX += vX; }
        if (gameManager.isLeft())  { iX -= vX; }
        if (gameManager.isDown())  { iY += vY; }
        if (gameManager.isUp())    { iY -= vY; }
    }
    
    /**
     * 
     */
    private void setBoundaries() {
        if (iX >= rightBoundary)  { iX = rightBoundary; }
        if (iX <= leftBoundary)   { iX = leftBoundary; }
        if (iY >= bottomBoundary) { iY = bottomBoundary; }
        if (iY <= topBoundary)    { iY = topBoundary; }
    }
    
    private void setImageState() {
        /* check whether the character stands still */
        if (!gameManager.isRight() &&
            !gameManager.isLeft()  &&
            !gameManager.isDown()  &&
            !gameManager.isUp()) {
            spriteFrame.setImage(imageStates.get(0));
            animator = false;
            framecounter = 0;
        }  
        
        if (gameManager.isRight()) {
            spriteFrame.setScaleX(1);
            this.setIsFlipH(false);
            if (!animator && (!gameManager.isDown() && !gameManager.isUp())) {
                spriteFrame.setImage(imageStates.get(1));
                if (framecounter >= runningspeed) {
                    animator = true;
                    framecounter = 0;
                    spriteFrame.setRotate(0);
                } else { 
                    framecounter += 1; 
                }                
            } else if (animator) {
                spriteFrame.setImage(imageStates.get(2));
                if (framecounter >= runningspeed) {
                    animator = false;
                    framecounter = 0;
                    spriteFrame.setRotate(0);
                } else {
                    framecounter += 1;
                }
            }            
        }
        
        if (gameManager.isLeft()) {
            spriteFrame.setScaleX(-1);
            this.setIsFlipH(true);
            if (!animator && (!gameManager.isDown() && !gameManager.isUp())) {
                spriteFrame.setImage(imageStates.get(1));
                if (framecounter >= runningspeed) {
                    animator = true;
                    framecounter = 0;
                    spriteFrame.setRotate(0);
                } else { 
                    framecounter += 1; 
                }                
            } else if (animator) {
                spriteFrame.setImage(imageStates.get(2));
                if (framecounter >= runningspeed) {
                    animator = false;
                    framecounter = 0;
                    spriteFrame.setRotate(0);
                } else {
                    framecounter += 1;
                }
            }            
        }
        
        if (gameManager.isDown()) {
            spriteFrame.setImage(imageStates.get(1));
            if (gameManager.isLeft()) {
                spriteFrame.setRotate(-45);
            } else if (gameManager.isRight()) {
                spriteFrame.setRotate(45);
            }
        }
        
        if (gameManager.isUp()) {
            spriteFrame.setImage(imageStates.get(1));
            if (gameManager.isLeft()) {
                spriteFrame.setRotate(45);
            } else if (gameManager.isRight()) {
                spriteFrame.setRotate(-45);
            }
        }
    }
    
    /**
     * 
     * @param x
     * @param y 
     */
    private void movePlayer(double x, double y) {
        spriteFrame.setTranslateX(x);
        spriteFrame.setTranslateY(y);
    }
    
    /**
     * 
     */
    private void playAudioClip() {
        if (gameManager.isSpace()) { gameManager.playBiteSound(); }
    }
    
    /**
     * 
     */
    public void checkCollision() {
       for (int i = 0; i < gameManager.objectManager.getCurrentObject().size(); i++) {
           GameObject object = gameManager.objectManager.getCurrentObject().get(i);
           collide(object);
       } 
    }
    
    /**
     * This method checks for collision detection between the Player object and
     * another GameObject object. It goes through a number of steps to ascertain
     * whether collision has taken place with the given object.
     * 
     * First, it checks whether the Player objects ImageView node and the 
     * GameObjects ImageView node have intersected. (via the BoundsInParent of Node)
     * 
     * If this is the case, the SVG path of both objects are used to create a new 
     * Shape via the SVGPath class intersect method. (which returns a new shape)
     * 
     * Based on the result of this intersect, the new Shape will be checked to 
     * see whether it has a width. If the new Shape has no width, we know the 
     * shape is empty and no intersection took place.
     * 
     * @param object A GameObject that is part of the ObjectManager.
     * @return Whether collision took place or not.
     */
    @Override
    public boolean collide(GameObject object) {
        // Boolean used to confirm whether collision was detected or not.
        boolean collisionDetect = false;
        
        // Checks if the player ImageView has collided with objects ImageView.
        if (gameManager.player.spriteFrame.getBoundsInParent().intersects(
            object.getSpriteFrame().getBoundsInParent())) {
            
            // A shape is generated based on the SVG path of the player and the object
            // If the shapes intersect, a new shape is created.
            Shape intersection = SVGPath.intersect(
                    gameManager.player.getSpriteBound(),
                    object.getSpriteBound());
            
            // Based on the prior intersection we will check whether the collision really
            // took place. If no collision took place, the object shouldn't have any size,
            // so the width would result in a '-1' (-1 meaning no width available).
            // We check here for the opposite of '-1' (0 and more) to see whether collision
            // took place.
            if (intersection.getBoundsInLocal().getWidth() != -1) {
                collisionDetect = true;
            }
        }        
        return collisionDetect;
    }
}
