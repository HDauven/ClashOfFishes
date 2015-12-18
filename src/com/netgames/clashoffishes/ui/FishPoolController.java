/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netgames.clashoffishes.ui;

import com.netgames.clashoffishes.Administration;
import com.netgames.clashoffishes.TableUser;
import com.netgames.clashoffishes.engine.GameMode;
import com.netgames.clashoffishes.interfaces.ILobbyListener;
import com.netgames.clashoffishes.server.Message;
import com.netgames.clashoffishes.server.remote.IClient;
import com.netgames.clashoffishes.server.remote.ILobby;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Stef
 */
public class FishPoolController implements Initializable, ILobbyListener {

    // TODO can't select the same character as another player.
    // TODO enable/disable character when a different character is selected.
    // TODO allow for multiple players to select character 'None'.
    // BUG  When a player pushes the 'ready' button, they are re-ordered. This shouldn't happen!
    // TODO If the host leaves, everyone should be kicked out of the lobby!
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
    private TableColumn<?, ?> clmPlayers;
    private TableColumn<?, ?> clmReady;
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
    private ListView<String> lstViewMessages;
    @FXML
    private Button btnSendMessage;
    @FXML
    private TextField tfMessage;
    @FXML
    private Label lbl_error;

    private ILobby lobby;

    private final String gameServerURL = "rmi://localhost:1100/Server";

    ObservableList<TableUser> tableUsers;
    //This object exists so the changeEvent gets triggered on tableUsers.removeAll()
    ObservableList<TableUser> tableUsers2;
    URL playerDir = this.getClass().getResource("/com/netgames/clashoffishes/images/player/");

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lbl_error.setVisible(false);
        // Haal de current lobby op.
        this.lobby = Administration.get().getLobby();
        try {
            Administration.get().getClient().addGUIListener(this);
            lblLobbyName.setText(lobby.getPoolNameProperty());
        } catch (RemoteException ex) {
            Logger.getLogger(FishPoolController.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Voeg alle characterNamen toe aan de listbox.
        List<String> characterNames = new ArrayList<>();
        characterNames.add("None");
        characterNames.add("Bubbles");
        characterNames.add("Cleo");
        characterNames.add("Fred");
        characterNames.add("Gill");
        this.cbCharacters.setItems(FXCollections.observableArrayList(characterNames));

        setupGui();

        try {
            System.out.println(this.lobby.getPoolNameProperty());
            lobby.broadcastPlayer(Administration.get().getClient().getUsername(), Administration.get().getClient());
            for (IClient client : this.lobby.getClients()) {
                this.tableUsers.add(new TableUser(client.getUsername(), "None", false));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            tbvPlayers.setItems(tableUsers);
        }

    }

    @FXML
    private void cbCharacters_OnChanged(ActionEvent event) {
        String selectedCharacter = this.cbCharacters.getValue();
        URL playerDir = this.getClass().getResource("/com/netgames/clashoffishes/images/player/");
        switch (selectedCharacter) {
            case "Bubbles":
                System.out.println("Bubbles has been selected");
                sendCharacter("Bubbles");
                this.pictCharacter.setImage(new Image(playerDir.toString() + "BubblesIcon.png", 80, 51, true, false, true));
                break;
            case "Cleo":
                System.out.println("Cleo has been selected");
                sendCharacter("Cleo");
                this.pictCharacter.setImage(new Image(playerDir.toString() + "CleoIcon.png", 80, 50, true, false, true));
                break;
            case "Fred":
                System.out.println("Fred has been selected");
                sendCharacter("Fred");
                this.pictCharacter.setImage(new Image(playerDir.toString() + "FredIcon.png", 80, 57, true, false, true));
                break;
            case "Gill":
                System.out.println("Gill has been selected");
                sendCharacter("Gill");
                this.pictCharacter.setImage(new Image(playerDir.toString() + "GillIcon.png", 80, 47, true, false, true));
                break;
            default:
                System.out.println("No character selected");
                this.pictCharacter.setImage(null);
                break;
        }
    }

    /**
     * Sends the newly chosen isReady boolean to the currently active lobby to
     * indicate whether a player is ready or not.
     *
     * @param isReady One of the chosen isReady options
     */
    private void sendCharacter(String characterName) {
        try {
            Administration.get().getClient().setCharacter(characterName);
            lobby.broadcastCharacter(characterName, Administration.get().getClient());
        } catch (RemoteException ex) {
            Logger.getLogger(FishPoolController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void btnReady_OnClick(ActionEvent event) {
        Platform.runLater(new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                TableUser tableuserUpdated = null;
                for (TableUser tableuser : tableUsers) {
                    if (tableuser.getUsername().equals(Administration.get().getLoggedInUser().getUsername())) {
                        tableuserUpdated = tableuser;
                    }
                }

                //RemoveAll for updateEvent
                if (!cbCharacters.getValue().equals("None")) {
                    if (tableuserUpdated.getReady() == false) {
                        tableuserUpdated.setReady(true);
                        sendReady(true);
                        btnReady.setText("I'm not ready!");
                    } else {
                        tableuserUpdated.setReady(false);
                        sendReady(false);
                        btnReady.setText("I'm ready!");
                    }
                }
                tbvPlayers.refresh();
                return null;
            }
        });
    }

    /**
     * Sends the newly chosen isReady boolean to the currently active lobby to
     * indicate whether a player is ready or not.
     *
     * @param isReady One of the chosen isReady options
     */
    private void sendReady(boolean isReady) {
        try {
            Administration.get().getClient().setIsReady(isReady);
            lobby.broadcastReady(isReady, Administration.get().getClient());
            System.out.println(Administration.get().getClient().getIsReady());
        } catch (RemoteException ex) {
            Logger.getLogger(FishPoolController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void btnStartGame_OnClick(ActionEvent event) {
        boolean arePlayersReady = true;
        boolean areCharactersSelected = true;
        try {
            for (IClient client : lobby.getClients()) {
                if (!client.getIsReady() == true) {
                    arePlayersReady = false;
                }
                if (client.getCharacter().equalsIgnoreCase("None")) {
                    areCharactersSelected = false;
                }
            }
            if (arePlayersReady == true && areCharactersSelected == true) {
                this.lobby.startGame();
            }
        } catch (RemoteException ex) {
            Logger.getLogger(FishPoolController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void btnSendMessage_OnClick(ActionEvent event) {
        this.SendMessage();
    }

    private void SendMessage() {
        try {
            lobby.broadcastMessage(new Message(Administration.get().getLoggedInUser().getUsername(), tfMessage.getText()), Administration.get().getClient());
        } catch (RemoteException ex) {
            Logger.getLogger(FishPoolController.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.tfMessage.clear();
    }

    @FXML
    private void OnEnterClick(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            this.SendMessage();
        }
    }

    /**
     * Add a user which is not ready yet with the username to the player-table
     *
     * @param user user which is added to the game
     */
    public void addUser(IClient user) {
        tableUsers.add(new TableUser(user));
        tbvPlayers.setItems(tableUsers);
    }

    public void removeUser(IClient user) {
        tableUsers.remove(new TableUser(user));
        tbvPlayers.setItems(tableUsers);
    }

    private void setupGui() {
        if (!Administration.get().getClient().getIsHost()) {
            btnStartGame.setVisible(false);
            rbEvolutionOfTime.setDisable(true);
            rbEvolved.setDisable(true);
            rbLastFishSwimming.setDisable(true);
        } else {
            this.rbEvolved.setOnAction((ActionEvent event) -> {
                sendGameMode(GameMode.EVOLVED.name());
            });

            this.rbEvolutionOfTime.setOnAction((ActionEvent event) -> {
                sendGameMode(GameMode.EVOLUTION_OF_TIME.name());
            });

            this.rbLastFishSwimming.setOnAction((ActionEvent event) -> {
                sendGameMode(GameMode.LAST_FISH_STANDING.name());
            });
        }

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

    /**
     * Sends the newly chosen GameMode to the currently active lobby.
     *
     * @param gameMode One of the chosen GameModes
     */
    private void sendGameMode(String gameMode) {
        try {
            lobby.broadcastGameMode(gameMode, Administration.get().getClient());
        } catch (RemoteException ex) {
            Logger.getLogger(FishPoolController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ILobby getLobby() {
        return lobby;
    }

    @Override
    public void displayMessage(String message) {
        Platform.runLater(() -> {
            lstViewMessages.getItems().add(message);
        });
    }

    @Override
    public void displaySelectedCharacter(String characterName, IClient sender) {
        Platform.runLater(new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                for (TableUser user : tableUsers) {
                    if (user.getUsername().equalsIgnoreCase(sender.getUsername())) {
                        user.setCharacter(characterName);
                        tbvPlayers.setItems(tableUsers);
                        tbvPlayers.refresh();
                        System.out.println(user.getUsername());
                        System.out.println(sender.getUsername() + " " + characterName);
                    }
                }
                return null;
            }
        });
    }

    @Override
    public void displayReady(boolean isReady, IClient sender) {
        Platform.runLater(new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                for (TableUser user : tableUsers) {
                    if (user.getUsername().equalsIgnoreCase(sender.getUsername())) {
                        user.setReady(isReady);
                        tbvPlayers.setItems(tableUsers);
                        tbvPlayers.refresh();
                        System.out.println(user.getUsername());
                        System.out.println(sender.getUsername() + " " + isReady);
                    }
                }
                return null;
            }
        });
    }

    @Override
    public void displayGameMode(GameMode gameMode) {
        if (gameMode.equals(GameMode.EVOLUTION_OF_TIME)) {
            Platform.runLater(() -> {
                this.gameMode.selectToggle(rbEvolutionOfTime);
            });
        } else if (gameMode.equals(GameMode.EVOLVED)) {
            Platform.runLater(() -> {
                this.gameMode.selectToggle(rbEvolved);
            });
        } else if (gameMode.equals(GameMode.LAST_FISH_STANDING)) {
            Platform.runLater(() -> {
                this.gameMode.selectToggle(rbLastFishSwimming);
            });
        }
    }

    @Override
    public void displayPlayer(String player, IClient sender) {
        Platform.runLater(new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                tableUsers.clear();
                for (IClient client : lobby.getClients()) {
                    tableUsers.add(new TableUser(client.getUsername(), client.getCharacter(), client.getIsReady()));
                }
                //tableUsers.add(new TableUser(player, "None", false));
                tbvPlayers.setItems(tableUsers);
                tbvPlayers.refresh();
                return null;
            }
        });
    }
}
