package com.netgames.clashoffishes.engine;

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

    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
    long prev = System.nanoTime();

    protected GameManager gameManager;

    public GameLoop(GameManager manager)
    {
        gameManager = manager;
    }

    @Override
    public void handle(long now)
    {
        // TODO
        gameManager.getPlayer().update();

        Random random = new Random();
        long elapsed = now - prev;
        int randInt = (int) (Math.random() * 10000 + 1);
        //System.out.println(elapsed);
        if ((elapsed / 1000000000) > randInt)
        {
            gameManager.addRandomObject();
            //System.out.println(sdf.format(Calendar.getInstance().getTime()));
            //add object if randInt % 4 == 0 dit object else % 3 == 0 dat object etc
            prev = System.nanoTime();
        }
    }

    @Override
    public void start()
    {
        super.start();
    }

    @Override
    public void stop()
    {
        super.stop();
    }
}
