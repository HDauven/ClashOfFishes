package com.netgames.clashoffishes.engine;

import java.text.SimpleDateFormat;
import java.util.Random;
import javafx.animation.AnimationTimer;

/**
 * The game play loop.
 *
 * @author Hein Dauven
 */
public class GameLoop extends AnimationTimer {
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
    long prev = System.nanoTime();
    
    /* A reference to the GameManager class. */
    protected GameManager gameManager;

    /**
     * Constructor for the GameLoop.
     * @param manager GameManager class reference.
     */
    public GameLoop(GameManager manager){
        gameManager = manager;
    }

    /**
     * This method is going to be called in every frame while the AnimationTimer is active.
     * Capped at 60 frames per second by default.
     * @param now The timestamp of the current frame given in nanoseconds. This value will be the same for all AnimationTimers called during one frame.
     */
    @Override
    public void handle(long now) {
        // TODO
        gameManager.getPlayer().update();

        Random random = new Random();
        long elapsed = now - prev;
        int randInt = (int) (Math.random() * 10000 + 1);
        //System.out.println(elapsed);
        if ((elapsed / 1000000000) > randInt) {
            gameManager.addRandomObject();
            //System.out.println(sdf.format(Calendar.getInstance().getTime()));
            //add object if randInt % 4 == 0 dit object else % 3 == 0 dat object etc
            prev = System.nanoTime();
        }
    }
    
    /**
     * Starts the AnimationTimers. Once it is started, the handle(long) method of this AnimationTimers will be called in every frame.
     */
    @Override
    public void start() {
        super.start();
    } 
    
    /**
     * Stops the AnimationTimers. 
     */
    @Override
    public void stop() {
        super.stop();
    }
}
