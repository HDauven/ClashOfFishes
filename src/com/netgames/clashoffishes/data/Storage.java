package com.netgames.clashoffishes.data;

import com.netgames.clashoffishes.User;


/**
 * Storage
 * Interface that functions as mediator between the various
 * types of storage methods available.
 * 
 * @author Hein Dauven
 * @version 1.0
 */
public interface Storage {
    public Boolean addUser(String username, String confirmedPassword, String email);
    public User getUser(String username);
}
