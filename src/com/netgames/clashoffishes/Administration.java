package com.netgames.clashoffishes;

import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bram on 1/10/15.
 */
public class Administration {

    private static Administration instance = null;

    private final ArrayList<User> users;

    protected Administration() {
        users = new ArrayList<>();
    }
    
    public static Administration get() {
      if(instance == null) {
         instance = new Administration();
      }
      return instance;
   }

    public User addUser(String username, String email) {
        User user = new User(username, email, 0);
        users.add(user);
        return user;
    }

    public User getUser(String username) {
        return users.stream()
                .filter(u -> u.getUsername().equalsIgnoreCase(username))
                .findFirst().orElse(null);
    }
    
    public void clear()
    {
        instance = new Administration();
    }
}
