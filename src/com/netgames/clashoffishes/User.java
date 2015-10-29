package com.netgames.clashoffishes;

import com.netgames.clashoffishes.engine.GameMode;
import java.util.HashMap;

/**
 * Created by bram on 1/10/15.
 */
public class User
{

    private final String username;
    private final String email;
    //HashMap zodat je een GameMode niet meerdere keren toe kunt voegen.
    private final HashMap<GameMode, Integer> highscores;

    public User(String username, String email)
    {
        this.username = username;
        this.email = email;
        highscores = new HashMap<>();
        highscores.put(GameMode.EVOLUTION_OF_TIME, 0);
        highscores.put(GameMode.EVOLVED, 0);
        highscores.put(GameMode.LAST_FISH_STANDING, 0);
    }

    public String getUsername()
    {
        return username;
    }

    public String getEmail()
    {
        return email;
    }

    public HashMap<GameMode, Integer> getHighscores()
    {
        return highscores;
    }

    public int getScore(GameMode m)
    {
        return highscores.get(m);
    }

    //Voegt gehaalde score bij de bestaande score;
    public Integer updateHighScore(GameMode mode, Integer score)
    {
        Integer newScore = highscores.get(mode) + score;
        highscores.put(mode, newScore);
        return newScore;
    }
    
    public void setHighScore(GameMode mode, Integer score)
    {
        highscores.put(mode, score);
    }
}
