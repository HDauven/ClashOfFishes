/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netgames.clashoffishes.server;

import com.netgames.clashoffishes.Administration;
import com.netgames.clashoffishes.engine.GameManager;
import com.netgames.clashoffishes.engine.object.Player;
import com.netgames.clashoffishes.engine.object.events.ObjectType;
import com.netgames.clashoffishes.server.remote.IGameClient;
import com.netgames.clashoffishes.server.remote.IGameServer;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import javafx.application.Platform;
import javafx.stage.Stage;

/**
 *
 * @author Stef
 */
public class GameClient extends UnicastRemoteObject implements IGameClient
{
    private String username;
    private String characterName;
    private int mapseed;
    private int playerID;
    
    private IGameServer gameServer;
    
    private GameManager gameManager;
    
    public GameClient(String username, String characterName, int mapseed, int playerID, IGameServer gameServer) throws RemoteException {
        this.username = username;
        this.characterName = characterName;
        this.mapseed = mapseed;
        this.playerID = playerID;
        
        this.gameServer = gameServer;
        
        gameServer.registerClient(this);
    }
    
    public String getUsername(){
        return this.username;
    }

    @Override
    public void startGame() throws RemoteException
    {
        //Niet zeker of dit klopt
        //Administration.get().getLobbyRegistry().startGameServer();
        this.gameManager = new GameManager(this.characterName, this.mapseed, this.playerID);
        Platform.runLater(() -> { 
            gameManager.start(new Stage()); 
        });
    }

    @Override
    public void updateMove(double speed, String key, boolean pressed, double x, double y, int playerID)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void collisionUpdate(int id, int objectId) throws RemoteException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void objectCreation(int x, int y, ObjectType objectType) throws RemoteException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getCharacterName() throws RemoteException {
        return this.characterName;
    }

    @Override
    public int getPlayerID() throws RemoteException {
        return this.playerID;
    }

}
