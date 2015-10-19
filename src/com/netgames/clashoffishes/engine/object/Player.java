package com.netgames.clashoffishes.engine.object;

import static com.netgames.clashoffishes.engine.GameManager.WIDTH;
import static com.netgames.clashoffishes.engine.GameManager.HEIGHT;
import com.netgames.clashoffishes.engine.GameManager;
import javafx.scene.image.Image;

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
        // setImageState();
        movePlayer(iX, iY);
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
    
    /**
     * 
     * @param x
     * @param y 
     */
    private void movePlayer(double x, double y) {
        spriteFrame.setTranslateX(x);
        spriteFrame.setTranslateY(y);
    }
}
