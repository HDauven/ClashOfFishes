/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netgames.clashoffishes.ui;

import com.netgames.clashoffishes.*;
import com.netgames.clashoffishes.data.DatabaseConnector;
import com.netgames.clashoffishes.data.Statement;
import java.io.IOException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

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
    private DatabaseConnector databaseConnector;

    private String userIdentifier, password, email;
    
    private Administration administration;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.databaseConnector = new DatabaseConnector();
    }

    @FXML
    private void login_OnClick(ActionEvent event) {
        this.userIdentifier = this.txtUsername.getText();
        this.password = this.txtPassword.getText();
        this.email = null;
        
        int result = 0;
        
        CallableStatement loginStatement = this.databaseConnector.getStatement(Statement.LOGIN);
        try {
            loginStatement.setString(1, this.userIdentifier);
            loginStatement.setString(2, this.password);
            loginStatement.registerOutParameter(3, java.sql.Types.INTEGER);
            loginStatement.registerOutParameter(4, java.sql.Types.VARCHAR);
            loginStatement.registerOutParameter(5, java.sql.Types.VARCHAR);
            loginStatement.execute();
            result = loginStatement.getInt(3);
            this.userIdentifier = loginStatement.getString(4);
            this.email = loginStatement.getString(5);
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        if (result == 0) {
            this.userIdentifier = null;
            this.password = null;
        } else if (result > 0) {
            this.administration = Administration.get();
            this.administration.addUser(this.userIdentifier, this.email);
            
            //Login is succesfull we are now redirected to a new UI StartMenu.FXML
            try {
                Stage stage = (Stage) this.paneMainForm.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/netgames/clashoffishes/ui/StartMenu.fxml"));
                Parent root = (Parent)loader.load();
                stage.setTitle("Welcome back " + this.userIdentifier);
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void register_OnClick(ActionEvent event) {

    }

}
