/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netgames.clashoffishes.ui;

import com.netgames.clashoffishes.Administration;
import com.netgames.clashoffishes.engine.GameManager;
import com.netgames.clashoffishes.util.GuiUtilities;
import java.lang.invoke.MethodHandles;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Stef
 */
public class MultiplayerMenuController implements Initializable
{
    private Administration administration;
    @FXML
    private AnchorPane paneMainForm;
    @FXML
    private Button btnBack;
    @FXML
    private ImageView pictCoFLogo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        this.administration = Administration.get();
        URL logoDir = this.getClass().getResource("/com/netgames/clashoffishes/images/logo.png");
        this.pictCoFLogo.setImage(new Image(logoDir.toString(), 777, 471, true, false, true));
        System.out.println(this.administration.getLoggedInUser().getUsername());
        System.out.println(this.administration.getLoggedInUser().getEmail());
    }    

    @FXML
    private void hostGame(ActionEvent event)
    {
        //xxx Tweede iteratie: 
        System.out.println("Not niet werkend voor de 1e iteratie");
    }

    @FXML
    private void joinGame(ActionEvent event)
    {
        GuiUtilities.buildStage(this.paneMainForm.getScene().getWindow(), "HostedGames", GuiUtilities.HOSTED_GAMES_TITLE);
    }

    @FXML
    private void highscore(ActionEvent event)
    {
        GuiUtilities.buildStage(this.paneMainForm.getScene().getWindow(), "Highscore", GuiUtilities.HIGHSCORE_TITLE);
    }

    @FXML
    private void btnBack_OnClick(ActionEvent event)
    {
        GuiUtilities.buildStage(this.paneMainForm.getScene().getWindow(), "StartMenu", GuiUtilities.getMainMenusTitle());
    }

    
}
