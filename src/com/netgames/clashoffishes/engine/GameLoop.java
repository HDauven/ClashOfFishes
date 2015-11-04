package com.netgames.clashoffishes.engine;

import com.netgames.clashoffishes.Administration;
import com.netgames.clashoffishes.engine.object.events.FishHook;
import java.text.SimpleDateFormat;
import java.util.Random;
import javafx.animation.AnimationTimer;

/**
 * The game play loop.
 *
 * @author Hein Dauven
 */
public class GameLoop extends AnimationTimer
{

    private final long NANO_TO_SECOND;
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
    long prev = System.nanoTime();
    Random random = new Random();
    private final long startTime = System.nanoTime();
    private long secondsLeft;
    private long prevSeconds;
    int speedTimeCounter;

    private final int length_of_game = 600;
    private final boolean winCondition = false;

    /* A reference to the GameManager class. */
    protected GameManager gameManager;

    /**
     * Constructor for the GameLoop.
     *
     * @param manager GameManager class reference.
     */
    public GameLoop(GameManager manager)
    {
        this.NANO_TO_SECOND = 1_000_000_000;
        speedTimeCounter = 0;
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
    public void handle(long now)
    {
        // TODO
        gameManager.getPlayer().update();

        long elapsed = now - prev;
        int randInt = (int) (Math.random() * 1_000 + 1); // moet 10_000 zijn, 1_000 is om te testen
        //System.out.println(elapsed);
        if ((elapsed / NANO_TO_SECOND) > randInt)
        {
            gameManager.addRandomObject();
            //System.out.println(sdf.format(Calendar.getInstance().getTime()));
            //add object if randInt % 4 == 0 dit object else % 3 == 0 dat object etc
            prev = System.nanoTime();
        }
        fishHook();
        modeEvolutionOfTime(now);
    }

    /**
     * Starts the AnimationTimers. Once it is started, the handle(long) method
     * of this AnimationTimers will be called in every frame.
     */
    @Override
    public void start()
    {
        super.start();
    }

    /**
     * Stops the AnimationTimers.
     */
    @Override
    public void stop()
    {
        super.stop();
    }

    /**
     * Method that checks if the GameMode is Evolution of Time. If this is true,
     * the method goes on to check whether the game time has passed. If this is
     * true, it sets the game state to FINISHED, stops the game, shows a win or
     * lose screen and updates the score for the player.
     *
     * @param now
     */
    private void modeEvolutionOfTime(long now)
    {
        System.out.println(gameManager.getPlayer().getvX());
        System.out.println(gameManager.getPlayer().getvY());
        if (gameManager.getGameMode() == GameMode.EVOLUTION_OF_TIME)
        {
            long elapsed2 = now - startTime;
            secondsLeft = length_of_game - (elapsed2 / NANO_TO_SECOND);
            gameManager.setTimeLeft(String.valueOf(secondsLeft));
            if (secondsLeft == 0)
            {
                //Spel voorbij na 2 minuten
                if (gameManager.getGameState() != GameState.FINISHED)
                {
                    gameManager.setGameState(GameState.FINISHED);
                    this.stop();
                    hasWon();
                    Administration.get().getLoggedInUser().updateHighScore(gameManager.getGameMode(), gameManager.getGameScore());
                    //GuiUtilities.buildStage(gameManager.getStage().getScene().getWindow(), "GameHighscore", "Score");
                    System.out.println("Time is up!");
                }
            }
        }
        if(gameManager.getPlayer().getvX() == 2.5 && gameManager.getPlayer().getvY() == 2.5)
        {
            speedTimeCounter++;
            if(speedTimeCounter == 240)
            {
                gameManager.getPlayer().setvX(2);
                gameManager.getPlayer().setvY(2);
                speedTimeCounter = 0;
            }
        }
    }

    /**
     * Method that checks whether the game has finished and if the player has
     * won. Based on the win condition (true for win, false for lose), a
     * corresponding screen is shown.
     */
    private void hasWon()
    {
        if (gameManager.getGameState() == GameState.FINISHED)
        {
            if (winCondition == true)
            {
                gameManager.getRoot().getChildren().add(
                        gameManager.getGameMenu().getVictoryScreen());
            }
            else
            {
                gameManager.getRoot().getChildren().add(
                        gameManager.getGameMenu().getDefeatScreen());
            }
        }
    }
    
    /**
     * 
     */
    private void fishHook() {
        for (FishHook h : gameManager.getFishHooks())
        {
            h.update();
            if(h.getYLocation() < -5)
            {
                gameManager.removeFishHook(h);
                System.out.println(h.getYLocation());
            }
        }
    }
}
