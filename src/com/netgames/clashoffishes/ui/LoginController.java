/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netgames.clashoffishes.ui;

import com.netgames.clashoffishes.data.DatabaseConnector;
import com.netgames.clashoffishes.data.Statement;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Christian Adkin
 */
public class LoginController implements Initializable {

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

    private String userIdentifier, password;

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
        
        int result = 0;
        
        CallableStatement loginStatement = this.databaseConnector.getStatement(Statement.LOGIN);
        ResultSet resultSet = null;
        try {
            loginStatement.setString(1, this.userIdentifier);
            loginStatement.setString(2, this.password);
            //loginStatement.setInt(3, result);
            result = loginStatement.getInt(3);
            loginStatement.executeQuery();
            resultSet = loginStatement.getResultSet();

            while (resultSet.next()) {
                result = resultSet.getInt("r_User_ID");
            }
            
            System.out.println(result);

        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void register_OnClick(ActionEvent event) {

    }

}
