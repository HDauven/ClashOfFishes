package com.netgames.clashoffishes.server;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Server that keeps track of all the existing Game Lobbies for the Clash of Fishes game.
 * @author Hein Dauven
 */
public class ClashOfFishesServer extends Application {
        
    /**
     * @param args the command line arguments
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root;
        try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/netgames/clashoffishes/server/ui/LobbyServerGUI.fxml"));
        root = (Parent) loader.load();
        primaryStage.setTitle("Admin lobby control center");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        primaryStage.setOnCloseRequest((WindowEvent event) -> {
            Platform.exit();
            System.exit(0);
        });
        } catch(Exception e) {
            System.out.println(e.toString());
        }
    }  
    
    public static void main(String[] args) {
        launch(args);
    }
}
