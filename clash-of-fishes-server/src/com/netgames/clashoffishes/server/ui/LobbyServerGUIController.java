package com.netgames.clashoffishes.server.ui;

import com.netgames.clashoffishes.server.RegistryServer;
import com.netgames.clashoffishes.server.Server;
import com.netgames.clashoffishes.server.remote.ILobby;
import java.net.URL;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
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
public class LobbyServerGUIController implements Initializable, Observer {

    @FXML
    private AnchorPane paneMainForm;
    @FXML
    private ListView<ILobby> lstViewServers;
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

    // Reference to server
    private RegistryServer registry;
    private Server server;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        registry = new RegistryServer();
        getRegistryLogMessages(registry);
        registry.addObserver(this);
        registry.getServer().addObserver(this);
    }

    @FXML
    private void btnCloseLobby_OnClick(ActionEvent event) {
    }
    
    /**
     * Method that processes the server output by calling the output ArrayList,
     * iterating over it and adding the output to the ListView.
     * This method is used only to get the logged output from before the Controller
     * is registered as an observer.
     * @param message The message you want to write.
     */
    private void getRegistryLogMessages(RegistryServer registry) {
        ArrayList<String> temp = registry.getOutput();
        for (String output : temp) {
            this.lstViewSystemLog.getItems().add(output);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof RegistryServer) {
            registry = (RegistryServer) o;
            this.lstViewSystemLog.getItems().add(registry.getLastOutput());
        } else if (o instanceof Server) {
            server = (Server) o;
            this.lstViewServers.getItems().add(server.getLastLobby());
        } else { 
            registry.logMessage("Something went really wrong..."); 
        }
    }

}
