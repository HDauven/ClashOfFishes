package com.netgames.clashoffishes.server;

import com.netgames.clashoffishes.Administration;
import com.netgames.clashoffishes.User;
import com.netgames.clashoffishes.server.remote.IClient;
import com.netgames.clashoffishes.server.remote.IServer;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class that creates the RMI registry for a Clash Of Fishes Lobby.
 *
 * @author Hein Dauven
 */
public class LobbyRegistry extends Observable
{

    // Set port number

    private static final int portNumber = 1099;
    private static final int portNumber2 = 1097;

    private static final Administration administration = Administration.get();
    private static final User user = administration.getLoggedInUser();

    // Set binding name for the lobby
    public static final String bindingName = user.getUsername();

    // List of the output of the Registry Server
    private ArrayList<String> output = new ArrayList<>();

    // Reference to lobby
    private Lobby lobby = null;

    // Reference to GameServer
    GameServer gameServer = null;

    private static IServer cofServer;
    private final String cofServerURL = "rmi://localhost:1100/Server";
    
    // Constructor
    public LobbyRegistry()
    {
        // Print the IP addresses of the host device
        printIPAddresses();

        // Print port number for registry
        logMessage("Server: Port number " + portNumber);

        // Create lobby
        try
        {
            lobby = new Lobby();
            logMessage("Server: lobby created");   
        }
        catch (RemoteException ex)
        {
            logMessage("Server: Cannot create lobby");
            logMessage("Server: RemoteException: " + ex.getMessage());
            System.out.println(getLastOutput());
            lobby = null;
        }

        // Bind lobby using Naming
        if (lobby != null)
        {
            try
            {
                LocateRegistry.createRegistry(portNumber);
                Naming.rebind("//localhost:" + portNumber + "/" + bindingName, lobby);
            }
            catch (MalformedURLException ex)
            {
                logMessage("Server: Cannot bind lobby");
                logMessage("Server: MalformedURLException: " + ex.getMessage());
            }
            catch (RemoteException ex)
            {
                logMessage("Server: Cannot bind lobby");
                logMessage("Server: RemoteException: " + ex.getMessage());
            }
            logMessage("Server: lobby bound to " + bindingName);
        }
        else
        {
            logMessage("Server: lobby not bound");
        }
    }

    /**
     * Method that looks up the Clash of Fishes Lobby Server in the name
     * registry, based on a given RMI URL.
     */
    public void clashOfFishesServerLookup()
    {
        try
        {
            cofServer = (IServer) Naming.lookup(cofServerURL);
            cofServer.registerLobby(lobby);
        }
        catch (NotBoundException ex)
        {
            System.out.println("Server: Cannot bind Clash of Fishes server");
            System.out.println("NotBoundException: " + ex.getMessage());
        }
        catch (MalformedURLException ex)
        {
            System.out.println("Server: Cannot bind Clash of Fishes server");
            System.out.println("MalformedURLException: " + ex.getMessage());
        }
        catch (RemoteException ex)
        {
            System.out.println("Server: Cannot bind Clash of Fishes server");
            System.out.println("RemoteException: " + ex.getMessage());
        }
    }

    // Print IP addresses and network interfaces
    private void printIPAddresses()
    {
        try
        {
            InetAddress localhost = InetAddress.getLocalHost();
            logMessage("Server: IP Address: " + localhost.getHostAddress());
            // Just in case this host has multiple IP addresses....
            InetAddress[] allMyIps = InetAddress.getAllByName(localhost.getCanonicalHostName());
            if (allMyIps != null && allMyIps.length > 1)
            {
                logMessage("Server: Full list of IP addresses:");
                for (InetAddress allMyIp : allMyIps)
                {
                    logMessage("    " + allMyIp);
                }
            }
        }
        catch (UnknownHostException ex)
        {
            logMessage("Server: Cannot get IP address of local host");
            logMessage("Server: UnknownHostException: " + ex.getMessage());
        }
    }

    /**
     * Method that gets the ArrayList containing output from the server.
     *
     * @return ArrayList containing logged server output
     */
    public ArrayList<String> getOutput()
    {
        return output;
    }

    /**
     * Method that gets the last server output.
     *
     * @return The last output the server reported
     */
    public String getLastOutput()
    {
        int lastIndex = output.size();
        String last = output.get(lastIndex - 1);
        return last;
    }

    /**
     * Method that writes server output to an ArrayList.
     *
     * @param output The message the server reports
     */
    public void logMessage(String output)
    {
        this.output.add(output);
        setChanged();
        notifyObservers();
    }

    public Lobby getLobby()
    {
        return lobby;
    }

    public void setLobby(Lobby lobby)
    {
        this.lobby = lobby;
    }

    public void startGameServer()
    {
        try
        {
            gameServer = new GameServer(this);
            for(IClient c : lobby.getClients()){
                GameClient gc = new GameClient(c.getUsername());
                gameServer.registerClient(gc);
            }
            LocateRegistry.getRegistry().unbind(bindingName);
            LocateRegistry.createRegistry(portNumber2);
            Naming.rebind("//localhost:" + portNumber2 + "/" + bindingName, gameServer);
            gameServer.start();
        }
        catch (RemoteException | MalformedURLException | NotBoundException ex)
        {
            Logger.getLogger(LobbyRegistry.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static IServer getCofServer()
    {
        return cofServer;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        // Welcome message
        System.out.println("Welcome to a Clash of Fishes lobby!");

        // Print IP addresses and network interfaces
        //printIPAddresses();
        // Create server
        LobbyRegistry server = new LobbyRegistry();
        server.clashOfFishesServerLookup();
    }

}
