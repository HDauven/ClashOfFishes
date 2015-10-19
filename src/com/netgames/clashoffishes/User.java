package com.netgames.clashoffishes;

import java.util.List;

/**
 * Created by bram on 1/10/15.
 */
public class User
{

    private final String username;
    private final String email;
    private List<Highscore> highscores;

    public User(String username, String email, List<Highscore> highscores) {
        this.username = username;
        this.email = email;
        this.highscores = highscores;
    }
    
    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public List<Highscore> getHighscores() {
        return highscores;
    }



}
