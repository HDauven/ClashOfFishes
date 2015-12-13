package com.netgames.clashoffishes.server.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface that makes the defined methods available for users of the IClient.
 * @author Hein Dauven
 */
public interface IClient extends Remote {  
    /**
     * Method that handles an incoming IMessage from the ILobby.
     * @param message The IMessage as is being received from the IServer
     * @throws RemoteException 
     */
    void retrieveMessage(IMessage message, IClient sender) 
            throws RemoteException;
    
    void retrievePlayer(String player, IClient sender) 
            throws RemoteException;
    
    void retrieveCharacter(String character, IClient sender) 
            throws RemoteException;
    
    void retrieveReady(boolean isReady, IClient sender) 
            throws RemoteException;
    
    void retrieveGameMode(String gameMode, IClient sender) 
            throws RemoteException;
    
    /**
     * Method that gets the Username of an IClient.
     * @return The IClient Username
     * @throws RemoteException 
     */
    String getUsername()
            throws RemoteException;

    public void setIsReady(boolean ready)
            throws RemoteException;
    
    /**
     * Method that gets the isReady of an IClient.
     * @return The IClient isReady
     * @throws RemoteException 
     */
    boolean getIsReady()
            throws RemoteException;
    
    String getCharacter()
            throws RemoteException;

    void setCharacter(String character)
            throws RemoteException;
}
