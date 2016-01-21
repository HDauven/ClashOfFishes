/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netgames.clashoffishes;

import com.netgames.clashoffishes.engine.GameMode;
import java.util.HashMap;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Stef
 */
public class UserTest {

    User u;

    @Before
    public void setUp() {
        u = new User(99, "Stef", "StefPhilipsen@gmail.com");
        u.setHighScore(GameMode.EVOLVED, 300);
        u.setHighScore(GameMode.EVOLUTION_OF_TIME, 200);
        u.setHighScore(GameMode.LAST_FISH_STANDING, 100);
    }

    /**
     * Test of getId method, of class User.
     */
    @Test
    public void testGetId() {
        assertEquals(99, u.getId());
    }

    /**
     * Test of getUsername method, of class User.
     */
    @Test
    public void testGetUsername() {
        assertEquals("Username is niet goed ingevoerd", "Stef", u.getUsername());
    }

    /**
     * Test of getEmail method, of class User.
     */
    @Test
    public void testGetEmail() {
        assertEquals("Email is niet goed ingevoerd", "StefPhilipsen@gmail.com", u.getEmail());
    }

    /**
     * Test of getHighscores method, of class User.
     */
    @Test
    public void testGetHighscores() {
        HashMap<GameMode, Integer> scores = u.getHighscores();
        assertEquals(new Integer(300), scores.get(GameMode.EVOLVED));
        assertEquals(new Integer(200), scores.get(GameMode.EVOLUTION_OF_TIME));
        assertEquals(new Integer(100), scores.get(GameMode.LAST_FISH_STANDING));
        
    }

    /**
     * Test of getScore method, of class User.
     */
    @Test
    public void testGetScore() {
        assertEquals(300, u.getScore(GameMode.EVOLVED));
        assertEquals(200, u.getScore(GameMode.EVOLUTION_OF_TIME));
        assertEquals(100, u.getScore(GameMode.LAST_FISH_STANDING));
    }

    /**
     * Test of updateHighScore method, of class User.
     */
    @Test
    public void testUpdateHighScore() {
        u.updateHighScore(GameMode.EVOLUTION_OF_TIME, 50);
        assertEquals("Score moet nu  250 zijn.", 250, u.getScore(GameMode.EVOLUTION_OF_TIME));
        HashMap<GameMode, Integer> scores = u.getHighscores();
        assertEquals("Score moet 100 zijn.", new Integer(100), scores.get(GameMode.LAST_FISH_STANDING));
    }

    /**
     * Test of setHighScore method, of class User.
     */
    @Test
    public void testSetHighScore() {
        u.setHighScore(GameMode.EVOLVED, 200);
        assertEquals(200, u.getScore(GameMode.EVOLVED));
    }

    /**
     * Test of toString method, of class User.
     */
    @Test
    public void testToString() {
        assertEquals("99 Stef", u.toString());
    }

}
