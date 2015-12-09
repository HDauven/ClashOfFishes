/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netgames.clashoffishes.util;

import com.netgames.clashoffishes.Administration;
import com.netgames.clashoffishes.server.LobbyRegistry;
import com.netgames.clashoffishes.server.remote.IClient;
import com.netgames.clashoffishes.ui.LoginController;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author MuK
 */
public class GuiUtilities
{

    //TODO GuiTitles aanvullen
    public static final String TITLE_GAME_WORLD = "Clash of Fishes!";
    public static final String TITLE_HIGHSCORE = "Highscores.";
    public static final String TITLE_HOSTED_GAMES = "Hosted Games.";
    public static final String TITLE_LOGIN = "Welcome - Please login using your username/email & password.";
    public static final String TITLE_REGISTRATION = "Register a new account.";
    public static final String TITLE_CHARACTER_SELECTION = "Select your character!";
    public static final String TITLE_START_MENU = "Main Menu";
    public static final String TITLE_MULTIPLAYER_MENU = "Multiplayer";

    //Object window = controller of sender
    public static Object buildStage(Object window, String fileName, String title)
    {
        try
        {
            Stage stage = (Stage) window;
            FXMLLoader loader = new FXMLLoader(GuiUtilities.class.getResource("/com/netgames/clashoffishes/ui/" + fileName + ".fxml"));
            Parent root = (Parent) loader.load();
            stage.setTitle(title);
            stage.setScene(new Scene(root));

            //TODO handle onCloseRequest for every fxml file, default is logout
            stage.setOnCloseRequest(new EventHandler<WindowEvent>()
            {
                @Override
                public void handle(WindowEvent event)
                {
                    //TO DO switch/case van maken met ENUM
                    System.out.println(fileName);
                    if (fileName.equals("StartMenu"))
                    {
                        event.consume();
                        GuiUtilities.logOut(stage);
                        return;
                    }
                    if (fileName.equals("Login"))
                    {
                        event.consume();
                        GuiUtilities.closeApplication(stage);
                        return;
                    }
                    if (fileName.equals("MultiplayerMenu"))
                    {
                        event.consume();
                        GuiUtilities.buildStage(stage.getScene().getWindow(), "StartMenu", TITLE_START_MENU);
                        return;
                    }
                    if (fileName.equals("HostedGames"))
                    {
                        event.consume();
                        GuiUtilities.buildStage(stage.getScene().getWindow(), "MultiplayerMenu", TITLE_MULTIPLAYER_MENU);
                        return;
                    }
                    if (fileName.equals("FishPool"))
                    {
                        event.consume();
                        try {
                            // TODO URGENT:check whether the host of the lobby closes the screen
                            if (Administration.get().getLobbyRegistry().getLobby().getClients().get(0).getUsername()
                                    .equals(Administration.get().getLoggedInUser().getUsername())) {
                                LobbyRegistry.getCofServer().removeLobby(Administration.get().getLobbyRegistry().getLobby());
                            } else {
                                // Remove client from the lobby he is registered to
                                // Administration.get().getLobby().removeClient();
                            }
                        } catch (RemoteException ex) {
                            Logger.getLogger(GuiUtilities.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        GuiUtilities.buildStage(stage.getScene().getWindow(), "HostedGames", TITLE_HOSTED_GAMES);
                        return;
                    }
                    if (fileName.equals("Highscore"))
                    {
                        event.consume();
                        GuiUtilities.buildStage(stage.getScene().getWindow(), "StartMenu", TITLE_START_MENU);
                        return;
                    }
                    if (fileName.equals("CharacterSelection"))
                    {
                        event.consume();
                        GuiUtilities.buildStage(stage.getScene().getWindow(), "StartMenu", TITLE_START_MENU);
                        return;
                    }
                    else
                    {
                        event.consume();
                        GuiUtilities.logOut(stage);
                        return;
                    }
                }
            });

            stage.show();
            return loader.getController();
        }
        catch (IOException ex)
        {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static String getMainMenusTitle()
    {
        Administration administration = Administration.get();
        String username = "";
        if (administration.getLoggedInUser() != null)
        {
            username = administration.getLoggedInUser().getUsername();
        }
        return "Welcome back: " + username;
    }

    public static String getFishPoolTitle()
    {
        String username = "";
        try {
            Administration administration = Administration.get();
            username = administration.getLobby().getPoolNameProperty();
        } catch (RemoteException ex) {
            Logger.getLogger(GuiUtilities.class.getName()).log(Level.SEVERE, null, ex);
        } finally { 
            return username;
        }
    }

    public static void logOut(Stage stage)
    {
        GuiUtilities.buildStage(stage.getScene().getWindow(), "Login", TITLE_LOGIN);
    }

    public static void closeApplication(Stage stage)
    {
        stage.hide();
        stage.close();
        Platform.exit();
        System.exit(0);
    }
}
