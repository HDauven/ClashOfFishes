/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netgames.clashoffishes.server;

import com.netgames.clashoffishes.engine.GameManager;
import com.netgames.clashoffishes.engine.GameState;
import com.netgames.clashoffishes.engine.object.Player;
import com.netgames.clashoffishes.server.remote.IGameClient;
import com.netgames.clashoffishes.server.remote.IGameServer;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import javafx.application.Platform;

/**
 *
 * @author Stef
 */
public class GameServer extends UnicastRemoteObject implements IGameServer
{
    private transient final Lobby lobby;
    
    private List<IGameClient> clients;
    
    GameManager gameManager = new GameManager();
    
    /**
     * Constructor for the server
     * @param registry
     * @throws RemoteException 
     */
    public GameServer(Lobby lobby) throws RemoteException {
        super();
        this.lobby = lobby;
        clients = new ArrayList<>();
    }


    @Override
    public void registerClient(IGameClient client) throws RemoteException
    {
        clients.add(client);
    }

    @Override
    public void updateMove(double speed, String key, boolean isPressed, double x, double y, int playerID) throws RemoteException
    {
        //TODO
    }

    @Override
    public void collision(int playerID, int objectID) throws RemoteException
    {
        //TODO
    }

    @Override
    public void stateChanged(GameState newState) throws RemoteException
    {
        //TODO
    }

    @Override
    public void start() throws RemoteException
    {
        for(IGameClient gameClient : this.clients){
            gameClient.startGame();
        }
    }

    @Override
    public void broadcastPlayer(Player player) throws RemoteException {
        for(IGameClient gameClient : this.clients){
            gameClient.recievePlayer(player);
        }
    }
}
