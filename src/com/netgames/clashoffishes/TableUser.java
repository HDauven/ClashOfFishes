/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netgames.clashoffishes;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Class for adding a user to a table. Has Simple..Properties for easy adding
 * and removing users from a table.
 *
 * @author Stef
 */
public class TableUser
{

    private SimpleStringProperty username;
    private SimpleStringProperty character;
    private SimpleBooleanProperty ready;

    public TableUser(User u)
    {
        username = new SimpleStringProperty(u.getUsername());
        character = new SimpleStringProperty("None");
        ready = new SimpleBooleanProperty(false);
    }

    public String getUsername()
    {
        return this.username.get();
    }
    
    public String getCharacter()
    {
        // TODO Add selected player to user class
        return "None";
    }
    
     public void setCharacter()
    {
        // TODO Change selected player
    }

    public Boolean getReady()
    {
        return this.ready.get();
    }

    public void setReady(boolean b)
    {
        this.ready = new SimpleBooleanProperty(b);
    }

    @Override
    public String toString()
    {
        return this.username + "Ready = " + ready;
    }
}
