package com.netgames.clashoffishes.server;

import com.netgames.clashoffishes.Administration;
import com.netgames.clashoffishes.engine.GameMode;
import com.netgames.clashoffishes.interfaces.ILobbyListener;
import com.netgames.clashoffishes.server.remote.IClient;
import com.netgames.clashoffishes.server.remote.IGameServer;
import com.netgames.clashoffishes.server.remote.ILobby;
import com.netgames.clashoffishes.server.remote.IMessage;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Client class that holds a link to a lobby it registers to. Implementation of
 * an IClient.
 *
 * @author Hein Dauven
 */
public class Client extends UnicastRemoteObject implements IClient {

    private static final long serialVersionUID = 1L;

    private ILobby lobby;
    private String username = null;
    private Boolean isHost;
    private Boolean isReady = false;
    private String character = "None";

    private List<ILobbyListener> GUIs;

    public Client(String username, boolean isHost, ILobby lobby) throws RemoteException {
        super();
        this.username = username;
        this.isHost = isHost;
        this.lobby = lobby;
        // TODO add fontys listener? 
        lobby.registerClient(this);
        GUIs = new ArrayList();
    }

    @Override
    public void retrieveMessage(IMessage message, IClient sender) throws RemoteException {
        System.out.println(message.toString());
        for (ILobbyListener guis : this.GUIs) {
            guis.displayMessage(message.toString());
        }
    }

    @Override
    public void retrievePlayer(String player, IClient sender) throws RemoteException {
        for (ILobbyListener guis : this.GUIs) {
            guis.displayPlayer(player, sender);
        }
    }

    @Override
    public void retrieveCharacter(String character, IClient sender) throws RemoteException {
        for (ILobbyListener guis : this.GUIs) {
            guis.displaySelectedCharacter(character, sender);
        }
    }

    @Override
    public void retrieveReady(boolean isReady, IClient sender) throws RemoteException {
        for (ILobbyListener guis : this.GUIs) {
            guis.displayReady(isReady, sender);
        }
    }

    @Override
    public void retrieveGameMode(String gameMode, IClient sender) throws RemoteException {
        System.out.println(gameMode);
        for (ILobbyListener guis : this.GUIs) {
            for (GameMode gm : GameMode.values()) {
                if (gm.toString().equalsIgnoreCase(gameMode)) {
                    guis.displayGameMode(gm);
                }
            }
        }
    }

    @Override
    public String getUsername() throws RemoteException {
        return this.username;
    }

    public Boolean getIsHost() {
        return isHost;
    }

    public void addGUIListener(ILobbyListener guiListener) {
        this.GUIs.add(guiListener);
    }

    public void removeGUIListener(ILobbyListener guiListener) {
        this.GUIs.remove(guiListener);
    }



    @Override
    public void setIsReady(boolean ready) throws RemoteException {
        this.isReady = ready;
    }

    @Override
    public boolean getIsReady() throws RemoteException {
        return this.isReady;
    }

    @Override
    public String getCharacter() throws RemoteException {
        return character;
    }

    @Override
    public void setCharacter(String character) throws RemoteException {
        this.character = character;
    }

    @Override
    public void createGameClient(IGameServer gameServer, int seed, int playerID) throws RemoteException {
        Administration.get().setGameServer(gameServer);
        Administration.get().setGameClient(new GameClient(username, character, seed, playerID, gameServer, lobby.getGameMode()));
    }

}
