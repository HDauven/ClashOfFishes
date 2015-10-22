package com.netgames.clashoffishes;

import com.netgames.clashoffishes.data.DatabaseStorage;
import com.netgames.clashoffishes.engine.GameMode;
import java.util.List;

/**
 * Created by Christian on 1/10/15.
 */
public class Administration
{

    private static Administration instance = null;
    private User user;
    private Lobby currentLobby;
    private List<Highscore> allUserHighscoresForGameMode;

    private final DatabaseStorage dbStorage;

    protected Administration()
    {
        user = null;
        currentLobby = null;
        allUserHighscoresForGameMode = null;
        dbStorage = new DatabaseStorage();
    }

    public static Administration get()
    {
        if (instance == null)
        {
            instance = new Administration();
        }
        return instance;
    }

    public void clear()
    {
        instance = new Administration();
    }

    public Boolean addUser(String username, String confirmedPassword, String email)
    {
        return dbStorage.addUser(username, confirmedPassword, email);
    }

    public User logIn(String userIdentifier, String password)
    {
        this.user = dbStorage.logIn(userIdentifier, password);
        return this.user;
    }

    public User getLoggedInUser()
    {
        return this.user;
    }

    public Lobby getCurrentLobby()
    {
        return this.currentLobby;
    }

    public void setCurrentLobby(Lobby currentLobby)
    {
        this.currentLobby = currentLobby;
    }

    public List<Highscore> getAllUserHighscoresForGameMode(GameMode gameMode)
    {
        this.allUserHighscoresForGameMode = this.dbStorage.getAllUserHighscoresForGameMode(gameMode);
        return this.allUserHighscoresForGameMode;
    }

    //TODO Nog niet geimplementeerd is deze nog wel nodig????
    public User getUser(String username)
    {
        return dbStorage.getUser(username);
    }

}
