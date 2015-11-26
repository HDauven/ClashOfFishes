package com.netgames.clashoffishes.server.console;

import com.netgames.clashoffishes.server.RegistryServer;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Console version of the Clash of Fishes Lobby Server.
 * @author Hein Dauven
 */
public class LobbyServerConsole implements Observer {

    // Reference to server
    private static RegistryServer registry;

    /**
     * Method that processes the server output by calling the output ArrayList,
     * iterating over it and adding the output to the ListView.
     * This method is used only to get the logged output from before the Controller
     * is registered as an observer.
     * @param message The message you want to write.
     */
    private static void getRegistryLogMessages(RegistryServer registry) {
        ArrayList<String> temp = registry.getOutput();
        for (String output : temp) {
            System.out.println(output);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        registry = (RegistryServer) o;
        System.out.println(registry.getLastOutput());
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        RegistryServer registryServer = new RegistryServer();
        LobbyServerConsole console = new LobbyServerConsole();
        getRegistryLogMessages(registryServer);
        registryServer.addObserver(console);
    }
}
