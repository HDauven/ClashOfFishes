package com.netgames.clashoffishes.server.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Interface that makes the defined methods available for users of the ILobby.
 *
 * @author Hein Dauven
 */
public interface ILobby extends Remote {

    /**
     * Method that checks whether an username is available or not.
     *
     * @param username the username to check
     * @return returns true when the username is available, returns false if the
     * username is in use
     * @throws RemoteException
     */
    boolean clientNameFree(String username)
            throws RemoteException;

    /**
     * Method that registers an IClient to the ILobby.
     *
     * @param client The IClient that is going to be added to the ILobby.
     * @throws RemoteException
     */
    void registerClient(IClient client)
            throws RemoteException;

    /**
     * Method that removes an IClient to the ILobby.
     *
     * @param client The IClient that is going to be removed from the ILobby.
     * @throws RemoteException
     */
    void removeClient(IClient client)
            throws RemoteException;

    /**
     * Method that broadcasts a message to all the IClients.
     *
     * @param message The message that is meant for all the IClients.
     * @param sender The client that send this message.
     * @throws RemoteException
     */
    void broadcastMessage(IMessage message, IClient sender)
            throws RemoteException;

    /**
     * Method that broadcasts a new player name to all clients
     * @param player The new player's name.
     * @param sender The new Client
     * @throws RemoteException 
     */
    void broadcastPlayer(String player, IClient sender)
            throws RemoteException;

    /**
     * Method that broadcast a change in character to all clients 
     * @param character The character that has been changed to.
     * @param sender The client who did the change.
     * @throws RemoteException 
     */
    void broadcastCharacter(String character, IClient sender)
            throws RemoteException;

    /**
     * Method that broadcasts a change in ready state to all clients
     * @param isReady The ready state that has been changed to.
     * @param sender The client who did the change.
     * @throws RemoteException 
     */
    void broadcastReady(boolean isReady, IClient sender)
            throws RemoteException;

    /**
     * A method that broadcasts a change in gameMode to all clients
     * @param gameMode The gameMode that has been changed to
     * @param sender The client who did the change.
     * @throws RemoteException 
     */
    void broadcastGameMode(String gameMode, IClient sender)
            throws RemoteException;

    /**
     * Method that gets all the messages from the IServer that were sent prior
     * to the IClient being connected to the IServer.
     *
     * @return returns a list of messages to the IClient.
     * @throws RemoteException
     */
    List<IMessage> getMessages()
            throws RemoteException;

    /**
     * Method that gets the list of available clients in the corresponding
     * lobby.
     *
     * @return returns a list of IClients
     * @throws RemoteException
     */
    List<IClient> getClients()
            throws RemoteException;

    /**
     * Return the lobby name
     * @return The lobby name.
     * @throws RemoteException 
     */
    String getPoolNameProperty()
            throws RemoteException;

    /**
     * Return all the players' names.
     * @return All the players' names in one string.
     * @throws RemoteException 
     */
    String getPlayersProperty()
            throws RemoteException;

    /**
     * Return the current set gameMode as String.
     * @return The current set gameMode as String.
     * @throws RemoteException 
     */
    String getGameModeProperty()
            throws RemoteException;

    /**
     * Return the current set gameMode.
     * @return The current set gameMode.
     * @throws RemoteException 
     */
    GameMode getGameMode()
            throws RemoteException;

    /**
     * A method to start the game.
     * @throws RemoteException 
     */
    void startGame()
            throws RemoteException;
    
    /**
     * A method that kicks all the players in the lobby.
     * @throws RemoteException 
     */
    void hostLeaves()
            throws RemoteException;
}
