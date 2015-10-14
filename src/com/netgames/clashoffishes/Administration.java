package com.netgames.clashoffishes;

import com.netgames.clashoffishes.data.DatabaseStorage;

/**
 * Created by bram on 1/10/15.
 */
public class Administration {

    private static Administration instance = null;
    private User user;
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

    public Boolean addUser(String username, String confirmedPassword, String email) {
        Boolean userAdded = false;
        userAdded = dbStorage.addUser(username, confirmedPassword, email);
        return userAdded;
    }

    public User getUser(String username) {
        return dbStorage.getUser(username);
    }
    
    
    public void clear()
    {
        instance = new Administration();
    }
    
    public User logIn(String userIdentifier, String password)
    {
        this.user = dbStorage.logIn(userIdentifier, password);
        return this.user;
    }
    
    public User getLoggedInUser() {
        
        return this.user;
    }
}
