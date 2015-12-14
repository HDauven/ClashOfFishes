package com.netgames.clashoffishes.server;

import com.netgames.clashoffishes.server.interfaces.ILobbyServerObserver;
import com.netgames.clashoffishes.server.remote.ILobby;
import com.netgames.clashoffishes.server.remote.IServer;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import javafx.application.Platform;

/**
 * Class that keeps track of all the lobbies that exist of this game.
 * Implementation of an IServer.
 * @author Hein Dauven
 */
public class Server implements IServer {
    // Version ID of the server
    private static final long serialVersionUID = 1L;
    
    private final RegistryServer registry;
    
    private List<ILobby> lobbies;
    
    /**
     * Constructor for the server
     * @param registry
     * @throws RemoteException 
     */
    public Server(RegistryServer registry) throws RemoteException {
        super();
        this.registry = registry;
        lobbies = new ArrayList<>();
    }

    @Override
    public void registerLobby(ILobby lobby) throws RemoteException {
        lobbies.add(lobby);
        for (ILobbyServerObserver guis : registry.getGUIs()) {
            guis.updateLobby(lobby);
        }
        registry.logMessage("Registered lobby: " + lobby.toString());
    }
    
    @Override
    public void removeLobby(ILobby lobby) throws RemoteException {
        lobbies.remove(lobby);
        for (ILobbyServerObserver guis : registry.getGUIs()) {
            guis.updateLobby(lobby);
        }
        registry.logMessage("Removed lobby: " + lobby.toString());
    }

    @Override
    public List<ILobby> listLobbies() throws RemoteException {
        return Collections.unmodifiableList(lobbies);
    }
    
    /**
     * Method that gets the last ILobby that was added.
     * @return The last ILobby the server added
     */
    public ILobby getLastLobby() {
        int lastIndex = lobbies.size();
        ILobby last = lobbies.get(lastIndex - 1);
        return last;
    }

    /**
     * Method that gets the list of ILobby's.
     * @return The ILobby list belonging to this server
     */
    public List<ILobby> getLobbies() {
        return lobbies;
    }
}
