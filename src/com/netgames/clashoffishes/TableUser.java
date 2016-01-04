package com.netgames.clashoffishes;

import com.netgames.clashoffishes.server.remote.IClient;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Class for adding a user to a table. Has Simple..Properties for easy adding
 * and removing users from a table.
 *
 * @author Stef Philipsen
 * @author Hein Dauven
 */
public class TableUser {

    private SimpleStringProperty username;
    private SimpleStringProperty character;
    private SimpleBooleanProperty ready;

    public TableUser(IClient u) {
        try {
            this.username = new SimpleStringProperty(u.getUsername());
        } catch (RemoteException ex) {
            Logger.getLogger(TableUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.character = new SimpleStringProperty("None");
        this.ready = new SimpleBooleanProperty(false);
    }

    public TableUser(String username, String character, boolean ready) {
        this.username = new SimpleStringProperty(username);
        this.character = new SimpleStringProperty(character);
        this.ready = new SimpleBooleanProperty(ready);
    }

    public String getUsername() {
        return this.username.get();
    }

    public String getCharacter() {
        return this.character.get();
    }

    public void setCharacter(String character) {
        this.character = new SimpleStringProperty(character);
    }

    public Boolean getReady() {
        return this.ready.get();
    }

    public void setReady(boolean b) {
        this.ready = new SimpleBooleanProperty(b);
    }

    @Override
    public String toString() {
        return this.username + "Ready = " + ready;
    }
}
