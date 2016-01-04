package com.netgames.clashoffishes.server;

import com.netgames.clashoffishes.engine.object.GameObject;

/**
 *
 * @author Stef Philipsen
 */
public interface IPlayer {

    int getID();

    void update();

    void setXYLocation();

    public void checkCollision();

    void setImageState();

    void collide(GameObject object);

    void scoringEngine(GameObject object);

    void movePlayer(double x, double y);

    void setBoundaries();
}
