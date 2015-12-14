package com.netgames.clashoffishes.server.console;

import com.netgames.clashoffishes.server.RegistryServer;
import com.netgames.clashoffishes.server.Server;
import com.netgames.clashoffishes.server.interfaces.ILobbyServerObserver;
import com.netgames.clashoffishes.server.remote.ILobby;
import java.util.ArrayList;

/**
 * Console version of the Clash of Fishes Lobby Server.
 *
 * @author Hein Dauven
 */
public class LobbyServerConsole implements ILobbyServerObserver { 
    
    // Reference to server
    private static RegistryServer registry;
    private static Server server;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        // TODO code application logic here
        RegistryServer registry = new RegistryServer();
        LobbyServerConsole console = new LobbyServerConsole();
        registry.addGUIListener(console);
        getRegistryLogMessages(registry);
    }

    /**
     * Method that processes the server output by calling the output ArrayList,
     * iterating over it and adding the output to the ListView. This method is
     * used only to get the logged output from before the Controller is
     * registered as an observer.
     *
     * @param message The message you want to write.
     */
    private static void getRegistryLogMessages(RegistryServer registry)
    {
        ArrayList<String> temp = registry.getOutput();
        for (String output : temp)
        {
            System.out.println(output);
        }
    }

    @Override
    public void updateMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void updateLobby(ILobby lobby) {
        System.out.println(lobby.toString());
    }
}
