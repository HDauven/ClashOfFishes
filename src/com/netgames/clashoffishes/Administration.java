package com.netgames.clashoffishes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bram on 1/10/15.
 */
public class Administration {
    List<User> users = new ArrayList<>();

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
}
