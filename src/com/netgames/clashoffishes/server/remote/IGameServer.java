package com.netgames.clashoffishes.server.remote;

import com.netgames.clashoffishes.engine.GameState;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * The IGameServer interface for RMI communication between clients and servers
 * for Clash of Fishes.
 *
 * @author Christian Adkin
 * @author Hein Dauven
 */
public interface IGameServer extends Remote {

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
     * Reports changes in the given players speed
     *
     * @param speed The speed at which the character is traveling.
     * @param playerID The player identifier.
     * @throws RemoteException
     */
    public void updateSpeed(double speed, int playerID) throws RemoteException;

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
     *
     * @throws RemoteException
     */
    public void start() throws RemoteException;

    /**
     * Returns a list with all GameClients registered on the GameServer.
     * @return a list of all GameClients.
     * @throws RemoteException 
     */
    public List<IGameClient> getClients()
            throws RemoteException;

    /**
     * Stuur een bericht naar alle clients dat de objecten verwijdert moeten worden.
     * @param id het object id
     * @throws RemoteException 
     */
    public void deleteObject (int id) 
            throws RemoteException;
    
    public void updateScore (int score, int playerID)
            throws RemoteException;
}
