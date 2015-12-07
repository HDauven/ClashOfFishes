/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netgames.clashoffishes.server;

import com.netgames.clashoffishes.engine.GameState;
import com.netgames.clashoffishes.server.remote.IGameClient;
import com.netgames.clashoffishes.server.remote.IGameServer;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import javafx.application.Platform;

/**
 *
 * @author Stef
 */
public class GameServer extends Observable implements IGameServer
{
    // Version ID of the server
    private static final long serialVersionUID = 1L;
    
    private final LobbyRegistry registryServer;
    
    private List<IPlayer> players;
    
    /**
     * Constructor for the server
     * @param registry
     * @throws RemoteException 
     */
    public GameServer(LobbyRegistry registry) throws RemoteException {
        super();
        registryServer = registry;
        players = new ArrayList<>();
    }

    @Override
    public void registerUser(IPlayer player) throws RemoteException {
        players.add((IPlayer) player);
        setChanged();
        Platform.runLater(() -> { notifyObservers(); });
        registryServer.logMessage("Player added: " + player.toString());
    }
    
    @Override
    public void removeUser(IPlayer player) throws RemoteException {
        players.remove(player);
        setChanged();
        Platform.runLater(() -> { notifyObservers(); });
        registryServer.logMessage("Removed lobby: " + player.toString());
    }

    @Override
    public List<IPlayer> listUsers() throws RemoteException {
        
        return Collections.unmodifiableList(players);
    }
    
    /**
     * Method that gets the last ILobby that was added.
     * @return The last ILobby the server added
     */
    public IPlayer getLastLobby() {
        int lastIndex = players.size();
        IPlayer last = players.get(lastIndex - 1);
        return (IPlayer)last;
    }

    @Override
    public void registerClient(IGameClient client) throws RemoteException
    {
    }

    @Override
    public void updateMove(double speed, String key, boolean isPressed, double x, double y, int playerID) throws RemoteException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void collision(int playerID, int objectID) throws RemoteException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void stateChanged(GameState newState) throws RemoteException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Observable o, Object o1)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
