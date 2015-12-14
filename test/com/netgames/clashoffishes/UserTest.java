/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netgames.clashoffishes;

import com.netgames.clashoffishes.engine.GameMode;
import java.util.HashMap;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Stef
 */
public class UserTest
{

    User u;

    @Before
    public void setUp()
    {
        u = new User(99, "Stef", "StefPhilipsen@gmail.com");
        u.setHighScore(GameMode.EVOLVED, 100);
        u.setHighScore(GameMode.EVOLUTION_OF_TIME, 200);
        u.setHighScore(GameMode.LAST_FISH_STANDING, 100);
    }

    @After
    public void tearDown()
    {
    }

    /**
     * Test of getUsername method, of class User.
     */
    @Test
    public void testGetUsername()
    {
        assertEquals("Username is niet goed ingevoerd", "Stef", u.getUsername());
    }

    /**
     * Test of getEmail method, of class User.
     */
    @Test
    public void testGetEmail()
    {
        assertEquals("Email is niet goed ingevoerd", "StefPhilipsen@gmail.com", u.getEmail());
    }

    /**
     * Test of getScore method, of class User.
     */
    @Test
    public void testGetScore()
    {
        assertEquals("Score is niet goed ingevoerd", 200, u.getScore(GameMode.EVOLUTION_OF_TIME));
    }

    @Test
    public void testUpdateScore()
    {
        u.updateHighScore(GameMode.EVOLUTION_OF_TIME, 50);
        assertEquals("Score moet nu  250 zijn.", 250, u.getScore(GameMode.EVOLUTION_OF_TIME));
        HashMap<GameMode, Integer> scores = u.getHighscores();
        assertEquals("Score moet 100 zijn.", new Integer(100), scores.get(GameMode.LAST_FISH_STANDING));
    }
}
