/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netgames.clashoffishes;

import com.netgames.clashoffishes.engine.GameMode;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Stef
 */
public class HighscoreTest
{

    Highscore score1 = new Highscore(GameMode.EVOLVED, "Stef", 100);
    Highscore score2 = new Highscore(GameMode.EVOLUTION_OF_TIME, "Stef", 200);
    Highscore score3 = new Highscore(GameMode.LAST_FISH_STANDING, "Stef", 300);

    public HighscoreTest()
    {

    }

    @BeforeClass
    public static void setUpClass()
    {

    }

    @AfterClass
    public static void tearDownClass()
    {
    }

    @Before
    public void setUp()
    {
    }

    @After
    public void tearDown()
    {
    }

    /**
     * Test of getGameMode method, of class Highscore.
     */
    @Test
    public void testGetGameMode()
    {
        assertEquals(GameMode.EVOLVED, score1.getGameMode());
        assertEquals(GameMode.EVOLUTION_OF_TIME, score2.getGameMode());
        assertEquals(GameMode.LAST_FISH_STANDING, score3.getGameMode());
    }

    /**
     * Test of getScore method, of class Highscore.
     */
    @Test
    public void testGetScore()
    {
        assertEquals(100, score1.getScore());
        assertEquals(200, score2.getScore());
        assertEquals(300, score3.getScore());
    }

    /**
     * Test of getUsername method, of class Highscore.
     */
    @Test
    public void testGetUsername()
    {
        assertEquals("Stef", score1.getUsername());
        assertEquals("Stef", score2.getUsername());
        assertEquals("Stef", score3.getUsername());
    }

    /**
     * Test of toString method, of class Highscore.
     */
    @Test
    public void testToString()
    {
        assertFalse(score1.toString().isEmpty());
    }

}
