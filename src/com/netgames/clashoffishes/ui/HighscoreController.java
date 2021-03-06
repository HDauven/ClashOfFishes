package com.netgames.clashoffishes.ui;

import com.netgames.clashoffishes.Administration;
import com.netgames.clashoffishes.Highscore;
import com.netgames.clashoffishes.engine.GameMode;
import com.netgames.clashoffishes.util.GuiUtilities;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Stef Philipsen
 */
public class HighscoreController implements Initializable {

    @FXML
    private Button btnBack;
    @FXML
    private AnchorPane paneMainForm;
    @FXML
    private ComboBox<GameMode> cbGameMode;
    @FXML
    private TableView<Highscore> tbvHighscore;
    @FXML
    private TableColumn<Highscore, String> clmPlayers;
    @FXML
    private TableColumn<Highscore, String> clmScore;

    private Administration administration;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.administration = Administration.get();

        this.clmPlayers.setCellValueFactory(new PropertyValueFactory<Highscore, String>("Username"));
        this.clmScore.setCellValueFactory(new PropertyValueFactory<Highscore, String>("Score"));

        ObservableList<GameMode> gameModes = FXCollections.observableArrayList(GameMode.EVOLUTION_OF_TIME, GameMode.EVOLVED, GameMode.LAST_FISH_STANDING);
        this.cbGameMode.setItems(gameModes);
    }

    @FXML
    private void btnBack_OnClick(ActionEvent event) {
        GuiUtilities.buildStage(this.paneMainForm.getScene().getWindow(), "MultiplayerMenu", GuiUtilities.getMainMenusTitle());
    }

    @FXML
    private void cbGameMode_OnChange(ActionEvent event) {
        GameMode selectedGameMode = this.cbGameMode.getValue();
        List<Highscore> highscores = this.administration.getAllUserHighscoresForGameMode(selectedGameMode);
        ObservableList<Highscore> obHighscores = FXCollections.observableArrayList(highscores);
        this.tbvHighscore.setItems(obHighscores);
    }

}
