package com.netgames.clashoffishes;

import com.netgames.clashoffishes.ui.LoginController;
import com.netgames.clashoffishes.util.GuiUtilities;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Hein Dauven
 */
public class ClashOfFishes extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // Stops my PC from crashing:
        System.setProperty("glass.accessible.force", "false");

        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/netgames/clashoffishes/ui/Login.fxml"));
            root = (Parent) loader.load();
            LoginController loginController = (LoginController) loader.getController();
            stage.setTitle(GuiUtilities.TITLE_LOGIN);
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            //stage.show();
            GuiUtilities.buildStage(stage.getScene().getWindow(), "Login", GuiUtilities.TITLE_LOGIN);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Runnable openConnection = new Runnable() {

            @Override
            public void run() {
                Administration.get();
                System.out.println("Connected with the database.");
            }
        };
        Thread t = new Thread(openConnection);
        t.start();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
