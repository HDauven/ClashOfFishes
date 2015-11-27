/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 * @author Bram
 */
public class ClashOfFishes extends Application
{

    @Override
    public void start(Stage stage) throws Exception
    {
        Parent root;
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/netgames/clashoffishes/ui/Login.fxml"));
            root = (Parent) loader.load();
            LoginController loginController = (LoginController) loader.getController();
            stage.setTitle(GuiUtilities.TITLE_LOGIN);
            stage.setScene(new Scene(root));
            //stage.show();
            GuiUtilities.buildStage(stage.getScene().getWindow(), "Login", GuiUtilities.TITLE_LOGIN);
        }
        catch (Exception e)
        {
            System.out.println(e.toString());
        }
        Runnable openConnection = new Runnable()
        {

            @Override
            public void run()
            {
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
    public static void main(String[] args)
    {
        launch(args);
    }

}
