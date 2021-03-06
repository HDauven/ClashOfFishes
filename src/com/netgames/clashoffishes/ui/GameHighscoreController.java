package com.netgames.clashoffishes.ui;

import com.netgames.clashoffishes.Highscore;
import com.netgames.clashoffishes.util.GuiUtilities;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Stef Philipsen
 */
public class GameHighscoreController implements Initializable {

    @FXML
    private AnchorPane paneMainForm;
    @FXML
    private TableView<?> tbvHighscore;
    @FXML
    private TableColumn<Highscore, String> clmPlayers;
    @FXML
    private TableColumn<Highscore, String> clmScore;
    @FXML
    private Button btnBack;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {     
        this.clmPlayers.setCellValueFactory(new PropertyValueFactory<Highscore, String>("Username"));
        this.clmScore.setCellValueFactory(new PropertyValueFactory<Highscore, String>("Score"));
    }

    @FXML
    private void btnBack_OnClick(ActionEvent event) {
        GuiUtilities.buildStage(this.paneMainForm.getScene().getWindow(), "StartMenu", GuiUtilities.getMainMenusTitle());
    }
}
