package com.netgames.clashoffishes.server.ui;

import com.netgames.clashoffishes.server.Server;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author MuK
 */
public class LobbyServerGUIController implements Initializable {

    @FXML
    private AnchorPane paneMainForm;
    @FXML
    private ListView<?> lstViewServers;
    @FXML
    private Label lblServers;
    @FXML
    private Label lblAdminControls;
    @FXML
    private Button btnCloseLobby;
    @FXML
    private ListView<String> lstViewSystemLog;
    @FXML
    private Label lblSystemLog;

    // Set port number
    private static final int portNumber = 1100;

    // Set binding name for the server
    private static final String bindingName = "Server";

    // Reference to server
    private Server server = null;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Print port number for registry
        logMessage("Server: Port number " + portNumber);

        // Create server
        try {
            server = new Server();
            logMessage("Server: server created");
        } catch (RemoteException ex) {
            logMessage("Server: Cannot create server");
            logMessage("Server: RemoteException: " + ex.getMessage());
            server = null;
        }

        // Bind chatroom using Naming
        if (server != null) {
            try {
                LocateRegistry.createRegistry(portNumber);
                Naming.rebind("//localhost:" + portNumber + "/" + bindingName, server);
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

    @FXML
    private void btnCloseLobby_OnClick(ActionEvent event) {
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
     * A function to write a message to the GUI.
     * This function can also write to the console with the added parameter
     * @param message The message you want to write.
     * @param showOutputInConsole If you want to also show this message in the console.
     */
    private void logMessage(String message, boolean showOutputInConsole) {
        this.lstViewSystemLog.getItems().add(message);
        if (showOutputInConsole)
            logMessage(message);
    }
    
    /**
     * A function to write a message to the GUI.
     * @param message The message you want to write.
     */
    private void logMessage(String message) {
        this.lstViewSystemLog.getItems().add(message);
    }

}
