package com.netgames.clashoffishes.engine;

import javafx.animation.AnimationTimer;

/**
 * The game play loop.
 * 
 * @author Hein Dauven
 */
public class GameLoop extends AnimationTimer {
    protected GameManager gameManager;

    public GameLoop(GameManager manager) {
        gameManager = manager;
    }
    
    @Override
    public void handle(long now) {
        // TODO
        gameManager.player.update();
    }
    
    @Override
    public void start() {
        super.start();
    } 
    
    @Override
    public void stop() {
        super.stop();
    }
}
