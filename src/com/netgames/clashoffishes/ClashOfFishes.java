/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netgames.clashoffishes;

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

        //Stage stage = new Stage();
        stage.setTitle("My New Stage Title");
        stage.setScene(new Scene(root));
        stage.show();
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        launch(args);
    }

}
