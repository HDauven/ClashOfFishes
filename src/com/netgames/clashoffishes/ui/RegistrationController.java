/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netgames.clashoffishes.ui;

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
 * @author Stef
 */
public class RegistrationController implements Initializable {

    @FXML
    private AnchorPane paneMainForm;
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPassword;
    @FXML
    private TextField txtConfirmPassword;
    @FXML
    private TextField txtEmail;
    @FXML
    private Button btnRegister;
    @FXML
    private Button btnCancel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void btnRegister_OnClick(ActionEvent event) {
    }

    @FXML
    private void btnCancel_OnClick(ActionEvent event) {
        GuiUtilities.buildStage(this.paneMainForm.getScene().getWindow(), "Login", "Login");  
    }

}
