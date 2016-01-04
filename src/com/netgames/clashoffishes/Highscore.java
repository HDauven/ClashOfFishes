package com.netgames.clashoffishes;

import com.netgames.clashoffishes.engine.GameMode;

/**
 *
 * @author Christian Adkin
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

    public int getScore() {
        return score;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "Highscore{" + "gameMode=" + gameMode + ", username=" + username + ", score=" + score + '}';
    }

}
