package com.netgames.clashoffishes;

import com.netgames.clashoffishes.data.DatabaseStorage;
import com.netgames.clashoffishes.engine.GameMode;
import com.netgames.clashoffishes.server.Client;
import com.netgames.clashoffishes.server.GameClient;
import com.netgames.clashoffishes.server.LobbyRegistry;
import com.netgames.clashoffishes.server.remote.IGameServer;
import com.netgames.clashoffishes.server.remote.ILobby;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Christian Adkin
 * @author Stef Philipsen
 * @author Hein Dauven
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
    private Client client;

    private IGameServer gameServer;
    private GameClient gameClient;

    File f = new File("props.txt");
    PrintWriter out;
    private String ipAddress;
    private String username;
    private String password;

    protected Administration() {
        this.user = null;

        this.lobby = null;
        this.allUserHighscoresForGameMode = null;
        this.validator = new EmailValidator();
        this.lobbyRegistry = null;
        this.dbStorage = new DatabaseStorage();
        this.readProps();
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

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public IGameServer getGameServer() {
        return gameServer;
    }

    public void setGameServer(IGameServer gameServer) {
        this.gameServer = gameServer;
    }

    public GameClient getGameClient() {
        return gameClient;
    }

    public void setGameClient(GameClient gameClient) {
        this.gameClient = gameClient;
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

    private void readProps() {
        try {
            if (!f.exists()) {
                f.createNewFile();
                out = new PrintWriter("props.txt");
                out.println("localhost");
                out.close();
            }
            Scanner sc = new Scanner(f);
            this.ipAddress = sc.next();
            System.out.println("IP-address server: " + ipAddress);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Administration.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Administration.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchElementException ex) {
            System.out.println("Zet een geldig ip-adres of localhost in props.txt");
        } finally {
            if (out != null) {
                out.close();
            }
        }

    }

    public String getIpAddress() {
        return this.ipAddress;
    }

    private static class InputStream {

        public InputStream() {
        }
    }
    
    public void resetClient() {
        this.client = null;
    }
    
    public void resetLobby() {
        this.lobby = null;
    }
}
