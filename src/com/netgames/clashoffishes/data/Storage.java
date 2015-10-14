package com.netgames.clashoffishes.data;

import com.netgames.clashoffishes.Administration;
import com.netgames.clashoffishes.User;
import java.io.IOException;


/**
 * Storage
 * Interface that functions as mediator between the various
 * types of storage methods available.
 * 
 * @author Hein Dauven
 * @version 1.0
 */
public interface Storage {
    public void addUser(User u, String password);
    public User getUser(String username);
    public User logIn(String username_or_email, String password);
}
