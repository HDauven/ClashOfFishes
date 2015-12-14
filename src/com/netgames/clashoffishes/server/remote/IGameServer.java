/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netgames.clashoffishes.server.remote;

import com.netgames.clashoffishes.engine.GameState;
import com.netgames.clashoffishes.server.IPlayer;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Observer;

/**
 * The IGameServer interface for RMI communication between clients and servers
 * for Clash of Fishes.
 *
 * @author Christian Adkin
 */
public interface IGameServer extends Remote
{

    /**
     * Register a client as listener to the IGameServer
     *
     * @param client The game client
     * @throws RemoteException
     */
    public void registerClient(IGameClient client) throws RemoteException;

    /**
     * Change in the movement of a player
     *
     * @param speed The speed at which the character is traveling.
     * @param key The key that has been pressed, we use this to derive the
     * direction.
     * @param isPressed True = Key press | False = Key not pressed.
     * @param x Player x position.
     * @param y Player y position.
     * @param playerID The player identifier.
     * @throws RemoteException
     */
    public void updateMove(double speed, String key, boolean isPressed, double x, double y, int playerID) throws RemoteException;

    /**
     * A Collision has occurred.
     *
     * @param playerID The player identifier that caused the collision.
     * @param objectID The object identifier that the player has collided with
     * @throws RemoteException
     */
    public void collision(int playerID, int objectID) throws RemoteException;

    /**
     * The game state has been changed
     *
     * @param newState The new state the game has been changed to.
     * @throws RemoteException
     */
    public void stateChanged(GameState newState) throws RemoteException;

    /**
     * Starts the game on all clients
     * @throws RemoteException 
     */
    public void start() throws RemoteException;
}
