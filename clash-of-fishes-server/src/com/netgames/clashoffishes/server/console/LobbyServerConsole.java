package com.netgames.clashoffishes.server.console;

import com.netgames.clashoffishes.server.RegistryServer;
import com.netgames.clashoffishes.server.Server;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Console version of the Clash of Fishes Lobby Server.
 *
 * @author Hein Dauven
 */
public class LobbyServerConsole extends Application implements Observer
{

    // Reference to server
    private static RegistryServer registry;
    private static Server server;

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
    public void update(Observable o, Object arg)
    {
        if (o instanceof RegistryServer)
        {
            registry = (RegistryServer) o;
            System.out.println(registry.getLastOutput());
        }
        else if (o instanceof Server)
        {
            server = (Server) o;
            System.out.println(server.getLastLobby());
        }
        else
        {
            registry.logMessage("Something went really wrong...");
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        launch(args);
        //Application.launch(args);
        // TODO code application logic here

    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        RegistryServer registryServer = new RegistryServer();
        LobbyServerConsole console = new LobbyServerConsole();
        getRegistryLogMessages(registryServer);
        registryServer.addObserver(console);
        registryServer.getServer().addObserver(console);
    }
}
