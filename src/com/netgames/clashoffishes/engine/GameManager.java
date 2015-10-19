package com.netgames.clashoffishes.engine;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Class that manages the game itself.
 * 
 * @author Hein Dauven
 */
public class GameManager extends Application {
    static final double WIDTH = 640, HEIGHT = 400;
    private boolean up, down, left, right;
    private boolean wKey, aKey, sKey, dKey;
    private Scene scene;
    private StackPane root;
    
    private final GameMode gameMode;
    private GameLoop gamePlayLoop;
    private ObjectManager castDirector;
    
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("InvinciBagel");
        root = new StackPane();
        scene = new Scene(root, WIDTH, HEIGHT, Color.WHITE);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        createSceneEventHandling();
        loadAudioAssets();
        loadImageAssets();
        createGameObject();
        addGameObjectNodes();
        createObjectManager();
        createSplashScreenNodes();
        addNodesToStackPane();
        createStartGameLoop();
    }
    
    public static void main(String[] args) {
        launch(args);
    }

    public GameManager(GameMode gameMode) {
        this.gameMode = gameMode;
    }
}
