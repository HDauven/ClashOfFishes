/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netgames.clashoffishes;

import com.netgames.clashoffishes.data.DatabaseStorage;
import com.netgames.clashoffishes.engine.GameMode;
import java.util.ArrayList;
import org.junit.After;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Stef
 */
public class AdministrationTest
{

    DatabaseStorage dbStorage;

    @Before
    public void setUp()
    {
        Administration.get().clear();
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
        assertNull("User bestaat niet, dus moet null zijn", Administration.get().logIn("asdf", "asdf"));
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

    @Test
    public void testSet_GetCurrentLobby()
    {
        Lobby lobby = new Lobby();
        Administration.get().setCurrentLobby(lobby);
        assertNotNull("Lobby kan geen null zijn", Administration.get().getCurrentLobby());
    }

    @Test
    public void testGetLoggedInUser()
    {
        User u = Administration.get().getLoggedInUser();
        assertNotNull("Ingelogde users kan geen null zijn.", u);
    }
}
