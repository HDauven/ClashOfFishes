package com.netgames.clashoffishes.util;

import com.netgames.clashoffishes.Administration;
import com.netgames.clashoffishes.server.LobbyRegistry;
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
 * @author Christian Adkin
 * @author Stef Philipsen
 * @author Hein Dauven
 */
public class GuiUtilities {

    //TODO GuiTitles aanvullen
    public static final String TITLE_GAME_WORLD = "Clash of Fishes!";
    public static final String TITLE_HIGHSCORE = "Highscores";
    public static final String TITLE_HOSTED_GAMES = "Hosted Games";
    public static final String TITLE_LOGIN = "Welcome - Please login using your username/email & password";
    public static final String TITLE_REGISTRATION = "Register a new account";
    public static final String TITLE_SINGLE_PLAYER = "Singleplayer";
    public static final String TITLE_START_MENU = "Main Menu";
    public static final String TITLE_MULTIPLAYER_MENU = "Multiplayer";

    //Object window = controller of sender
    public static Object buildStage(Object window, String fileName, String title) {
        try {
            Stage stage = (Stage) window;
            FXMLLoader loader = new FXMLLoader(GuiUtilities.class.getResource("/com/netgames/clashoffishes/ui/" + fileName + ".fxml"));
            Parent root = (Parent) loader.load();
            stage.setTitle(title);
            stage.setScene(new Scene(root));

            //TODO handle onCloseRequest for every fxml file, default is logout
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    //TO DO switch/case van maken met ENUM
                    System.out.println(fileName);
                    if (fileName.equals("StartMenu")) {
                        event.consume();
                        GuiUtilities.logOut(stage);
                        return;
                    }
                    if (fileName.equals("Login")) {
                        event.consume();
                        GuiUtilities.closeApplication(stage);
                        return;
                    }
                    if (fileName.equals("MultiplayerMenu")) {
                        event.consume();
                        GuiUtilities.buildStage(stage.getScene().getWindow(), "StartMenu", TITLE_START_MENU);
                        return;
                    }
                    if (fileName.equals("HostedGames")) {
                        event.consume();
                        GuiUtilities.buildStage(stage.getScene().getWindow(), "MultiplayerMenu", TITLE_MULTIPLAYER_MENU);
                        return;
                    }
                    if (fileName.equals("Lobby")) {
                        event.consume();
                        try {
                            if (Administration.get().getLobby().getClients().get(0).getUsername()
                                    .equals(Administration.get().getLoggedInUser().getUsername())) {
                                LobbyRegistry.getCofServer().removeLobby(Administration.get().getLobbyRegistry().getLobby());
                            } else {
                                // Removes a client from the lobby it is in
                                Administration.get().getLobby().removeClient(Administration.get().getClient());
                                // Tells the other players in the lobby that the client has left
                                Administration.get().getLobby().broadcastPlayer(Administration.get().getClient().getUsername(), Administration.get().getClient());
                            }
                        } catch (RemoteException ex) {
                            Logger.getLogger(GuiUtilities.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        GuiUtilities.buildStage(stage.getScene().getWindow(), "HostedGames", TITLE_HOSTED_GAMES);
                        return;
                    }
                    if (fileName.equals("Highscore")) {
                        event.consume();
                        GuiUtilities.buildStage(stage.getScene().getWindow(), "StartMenu", TITLE_START_MENU);
                        return;
                    }
                    if (fileName.equals("SinglePlayer")) {
                        event.consume();
                        GuiUtilities.buildStage(stage.getScene().getWindow(), "StartMenu", TITLE_START_MENU);
                        return;
                    } else {
                        event.consume();
                        GuiUtilities.logOut(stage);
                        return;
                    }
                }
            });

            stage.show();
            return loader.getController();
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
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

    public static void logOut(Stage stage) {
        GuiUtilities.buildStage(stage.getScene().getWindow(), "Login", TITLE_LOGIN);
    }

    public static void closeApplication(Stage stage) {
        stage.hide();
        stage.close();
        Platform.exit();
        System.exit(0);
    }
}
