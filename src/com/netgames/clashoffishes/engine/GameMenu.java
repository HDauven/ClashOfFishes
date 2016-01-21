package com.netgames.clashoffishes.engine;

import com.netgames.clashoffishes.Administration;
import static com.netgames.clashoffishes.engine.GameManager.HEIGHT;
import static com.netgames.clashoffishes.engine.GameManager.WIDTH;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * Class that holds the GameMenu data.
 *
 * @author Hein Dauven
 */
public class GameMenu {

    private GameManager gameManager;
    private Group scoreMenuGroup, menuBarGroup, miniMapGroup, victoryScreenGroup, defeatScreenGroup;
    private Canvas menuBar, miniMap, scoreWindow, victoryScreen, defeatScreen;
    private GraphicsContext gc;
    private Button pauseGameButton, continueGameButton;
    private HBox menuBarBox;
    private Text scoreText;
    private Text playerTextOne, playerTextTwo, playerTextThree, playerTextFour;
    private Text scoreTextOne, scoreTextTwo, scoreTextThree, scoreTextFour;
    private Text scoreLabelOne, scoreLabelTwo, scoreLabelThree, scoreLabelFour;
    private Text victoryText, defeatText;
    private ImageView playerViewOne, playerViewTwo, playerViewThree, playerViewFour;
    private Image playerIconOne, playerIconTwo, playerIconThree, playerIconFour;
    private URL playerDir;
    private Text timeText;

    public GameMenu(GameManager manager) {
        this.gameManager = manager;
        init();
    }

    // Initializer for the GameMenu
    private void init() {
        scoreMenuGroup = new Group();
        menuBarGroup = new Group();
        miniMapGroup = new Group();
        victoryScreenGroup = new Group();
        defeatScreenGroup = new Group();

        playerDir = this.getClass().getResource("/com/netgames/clashoffishes/images/player/");

        menuBar();
        scoreMenu();
        miniMap();
        victoryScreen();
        defeatScreen();

        menuBarGroup.getChildren().add(menuBarBox);
    }

    /**
     * Method that creates the Menu bar.
     */
    private void menuBar() {
        menuBar = new Canvas(600, 50);
        gc = menuBar.getGraphicsContext2D();
        gc.beginPath();
        gc.moveTo(0, 50);
        gc.lineTo(0, 20);
        gc.quadraticCurveTo(0, 0, 20, 0);
        gc.lineTo(580, 0);
        gc.quadraticCurveTo(600, 0, 600, 20);
        gc.lineTo(600, 50);
        gc.closePath();
        gc.setFill(generateLinearGradient("60979c", "2b4c55"));
        gc.setGlobalAlpha(0.5);
        gc.fill();
        gc.setStroke(Color.web("#8ed0cf"));
        gc.setLineWidth(2);
        gc.stroke();
        menuBar.setTranslateX((WIDTH - 600) / 2);
        menuBar.setTranslateY(HEIGHT - 50);
        menuBarGroup.getChildren().add(menuBar);

        menuBarBox = new HBox(20);
        menuBarBox.setPadding(new Insets(7));
        menuBarBox.setTranslateX((WIDTH - 600) / 2);
        menuBarBox.setTranslateY(HEIGHT - 50);

        pauseGameButton = new Button();
        pauseGameButton.setText("Pause Game");
        pauseGameButton.setStyle("-fx-font: 12 system; -fx-text-fill: black;"
                + "-fx-font-weight: bold;"
                + "-fx-padding: 10 20 10 20; "
                + "-fx-background-color: #87c4c7, "
                + "linear-gradient(#4f7e86 0%, #608b94 20%, #3f6a70 100%),"
                + "linear-gradient(#8dd5d4, #4f9094),"
                + "radial-gradient(center 50% 0%, radius 100%, rgba(205,225,236,0.9), rgba(255,255,255,0));"
                + "-fx-background-radius: 15,14,13,15;"
                + "-fx-background-insets: 0,1,2,0;");
        pauseGameButton.setOnAction((ActionEvent event) -> {
            sendGameState(GameState.PAUSED);
        });
        menuBarBox.getChildren().add(pauseGameButton);

        continueGameButton = new Button();
        continueGameButton.setText("Continue Game");
        continueGameButton.setStyle("-fx-font: 12 system; -fx-text-fill: black;"
                + "-fx-font-weight: bold;"
                + "-fx-padding: 10 20 10 20; "
                + "-fx-background-color: #87c4c7, "
                + "linear-gradient(#4f7e86 0%, #608b94 20%, #3f6a70 100%),"
                + "linear-gradient(#8dd5d4, #4f9094),"
                + "radial-gradient(center 50% 0%, radius 100%, rgba(205,225,236,0.9), rgba(255,255,255,0));"
                + "-fx-background-radius: 15,14,13,15;"
                + "-fx-background-insets: 0,1,2,0;");
        continueGameButton.setOnAction((ActionEvent event) -> {
            sendGameState(GameState.RUNNING);
        });
        menuBarBox.getChildren().add(continueGameButton);
    }

    private void sendGameState(GameState state) {
        try {
            Administration.get().getGameServer().stateChanged(state);
        } catch (RemoteException ex) {
            Logger.getLogger(GameMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Method that creates the Score menu.
     */
    private void scoreMenu() {
        scoreWindow = new Canvas(100, 440);
        gc = scoreWindow.getGraphicsContext2D();
        gc.beginPath();
        gc.moveTo(0, 0);
        gc.lineTo(0, 440);
        gc.lineTo(75, 440);
        gc.quadraticCurveTo(100, 440, 100, 415);
        gc.lineTo(100, 25);
        gc.quadraticCurveTo(100, 0, 75, 0);
        gc.closePath();
        gc.setFill(generateLinearGradient("60979c", "2b4c55"));
        gc.setGlobalAlpha(0.5);
        gc.fill();
        gc.setStroke(Color.web("#8ed0cf"));
        gc.setLineWidth(2);
        gc.stroke();
        scoreWindow.setTranslateX(0);
        scoreWindow.setTranslateY((HEIGHT / 2) - 220);
        scoreMenuGroup.getChildren().add(scoreWindow);

        scoreText = new Text();
        scoreText.setText("Scoreboard");
        scoreText.setFill(Color.WHITE);
        scoreText.setFont(Font.font("System", FontWeight.BOLD, 14));
        scoreText.setLayoutX(10);
        scoreText.setLayoutY((HEIGHT / 2) - 195);
        scoreMenuGroup.getChildren().add(scoreText);
    }

    /**
     * Method that creates the minimap.
     */
    private void miniMap() {
        miniMap = new Canvas(200, 200);
        gc = miniMap.getGraphicsContext2D();
        gc.beginPath();
        gc.moveTo(0, 0);
        gc.lineTo(200, 0);
        gc.lineTo(200, 200);
        gc.quadraticCurveTo(0, 200, 0, 0);
        gc.closePath();
        gc.setFill(generateLinearGradient("60979c", "2b4c55"));
        gc.setGlobalAlpha(0.5);
        gc.fill();
        gc.setStroke(Color.web("#8ed0cf"));
        gc.setLineWidth(2);
        gc.stroke();
        miniMap.setTranslateX(WIDTH - 200);
        miniMapGroup.getChildren().add(miniMap);

        timeText = new Text();
        timeText.textProperty().bind(gameManager.getTimeLeft());
        timeText.setFill(Color.WHITE);
        timeText.setFont(Font.font("System", FontWeight.BOLD, 50));
        timeText.setTranslateX(WIDTH - 100);
        timeText.setTranslateY(75);
        timeText.setTextAlignment(TextAlignment.RIGHT);
        miniMapGroup.getChildren().add(timeText);
    }

    /**
     * Method that creates the Victory screen.
     */
    private void victoryScreen() {
        victoryScreen = new Canvas(500, 200);
        gc = victoryScreen.getGraphicsContext2D();
        gc.beginPath();
        gc.moveTo(25, 0);
        gc.lineTo(475, 0);
        gc.quadraticCurveTo(500, 100, 475, 200);
        gc.lineTo(25, 200);
        gc.quadraticCurveTo(0, 100, 25, 0);
        gc.closePath();
        gc.setFill(generateLinearGradient("71cb22", "79da25"));
        gc.setGlobalAlpha(0.5);
        gc.fill();
        gc.setStroke(Color.web("#1c9600"));
        gc.setLineWidth(2);
        gc.stroke();
        victoryScreen.setTranslateX((WIDTH / 2) - (victoryScreen.getWidth() / 2));
        victoryScreen.setTranslateY((HEIGHT / 2) - (victoryScreen.getHeight() / 2));
        victoryScreenGroup.getChildren().add(victoryScreen);

        victoryText = new Text();
        victoryText.setText("Victory!");
        victoryText.setFill(Color.WHITE);
        victoryText.prefWidth(100);
        victoryText.setFont(Font.font("System", FontWeight.BOLD, 20));
        victoryText.setLayoutX((WIDTH / 2) - 35);
        victoryText.setLayoutY((HEIGHT / 2 - 50));
        victoryScreenGroup.getChildren().add(victoryText);
    }

    /**
     * Method that creates the Defeat screen.
     */
    private void defeatScreen() {
        defeatScreen = new Canvas(500, 200);
        gc = defeatScreen.getGraphicsContext2D();
        gc.beginPath();
        gc.moveTo(25, 0);
        gc.lineTo(475, 0);
        gc.quadraticCurveTo(500, 100, 475, 200);
        gc.lineTo(25, 200);
        gc.quadraticCurveTo(0, 100, 25, 0);
        gc.closePath();
        gc.setFill(generateLinearGradient("ff0303", "ff3e3e"));
        gc.setGlobalAlpha(0.5);
        gc.fill();
        gc.setStroke(Color.web("#D40000"));
        gc.setLineWidth(2);
        gc.stroke();
        defeatScreen.setTranslateX((WIDTH / 2) - (victoryScreen.getWidth() / 2));
        defeatScreen.setTranslateY((HEIGHT / 2) - (defeatScreen.getHeight() / 2));
        defeatScreenGroup.getChildren().add(defeatScreen);

        defeatText = new Text();
        defeatText.setText("Defeat!");
        defeatText.setFill(Color.WHITE);
        defeatText.prefWidth(100);
        defeatText.setFont(Font.font("System", FontWeight.BOLD, 20));
        defeatText.setLayoutX((WIDTH / 2) - 35);
        defeatText.setLayoutY((HEIGHT / 2 - 50));
        defeatScreenGroup.getChildren().add(defeatText);
    }

    private LinearGradient generateLinearGradient(String beginColor, String endColor) {
        Stop[] stops = new Stop[]{
            new Stop(0, Color.web(beginColor)), new Stop(1, Color.web(endColor))
        };
        LinearGradient linearGradient = new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, stops);
        return linearGradient;
    }

    /**
     * Updates the score for Player one on screen.
     */
    public void updateScoreLabel(int playerID, int score) {
        switch (playerID) {
            case 0:
                this.scoreLabelOne.setText(String.valueOf(score));
                break;
            case 1:
                this.scoreLabelTwo.setText(String.valueOf(score));
                break;
            case 2:
                this.scoreLabelThree.setText(String.valueOf(score));
                break;
            case 3:
                this.scoreLabelFour.setText(String.valueOf(score));
                break;
        }
    }

    /**
     * Gets the player icon belonging to the given character name.
     *
     * @param characterName
     * @param playerID
     */
    private Image getPlayerIcons(String characterName) {
        Image tempCharacterIcon = null;
        switch (characterName.toUpperCase()) {
            case "BUBBLES":
                tempCharacterIcon = new Image(playerDir.toString() + "BubblesIcon.png", 80, 51, true, false, true);
                break;
            case "CLEO":
                tempCharacterIcon = new Image(playerDir.toString() + "CleoIcon.png", 80, 50, true, false, true);
                break;
            case "FRED":
                tempCharacterIcon = new Image(playerDir.toString() + "FredIcon.png", 80, 57, true, false, true);
                break;
            case "GILL":
                tempCharacterIcon = new Image(playerDir.toString() + "GillIcon.png", 80, 47, true, false, true);
                break;
        }
        return tempCharacterIcon;
    }

    /**
     * Creates the information belonging to the given playerID and characterName
     * in the scoreboard menu.
     *
     * @param characterName
     * @param playerID
     */
    public void createPlayerInfo(String characterName, int playerID) {
        Image characterIcon = getPlayerIcons(characterName);
        if (playerID == 0) {
            generatePlayerOneInfo(characterIcon);
        } else if (playerID == 1) {
            generatePlayerTwoInfo(characterIcon);
        } else if (playerID == 2) {
            generatePlayerThreeInfo(characterIcon);
        } else if (playerID == 3) {
            generatePlayerFourInfo(characterIcon);
        }
    }

    /**
     * Generates the scoreboard information for player one
     */
    private void generatePlayerOneInfo(Image characterIcon) {
        playerTextOne = new Text();
        playerTextOne.setText("Player one: ");
        playerTextOne.setFill(Color.WHITE);
        playerTextOne.setFont(Font.font("System", FontWeight.BOLD, 12));
        playerTextOne.setLayoutX(10);
        playerTextOne.setLayoutY((HEIGHT / 2) - 170);
        scoreMenuGroup.getChildren().add(playerTextOne);

        playerViewOne = new ImageView();
        playerIconOne = characterIcon;
        playerViewOne.setImage(playerIconOne);
        playerViewOne.setLayoutX(10);
        playerViewOne.setLayoutY((HEIGHT / 2) - 160);
        scoreMenuGroup.getChildren().add(playerViewOne);

        scoreTextOne = new Text();
        scoreTextOne.setText("Score: ");
        scoreTextOne.setFill(Color.WHITE);
        scoreTextOne.setFont(Font.font("System", FontWeight.BOLD, 12));
        scoreTextOne.setLayoutX(10);
        scoreTextOne.setLayoutY((HEIGHT / 2) - 90);
        scoreMenuGroup.getChildren().add(scoreTextOne);

        scoreLabelOne = new Text();
        scoreLabelOne.setText(String.valueOf(gameManager.getGameScore()));
        scoreLabelOne.setFill(Color.WHITE);
        scoreLabelOne.setFont(Font.font("System", FontWeight.BOLD, 12));
        scoreLabelOne.setLayoutX(50);
        scoreLabelOne.setLayoutY((HEIGHT / 2) - 90);
        scoreMenuGroup.getChildren().add(scoreLabelOne);
    }

    /**
     * Generates the scoreboard information for player two
     */
    private void generatePlayerTwoInfo(Image characterIcon) {
        playerTextTwo = new Text();
        playerTextTwo.setText("Player two: ");
        playerTextTwo.setFill(Color.WHITE);
        playerTextTwo.setFont(Font.font("System", FontWeight.BOLD, 12));
        playerTextTwo.setLayoutX(10);
        playerTextTwo.setLayoutY((HEIGHT / 2) - 70);
        scoreMenuGroup.getChildren().add(playerTextTwo);

        playerViewTwo = new ImageView();
        playerIconTwo = characterIcon;
        playerViewTwo.setImage(playerIconTwo);
        playerViewTwo.setLayoutX(10);
        playerViewTwo.setLayoutY((HEIGHT / 2) - 60);
        scoreMenuGroup.getChildren().add(playerViewTwo);

        scoreTextTwo = new Text();
        scoreTextTwo.setText("Score: ");
        scoreTextTwo.setFill(Color.WHITE);
        scoreTextTwo.setFont(Font.font("System", FontWeight.BOLD, 12));
        scoreTextTwo.setLayoutX(10);
        scoreTextTwo.setLayoutY((HEIGHT / 2) + 10);
        scoreMenuGroup.getChildren().add(scoreTextTwo);

        scoreLabelTwo = new Text();
        scoreLabelTwo.setText("0");
        scoreLabelTwo.setFill(Color.WHITE);
        scoreLabelTwo.setFont(Font.font("System", FontWeight.BOLD, 12));
        scoreLabelTwo.setLayoutX(50);
        scoreLabelTwo.setLayoutY((HEIGHT / 2) + 10);
        scoreMenuGroup.getChildren().add(scoreLabelTwo);
    }

    /**
     * Generates the scoreboard information for player three
     */
    private void generatePlayerThreeInfo(Image characterIcon) {
        playerTextThree = new Text();
        playerTextThree.setText("Player three: ");
        playerTextThree.setFill(Color.WHITE);
        playerTextThree.setFont(Font.font("System", FontWeight.BOLD, 12));
        playerTextThree.setLayoutX(10);
        playerTextThree.setLayoutY((HEIGHT / 2) + 30);
        scoreMenuGroup.getChildren().add(playerTextThree);

        playerViewThree = new ImageView();
        playerIconThree = characterIcon;
        playerViewThree.setImage(playerIconThree);
        playerViewThree.setLayoutX(10);
        playerViewThree.setLayoutY((HEIGHT / 2) + 40);
        scoreMenuGroup.getChildren().add(playerViewThree);

        scoreTextThree = new Text();
        scoreTextThree.setText("Score: ");
        scoreTextThree.setFill(Color.WHITE);
        scoreTextThree.setFont(Font.font("System", FontWeight.BOLD, 12));
        scoreTextThree.setLayoutX(10);
        scoreTextThree.setLayoutY((HEIGHT / 2) + 110);
        scoreMenuGroup.getChildren().add(scoreTextThree);

        scoreLabelThree = new Text();
        scoreLabelThree.setText("0");
        scoreLabelThree.setFill(Color.WHITE);
        scoreLabelThree.setFont(Font.font("System", FontWeight.BOLD, 12));
        scoreLabelThree.setLayoutX(50);
        scoreLabelThree.setLayoutY((HEIGHT / 2) + 110);
        scoreMenuGroup.getChildren().add(scoreLabelThree);
    }

    /**
     * Generates the scoreboard information for player four
     */
    private void generatePlayerFourInfo(Image characterIcon) {
        playerTextFour = new Text();
        playerTextFour.setText("Player four: ");
        playerTextFour.setFill(Color.WHITE);
        playerTextFour.setFont(Font.font("System", FontWeight.BOLD, 12));
        playerTextFour.setLayoutX(10);
        playerTextFour.setLayoutY((HEIGHT / 2) + 130);
        scoreMenuGroup.getChildren().add(playerTextFour);

        playerViewFour = new ImageView();
        playerIconFour = characterIcon;
        playerViewFour.setImage(playerIconFour);
        playerViewFour.setLayoutX(10);
        playerViewFour.setLayoutY((HEIGHT / 2) + 140);
        scoreMenuGroup.getChildren().add(playerViewFour);

        scoreTextFour = new Text();
        scoreTextFour.setText("Score: ");
        scoreTextFour.setFill(Color.WHITE);
        scoreTextFour.setFont(Font.font("System", FontWeight.BOLD, 12));
        scoreTextFour.setLayoutX(10);
        scoreTextFour.setLayoutY((HEIGHT / 2) + 210);
        scoreMenuGroup.getChildren().add(scoreTextFour);

        scoreLabelFour = new Text();
        scoreLabelFour.setText("0");
        scoreLabelFour.setFill(Color.WHITE);
        scoreLabelFour.setFont(Font.font("System", FontWeight.BOLD, 12));
        scoreLabelFour.setLayoutX(50);
        scoreLabelFour.setLayoutY((HEIGHT / 2) + 210);
        scoreMenuGroup.getChildren().add(scoreLabelFour);
    }

    public Group getScoreMenuGroup() {
        return scoreMenuGroup;
    }

    public Group getMenuBarGroup() {
        return menuBarGroup;
    }

    public Group getMiniMapGroup() {
        return miniMapGroup;
    }

    public Group getVictoryScreen() {
        return victoryScreenGroup;
    }

    public Group getDefeatScreen() {
        return defeatScreenGroup;
    }
}
