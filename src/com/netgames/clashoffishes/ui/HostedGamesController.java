/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netgames.clashoffishes.ui;

import com.netgames.clashoffishes.Administration;
import com.netgames.clashoffishes.server.Client;
import com.netgames.clashoffishes.server.Lobby;
import com.netgames.clashoffishes.server.LobbyRegistry;
import com.netgames.clashoffishes.server.remote.ILobby;
import com.netgames.clashoffishes.server.remote.IServer;
import com.netgames.clashoffishes.util.GuiUtilities;
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
import javafx.concurrent.Task;
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
    private Button btnRefreshLobbyList;

    @FXML
    private TableView<ILobby> tbvHostedGames;
    @FXML
    private TableColumn<ILobby, String> clmPoolName;
    @FXML
    private TableColumn<ILobby, String> clmPlayers;
    @FXML
    private TableColumn<ILobby, String> clmGameMode;

    private Administration administration;

    private IServer cofServer;
    private final String cofServerURL = "rmi://145.93.173.122:1100/Server";
    private List<ILobby> lobbyList = new ArrayList<>();

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //TODO initialize components for controller
        this.administration = Administration.get();

        clmPoolName.setCellValueFactory(new PropertyValueFactory<>("PoolNameProperty"));
        clmPlayers.setCellValueFactory(new PropertyValueFactory<>("PlayersProperty"));
        clmGameMode.setCellValueFactory(new PropertyValueFactory<>("GameModeProperty"));
        
        getNewLobbies();
    }

    @FXML
    private void btnJoinGame_OnClick(ActionEvent event) {
//        Task task = new Task<Void>() {
//            @Override
//            protected Void call() throws Exception {
//                // TODO get lobby, create client, register client.
        try {
                for (ILobby lobby : cofServer.listLobbies()) {
                    ILobby temp = tbvHostedGames.getSelectionModel().getSelectedItem();
                    if (temp.equals(lobby)) {
                        System.out.println("Diz niggah enterz dem if");
                        Administration.get().setLobby(lobby);
                        Administration.get().setClient(new Client(Administration.get().getLoggedInUser().getUsername(), lobby));
                        System.out.println("Diz niggah enterz dem if");
                    }
                }
                Platform.runLater(() -> {
                    GuiUtilities.buildStage(paneMainForm.getScene().getWindow(), "FishPool", GuiUtilities.getFishPoolTitle());
                });
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
//                return null;
//            }
//        };
//        (new Thread(task)).start();
    }

    @FXML
    private void btnBack_OnClick(ActionEvent event) {
        GuiUtilities.buildStage(paneMainForm.getScene().getWindow(), "MultiplayerMenu", GuiUtilities.getMainMenusTitle());
    }
    
    @FXML
    private void btnRefreshLobbyList_OnClick(ActionEvent event) {
        getNewLobbies();
    }
    
    /**
     * Executes the Clash of Fishes server lookup and Lobby list refresh on a 
     * different thread to offload the JAT.
     */
    private void getNewLobbies() {
        Task task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                clashOfFishesServerLookup();
                refreshServerList();
                return null;
            }
        };
        (new Thread(task)).start();
    }

    /**
     * Method that looks up the Clash of Fishes Server in the name registry,
     * based on a given RMI URL.
     */
    private void clashOfFishesServerLookup() {
        try {
            cofServer = (IServer) Naming.lookup(cofServerURL);
            //System.out.println(cofServer.listLobbies().toString());
            lobbyList = (List<ILobby>) cofServer.listLobbies();
        } catch (NotBoundException ex) {
            System.out.println("NotBoundException: " + ex.getMessage());
        } catch (MalformedURLException ex) {
            System.out.println("MalformedURLException: " + ex.getMessage());
        } catch (RemoteException ex) {
            System.out.println("RemoteException: " + ex.getMessage());
        }
    }

    /**
     * Method that refreshes the GUI with the latest available list of lobbies.
     */
    private void refreshServerList() {
        ObservableList<ILobby> lobbies = FXCollections.observableArrayList();
        // Does the same as for (ILobby lobby : this.lobbyList) { lobbies.addAll(lobby); }
        this.lobbyList.stream().forEach((lobby) -> {
            lobbies.addAll(FXCollections.observableArrayList(lobby));
        });
        tbvHostedGames.setItems(lobbies);
    }
}
