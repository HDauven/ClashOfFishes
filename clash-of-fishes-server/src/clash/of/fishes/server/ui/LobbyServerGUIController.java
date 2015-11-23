/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clash.of.fishes.server.ui;

import java.net.URL;
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
    private ListView<?> lstViewSystemLog;
    @FXML
    private Label lblSystemLog;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnCloseLobby_OnClick(ActionEvent event) {
    }
    
}
