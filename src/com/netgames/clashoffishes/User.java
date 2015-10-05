package com.netgames.clashoffishes;

/**
 * Created by bram on 1/10/15.
 */
public class User {
    private final String username;
    private final String email;
    private final int score;

    public User(String username, String email, int score) {
        this.username = username;
        this.email = email;
        this.score = score;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public int getScore() {
        return score;
    }
}
