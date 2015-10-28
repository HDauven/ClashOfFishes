package com.netgames.clashoffishes.engine;

import static com.netgames.clashoffishes.engine.GameManager.HEIGHT;
import static com.netgames.clashoffishes.engine.GameManager.WIDTH;
import java.net.URL;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * Class that holds the GameMenu data.
 * 
 * @author Hein
 */
public class GameMenu {
    private GameManager gameManager;
    private Group scoreMenuGroup, menuBarGroup, miniMapGroup;
    private Rectangle scoreWindow, menuBar, miniMap;
    private Button pauseGameButton, continueGameButton;
    private HBox menuBarBox;
    private Text scoreText;
    private Text playerTextOne, playerTextTwo, playerTextThree, playerTextFour;
    private Text scoreTextOne, scoreTextTwo, scoreTextThree, scoreTextFour;
    private Text scoreLabelOne, scoreLabelTwo, scoreLabelThree, scoreLabelFour;
    private ImageView playerViewOne, playerViewTwo, playerViewThree, playerViewFour;
    private Image playerIconOne, playerIconTwo, playerIconThree, playerIconFour;
    private URL playerDir;
    
    public GameMenu(GameManager manager) {
        this.gameManager = manager;
        init();
    }
    
    // Initializer for the GameMenu
    private void init() {
        scoreMenuGroup = new Group();
        menuBarGroup   = new Group();
        miniMapGroup   = new Group();
        playerDir = this.getClass().getResource("/com/netgames/clashoffishes/images/player/");
        
        menuBar();
        scoreMenu();
        miniMap();
        
        menuBarGroup.getChildren().add(menuBarBox);
    }
    
    /**
     * Method that creates the Menu bar.
     */
    private void menuBar() {
        menuBar = new Rectangle(600, 50, Color.BLACK);
        menuBar.setTranslateX((WIDTH - 600) / 2);
        menuBar.setTranslateY(HEIGHT - 50);
        menuBar.opacityProperty().set(0.5);
        menuBarGroup.getChildren().add(menuBar);
        
        menuBarBox = new HBox(20);
        menuBarBox.setPadding(new Insets(5));
        menuBarBox.setTranslateX((WIDTH - 600) / 2);
        menuBarBox.setTranslateY(HEIGHT - 50);
        
        pauseGameButton = new Button();
        pauseGameButton.setText("Pause Game");
        pauseGameButton.setStyle("-fx-font: 12 system; -fx-text-fill: white;"
                + "-fx-padding: 10 20 10 20; "
                + "-fx-background-color: #090a0c, "
                + "linear-gradient(#38424b 0%, #1f2429 20%, #191d22 100%),"
                + "linear-gradient(#20262b, #191d22),"
                + "radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));"
                + "-fx-background-radius: 15,14,13,15;"
                + "-fx-background-insets: 0,1,2,0;");
        pauseGameButton.setOnAction((ActionEvent event) -> {
            gameManager.getGameLoop().stop();
            gameManager.setGameState(GameState.PAUSED);
        });
        menuBarBox.getChildren().add(pauseGameButton); 
        
        continueGameButton = new Button();
        continueGameButton.setText("Continue Game");
        continueGameButton.setStyle("-fx-font: 12 system; -fx-text-fill: white;"
                + "-fx-padding: 10 20 10 20; "
                + "-fx-background-color: #090a0c, "
                + "linear-gradient(#38424b 0%, #1f2429 20%, #191d22 100%),"
                + "linear-gradient(#20262b, #191d22),"
                + "radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));"
                + "-fx-background-radius: 15,14,13,15;"
                + "-fx-background-insets: 0,1,2,0;");
        continueGameButton.setOnAction((ActionEvent event) -> {
            gameManager.getGameLoop().start();
            gameManager.setGameState(GameState.RUNNING);
        });
        menuBarBox.getChildren().add(continueGameButton);
    }
    
    /**
     * Method that creates the Score menu.
     */
    private void scoreMenu() {
        scoreWindow = new Rectangle(100, 440, Color.BLACK);
        scoreWindow.setTranslateX(0);
        scoreWindow.setTranslateY((HEIGHT / 2) - 220);
        scoreWindow.opacityProperty().set(0.5);
        scoreMenuGroup.getChildren().add(scoreWindow);
        
        scoreText = new Text();
        scoreText.setText("Scoreboard");
        scoreText.setFill(Color.WHITE);
        scoreText.setFont(Font.font("System", FontWeight.BOLD, 14));
        scoreText.setLayoutX(10);
        scoreText.setLayoutY((HEIGHT / 2) - 195);
        scoreMenuGroup.getChildren().add(scoreText);
        
        playerTextOne = new Text();
        playerTextOne.setText("Player one: ");
        playerTextOne.setFill(Color.WHITE);
        playerTextOne.setFont(Font.font("System", FontWeight.BOLD, 12));
        playerTextOne.setLayoutX(10);
        playerTextOne.setLayoutY((HEIGHT / 2) - 170);
        scoreMenuGroup.getChildren().add(playerTextOne);
        
        playerViewOne = new ImageView();
        playerIconOne = new Image(playerDir.toString() + "BubblesIcon.png", 80, 51, true, false, true);
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
        
        playerTextTwo = new Text();
        playerTextTwo.setText("Player two: ");
        playerTextTwo.setFill(Color.WHITE);
        playerTextTwo.setFont(Font.font("System", FontWeight.BOLD, 12));
        playerTextTwo.setLayoutX(10);
        playerTextTwo.setLayoutY((HEIGHT / 2) - 70);
        scoreMenuGroup.getChildren().add(playerTextTwo);
        
        playerViewTwo = new ImageView();
        playerIconTwo = new Image(playerDir.toString() + "CleoIcon.png", 80, 50, true, false, true);
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
        
        playerTextThree = new Text();
        playerTextThree.setText("Player three: ");
        playerTextThree.setFill(Color.WHITE);
        playerTextThree.setFont(Font.font("System", FontWeight.BOLD, 12));
        playerTextThree.setLayoutX(10);
        playerTextThree.setLayoutY((HEIGHT / 2) + 30);
        scoreMenuGroup.getChildren().add(playerTextThree);
        
        playerViewThree = new ImageView();
        playerIconThree = new Image(playerDir.toString() + "FredIcon.png", 80, 57, true, false, true);
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
        
        playerTextFour = new Text();
        playerTextFour.setText("Player four: ");
        playerTextFour.setFill(Color.WHITE);
        playerTextFour.setFont(Font.font("System", FontWeight.BOLD, 12));
        playerTextFour.setLayoutX(10);
        playerTextFour.setLayoutY((HEIGHT / 2) + 130);
        scoreMenuGroup.getChildren().add(playerTextFour);
        
        playerViewFour = new ImageView();
        playerIconFour = new Image(playerDir.toString() + "GillIcon.png", 80, 47, true, false, true);
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
    
    /**
     * Method that creates the minimap.
     */
    private void miniMap() {
        miniMap = new Rectangle(200, 200, Color.BLACK);
        miniMap.setTranslateX(WIDTH - 200);
        miniMap.opacityProperty().set(0.5);
        miniMapGroup.getChildren().add(miniMap);  
    }

    /**
     * Updates the score for Player one on screen.
     */
    public void updateScoreLabelOne() {
        this.scoreLabelOne.setText(String.valueOf(gameManager.getGameScore()));
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
}
