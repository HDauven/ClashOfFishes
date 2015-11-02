/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netgames.clashoffishes;

import com.netgames.clashoffishes.engine.GameManager;
import com.netgames.clashoffishes.engine.GameMode;
import java.util.HashMap;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Stef
 */
public class LobbyTest
{

    User u;
    User u2;
    HashMap<GameMode, Integer> scores = new HashMap<>();

    @Before
    public void setUp()
    {
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

    @After
    public void tearDown()
    {
    }

    /**
     * Test of addUser method, of class Lobby.
     */
    @Test
    public void testAddUser()
    {
        Lobby lobby = new Lobby();
        lobby.addUser(u);
        assertEquals("Eerste persoon is toegevoegd, dus size moet 1 zijn", 1, lobby.getUsers().size());
        lobby.addUser(null);
        assertEquals(1, lobby.getUsers().size());
        lobby.addUser(u);
        assertEquals(1, lobby.getUsers().size());
        lobby.addUser(u2);
        assertEquals(2, lobby.getUsers().size());
    }

    /**
     * Test of removeUser method, of class Lobby.
     */
    @Test
    public void testRemoveUser()
    {
        Lobby lobby = new Lobby();
        lobby.addUser(u);
        lobby.removeUser(u2);
        assertEquals("De toegevoegde user mag niet verwijderd zijn.", 1, lobby.getUsers().size());
        lobby.addUser(u2);
        lobby.removeUser(u);
        assertEquals("De user 'Stef' moet verwijderd zijn.", 1, lobby.getUsers().size());
        assertEquals("Naam van enige user moet Henk zijn", "Henk", lobby.getUsers().get(0).getUsername());
    }

    /**
     * Test of userIsReady method, of class Lobby.
     */
    @Test
    public void testUserIsReady_testIsUserReady()
    {
        Lobby lobby = new Lobby();
        lobby.addUser(u);
        lobby.addUser(u2);
        lobby.userIsReady(u, true);
        assertTrue("u moet ready zijn.", lobby.isUserReady(u));
        lobby.userIsReady(u, false);
        assertFalse("u moet niet ready zijn", lobby.isUserReady(u));
    }

    /**
     * Test of isEveryoneReady method, of class Lobby.
     */
    @Test
    public void testIsEveryoneReady()
    {
        Lobby lobby = new Lobby();
        lobby.addUser(u);
        lobby.addUser(u2);
        lobby.userIsReady(u, true);
        assertFalse("u2 is nog niet ready.", lobby.isEveryoneReady());
        lobby.userIsReady(u2, true);
        assertTrue("Alle users zijn ready.", lobby.isEveryoneReady());
    }

    /**
     * Test of getSelectedGameMode method, of class Lobby.
     */
    @Test
    public void testGetSelectedGameMode_testSetSelectedGameMode()
    {
        Lobby lobby = new Lobby();
        lobby.setGameMode(GameMode.EVOLVED);
        assertEquals("GameMode", GameMode.EVOLVED, lobby.getGameMode());
    }

    /**
     * Test of createGame method, of class Lobby.
     */
    @Test
    public void testCreateGame()
    {
        Lobby lobby = new Lobby();
        GameManager gm = lobby.createGame();
        assertNotNull("Lobby moet aangemaakt zijn.", gm);
    }

}
