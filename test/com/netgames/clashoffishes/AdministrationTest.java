/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netgames.clashoffishes;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Stef
 */
public class AdministrationTest
{    
    @Before
    public void setUp()
    {
        Administration.get().clear();
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
        assertNotNull("User moet worden toegevoegd", Administration.get().addUser(new User("Stef", "StefPhilipsen@gmail.com"), "wachtwoord"));
        assertNull("Username bestaat al", Administration.get().addUser(new User("Stef", "user1@gmail.com"), "wachtwoord"));
        assertNull("Email bestaat al", Administration.get().addUser(new User("User1", "StefPhilipsen@gmail.com"), "wachtwoord"));
        assertNull("Naam mag niet leeg zijn", Administration.get().addUser(new User("", "leeg@gmail.com"), "wachtwoord"));
        
        //Geldig email-adres testen
        assertNull("Geen geldig emailadres ingevoerd, moet null zijn", Administration.get().addUser(new User("Henk", "geenApenstaartje"), "wachtwoord"));      
        assertNull("Geen geldig emailadres ingevoerd, moet null zijn", Administration.get().addUser(new User("Henk", "@aa"), "wachtwoord"));
        assertNull("Geen geldig emailadres ingevoerd, moet null zijn", Administration.get().addUser(new User("Henk", "aa@"), "wachtwoord"));
        assertNull("Geen geldig emailadres ingevoerd, moet null zijn", Administration.get().addUser(new User("Henk", "a@a"), "wachtwoord"));
        assertNull("Geen geldig emailadres ingevoerd, moet null zijn", Administration.get().addUser(new User("Henk", "aa@aa."), "wachtwoord"));      
    }

    /**
     * Test of getUser method, of class Administration.
     */
    @Test
    public void testGetUser()
    {
        User u = new User("Stef", "StefPhilipsen@gmail.com");
        assertNotNull("User moet worden toegevoegd", Administration.get().addUser(new User("Stef", "StefPhilipsen@gmail.com"), "wachtwoord"));
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
    
}
