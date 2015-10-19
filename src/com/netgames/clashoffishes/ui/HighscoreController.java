/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netgames.clashoffishes.ui;

import com.netgames.clashoffishes.Administration;
import com.netgames.clashoffishes.util.GuiUtilities;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Stef
 */
public class HighscoreController implements Initializable
{
    @FXML
    private Button btnBack;
    @FXML
    private AnchorPane paneMainForm;
    @FXML
    private ComboBox<?> cbGameMode;
    @FXML
    private TableView<?> tbvHighscore;
    @FXML
    private TableColumn<?, ?> clmPlayers;
    @FXML
    private TableColumn<?, ?> clmScore;

    private Administration administratie;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO initialize components for controller
        this.administratie = Administration.get();
        
        
        
        
        this.cbGameMode.setItems(null);
        
        
    }    

    @FXML
    private void btnBack_OnClick(ActionEvent event) {
        GuiUtilities.buildStage(this.paneMainForm.getScene().getWindow(), "StartMenu", GuiUtilities.getStartMenuTitle());
    }
    
}
