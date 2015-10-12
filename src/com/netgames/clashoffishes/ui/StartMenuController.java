/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netgames.clashoffishes.ui;

import com.netgames.clashoffishes.Administration;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author Stef
 */
public class StartMenuController implements Initializable
{
    private Administration administration;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        this.administration = Administration.get();
        System.out.println(this.administration.getUser("MuK").getEmail());
    }    

    @FXML
    private void hostGame(ActionEvent event)
    {
    }

    @FXML
    private void joinGame(ActionEvent event)
    {
    }

    @FXML
    private void highscore(ActionEvent event)
    {
    }

    @FXML
    private void quitGame(ActionEvent event)
    {
    }
    
}
