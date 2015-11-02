/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netgames.clashoffishes.data;

import com.netgames.clashoffishes.User;
import com.netgames.clashoffishes.engine.GameMode;
import java.util.HashMap;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Stef
 */
public class DatabaseStorageTest
{

    User u1;
    User u2;
    private static DatabaseStorage dbStorage;
    HashMap<GameMode, Integer> scores = new HashMap<>();

    @Before
    public void setUp()
    {
        scores.put(GameMode.EVOLUTION_OF_TIME, 100);
        scores.put(GameMode.EVOLVED, 150);
        scores.put(GameMode.LAST_FISH_STANDING, 200);
        dbStorage = new DatabaseStorage();
    }

    @After
    public void tearDown()
    {
    }

    @AfterClass
    public static void tearDownAfterClass()
    {
        //Users weer verwijderen
        User u1 = dbStorage.getUser("Henk");
        User u2 = dbStorage.getUser("Piet");
        User u3 = dbStorage.getUser("Henkie");
        if (u1 != null)
        {
            dbStorage.removeUser(u1.getId());
        }
        if (u2 != null)
        {
            dbStorage.removeUser(u2.getId());
        }
        if (u3 != null)
        {
            dbStorage.removeUser(u3.getId());
        }
    }

    /**
     * Test of addUser method, of class DatabaseStorage.
     */
    @Test
    public void testAddUser()
    {
        dbStorage.addUser("Henk", "wachtwoord", "Henk@Henk.nl");
        dbStorage.addUser("Piet", "wachtwoord", "Piet@hotmail.com");
        dbStorage.addUser("henk", "wachtwoord1", "Henkie@Henkie.nl");// Mag niet worden toegevoegd, username bestaat al in db
        dbStorage.addUser("henkie", "wachtwoord", "henk@henk.nl");//Mag niet worden toegevoegd, email bestaat al in db
    }

    /**
     * Test of getUser method, of class DatabaseStorage.
     */
    @Test
    public void testGetUser()
    {
        assertNull(dbStorage.getUser("henkie"));
        User henk = dbStorage.getUser("henk");
        assertNotNull("User Henk moet bestaan.", henk);

        assertEquals("Email moet 'Henk@Henk.nl' zijn.", "Henk@Henk.nl", henk.getEmail());
        User piet = dbStorage.getUser("Piet");
        assertNotNull("Score moet 16 zijn.", piet.getHighscores());
    }

    /**
     * Test of logIn method, of class DatabaseStorage.
     */
    @Test
    public void testLogIn()
    {
        dbStorage.addUser("Henk", "wachtwoord", "Henk@Henk.nl");
        dbStorage.addUser("Piet", "wachtwoord", "Piet@hotmail.com");
        
        assertNull(dbStorage.logIn("henk", "verkeerdWW"));
        assertNull(dbStorage.logIn("henk@henk.nl", "verkeerdWW"));
        assertNull(dbStorage.logIn("henk", "wachtwoord1"));
        assertNull(dbStorage.logIn("henk", "WACHTWOORD"));
        assertNotNull(dbStorage.logIn("henk", "wachtwoord"));
        assertNotNull(dbStorage.logIn("henk@henk.nl", "wachtwoord"));
    }

    @Test
    public void testUpdateHighScores()
    {
        dbStorage.addUser("Henk", "wachtwoord", "Henk@Henk.nl");
        User henk = dbStorage.getUser("Henk");
        henk.updateHighScore(GameMode.EVOLVED, 15);
        henk.updateHighScore(GameMode.LAST_FISH_STANDING, 200);
        //henk.
        assertEquals("Score moet 0 zijn. ", (Integer) 0, henk.getHighscores().get(GameMode.EVOLUTION_OF_TIME));
        assertEquals("Score moet 15 zijn. ", (Integer) 15, henk.getHighscores().get(GameMode.EVOLVED));
        assertEquals("Score moet 200 zijn. ", (Integer) 200, henk.getHighscores().get(GameMode.LAST_FISH_STANDING));
        henk.updateHighScore(GameMode.EVOLVED, 15);
        assertEquals("Score moet 30 zijn. ", (Integer) 30, henk.getHighscores().get(GameMode.EVOLVED));
    }
}
