/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netgames.clashoffishes.ui;

import com.netgames.clashoffishes.*;
import com.netgames.clashoffishes.data.DatabaseConnector;
import com.netgames.clashoffishes.util.GuiUtilities;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Christian Adkin
 */
public class LoginController implements Initializable {

    @FXML
    private AnchorPane paneMainForm;
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPassword;
    @FXML
    private Button btnLogin;
    @FXML
    private Button btnRegister;

    // Datafields
    private String userIdentifier, password, email;

    private Administration administration;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.administration = Administration.get();
    }

    @FXML
    private void login_OnClick(ActionEvent event) {
        this.userIdentifier = this.txtUsername.getText();
        this.password = this.txtPassword.getText();
        
        if (this.administration.logIn(userIdentifier, password) == null) {
            this.userIdentifier = null;
            this.password = null;
        } else {
            GuiUtilities.buildStage(this.paneMainForm.getScene().getWindow(), "StartMenu", GuiUtilities.getStartMenuTitle());
        }
    }

    @FXML
    private void register_OnClick(ActionEvent event) {     
        GuiUtilities.buildStage(this.paneMainForm.getScene().getWindow(), "Registration", GuiUtilities.REGISTRATION_TITLE);   
    }

}
