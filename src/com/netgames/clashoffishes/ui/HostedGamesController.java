/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netgames.clashoffishes.ui;

import com.netgames.clashoffishes.Administration;
import com.netgames.clashoffishes.server.Lobby;
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
    private TableView<ILobby> tbvHostedGames;
    @FXML
    private TableColumn<ILobby, String> clmPoolName;
    @FXML
    private TableColumn<ILobby, String> clmPlayers;
    @FXML
    private TableColumn<ILobby, String> clmGameMode;

    private Administration administration;

    private IServer cofServer;
    private final String cofServerURL = "rmi://145.93.173.168:1100/Server";
    private List<ILobby> lobbyList = new ArrayList<>();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //TODO initialize components for controller
        this.administration = Administration.get();

        clmPoolName.setCellValueFactory(new PropertyValueFactory<ILobby, String>("PoolNameProperty"));
        clmPlayers.setCellValueFactory(new PropertyValueFactory<ILobby, String>("PlayersProperty"));
        clmGameMode.setCellValueFactory(new PropertyValueFactory<ILobby, String>("GameModeProperty"));

        clashOfFishesServerLookup();
        
        ObservableList<ILobby> lobbies = FXCollections.observableArrayList();
        for (ILobby lobby : this.lobbyList) {
            System.out.println(lobby.toString());
            try {
                System.out.println(lobby.getClients().get(0).getUsername());
                lobbies.addAll(FXCollections.observableArrayList(lobby));  
            } catch (RemoteException ex) {
                Logger.getLogger(HostedGamesController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        tbvHostedGames.setItems(lobbies);
    }

    @FXML
    private void btnJoinGame_OnClick(ActionEvent event) {
        Object controllerObject = GuiUtilities.buildStage(paneMainForm.getScene().getWindow(), "FishPool", GuiUtilities.getFishPoolTitle());
        FishPoolController controller = (FishPoolController) controllerObject;
        //controller.addUser(Administration.get().getLoggedInUser());
    }

    @FXML
    private void btnBack_OnClick(ActionEvent event) {
        GuiUtilities.buildStage(paneMainForm.getScene().getWindow(), "MultiplayerMenu", GuiUtilities.getMainMenusTitle());
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

    private void refreshServerList() {
        ArrayList<ILobby> temp = new ArrayList<>();
        for (ILobby l : temp) {
            //tbvHostedGames.getItems().add(l);
        }
    }
}
