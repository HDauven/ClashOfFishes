package com.netgames.clashoffishes;

import com.netgames.clashoffishes.data.DatabaseStorage;
import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bram on 1/10/15.
 */
public class Administration {

    private static Administration instance = null;
    private final User user;
    private DatabaseStorage dbStorage;

    protected Administration() {
        user = null;
        dbStorage = new DatabaseStorage();
    }
    
    public static Administration get() {
      if(instance == null) {
         instance = new Administration();
      }
      return instance;
   }

    public User addUser(User user) {
        dbStorage.addUser(user);
        return user;
    }

    public User getUser(String username) {
        return dbStorage.getUser(username);
    }
    
    public void clear()
    {
        instance = new Administration();
    }

    public void load(Administration load)
    {
        this.clear();
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
