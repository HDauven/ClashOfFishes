package com.netgames.clashoffishes.server;

import com.netgames.clashoffishes.server.remote.ILobby;
import com.netgames.clashoffishes.server.remote.IServer;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class that keeps track of all the lobbies that exist of this game.
 * Implementation of an IServer.
 * @author Hein Dauven
 */
public class Server extends UnicastRemoteObject implements IServer {
    // TODO recode for practical purposes, instead of system.out.println
    private static final long serialVersionUID = 1L;
    
    private final RegistryServer registryServer;
    
    private List<ILobby> lobbies;
    
    /**
     * Constructor for the server
     * @param registry
     * @throws RemoteException 
     */
    public Server(RegistryServer registry) throws RemoteException {
        super();
        registryServer = registry;
        lobbies = new ArrayList<>();
    }

    @Override
    public void registerLobby(ILobby lobby) throws RemoteException {
        lobbies.add(lobby);
        registryServer.logMessage("Registered lobby: " + lobby.toString());
    }
    
    @Override
    public void removeLobby(ILobby lobby) throws RemoteException {
        lobbies.remove(lobby);
        registryServer.logMessage("Removed lobby: " + lobby.toString());
    }

    @Override
    public List<ILobby> listLobbies() throws RemoteException {
        return Collections.unmodifiableList(lobbies);
    }
}
