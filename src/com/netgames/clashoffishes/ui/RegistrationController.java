/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netgames.clashoffishes.ui;

import com.netgames.clashoffishes.Administration;
import com.netgames.clashoffishes.util.GuiUtilities;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

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
    @FXML
    private Label lbl_error;

    //Datafields
    private Administration administration;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.administration = Administration.get();
        lbl_error.setTextFill(Color.RED);
        lbl_error.setVisible(false);
    }

    @FXML
    private void btnRegister_OnClick(ActionEvent event) {

        //Retrieve all the text from the input fields.
        String username, password, confirmedPassword, email;
        username = this.txtUsername.getText();
        password = this.txtPassword.getText();
        confirmedPassword = this.txtConfirmPassword.getText();
        email = this.txtEmail.getText();

        //Check if the password and confirmPassword fields are equal
        // if so we try to add the user into the administration
        // we tell the user about the result.
        if (password.equals(confirmedPassword)) {
            if (this.administration.addUser(username, password, email)) {
                //Gebruiker is toegevoegd
                System.out.println("Gebruiker is toegevoegd");

                Stage stage = (Stage) paneMainForm.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(GuiUtilities.class.getResource("/com/netgames/clashoffishes/ui/" + "Login" + ".fxml"));
                Parent root;
                try {
                    root = (Parent) loader.load();
                    stage.setTitle("Login");
                    stage.setScene(new Scene(root));
                    LoginController loginController = (LoginController) loader.getController();
                    loginController.setUsername(username);
                    stage.show();
                } catch (IOException ex) {
                    Logger.getLogger(RegistrationController.class.getName()).log(Level.SEVERE, null, ex);
                }
                ClearTextFields();
            } else {
                //User is not added into database. Reason: Username or Email already in use.
                System.out.println("User is not added into database. Reason: Username or Email already in use.");
                setErrorLabel("Username or Email already in use");
            }
        } else {
            //password and confirmedPassword do not match
            System.out.println("password and confirmedPassword do not match");
            setErrorLabel("Passwords do not match.");
        }

    }

    @FXML
    private void btnCancel_OnClick(ActionEvent event) {
        GuiUtilities.buildStage(this.paneMainForm.getScene().getWindow(), "Login", GuiUtilities.TITLE_LOGIN);
    }

    private void ClearTextFields() {
        this.txtUsername.setText("");
        this.txtPassword.setText("");
        this.txtConfirmPassword.setText("");
        this.txtEmail.setText("");
    }

    private void setErrorLabel(String error) {
        lbl_error.setTextFill(Color.RED);
        lbl_error.setText(error);
        lbl_error.setVisible(true);
        Thread tr = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    Platform.runLater(new Runnable() {

                        @Override
                        public void run() {
                            lbl_error.setVisible(false);
                        }

                    });
                } catch (InterruptedException ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        tr.start();
    }
}
