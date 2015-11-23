package clash.of.fishes.server;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Server that keeps track of all the existing Game Lobbies for the Clash of Fishes game.
 * @author Hein Dauven
 */
public class ClashOfFishesServer extends Application {
    // Set port number
    private static final int portNumber = 1100;
    
    // Set binding name for the server
    private static final String bindingName = "Server";
    
    // Reference to server
    private Server server = null;
    
    // Constructor
    public ClashOfFishesServer() {
        // Print port number for registry
        System.out.println("Server: Port number " + portNumber);
        
        // Create server
        try {
            server = new Server();
            System.out.println("Server: server created");
        } catch (RemoteException ex) {
            System.out.println("Server: Cannot create server");
            System.out.println("Server: RemoteException: " + ex.getMessage());
            server = null;
        }
        
        // Bind chatroom using Naming
        if (server != null) {
            try {
                LocateRegistry.createRegistry(portNumber);
                Naming.rebind("//localhost:" + portNumber + "/" + bindingName, server);
            } catch (MalformedURLException ex) {
                System.out.println("Server: Cannot bind server");
                System.out.println("Server: MalformedURLException: " + ex.getMessage());
            } catch (RemoteException ex) {
                System.out.println("Server: Cannot bind server");
                System.out.println("Server: RemoteException: " + ex.getMessage());
            }
            System.out.println("Server: server bound to " + bindingName);
        } else {
            System.out.println("Server: server not bound");
        }
    }
    
    // Print IP addresses and network interfaces
    private static void printIPAddresses() {
        try {
            InetAddress localhost = InetAddress.getLocalHost();
            System.out.println("Server: IP Address: " + localhost.getHostAddress());
            // Just in case this host has multiple IP addresses....
            InetAddress[] allMyIps = InetAddress.getAllByName(localhost.getCanonicalHostName());
            if (allMyIps != null && allMyIps.length > 1) {
                System.out.println("Server: Full list of IP addresses:");
                for (InetAddress allMyIp : allMyIps) {
                    System.out.println("    " + allMyIp);
                }
            }
        } catch (UnknownHostException ex) {
            System.out.println("Server: Cannot get IP address of local host");
            System.out.println("Server: UnknownHostException: " + ex.getMessage());
        }
    }
    
    /**
     * @param args the command line arguments
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root;
        try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/clash/of/fishes/server/ui/LobbyServerGUI.fxml"));
        root = (Parent) loader.load();
        primaryStage.setTitle("Admin lobby control center");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        } catch(Exception e) {
            System.out.println(e.toString());
        }
    }  
    
    public static void main(String[] args) {
        launch(args);
        // Welcome message
        //System.out.println("Welcome to the Clash of Fishes server!");

        // Print IP addresses and network interfaces
        //printIPAddresses();

        // Create Clash Of Fishes server
        //ClashOfFishesServer cofServer = new ClashOfFishesServer();
    }
}
