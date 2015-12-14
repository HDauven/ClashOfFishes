package com.netgames.clashoffishes.server;

import com.netgames.clashoffishes.Administration;
import com.netgames.clashoffishes.User;
import com.netgames.clashoffishes.engine.GameMode;
import com.netgames.clashoffishes.server.remote.IClient;
import com.netgames.clashoffishes.server.remote.ILobby;
import com.netgames.clashoffishes.server.remote.IMessage;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class that keeps track of all the clients that exist of for this game.
 * Implementation of an ILobby.
 *
 * @author Hein Dauven
 */
public class Lobby extends UnicastRemoteObject implements ILobby {

    // TODO add more game/lobby info
    private static final long serialVersionUID = 1L;

    private List<IClient> clients;
    private List<IMessage> messages;
    private User host;

    private GameMode gameMode = GameMode.EVOLVED;
    
    
    
    

    /**
     *
     * @throws RemoteException
     */
    public Lobby() throws RemoteException {
        super();
        clients = new ArrayList<>();
        messages = new ArrayList<>();
        host = Administration.get().getLoggedInUser();
        
        Administration.get().setClient(new Client(host.getUsername(), true, this));
    }

    @Override
    public boolean clientNameFree(String username) throws RemoteException {
        for (IClient c : clients) {
            if (c.getUsername().equals(username)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void registerClient(IClient client) throws RemoteException {
        if (this.clients.size() >= 4){ 
            System.out.println("Lobby is full mate.");
        } else {
            clients.add(client);
        }
    }
    
    @Override
    public void removeClient(IClient client) throws RemoteException {
        if (this.clients.size() <= 0) {
            System.out.println("fuck");
        } else {
            clients.remove(client);
        }
    }

    @Override
    public void broadcastMessage(IMessage message, IClient sender)  throws RemoteException {
        messages.add(message);
        int i = 0;
        while (i < clients.size()) {
            clients.get(i).retrieveMessage(message, clients.get(i));
            i++;
        }
    }
    
    @Override
    public void broadcastPlayer(String player, IClient sender)  throws RemoteException {
        int i = 0;
        while (i < clients.size()) {
            clients.get(i).retrievePlayer(player, sender);
            i++;
        }
    }

    @Override
    public void broadcastCharacter(String character, IClient sender)  throws RemoteException {
        int i = 0;
        while (i < clients.size()) {
            clients.get(i).retrieveCharacter(character, sender);
            i++;
        }
    }

    @Override
    public void broadcastReady(boolean isReady, IClient sender)  throws RemoteException {
        int i = 0;
        while (i < clients.size()) {
            clients.get(i).retrieveReady(isReady, sender);
            i++;
        }
    }

    @Override
    public void broadcastGameMode(String gameMode, IClient sender)  throws RemoteException {
        int i = 0;
        while (i < clients.size()) {
            System.out.println(gameMode);
            clients.get(i).retrieveGameMode(gameMode, clients.get(i));
            i++;
        }
    }

    public String getLobbyTitle() {
        try {
            return this.clients.get(0).getUsername() + "'s lobby";
        } catch (RemoteException remoteException) {
            System.out.println("ERROR GETTING CLIENT USERNAME");
            return "ERROR GETTING CLIENT USERNAME";
        }
    }

    public String getUsersString() {
        String usersString = "";
        try {
            for (IClient client : this.clients) {
                usersString += client.getUsername() + " - ";
            }
        } catch (RemoteException remoteException) {
            System.out.println("ERROR GETTING CLIENT USERNAMES");
            return "ERROR GETTING CLIENT USERNAMES";
        }
        return usersString;
    }
    
    //TODO Beter formuleren comment 
    //GetStringProperties voor het inzetten van de lobby in de tableView 
    @Override
    public String getPoolNameProperty() {
            return this.getLobbyTitle();
        }
    
    @Override
    public String getPlayersProperty() {
        return this.getUsersString();
    }

    @Override
    public String getGameModeProperty() {
        return this.gameMode.toString().toLowerCase();
    }

    @Override
    public List<IClient> getClients() throws RemoteException {
        return clients;
    }

    public void setClients(List<IClient> clients) {
        this.clients = clients;
    }
    
    
    @Override
    public List<IMessage> getMessages() throws RemoteException {
        return Collections.unmodifiableList(messages);
    }

    @Override
    public String toString() {
        return this.ref.remoteToString();
    }
    
    @Override
    public void startGame(){
        try {
            int seed = (int)System.currentTimeMillis();
            Administration.get().setGameServer(new GameServer(this));
            
            int playerID = 0;
            for(IClient client : this.clients) {
                client.createGameClient(Administration.get().getGameServer(), seed, playerID);
                playerID++;
            }
            Administration.get().getGameServer().start();
        } catch (RemoteException ex) {
            Logger.getLogger(Lobby.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
