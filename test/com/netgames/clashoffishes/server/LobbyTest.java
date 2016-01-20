/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netgames.clashoffishes.server;

import com.netgames.clashoffishes.User;
import com.netgames.clashoffishes.engine.GameMode;
import com.netgames.clashoffishes.server.remote.ILobby;
import java.rmi.RemoteException;
import java.util.HashMap;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author Stef
 */
public class LobbyTest {

    User u;
    User u2;
    ILobby lobby;
    HashMap<GameMode, Integer> scores = new HashMap<>();
    Client c;
    Client c2;

    public void setUp() throws RemoteException {
        this.lobby = new Lobby();
        c = new Client("Stef", true, lobby);
        c2 = new Client("Henk", false, lobby);
        scores.put(GameMode.EVOLVED, 100);
        scores.put(GameMode.EVOLUTION_OF_TIME, 200);
        scores.put(GameMode.LAST_FISH_STANDING, 100);
        u = new User(99, "Stef", "StefPhilipsen@gmail.com");
        u2 = new User(100, "Henk", "Henkie@hotmail.com");
        u.setHighScore(GameMode.EVOLVED, 100);
        u.setHighScore(GameMode.EVOLUTION_OF_TIME, 200);
        u.setHighScore(GameMode.LAST_FISH_STANDING, 100);
        u2.setHighScore(GameMode.EVOLVED, 100);
        u2.setHighScore(GameMode.EVOLUTION_OF_TIME, 200);
        u2.setHighScore(GameMode.LAST_FISH_STANDING, 100);
    }

    /**
     * Test of registerClient method, of class Lobby.
     */
    @Test
    public void testRegisterClient() throws Exception {
        lobby.registerClient(c);
        assertEquals(1, lobby.getClients().size());
        lobby.registerClient(c2);
        assertEquals(2, lobby.getClients().size());
        lobby.registerClient(c);
        assertEquals("Gebruiker mag niet 2 keer toegevoegd worden", 2, lobby.getClients().size());
        this.testRemoveClient();
    }

    /**
     * Test of removeClient method, of class Lobby.
     */
    public void testRemoveClient() throws Exception {
        
        lobby.removeClient(c);
        assertEquals(1, lobby.getClients().size());
        lobby.removeClient(c);
        assertEquals(1, lobby.getClients().size());
        lobby.removeClient(c2);
        assertEquals(0, lobby.getClients().size());
    }

    /**
     * Test of broadcastMessage method, of class Lobby.
     */
    @Test
    public void testBroadcastMessage() throws Exception {
        lobby.registerClient(c);
        lobby.broadcastMessage(new Message(c.getUsername(), "Hallo"), c);
        assertEquals("Bericht moet toegevoegd worden", 1, lobby.getMessages().size());
        lobby.broadcastMessage(new Message("asdf", "asdf"), c);
        assertEquals("Gebruikersnaam 'asdf' zit niet in deze game.", 1, lobby.getMessages().size());
        lobby.broadcastMessage(new Message("Stef", "asdfasdf"), null);
        assertEquals("Client null kan geen afzender zijn", 1, lobby.getMessages().size());
    }

    /**
     * Test of broadcastPlayer method, of class Lobby.
     */
    @Test
    public void testBroadcastPlayer() throws Exception {
        lobby.broadcastPlayer("Stef", c);
    }

    /**
     * Test of broadcastCharacter method, of class Lobby.
     */
    @Test
    public void testBroadcastCharacter() throws Exception {
        lobby.broadcastCharacter("Fred", c);
    }

    /**
     * Test of broadcastReady method, of class Lobby.
     */
    @Test
    public void testBroadcastReady() throws Exception {
        lobby.broadcastReady(true, c);
        assertTrue(lobby.getClients().get(0).getIsReady());
    }

    /**
     * Test of broadcastGameMode method, of class Lobby.
     */
    @Test
    public void testBroadcastGameMode() throws Exception {
        lobby.broadcastGameMode(GameMode.EVOLUTION_OF_TIME.toString(), c);
        assertEquals(lobby.getGameMode(), GameMode.EVOLUTION_OF_TIME);
        assertEquals(lobby.getGameModeProperty(), GameMode.EVOLUTION_OF_TIME.toString());
        lobby.broadcastGameMode(GameMode.EVOLVED.toString(), c);
        assertEquals(lobby.getGameMode(), GameMode.EVOLVED);
        assertEquals(lobby.getGameModeProperty(), GameMode.EVOLVED.toString());
        lobby.broadcastGameMode(GameMode.LAST_FISH_STANDING.toString(), c2);
        Assert.assertNotSame("Alleen host mag de game-mode veranderen", GameMode.LAST_FISH_STANDING, lobby.getGameMode());
        Assert.assertNotSame("Alleen host mag de game-mode veranderen", GameMode.LAST_FISH_STANDING.toString(), lobby.getGameModeProperty());
    }

    /**
     * Test of getLobbyTitle method, of class Lobby.
     */
    @Test
    public void testGetLobbyTitle() throws RemoteException {
        assertEquals(c.getUsername() + "'s lobby", lobby.getPoolNameProperty());
    }

    /**
     * Test of getUsersString method, of class Lobby.
     */
    @Test
    public void testGetUsersString() throws RemoteException {
        Assert.assertNotSame("ERROR GETTING CLIENT USERNAMES", lobby.getPlayersProperty());
    }

    /**
     * Test of getClients method, of class Lobby.
     */
    @Test
    public void testGetClients() throws Exception {
        
    }

    /**
     * Test of getMessages method, of class Lobby.
     */
    @Test
    public void testGetMessages() throws Exception {
        lobby.broadcastMessage(new Message("Stef", "Hallo"), c);
        lobby.broadcastMessage(new Message("Stef", "Hallo2"), c);
        assertEquals(lobby.getMessages().size(), 2);
    }

    /**
     * Test of startGame method, of class Lobby.
     */
    @Test
    public void testStartGame() {
        
    }

    /**
     * Test of hostLeaves method, of class Lobby.
     */
    @Test
    public void testHostLeaves() throws Exception {
        lobby.hostLeaves();
        assertEquals(lobby.getClients().size(), 0);
    }

}
