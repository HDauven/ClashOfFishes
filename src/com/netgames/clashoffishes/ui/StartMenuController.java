package com.netgames.clashoffishes.ui;

import com.netgames.clashoffishes.util.GuiUtilities;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Christian Adkin
 */
public class StartMenuController implements Initializable {

    @FXML
    private AnchorPane paneMainForm;
    @FXML
    private Button btnSingleplayer;
    @FXML
    private Button btnMultiplayer;
    @FXML
    private Button btnOptions;
    @FXML
    private Button btnHelp;
    @FXML
    private Button btnCredits;
    @FXML
    private Button btnAchievements;
    @FXML
    private ImageView pictCoFLogo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        URL logoDir = this.getClass().getResource("/com/netgames/clashoffishes/images/logo.png");
        this.pictCoFLogo.setImage(new Image(logoDir.toString(), 777, 471, true, false, true));

    }

    @FXML
    private void btnSingleplayer_OnClick(ActionEvent event) {
        //xxx Hier zou een gameManager misschien nog toegevoegd worden aan de singleton Administratie?
        GuiUtilities.buildStage(this.paneMainForm.getScene().getWindow(), "SinglePlayer", GuiUtilities.TITLE_SINGLE_PLAYER);
    }

    @FXML
    private void btnMultiplayer_OnClick(ActionEvent event) {
        GuiUtilities.buildStage(this.paneMainForm.getScene().getWindow(), "MultiplayerMenu", GuiUtilities.getMainMenusTitle());
    }

    @FXML
    private void btnOptions_OnClick(ActionEvent event) {
        // TODO Options menu maken
    }

    @FXML
    private void btnHelp_OnClick(ActionEvent event) {
        // TODO Help menu maken
    }

    @FXML
    private void btnCredits_OnClick(ActionEvent event) {
        // TODO Credits menu maken
    }

    @FXML
    private void btnAchievements_OnClick(ActionEvent event) {
        // TODO Achievements menu maken
    }
}
