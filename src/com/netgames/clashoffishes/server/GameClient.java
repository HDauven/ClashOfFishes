/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netgames.clashoffishes.server;

import com.netgames.clashoffishes.engine.object.events.ObjectType;
import com.netgames.clashoffishes.server.remote.IGameClient;
import java.rmi.RemoteException;

/**
 *
 * @author Stef
 */
public class GameClient implements IGameClient
{
    private String username;
    
    public GameClient(String username){
        this.username = username;
    }
    
    public String getUsername(){
        return this.username;
    }

    @Override
    public void startGame(Integer mapSeed) throws RemoteException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
}
