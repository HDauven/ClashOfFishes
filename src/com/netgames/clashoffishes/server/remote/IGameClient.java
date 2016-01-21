package com.netgames.clashoffishes.server.remote;

import com.netgames.clashoffishes.engine.GameState;
import com.netgames.clashoffishes.engine.object.events.ObjectType;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Stef Philipsen
 * @author Hein Dauven
 */
public interface IGameClient extends Remote {

    /**
     * Deze methode wordt aangeroepen als alle spelers klaar zijn en de game
     * gestart kan worden.
     * @throws RemoteException Als er iets fout gaat met RMI wordt deze exceptie
     * opgegooid.
     */
    public void startGame()
            throws RemoteException;

   
    /**
     * Deze methode wordt aangeroepen als er een verandering in de beweging van een vis wordt gemaakt.
     * @param speed De snelheid waarmee hij beweegt.
     * @param key De toets die ingedrukt is.
     * @param pressed Welke state is de toets in.
     * @param x x coordinaat.
     * @param y y coordinaat.
     * @param playerID De speler die de verandering maakt.
     * @throws RemoteException 
     */
    public void updateMove(double speed, String key, boolean pressed, double x, double y, int playerID)
            throws RemoteException;

    /**
     * Reports changes in the given players speed
     *
     * @param speed The speed at which the character is traveling.
     * @param playerID The player identifier.
     * @param reverseMovement Whether the movement of a player is reversed or not
     * @throws RemoteException
     */
    public void updateSpeed(double speed, int playerID, boolean reverseMovement) throws RemoteException;

    /**
     * Deze methode wordt aangeroepen als er op een andere client een collision
     * heeft plaatsgevonden. De server stuurt dit door naar de andere clients
     * zodat deze hun objecten kunnen updaten. De speler wordt geupdate en het
     * object verwijderd.
     *
     * @param id ID van de speler
     * @param objectId ID van het object wat verwijderd kan worden
     * @param playerScore Nieuwe score van de player die collision doorgeeft
     * @throws java.rmi.RemoteException Als er iets fout gaat met RMI wordt deze
     * exceptie opgegooid.
     */
    public void collisionUpdate(int id, int objectId, int playerScore)
            throws RemoteException;

    /**
     * Deze methode creert een object op het coordinaat (x, y) van het type dat
     * meegegeven is.
     *
     * @param id Het gewenste objectID.
     * @param x Het gewenste x-coordinaat
     * @param y Het gewenste y-coordinaat
     * @param objectType Het object-type dat aangemaakt wordt.
     * @throws java.rmi.RemoteException Als er iets fout gaat met RMI wordt deze
     * exceptie opgegooid.
     */
    public void objectCreation(int id, int x, int y, ObjectType objectType)
            throws RemoteException;

    /**
     * Verwijdert een object aan de client side
     * @param id Het geweste objectID
     * @throws RemoteException 
     */
    public void receiveObjectDeletion (int id) 
            throws RemoteException;

    
    /**
     * Haal de character naam van de GameClient op.
     * @return De character naam van de GameClient.
     * @throws RemoteException 
     */
    public String getCharacterName()
            throws RemoteException;

    /**
     * Haal de playerID van de GameClient op.
     * @return De playerID van de GameClient.
     * @throws RemoteException 
     */
    public int getPlayerID()
            throws RemoteException;

    /**
     * Changes the state of the game on the client side.
     *
     * @param gameState De nieuwe GameState.
     * @throws RemoteException
     */
    void changeGameState(GameState gameState)
            throws RemoteException;
    
}
