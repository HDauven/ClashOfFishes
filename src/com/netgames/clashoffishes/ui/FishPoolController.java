/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netgames.clashoffishes.ui;

import com.netgames.clashoffishes.Administration;
import com.netgames.clashoffishes.TableUser;
import com.netgames.clashoffishes.User;
import com.netgames.clashoffishes.engine.GameMode;
import com.netgames.clashoffishes.interfaces.IChangeGui;
import com.netgames.clashoffishes.server.Message;
import com.netgames.clashoffishes.server.remote.IClient;
import com.netgames.clashoffishes.server.remote.IGameServer;
import com.netgames.clashoffishes.server.remote.ILobby;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
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
public class FishPoolController implements Initializable, IChangeGui
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

    private ILobby lobby;
    
    private final String gameServerURL = "rmi://localhost:1100/Server";
    IGameServer gameServer = null;

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
    public void initialize(URL url, ResourceBundle rb)
    {
        // Haal de current lobby op.
        this.lobby = Administration.get().getLobby();
        try
        {
        Administration.get().getClient().addGUIListener(this);
            lblLobbyName.setText(lobby.getPoolNameProperty());
        }
        catch (RemoteException ex)
        {
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

        try
        {
            System.out.println(this.lobby.getPoolNameProperty());
            for (IClient client : this.lobby.getClients())
            {
                this.tableUsers.add(new TableUser(client.getUsername(), "test", false));
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        finally
        {
            tbvPlayers.setItems(tableUsers);
        }

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
        TableUser tableuserUpdated = null;
        for (TableUser tableuser : this.tableUsers)
        {
            if (tableuser.getUsername().equals(Administration.get().getLoggedInUser().getUsername()) && !this.cbCharacters.getValue().equals("None"))
            {
                tableuserUpdated = tableuser;
            }
        }

        //RemoveAll for updateEvent
        tableUsers2.clear();
        tableUsers2.addAll(tableUsers);
        tableUsers.removeAll(tableUsers2);

        tableUsers2.remove(tableuserUpdated);
        if (tableuserUpdated != null)
        {
            if (tableuserUpdated.getReady() == false)
            {
                tableuserUpdated.setReady(true);
                btnReady.setText("I'm not ready!");
            }
            else
            {
                tableuserUpdated.setReady(false);
                btnReady.setText("I'm ready!");
            }
        }
        tableUsers2.add(tableuserUpdated);
        tbvPlayers.getItems().clear();
        tableUsers.addAll(tableUsers2);
        tbvPlayers.setItems(tableUsers);
    }

    @FXML
    private void btnStartGame_OnClick(ActionEvent event)
    {
        //xxx Hier zou een gameManager misschien nog toegevoegd worden aan de singleton Administratie?
        //LobbyRegistry lobbyRegistry = new LobbyRegistry();
        
        //Signal to gameserver
        //Gameserver will send start method to all clients
        //Start local gameserver
        try
        {
            System.out.println("Start game button pressed");
            lobby.startGame();
        }
        catch (RemoteException ex)
        {
            Logger.getLogger(FishPoolController.class.getName()).log(Level.SEVERE, null, ex);
        }
        //GameManager gameManager = new GameManager();
        //gameManager.start(new Stage());
    }

    @FXML
    private void btnSendMessage_OnClick(ActionEvent event)
    {
        this.SendMessage();
    }
    
    private void SendMessage() {
        try {
            lobby.broadcastMessage(new Message(Administration.get().getLoggedInUser().getUsername(),tfMessage.getText()), Administration.get().getClient());
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
    public void addUser(User user)
    {
        tableUsers.add(new TableUser(user));
        tbvPlayers.setItems(tableUsers);
    }

    private void setupGui()
    {
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
    
    /**
     * Sends the newly chosen GameMode to the currently active lobby.
     * @param gameMode One of the chosen GameModes
     */
    private void sendGameMode(String gameMode) {
        try {
            lobby.broadcastGameMode(gameMode, Administration.get().getClient());
        } catch (RemoteException ex) {
            Logger.getLogger(FishPoolController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ILobby getLobby()
    {
        return lobby;
    }
    
    /**
     * Method that looks up the Clash of Fishes Lobby Server in the name
     * registry, based on a given RMI URL.
     */
    public void gameServerLookup()
    {
        try
        {
            //gameServerURL controleren
            gameServer = (IGameServer) Naming.lookup(gameServerURL);
            gameServer.start();
        }
        catch (NotBoundException ex)
        {
            System.out.println("Server: Cannot bind Clash of Fishes server");
            System.out.println("NotBoundException: " + ex.getMessage());
        }
        catch (MalformedURLException ex)
        {
            System.out.println("Server: Cannot bind Clash of Fishes server");
            System.out.println("MalformedURLException: " + ex.getMessage());
        }
        catch (RemoteException ex)
        {
            System.out.println("Server: Cannot bind Clash of Fishes server");
            System.out.println("RemoteException: " + ex.getMessage());
        }
    }

    @Override
    public void displayMessage(String message) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                lstViewMessages.getItems().add(message);
            }
        });    
    }

    @Override
    public void displaySelectedCharacter(String characterName)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void displayReady(boolean isReady)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void displayGameMode(GameMode gameMode)
    {
        if (gameMode.equals(GameMode.EVOLUTION_OF_TIME)) {
            this.gameMode.selectToggle(rbEvolutionOfTime);
        } else if (gameMode.equals(GameMode.EVOLVED)) {
            this.gameMode.selectToggle(rbEvolved);
        } else if (gameMode.equals(GameMode.LAST_FISH_STANDING)) {
            this.gameMode.selectToggle(rbLastFishSwimming);
        }
    }

    @Override
    public void displayPlayer(String player) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
