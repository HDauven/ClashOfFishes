/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netgames.clashoffishes.server.remote;

import com.netgames.clashoffishes.engine.GameState;
import com.netgames.clashoffishes.engine.object.events.ObjectType;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Stef
 */
public interface IGameClient extends Remote {

    /**
     * Deze methode wordt aangeroepen als alle spelers klaar zijn en de game
     * gestart kan worden.
     *
     * @param mapSeed variable zodat de map op elke client hetzelfde weergegeven
     * wordt
     * @throws RemoteException Als er iets fout gaat met RMI wordt deze exceptie
     * opgegooid.
     */
    public void startGame()
            throws RemoteException;

    /**
     * Deze methode wordt aangeroepen als de positie, snelheid of richting van
     * een object veranderd is, zodat de client het object kan updaten.
     *
     * @param id ID van het object welke veranderd is
     * @param positie de nieuwe positie van het object
     * @param speed de nieuwe snelheid van het object
     * @param richting de nieuwe richting van het object
     * @throws java.rmi.RemoteException Als er iets fout gaat met RMI wordt deze
     * exceptie opgegooid.
     */
    public void updateMove(double speed, String key, boolean pressed, double x, double y, int playerID)
            throws RemoteException;

    /**
     * Deze methode wordt aangeroepen als er op een andere client een collision
     * heeft plaatsgevonden. De server stuurt dit door naar de andere clients
     * zodat deze hun objecten kunnen updaten. De speler wordt geupdate en het
     * object verwijderd.
     *
     * @param id ID van de speler
     * @param objectId ID van het object wat verwijderd kan worden
     * @throws java.rmi.RemoteException Als er iets fout gaat met RMI wordt deze
     * exceptie opgegooid.
     */
    public void collisionUpdate(int id, int objectId)
            throws RemoteException;

    /**
     * Deze methode creert een object op het coordinaat (x, y) van het type dat
     * meegegeven is.
     *
     * @param x Het gewenste x-coordinaat
     * @param y Het gewenste y-coordinaat
     * @param objectType Het object-type dat aangemaakt wordt.
     * @throws java.rmi.RemoteException Als er iets fout gaat met RMI wordt deze
     * exceptie opgegooid.
     */
    public void objectCreation(int x, int y, ObjectType objectType)
            throws RemoteException;
    
    /**
     * Changes the state of the game on the client side.
     * @param gameState
     * @throws RemoteException 
     */
    void changeGameState(GameState gameState)
            throws RemoteException;
}
