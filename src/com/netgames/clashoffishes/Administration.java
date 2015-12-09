package com.netgames.clashoffishes;

import com.netgames.clashoffishes.data.DatabaseStorage;
import com.netgames.clashoffishes.engine.GameMode;
import com.netgames.clashoffishes.server.Lobby;
import com.netgames.clashoffishes.server.LobbyRegistry;
import com.netgames.clashoffishes.server.remote.IClient;
import com.netgames.clashoffishes.server.remote.ILobby;
import java.util.ArrayList;

/**
 * Created by Christian on 1/10/15.
 */
public class Administration {

    private static Administration instance = null;
    private User user;

    private ArrayList<Highscore> allUserHighscoresForGameMode;
    private EmailValidator validator;

    private LobbyRegistry lobbyRegistry;
    private static DatabaseStorage dbStorage;

    private int objectNr;
    
    private ILobby lobby;
    private IClient client;

    protected Administration() {
        this.user = null;

        this.lobby = null;
        this.allUserHighscoresForGameMode = null;
        this.validator = new EmailValidator();
        this.lobbyRegistry = null;
        this.dbStorage = new DatabaseStorage();
    }

    public static Administration get() {
        if (instance == null) {
            instance = new Administration();
        }
        if (!dbStorage.hasConnection()) {
            dbStorage = new DatabaseStorage();
        }

        return instance;
    }

    public void clear() {
        instance = new Administration();
    }

    /**
     * Add a new user to the database. Make sure all fields are not empty,
     *
     * @param username
     * @param confirmedPassword
     * @param email
     * @return
     */
    public Boolean addUser(String username, String confirmedPassword, String email) {
        if (username.equals("") || confirmedPassword.equals("") || !validator.validate(email)) {
            return false;
        }
        //TODO Controle op geldig emailadres, minimale lengte wachtwoord etc.
        return dbStorage.addUser(username, confirmedPassword, email);
    }

    public User logIn(String userIdentifier, String password) {
        dbStorage = new DatabaseStorage();
        this.user = dbStorage.logIn(userIdentifier, password);
        return this.user;
    }

    public User getLoggedInUser() {
        return this.user;
    }

    public LobbyRegistry getLobbyRegistry() {
        return lobbyRegistry;
    }

    public void setLobbyRegistry(LobbyRegistry lobbyRegistry) {
        this.lobbyRegistry = lobbyRegistry;
    }

    public ILobby getLobby() {
        return lobby;
    }

    public void setLobby(ILobby lobby) {
        this.lobby = lobby;
    }

    public IClient getClient() {
        return client;
    }

    public void setClient(IClient client) {
        this.client = client;
    }

    public ArrayList<Highscore> getAllUserHighscoresForGameMode(GameMode gameMode) {
        this.allUserHighscoresForGameMode = this.dbStorage.getAllUserHighscoresForGameMode(gameMode);
        return this.allUserHighscoresForGameMode;
    }

    public User getUser(String username) {
        if (username.isEmpty()) {
            return null;
        }

        return dbStorage.getUser(username);
    }

    public boolean hasConnection() {
        return dbStorage.hasConnection();
    }

    public int nextObjectNr() {
        return objectNr++;
    }
}
