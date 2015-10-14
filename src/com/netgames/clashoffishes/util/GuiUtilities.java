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
    
    
}
