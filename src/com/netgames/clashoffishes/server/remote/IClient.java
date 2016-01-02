package com.netgames.clashoffishes.server.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface that makes the defined methods available for users of the IClient.
 *
 * @author Hein Dauven
 */
public interface IClient extends Remote {

    /**
     * Method that handles an incoming IMessage from the ILobby.
     *
     * @param message The IMessage as is being received from the IServer
     * @throws RemoteException
     */
    void retrieveMessage(IMessage message, IClient sender)
            throws RemoteException;

    /**
     * Method that handles an incoming change in players in the lobby.
     * @param player The name of the player who joined the lobby.
     * @param sender The IClient who sent this change.
     * @throws RemoteException 
     */
    void retrievePlayer(String player, IClient sender)
            throws RemoteException;

    /**
     * Method that handles an incoming change in characters in the lobby.
     * @param character The character that has changed
     * @param sender The IClient who sent this change.
     * @throws RemoteException 
     */
    void retrieveCharacter(String character, IClient sender)
            throws RemoteException;

    /**
     * Method that handles an incoming change in ready state in the lobby.
     * @param isReady The value the ready state has been set to
     * @param sender The IClient who sent this change.
     * @throws RemoteException 
     */
    void retrieveReady(boolean isReady, IClient sender)
            throws RemoteException;

    /**
     * Method that handles an incoming change in gameMode in the lobby.
     * @param gameMode The gameMode the game has been set to
     * @param sender The IClient who sent this change.
     * @throws RemoteException 
     */
    void retrieveGameMode(String gameMode, IClient sender)
            throws RemoteException;

    /**
     * Method that gets the Username of an IClient.
     *
     * @return The IClient Username
     * @throws RemoteException
     */
    String getUsername()
            throws RemoteException;

    /**
     * The method sets the clients new ready state.
     * @param ready The ready state for the client.
     * @throws RemoteException 
     */
    public void setIsReady(boolean ready)
            throws RemoteException;

    /**
     * Method that gets the isReady of an IClient.
     *
     * @return The IClient isReady
     * @throws RemoteException
     */
    boolean getIsReady()
            throws RemoteException;

    /**
     * Return the Clients character name
     * @return the clients character name
     * @throws RemoteException 
     */
    String getCharacter()
            throws RemoteException;

    /**
     * Set this clients character name
     * @param character The new character name
     * @throws RemoteException 
     */
    void setCharacter(String character)
            throws RemoteException;

    /**
     * Create a GameClient on the remote GameServer with the seed and playerID.
     * @param gameServer Remote reference to the GameServer
     * @param seed The map seed.
     * @param playerID The player ID this client is assigned.
     * @throws RemoteException 
     */
    void createGameClient(IGameServer gameServer, int seed, int playerID)
            throws RemoteException;
}
