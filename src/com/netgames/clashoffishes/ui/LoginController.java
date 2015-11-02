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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

/**
 * FXML Controller class
 *
 * @author Christian Adkin
 */
public class LoginController implements Initializable
{

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
    @FXML
    private Label lbl_error;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        this.administration = Administration.get();
        lbl_error.setVisible(false);
    }

    @FXML
    private void login_OnClick(ActionEvent event)
    {
        this.userIdentifier = this.txtUsername.getText();
        this.password = this.txtPassword.getText();

        if (this.administration.logIn(userIdentifier, password) == null)
        {
            lbl_error.setText("Login failed");
            lbl_error.setTextFill(Color.RED);
            lbl_error.setVisible(true);
            this.connectLabel();
            this.userIdentifier = null;
            this.password = null;
        }
        else
        {
            GuiUtilities.buildStage(this.paneMainForm.getScene().getWindow(), "StartMenu", GuiUtilities.getMainMenusTitle());
        }
    }

    @FXML
    private void register_OnClick(ActionEvent event)
    {
        GuiUtilities.buildStage(this.paneMainForm.getScene().getWindow(), "Registration", GuiUtilities.REGISTRATION_TITLE);
    }

    private void connectLabel()
    {
        Thread tr = new Thread(new Runnable()
        {

            @Override
            public void run()
            {
                try
                {
                    Thread.sleep(3000);
                    Platform.runLater(new Runnable()
                    {

                        @Override
                        public void run()
                        {
                            lbl_error.setVisible(false);
                        }

                    });
                }
                catch (InterruptedException ex)
                {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        tr.start();
    }

}
