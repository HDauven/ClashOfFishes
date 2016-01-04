package com.netgames.clashoffishes.ui;

import com.netgames.clashoffishes.Administration;
import com.netgames.clashoffishes.util.GuiUtilities;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

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
    PrintWriter out;

    @FXML
    private Label lbl_error;
    @FXML
    private CheckBox rbtn_remember;

    Scanner s;
    Thread tr;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        readLogin();
        lbl_error.setVisible(false);
        //txtUsername.setText(s.nextLine());
        //txtPassword.setText(s.nextLine());
    }

    private void readLogin() {
        try {
            s = new Scanner(new File("login.txt"));
            txtUsername.setText(s.nextLine());
            txtPassword.setText(s.nextLine());
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void login_OnClick(ActionEvent event) {
        try {
            this.userIdentifier = this.txtUsername.getText();
            this.password = this.txtPassword.getText();
            if (rbtn_remember.isSelected()) {
                File f = new File("login.txt");
                out = new PrintWriter(f);
                out.println(userIdentifier);
                out.println(password);
                out.close();
            }

            if (!Administration.get().hasConnection()) {
                lbl_error.setText("No database-connection.");
                lbl_error.setTextFill(Color.RED);
                lbl_error.setVisible(true);
                return;
            } else {
                if (Administration.get().logIn(userIdentifier, password) == null) {
                    lbl_error.setText("Login failed");
                    lbl_error.setTextFill(Color.RED);
                    lbl_error.setVisible(true);
                    this.connectLabel();
                    this.userIdentifier = null;
                    this.password = null;
                } else {
                    GuiUtilities.buildStage(this.paneMainForm.getScene().getWindow(), "StartMenu", GuiUtilities.getMainMenusTitle());
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }


    @FXML
    private void register_OnClick(ActionEvent event) {
        GuiUtilities.buildStage(this.paneMainForm.getScene().getWindow(), "Registration", GuiUtilities.TITLE_REGISTRATION);
    }

    private void connectLabel() {
        tr = new Thread(new Runnable() {

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

    @FXML
    private void txt_onKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            this.login_OnClick(null);
        }
    }

    void setUsername(String username) {
        txtUsername.setText(username);
        txtPassword.setText("");
        txtPassword.requestFocus();
    }

    public void openDatabase() {
        if (!Administration.get().hasConnection()) {
            lbl_error.setText("No database-connection.");
            lbl_error.setTextFill(Color.RED);
            lbl_error.setVisible(true);
        }
    }
}
