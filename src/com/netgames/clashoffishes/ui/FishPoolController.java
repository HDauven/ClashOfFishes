/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netgames.clashoffishes.ui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Stef
 */
public class FishPoolController implements Initializable
{
    @FXML
    private AnchorPane paneMainForm;
    @FXML
    private Label lblLobbyName;
    @FXML
    private TableView<?> tbvPlayers;
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
    private Button btnReady;
    @FXML
    private Button btnStartGame;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO initialize components for controller
    }    

    @FXML
    private void btnReady_OnClick(ActionEvent event) {
        
    }

    @FXML
    private void btnStartGame_OnClick(ActionEvent event) {
        //TODO Hein code om applicatie op te starten.
        System.out.println("TODO Hein code om applicatie op te starten.");
        System.out.println("TODO Hein code om applicatie op te starten.");
        System.out.println("TODO Hein code om applicatie op te starten.");
    }
    
}
