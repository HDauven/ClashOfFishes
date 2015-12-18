package com.netgames.clashoffishes.server.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Interface that makes the defined methods available for users of the IServer.
 *
 * @author Hein Dauven
 */
public interface IServer extends Remote {

    /**
     * Method that registers an ILobby to the IServer.
     *
     * @param lobby The ILobby that is going to be added to the IServer.
     * @throws RemoteException Warn that the registration has failed.
     */
    void registerLobby(ILobby lobby)
            throws RemoteException;

    /**
     * Method that removes the given ILobby from the IServer.
     *
     * @param lobby The ILobby that is going to be removed from the IServer.
     * @throws RemoteException Warn that the removal of this ILobby has failed.
     */
    void removeLobby(ILobby lobby)
            throws RemoteException;

    /**
     * Method that gets all the ILobbies that are registered with the IServer.
     *
     * @return returns a list of messages to the IClient.
     * @throws RemoteException
     */
    List<ILobby> listLobbies()
            throws RemoteException;
}
