/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netgames.clashoffishes.ui;

import com.netgames.clashoffishes.Administration;
import com.netgames.clashoffishes.Lobby;
import com.netgames.clashoffishes.engine.GameMode;
import com.netgames.clashoffishes.util.GuiUtilities;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
 * @author Stef
 */
public class HostedGamesController implements Initializable {

    @FXML
    private AnchorPane paneMainForm;

    @FXML
    private Button btnJoinGame;
    @FXML
    private Button btnBack;

    @FXML
    private TableView<Lobby> tbvHostedGames;
    @FXML
    private TableColumn<Lobby, String> clmPoolName;
    @FXML
    private TableColumn<Lobby, String> clmPlayers;
    @FXML
    private TableColumn<Lobby, String> clmGameMode;

    private Administration administration;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //TODO initialize components for controller
        this.administration = Administration.get();
        
        clmPoolName.setCellValueFactory(new PropertyValueFactory<Lobby, String>("PoolNameProperty"));
        clmPlayers.setCellValueFactory(new PropertyValueFactory<Lobby, String>("PlayersProperty"));
        clmGameMode.setCellValueFactory(new PropertyValueFactory<Lobby, String>("GameModeProperty"));
        
        Lobby lobby = new Lobby();
        lobby.addUser(Administration.get().getLoggedInUser());
        lobby.setGameMode(GameMode.EVOLVED);
        
        administration.setCurrentLobby(lobby);
        
        ObservableList<Lobby> lobbies = FXCollections.observableArrayList(lobby, lobby, lobby, lobby);

        
        tbvHostedGames.setItems(lobbies);
        //tbvHostedGames.getColumns().addAll(clmPoolName, clmPlayers, clmGameMode);
        //TODO Aan lobby werken
    }

    @FXML
    private void btnJoinGame_OnClick(ActionEvent event) {
        Object controllerObject = GuiUtilities.buildStage(paneMainForm.getScene().getWindow(), "FishPool", GuiUtilities.getFishPoolTitle());
        FishPoolController controller = (FishPoolController) controllerObject;
        controller.addUser(Administration.get().getLoggedInUser());
    }

    @FXML
    private void btnBack_OnClick(ActionEvent event) {
        GuiUtilities.buildStage(paneMainForm.getScene().getWindow(), "MultiplayerMenu", GuiUtilities.getMainMenusTitle());
    }

}
