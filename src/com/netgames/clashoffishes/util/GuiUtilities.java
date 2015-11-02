/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netgames.clashoffishes.util;

import com.netgames.clashoffishes.Administration;
import com.netgames.clashoffishes.ui.LoginController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author MuK
 */
public class GuiUtilities {
    
    //TODO GuiTitles aanvullen
    public static final String GAME_WORLD_TITLE = "Clash of Fishes!";
    public static final String HIGHSCORE_TITLE = "Highscores.";  
    public static final String HOSTED_GAMES_TITLE = "Hosted Games.";
    public static final String LOGIN_TITLE = "Welcome - Please login using your username/email & password.";   
    public static final String REGISTRATION_TITLE = "Register a new account.";
    public static final String CHARACTER_SELECTION_TITLE = "Select your character!";
    
    public static void buildStage(Object window, String fileName, String title) {
        try {
                Stage stage = (Stage) window;
                FXMLLoader loader = new FXMLLoader(GuiUtilities.class.getResource("/com/netgames/clashoffishes/ui/" + fileName + ".fxml"));
                Parent root = (Parent) loader.load();
                stage.setTitle(title);
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    public static String getMainMenusTitle() {
        Administration administration = Administration.get();
        String username = "";
        if (administration.getLoggedInUser() != null) {
            username = administration.getLoggedInUser().getUsername();
        }
        return "Welcome back: " + username;
    } 
    
    public static String getFishPoolTitle() {
        Administration administration = Administration.get();
        String username = administration.getCurrentLobby().getUsers().get(0).getUsername() + "'s Lobby";
        return username;
    }
}
