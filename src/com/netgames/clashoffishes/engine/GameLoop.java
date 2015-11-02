package com.netgames.clashoffishes.engine;

import com.netgames.clashoffishes.Administration;
import java.text.SimpleDateFormat;
import java.util.Random;
import javafx.animation.AnimationTimer;

/**
 * The game play loop.
 *
 * @author Hein Dauven
 */
public class GameLoop extends AnimationTimer {

    private final long NANO_TO_SECOND;
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
    long prev = System.nanoTime();
    Random random = new Random();
    private final long startTime = System.nanoTime();
    private long secondsLeft;

    private final int length_of_game = 20;
    private final boolean winCondition = false;

    /* A reference to the GameManager class. */
    protected GameManager gameManager;

    /**
     * Constructor for the GameLoop.
     *
     * @param manager GameManager class reference.
     */
    public GameLoop(GameManager manager) {
        this.NANO_TO_SECOND = 1_000_000_000;
        gameManager = manager;
    }

    /**
     * This method is going to be called in every frame while the AnimationTimer
     * is active. Capped at 60 frames per second by default.
     *
     * @param now The timestamp of the current frame given in nanoseconds. This
     * value will be the same for all AnimationTimers called during one frame.
     */
    @Override
    public void handle(long now) {
        // TODO
        gameManager.getPlayer().update();

        long elapsed = now - prev;
        int randInt = (int) (Math.random() * 10000 + 1);
        //System.out.println(elapsed);
        if ((elapsed / NANO_TO_SECOND) > randInt) {
            gameManager.addRandomObject();
            //System.out.println(sdf.format(Calendar.getInstance().getTime()));
            //add object if randInt % 4 == 0 dit object else % 3 == 0 dat object etc
            prev = System.nanoTime();
        }
        if (gameManager.getGameMode() == GameMode.EVOLUTION_OF_TIME) {
            long elapsed2 = now - startTime;
            secondsLeft = length_of_game - (elapsed2 / NANO_TO_SECOND);
            gameManager.setTimeLeft(String.valueOf(secondsLeft));
            if (secondsLeft == 0) {
                //Spel voorbij na 2 minuten
                if (gameManager.getGameState() != GameState.FINISHED) {
                    gameManager.setGameState(GameState.FINISHED);
                    this.stop();
                    hasWon();
                    Administration.get().getLoggedInUser().updateHighScore(gameManager.getGameMode(), gameManager.getGameScore());
                    //GuiUtilities.buildStage(gameManager.getStage().getScene().getWindow(), "GameHighscore", "Score");
                    System.out.println("Time is up!");
                }
            }
        }
    }

    /**
     * Starts the AnimationTimers. Once it is started, the handle(long) method
     * of this AnimationTimers will be called in every frame.
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
    
    private void hasWon() {
        if (gameManager.getGameState() == GameState.FINISHED) {
            if (winCondition == true) {
                gameManager.getRoot().getChildren().add(
                    gameManager.getGameMenu().getVictoryScreen());   
            } else {
                gameManager.getRoot().getChildren().add(
                        gameManager.getGameMenu().getDefeatScreen());
            }
        }
    }
}
