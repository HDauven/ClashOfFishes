/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netgames.clashoffishes.ui;

import com.netgames.clashoffishes.Administration;
import com.netgames.clashoffishes.util.GuiUtilities;
import java.lang.invoke.MethodHandles;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Stef
 */
public class StartMenuController implements Initializable
{
    private Administration administration;
    @FXML
    private AnchorPane paneMainForm;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        this.administration = Administration.get();
        System.out.println(this.administration.getLoggedInUser().getUsername());
        System.out.println(this.administration.getLoggedInUser().getEmail());
    }    

    @FXML
    private void hostGame(ActionEvent event)
    {
        //TODO Hein code om applicatie op te starten.
        System.out.println("TODO Hein code om applicatie op te starten.");
        System.out.println("TODO Hein code om applicatie op te starten.");
        System.out.println("TODO Hein code om applicatie op te starten.");
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
    private void quitGame(ActionEvent event)
    {
        GuiUtilities.buildStage(this.paneMainForm.getScene().getWindow(), "Login", GuiUtilities.LOGIN_TITLE);
    }
    
}
