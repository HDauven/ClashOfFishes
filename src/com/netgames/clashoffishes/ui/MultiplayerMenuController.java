package com.netgames.clashoffishes.ui;

import com.netgames.clashoffishes.Administration;
import com.netgames.clashoffishes.server.LobbyRegistry;
import com.netgames.clashoffishes.util.GuiUtilities;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Stef Philipsen
 * @author Hein Dauven
 */
public class MultiplayerMenuController implements Initializable {

    private Administration administration;
    @FXML
    private AnchorPane paneMainForm;
    @FXML
    private Button btnBack;
    @FXML
    private ImageView pictCoFLogo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.administration = Administration.get();
        URL logoDir = this.getClass().getResource("/com/netgames/clashoffishes/images/logo.png");
        this.pictCoFLogo.setImage(new Image(logoDir.toString(), 777, 471, true, false, true));
       // System.out.println(this.administration.getLoggedInUser().getUsername());
        //System.out.println(this.administration.getLoggedInUser().getEmail());
    }

    @FXML
    private void hostGame(ActionEvent event) {
        // Executes the creation of a LobbyRegistry and its lookup on a seperate thread.
        // Once this is done, the 'task' tells the JAT to build the new, corresponding stage.
        Task task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                //TODO werkend krijgen voor multiplayer
                LobbyRegistry lobbyRegistry = new LobbyRegistry();
                lobbyRegistry.clashOfFishesServerLookup();
                Administration.get().setLobbyRegistry(lobbyRegistry);
                Administration.get().setLobby(lobbyRegistry.getLobby());
                Platform.runLater(() -> {
                    GuiUtilities.buildStage(MultiplayerMenuController.this.paneMainForm.getScene().getWindow(), "Lobby", GuiUtilities.getFishPoolTitle());
                });
                return null;
            }
        };
        (new Thread(task)).start();
    }

    @FXML
    private void joinGame(ActionEvent event) {
        GuiUtilities.buildStage(this.paneMainForm.getScene().getWindow(), "HostedGames", GuiUtilities.TITLE_HOSTED_GAMES);
    }

    @FXML
    private void highscore(ActionEvent event) {
        GuiUtilities.buildStage(this.paneMainForm.getScene().getWindow(), "Highscore", GuiUtilities.TITLE_HIGHSCORE);
    }

    @FXML
    private void btnBack_OnClick(ActionEvent event) {
        GuiUtilities.buildStage(this.paneMainForm.getScene().getWindow(), "StartMenu", GuiUtilities.getMainMenusTitle());
    }

}
