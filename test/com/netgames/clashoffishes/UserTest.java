/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netgames.clashoffishes;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

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
        u = new User("Stef", "StefPhilipsen@gmail.com", 1);
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
        assertEquals("Score is niet goed ingevoerd", 1, u.getScore());
    }
    
}
