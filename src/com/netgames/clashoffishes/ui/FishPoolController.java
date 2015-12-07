/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netgames.clashoffishes.ui;

import com.netgames.clashoffishes.Administration;
import com.netgames.clashoffishes.TableUser;
import com.netgames.clashoffishes.User;
import com.netgames.clashoffishes.engine.GameManager;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Stef
 */
public class FishPoolController implements Initializable
{
    // TODO can't set 'ready' when character 'None' selected.
    // TODO can't select the same character as another player.
    // TODO update GUI when a different character is selected.
    // TODO enable/disable character when a different character is selected.
    // TODO allow for multiple players to select character 'None'.
    @FXML
    private AnchorPane paneMainForm;
    @FXML
    private Label lblLobbyName;
    @FXML
    private TableView tbvPlayers;
    @FXML
    private RadioButton rbLastFishSwimming;
    @FXML
    private RadioButton rbEvolutionOfTime;
    @FXML
    private RadioButton rbEvolved;
    @FXML
    private TableColumn<?, ?> clmPlayers;
    @FXML
    private TableColumn<?, ?> clmReady;
    @FXML
    private TableColumn<?, ?> clmCharacter;
    @FXML
    private Button btnReady;
    @FXML
    private Button btnStartGame;
    @FXML
    private ToggleGroup gameMode;
    @FXML
    private ImageView pictCharacter;
    @FXML
    private ComboBox<String> cbCharacters;
    @FXML
    private Label lblCharacter;
    @FXML
    private ListView<?> lstViewMessages;
    @FXML
    private Button btnSendMessage;
    @FXML
    private TextField tfMessage;

    ObservableList<TableUser> tableUsers;
    //This object exists so the changeEvent gets triggered on tableUsers.removeAll()
    ObservableList<TableUser> tableUsers2;
    URL playerDir = this.getClass().getResource("/com/netgames/clashoffishes/images/player/");

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO initialize components for controller
        List<String> characterNames = new ArrayList<>();
        characterNames.add("None");
        characterNames.add("Bubbles");
        characterNames.add("Cleo");
        characterNames.add("Fred");
        characterNames.add("Gill");
        this.cbCharacters.setItems(FXCollections.observableArrayList(characterNames));

        clmPlayers = new TableColumn("Players");
        clmCharacter = new TableColumn("Character");
        clmReady = new TableColumn("Ready");

        clmPlayers.prefWidthProperty().bind(tbvPlayers.widthProperty().multiply(0.50));
        clmCharacter.prefWidthProperty().bind(tbvPlayers.widthProperty().multiply(0.22));
        clmReady.prefWidthProperty().bind(tbvPlayers.widthProperty().multiply(0.22));

        tbvPlayers.getColumns().addAll(clmPlayers, clmCharacter, clmReady);
        tableUsers = FXCollections.observableArrayList();
        this.clmPlayers.setCellValueFactory(new PropertyValueFactory<>("Username"));
        this.clmCharacter.setCellValueFactory(new PropertyValueFactory<>("Character"));
        this.clmReady.setCellValueFactory(new PropertyValueFactory<>("Ready"));

        this.tbvPlayers.setItems(tableUsers);
        tableUsers2 = FXCollections.observableArrayList();
        rbLastFishSwimming.selectedProperty().set(true);
        cbCharacters.getSelectionModel().select(0);
    }

    @FXML
    private void cbCharacters_OnChanged(ActionEvent event)
    {
        String selectedCharacter = this.cbCharacters.getValue();
        URL playerDir = this.getClass().getResource("/com/netgames/clashoffishes/images/player/");
        switch (selectedCharacter)
        {
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
    private void btnReady_OnClick(ActionEvent event)
    {
        TableUser tuUpdated = null;
        for (TableUser tu : this.tableUsers)
        {
            if (tu.getUsername().equals(Administration.get().getLoggedInUser().getUsername()))
            {
                tuUpdated = tu;
            }
        }
        //RemoveAll for updateEvent
        tableUsers2.clear();
        tableUsers2.addAll(tableUsers);
        tableUsers.removeAll(tableUsers2);

        tableUsers2.remove(tuUpdated);
        if (tuUpdated != null)
        {
            if (tuUpdated.getReady() == false)
            {
                tuUpdated.setReady(true);
                btnReady.setText("I'm not ready!");
            }
            else
            {
                tuUpdated.setReady(false);
                btnReady.setText("I'm ready!");
            }
        }
        tableUsers2.add(tuUpdated);
        tbvPlayers.getItems().clear();
        tableUsers.addAll(tableUsers2);
        tbvPlayers.setItems(tableUsers);
    }

    @FXML
    private void btnStartGame_OnClick(ActionEvent event)
    {
        //xxx Hier zou een gameManager misschien nog toegevoegd worden aan de singleton Administratie?
        GameManager gameManager = new GameManager();
        gameManager.start(new Stage());
    }

    @FXML
    private void btnSendMessage_OnClick(ActionEvent event)
    {

    }

    /**
     * Add a user which is not ready yet with the username to the player-table
     *
     * @param user user which is added to the game
     */
    public void addUser(User user)
    {
        tableUsers.add(new TableUser(user));
        tbvPlayers.setItems(tableUsers);
    }
}
