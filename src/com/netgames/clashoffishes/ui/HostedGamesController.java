/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netgames.clashoffishes.ui;

import com.netgames.clashoffishes.Administration;
import com.netgames.clashoffishes.Lobby;
import com.netgames.clashoffishes.engine.GameMode;
import com.netgames.clashoffishes.server.remote.ILobby;
import com.netgames.clashoffishes.server.remote.IServer;
import com.netgames.clashoffishes.util.GuiUtilities;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Observable;
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
    
    private static IServer cofServer;
    private static final String cofServerURL = "rmi://localhost:1100/Server";;
    
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
        clashOfFishesServerLookup();
    }

    @FXML
    private void btnJoinGame_OnClick(ActionEvent event) {
        GuiUtilities.buildStage(paneMainForm.getScene().getWindow(), "FishPool", GuiUtilities.getFishPoolTitle());
    }

    @FXML
    private void btnBack_OnClick(ActionEvent event) {
        GuiUtilities.buildStage(paneMainForm.getScene().getWindow(), "MultiplayerMenu", GuiUtilities.getMainMenusTitle());
    }
    
    /**
     * Method that looks up the Clash of Fishes Server in the name registry, 
     * based on a given RMI URL.
     */
    private static void clashOfFishesServerLookup() {
        try {
            cofServer = (IServer) Naming.lookup(cofServerURL);
            System.out.println(cofServer.listLobbies().toString());
        } catch (NotBoundException ex) {
            System.out.println("NotBoundException: " + ex.getMessage());
        } catch (MalformedURLException ex) {
            System.out.println("MalformedURLException: " + ex.getMessage());
        } catch (RemoteException ex) {
            System.out.println("RemoteException: " + ex.getMessage());
        }
    }
    
    private void refreshServerList() {
        ArrayList<ILobby> temp = new ArrayList<>();
        for (ILobby l : temp) {
            //tbvHostedGames.getItems().add(l);
        }
    }
}
