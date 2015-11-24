package com.netgames.clashoffishes;

import com.netgames.clashoffishes.data.DatabaseStorage;
import com.netgames.clashoffishes.engine.GameMode;
import java.util.ArrayList;

/**
 * Created by Christian on 1/10/15.
 */
public class Administration
{

    private static Administration instance = null;
    private User user;
    private Lobby currentLobby;
    private ArrayList<Highscore> allUserHighscoresForGameMode;
    EmailValidator validator;

    private final DatabaseStorage dbStorage;

    protected Administration()
    {
        user = null;
        currentLobby = null;
        allUserHighscoresForGameMode = null;
        dbStorage = new DatabaseStorage();
        //Testwaarde
        user = new User(1, "Stef", "stef@stef.nl");
        validator = new EmailValidator();
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

    /**
     * Add a new user to the database.
     * Make sure all fields are not empty, 
     * @param username
     * @param confirmedPassword
     * @param email
     * @return 
     */
    public Boolean addUser(String username, String confirmedPassword, String email)
    {
        if(username.equals("") || confirmedPassword.equals("") || !validator.validate(email))
            return false;
        //TODO Controle op geldig emailadres, minimale lengte wachtwoord etc.
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

    public ArrayList<Highscore> getAllUserHighscoresForGameMode(GameMode gameMode)
    {
        this.allUserHighscoresForGameMode = this.dbStorage.getAllUserHighscoresForGameMode(gameMode);
        return this.allUserHighscoresForGameMode;
    }

    public User getUser(String username)
    {
        if(username.isEmpty())
            return null;
        
        return dbStorage.getUser(username);
    }
}
