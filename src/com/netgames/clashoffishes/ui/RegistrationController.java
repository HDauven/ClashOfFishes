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

    //Datafields
    private Administration administration;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.administration = Administration.get();
    }

    @FXML
    private void btnRegister_OnClick(ActionEvent event) {
        
        //Retrieve all the text from the input fields.
        String username, password, confirmedPassword, email;
        username = this.txtUsername.getText();
        password = this.txtPassword.getText();
        confirmedPassword = this.txtConfirmPassword.getText();
        email = this.txtEmail.getText();
        
        //Check if the password and confirmPassword fields are equeal
            // if so we try to add the user into the administration
            // we tell the user about the result.
        if (password.equals(confirmedPassword)) {
            if (this.administration.addUser(username,password,email)) {
                //Gebruiker is toegevoegd
                System.out.println("Gebruiker is toegevoegd");
                ClearTextFields();
            } else {
                //User is not added into database. Reason: Username or Email already in use.
                System.out.println("User is not added into database. Reason: Username or Email already in use.");
            }
        } else {
            //password and confirmedPassword do not match
            System.out.println("password and confirmedPassword do not match");
        }  
    }

    @FXML
    private void btnCancel_OnClick(ActionEvent event) {
        GuiUtilities.buildStage(this.paneMainForm.getScene().getWindow(), "Login", "Login");  
    }

    private void ClearTextFields () {
        this.txtUsername.setText("");
        this.txtPassword.setText("");
        this.txtConfirmPassword.setText("");
        this.txtEmail.setText("");
    }
}
