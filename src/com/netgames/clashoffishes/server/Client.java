package com.netgames.clashoffishes.server;

import com.netgames.clashoffishes.engine.GameMode;
import com.netgames.clashoffishes.interfaces.IChangeGui;
import com.netgames.clashoffishes.server.remote.IClient;
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
public class Client extends UnicastRemoteObject implements IClient, Runnable {

    private static final long serialVersionUID = 1L;

    private ILobby lobby;
    private String username = null;

    private List<IChangeGui> GUIs;

    public Client(String username, ILobby lobby) throws RemoteException {
        super();
        this.username = username;
        this.lobby = lobby;
        // TODO add fontys listener? 
        lobby.registerClient(this);
        GUIs = new ArrayList();
    }

    @Override
    public void retrieveMessage(IMessage message, IClient sender)  throws RemoteException {
        System.out.println(message.toString());
        for (IChangeGui guis : this.GUIs) {
            guis.displayMessage(message.toString());
        }
    }
    
    @Override
    public void retrievePlayer(String player, IClient sender)  throws RemoteException {
        for (IChangeGui guis : this.GUIs) {
            guis.displayPlayer(player);
        }
    }

    @Override
    public void retrieveCharacter(String character, IClient sender)  throws RemoteException {
        for (IChangeGui guis : this.GUIs) {
            guis.displaySelectedCharacter(character);
        }
    }

    @Override
    public void retrieveReady(boolean isReady, IClient sender)  throws RemoteException {
        for (IChangeGui guis : this.GUIs) {
            guis.displayReady(isReady);
        }
    }

    @Override
    public void retrieveGameMode(String gameMode, IClient sender)  throws RemoteException {
        System.out.println(gameMode);
        for (IChangeGui guis : this.GUIs) {
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

    public void addGUIListener(IChangeGui guiListener) {
        this.GUIs.add(guiListener);
    }

    public void removeGUIListener(IChangeGui guiListener) {
        this.GUIs.remove(guiListener);
    }

    @Override
    public void run() {
        // TODO recode this for practical purposes, with a string instead of a System.out.println
        List<IMessage> messages;
        try {
            messages = lobby.getMessages();
            for (IMessage m : messages) {
                System.out.println(m.toString());
                for (IChangeGui guis : this.GUIs) {
                    guis.displayMessage(m.toString());
                }
            }
        } catch (RemoteException ex) {
            System.out.println("RemoteException: " + ex.getMessage());
        }

        Scanner scanner = new Scanner(System.in);
        String message;
        while (true) {
            message = scanner.nextLine();
            try {
                lobby.broadcastMessage(new Message(this.username, message),(IClient)this);
            } catch (RemoteException e) {
                System.out.println("RemoteException: " + e.getMessage());
            }
        }
    }

    

}
