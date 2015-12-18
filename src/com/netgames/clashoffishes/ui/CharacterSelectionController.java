/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netgames.clashoffishes.ui;

import com.netgames.clashoffishes.engine.GameManager;
import com.netgames.clashoffishes.engine.GameMode;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author MuK
 */
public class CharacterSelectionController implements Initializable {

    @FXML
    private Label lblCharacter;
    @FXML
    private ComboBox<String> cbCharacters;
    @FXML
    private ImageView pictCharacter;
    @FXML
    private Button btnStartGame;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        List<String> characterNames = new ArrayList<>();
        characterNames.add("Bubbles");
        characterNames.add("Cleo");
        characterNames.add("Fred");
        characterNames.add("Gill");
        this.cbCharacters.setItems(FXCollections.observableArrayList(characterNames));
        this.cbCharacters.getSelectionModel().select(0);
        this.pictCharacter.setImage(new Image("/com/netgames/clashoffishes/images/player/" + "BubblesIcon.png", 80, 51, true, false, true));
    }

    @FXML
    private void cbCharacters_OnChanged(ActionEvent event) {
        String selectedCharacter = this.cbCharacters.getValue();
        URL playerDir = this.getClass().getResource("/com/netgames/clashoffishes/images/player/");
        switch (selectedCharacter) {
            case "Bubbles":
                System.out.println("Bubbles has been selected");
                this.pictCharacter.setImage(new Image(playerDir.toString() + "BubblesIcon.png", 80, 51, true, false, true));
                break;
            case "Cleo":
                System.out.println("Cleo has been selected");
                this.pictCharacter.setImage(new Image(playerDir.toString() + "CleoIcon.png", 80, 50, true, false, true));
                break;
            case "Fred":
                System.out.println("Fred has been selected");
                this.pictCharacter.setImage(new Image(playerDir.toString() + "FredIcon.png", 80, 57, true, false, true));
                break;
            case "Gill":
                System.out.println("Gill has been selected");
                this.pictCharacter.setImage(new Image(playerDir.toString() + "GillIcon.png", 80, 47, true, false, true));
                break;
            default:
                System.out.println("No character selected");
                this.pictCharacter.setImage(null);
                break;
        }
    }

    @FXML
    private void btnStartGame_OnClick(ActionEvent event) {
        GameManager gameManager = new GameManager(this.cbCharacters.getValue(), 0, 0, GameMode.EVOLUTION_OF_TIME);
        gameManager.start(new Stage());
    }

}
