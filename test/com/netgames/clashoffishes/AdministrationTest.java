package com.netgames.clashoffishes;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.netgames.clashoffishes.Administration;
import com.netgames.clashoffishes.Highscore;
import com.netgames.clashoffishes.User;
import com.netgames.clashoffishes.data.DatabaseStorage;
import com.netgames.clashoffishes.engine.GameMode;
import com.netgames.clashoffishes.server.Client;
import com.netgames.clashoffishes.server.GameClient;
import com.netgames.clashoffishes.server.GameServer;
import com.netgames.clashoffishes.server.Lobby;
import com.netgames.clashoffishes.server.LobbyRegistry;
import java.rmi.RemoteException;
import java.util.ArrayList;
import org.junit.After;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Stef
 */
public class AdministrationTest {
    
DatabaseStorage dbStorage;

    @Before
    public void setUp()
    {
        Administration.get().clear();
        Administration.get().logIn("Admin", "admin");
        dbStorage = new DatabaseStorage();
        User u1 = dbStorage.getUser("Stef");
        User u2 = dbStorage.getUser("Henk");
        if(u1 != null){
            dbStorage.removeUser(u1.getId());
        }
        if(u2 != null){
            dbStorage.removeUser(u2.getId());
        }
    }

    @After
    public void tearDown()
    {
    }

    /**
     * Test of addUser method, of class Administration.
     */
    @Test
    public void testAddUser()
    {
        Administration.get().clear();
        assertNotNull("User moet worden toegevoegd", Administration.get().addUser("Stef", "wachtwoord", "StefPhilipsen@gmail.com"));
        assertFalse("Username bestaat al", Administration.get().addUser("Stef", "wachtwoord", "user1@gmail.com"));
        assertFalse("Email bestaat al", Administration.get().addUser("User1", "wachtwoord", "StefPhilipsen@gmail.com"));
        assertFalse("Naam mag niet leeg zijn", Administration.get().addUser("", "wachtwoord", "leeg@gmail.com"));
        assertFalse("Wachtwoord mag niet leeg zijn.", Administration.get().addUser("Piet", "", "Stef@Stef.nl"));

        //Geldig email-adres testen
        assertFalse("Geen geldig emailadres ingevoerd, moet null zijn", Administration.get().addUser("Henk", "wachtwoord", "geenApenstaartje"));
        assertFalse("Geen geldig emailadres ingevoerd, moet null zijn", Administration.get().addUser("Henk", "wachtwoord", "@aa"));
        assertFalse("Geen geldig emailadres ingevoerd, moet null zijn", Administration.get().addUser("Henk", "wachtwoord", "aa@"));
        assertFalse("Geen geldig emailadres ingevoerd, moet null zijn", Administration.get().addUser("Henk", "wachtwoord", "a@a"));
        assertFalse("Geen geldig emailadres ingevoerd, moet null zijn", Administration.get().addUser("Henk", "wachtwoord", "aa@aa."));
    }

    /**
     * Test of getUser method, of class Administration.
     */
    @Test
    public void testGetUser()
    {
        //Username, password, email
        assertNotNull("User moet worden toegevoegd", Administration.get().addUser("Stef", "wachtwoord", "StefPhilipsen@gmail.com"));
        assertNull("User 'Henk' bestaat niet, dus moet null zijn", Administration.get().getUser("Henk"));
        assertNull("User '' bestaat niet, dus moet null zijn", Administration.get().getUser(""));
        assertNotNull("User 'Stef' bestaat", Administration.get().getUser("Stef"));
    }

    /**
     * Test of get method, of class Administration.
     */
    @Test
    public void testGet()
    {
        assertNotNull("Administration mag nooit null zijn.", Administration.get());
    }

    @Test
    public void testLogIn()
    {
        assertNull("User bestaat niet, dus moet null zijn", Administration.get().logIn("asdfasdfasdfasdfasdfaweraerawserfds", "kjhljkl"));
        assertNotNull("User bestaat, dus mag geen null zijn.", Administration.get().logIn("Admin", "admin"));
    }

    @Test
    public void testGetAllUserHighScoresForGameMode()
    {
        ArrayList<Highscore> l = Administration.get().getAllUserHighscoresForGameMode(GameMode.EVOLVED);
        assertFalse("Lijst mag niet leeg zijn.", l.isEmpty());
    }

    @Test
    public void testgetUser()
    {
        assertNotNull("User moet worden toegevoegd", Administration.get().addUser("Stef", "wachtwoord", "StefPhilipsen@gmail.com"));
        User u = Administration.get().getUser("Stef");
        assertNotNull("Opgehaalde user 'Stef' mag niet null zijn.", u);
    }

//    @Test
//    public void testSet_GetCurrentLobby()
//    {
//        __Lobby lobby = new __Lobby();
//        Administration.get().setCurrentLobby(lobby);
//        assertNotNull("Lobby kan geen null zijn", Administration.get().getCurrentLobby());
//    }

    @Test
    public void testGetLoggedInUser()
    {
        //Administration.get().logIn("Admin", "admin");
        User u = Administration.get().getLoggedInUser();
        assertNotNull("Ingelogde users kan geen null zijn.", u);
    }
    
        /**
     * Test of hasConnection method, of class Administration.
     */
    @Test
    public void testHasConnection() {
        Assert.assertTrue(Administration.get().hasConnection());
    }

    /**
     * Test of nextObjectNr method, of class Administration.
     */
    @Test
    public void testNextObjectNr() {
        int nr = Administration.get().nextObjectNr();
        assertTrue(nr > 0);
        assertEquals(nr + 1, Administration.get().nextObjectNr());
    }

    /**
     * Test of getIpAddress method, of class Administration.
     */
    @Test
    public void testGetIpAddress() {
        assertTrue(Administration.get().getIpAddress().length() > 7);//13
    }

    /**
     * Test of resetClient method, of class Administration.
     */
    @Test
    public void testResetClient() throws RemoteException {
        //Administration.get().logIn("Admin", "admin");
        Lobby l = new Lobby();
        Client c = new Client("Stef", true, l);
        Administration.get().setClient(c);
        assertNotNull(Administration.get().getClient());
        Administration.get().resetClient();
        assertNull(Administration.get().getClient());
    }

    /**
     * Test of resetLobby method, of class Administration.
     */
    @Test
    public void testResetLobby() throws RemoteException {
        //Administration.get().logIn("Admin", "admin");
        Administration.get().setLobby(new Lobby());
        assertNotNull(Administration.get().getLobby());
        Administration.get().resetLobby();
        assertNull(Administration.get().getLobby());
    }

    /**
     * Test of resetLobbyRegistry method, of class Administration.
     */
    @Test
    public void testResetLobbyRegistry() {
        //Administration.get().logIn("Admin", "admin");
        Administration.get().setLobbyRegistry(new LobbyRegistry());
        assertNotNull(Administration.get().getLobbyRegistry());
        Administration.get().resetLobbyRegistry();
        assertNull(Administration.get().getLobbyRegistry());
    }
    
    @Test
    public void testSetGameClient() throws RemoteException{
        //Administration.get().logIn("Admin", "admin");
        assertNull(Administration.get().getGameClient());
        Administration.get().setGameClient(new GameClient("Admin", "Cleo", 123, 1, new GameServer(new Lobby()), GameMode.EVOLVED));
        assertNotNull(Administration.get().getGameClient());
    }
    
    @Test
    public void testSetGameServer() throws RemoteException{
        //Administration.get().logIn("Admin", "admin");
        assertNull(Administration.get().getGameServer());
        Administration.get().setGameServer(new GameServer(new Lobby()));
        assertNotNull(Administration.get().getGameServer());
    }
}