/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netgames.clashoffishes.data;

import com.netgames.clashoffishes.User;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Stef
 */
public class DatabaseStorageTest
{

    DatabaseStorage dbStorage;

    @Before
    public void setUp()
    {
        dbStorage = new DatabaseStorage();
    }

    @After
    public void tearDown()
    {
    }
    
    @AfterClass
    public void tearDownAfterClass()
    {
        //Users weer verwijderen
    }

    /**
     * Test of addUser method, of class DatabaseStorage.
     */
    @Test
    public void testAddUser()
    {
        dbStorage.addUser(new User("Henk", "Henk@Henk.nl"), "wachtwoord");
        dbStorage.addUser(new User("Piet", "Piet@hotmail.com", 16), "wachtwoord");
        dbStorage.addUser(new User("henk", "Henkie@Henkie.nl"), "wachtwoord1");// Mag niet worden toegevoegd, username bestaat al in db
        dbStorage.addUser(new User("henkie", "henk@henk.nl"), "wachtwoord");//Mag niet worden toegevoegd, email bestaat al in db
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
        assertEquals("Score moet 0 zijn. ", henk.getScore());
        assertEquals("Email moet 'Henk@Henk.nl' zijn.", "Henk@Henk.nl", henk.getEmail());
        User piet = dbStorage.getUser("Piet");
        assertEquals("Score moet 16 zijn.", 16, piet.getScore());
    }

    /**
     * Test of logIn method, of class DatabaseStorage.
     */
    @Test
    public void testLogIn()
    {
        assertNull(dbStorage.logIn("henk", "verkeerdWW"));
        assertNull(dbStorage.logIn("henk@henk.nl", "verkeerdWW"));
        assertNull(dbStorage.logIn("henk", "wachtwoord1"));
        assertNull(dbStorage.logIn("henk", "WACHTWOORD"));
        assertNotNull(dbStorage.logIn("henk", "wachtwoord"));
        assertNotNull(dbStorage.logIn("henk@henk.nl", "wachtwoord"));
    }
}
