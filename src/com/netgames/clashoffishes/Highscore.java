/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netgames.clashoffishes;

import com.netgames.clashoffishes.engine.GameMode;

/**
 *
 * @author MuK
 */
public class Highscore {

    private GameMode gameMode;
    private String username;
    private int score;
    

    public Highscore(GameMode gameMode, String username, int score) {
        this.gameMode = gameMode;
        this.username = username;
        this.score = score;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Highscore{" + "gameMode=" + gameMode + ", username=" + username + ", score=" + score + '}';
    }
    
    

}
