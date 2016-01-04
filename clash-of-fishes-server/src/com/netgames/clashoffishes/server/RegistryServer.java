package com.netgames.clashoffishes.server;

import com.netgames.clashoffishes.server.interfaces.ILobbyServerObserver;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that creates the RMI registry for the Clash Of Fishes Lobby Server.
 *
 * @author Hein Dauven
 */
public class RegistryServer {

    // Set port number
    private static final int portNumber = 1100;

    // Set binding name for the server
    private static final String bindingName = "Server";

    // List of the output of the Registry Server
    private ArrayList<String> output = new ArrayList<>();

    // Reference to server
    private Server server = null;

    // List of all the GUIs that are tracking changes in the RegistryServer and Server class
    private List<ILobbyServerObserver> GUIs;

    // Constructor
    public RegistryServer() {
        // Initialize the GUIs list
        GUIs = new ArrayList();

        // Print the IP addresses of the host device
        printIPAddresses();

        // Print port number for registry
        logMessage("Server: Port number " + portNumber);

        // Create server
        try {
            server = new Server(this);
            logMessage("Server: server created");
        } catch (RemoteException ex) {
            logMessage("Server: Cannot create server");
            logMessage("Server: RemoteException: " + ex.getMessage());
            server = null;
        }

        // Bind server using Naming
        if (server != null) {
            try {
                LocateRegistry.createRegistry(portNumber);
                UnicastRemoteObject.exportObject(server, portNumber);
                Naming.rebind("//145.93.165.2:" + portNumber + "/" + bindingName, server);
            } catch (MalformedURLException ex) {
                logMessage("Server: Cannot bind server");
                logMessage("Server: MalformedURLException: " + ex.getMessage());
            } catch (RemoteException ex) {
                logMessage("Server: Cannot bind server");
                logMessage("Server: RemoteException: " + ex.getMessage());
            }
            logMessage("Server: server bound to " + bindingName);
        } else {
            logMessage("Server: server not bound");
        }
    }

    // Print IP addresses and network interfaces
    private void printIPAddresses() {
        try {
            InetAddress localhost = InetAddress.getLocalHost();
            logMessage("Server: IP Address: " + localhost.getHostAddress());
            // Just in case this host has multiple IP addresses....
            InetAddress[] allMyIps = InetAddress.getAllByName(localhost.getCanonicalHostName());
            if (allMyIps != null && allMyIps.length > 1) {
                logMessage("Server: Full list of IP addresses:");
                for (InetAddress allMyIp : allMyIps) {
                    logMessage("    " + allMyIp);
                }
            }
        } catch (UnknownHostException ex) {
            logMessage("Server: Cannot get IP address of local host");
            logMessage("Server: UnknownHostException: " + ex.getMessage());
        }
    }

    /**
     * Method that gets the ArrayList containing output from the server.
     *
     * @return ArrayList containing logged server output
     */
    public ArrayList<String> getOutput() {
        return output;
    }

    /**
     * Method that gets the last server output.
     *
     * @return The last output the server reported
     */
    public String getLastOutput() {
        int lastIndex = output.size();
        String last = output.get(lastIndex - 1);
        return last;
    }

    /**
     * Method that writes server output to an ArrayList.
     *
     * @param message The message the server reports
     */
    public void logMessage(String message) {
        this.output.add(message);
        for (ILobbyServerObserver guis : this.GUIs) {
            guis.updateMessage(message);
        }
    }

    public Server getServer() {
        return server;
    }

    public List<ILobbyServerObserver> getGUIs() {
        return GUIs;
    }

    public void addGUIListener(ILobbyServerObserver guiListener) {
        this.GUIs.add(guiListener);
    }

    public void removeGUIListener(ILobbyServerObserver guiListener) {
        this.GUIs.remove(guiListener);
    }
}
