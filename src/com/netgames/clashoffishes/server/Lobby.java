package com.netgames.clashoffishes.server;

import com.netgames.clashoffishes.server.remote.IClient;
import com.netgames.clashoffishes.server.remote.ILobby;
import com.netgames.clashoffishes.server.remote.IMessage;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class that keeps track of all the clients that exist of for this game.
 * Implementation of an ILobby.
 * @author Hein Dauven
 */
public class Lobby extends UnicastRemoteObject implements ILobby {

    private static final long serialVersionUID = 1L;
    
    private List<IClient> clients;
    private List<IMessage> messages;

    /**
     * 
     * @throws RemoteException 
     */
    public Lobby() throws RemoteException {
        super();
        clients  = new ArrayList<>();
        messages = new ArrayList<>();
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
        clients.add(client);
    }

    @Override
    public void broadcastMessage(IMessage message) throws RemoteException {
        messages.add(message);
        int i = 0;
        while (i < clients.size()) {
            clients.get(i++).retrieveMessage(message);
        }
    }

    @Override
    public List<IMessage> getMessages() throws RemoteException {
        return Collections.unmodifiableList(messages);
    }

    
    
}
