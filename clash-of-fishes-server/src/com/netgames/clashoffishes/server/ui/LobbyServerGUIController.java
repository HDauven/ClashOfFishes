package com.netgames.clashoffishes.server.ui;

import com.netgames.clashoffishes.server.RegistryServer;
import com.netgames.clashoffishes.server.Server;
import com.netgames.clashoffishes.server.interfaces.ILobbyServerObserver;
import com.netgames.clashoffishes.server.remote.ILobby;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.concurrent.Task;
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
 * @author Christian Adkin
 * @author Hein Dauven
 */
public class LobbyServerGUIController implements Initializable, ILobbyServerObserver {

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
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        registry = new RegistryServer();
        registry.addGUIListener(this);
        server = registry.getServer();

        getRegistryLogMessages(registry);
    }

    @FXML
    private void btnCloseLobby_OnClick(ActionEvent event) {
        ILobby temp = lstViewServers.getSelectionModel().getSelectedItem();
        try {
            server.removeLobby(temp);
        } catch (RemoteException ex) {
            Logger.getLogger(LobbyServerGUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Method that processes the server output by calling the output ArrayList,
     * iterating over it and adding the output to the ListView. This method is
     * used only to get the logged output from before the Controller is
     * registered as an observer.
     *
     * @param message The message you want to write.
     */
    private void getRegistryLogMessages(RegistryServer registry) {
        ArrayList<String> temp = registry.getOutput();
        for (String output : temp) {
            this.lstViewSystemLog.getItems().add(output);
        }
    }

    /**
     * Executes each time a change takes place on the registry server log
     * output.
     *
     * @param message
     */
    @Override
    public void updateMessage(String message) {
        Platform.runLater(new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                ArrayList<String> temp = registry.getOutput();
                lstViewSystemLog.getItems().clear();
                lstViewSystemLog.getItems().addAll(temp);
                return null;
            }
        });
    }

    /**
     * Executes each time a change takes place on the server.
     *
     * @param lobby
     */
    @Override
    public void updateLobby(ILobby lobby) {
        Platform.runLater(new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                ArrayList<ILobby> temp = (ArrayList<ILobby>) server.getLobbies();
                lstViewServers.getItems().clear();
                lstViewServers.getItems().addAll(temp);
                return null;
            }
        });
    }
}
