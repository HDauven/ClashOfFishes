package com.netgames.clashoffishes.engine;

import java.net.URL;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import com.netgames.clashoffishes.engine.object.*;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * Class that manages the game itself.
 * 
 * @author Hein Dauven
 */
public class GameManager extends Application {
    public static final double WIDTH = 1024, HEIGHT = 768;
    private boolean up, down, left, right;
    private boolean wKey, aKey, sKey, dKey;
    private boolean space;
    private Scene scene;
    private Group root;
    private Group menu;
    private Rectangle scoreWindow, menuBar, miniMap;
    private GameLoop gameLoop;
    private ObjectManager objectManager;
    private Player player;
    private int gameScore = 0;
    private Text scoreText;
    private Text playerTextOne, playerTextTwo, playerTextThree, playerTextFour;
    private Text scoreTextOne, scoreTextTwo, scoreTextThree, scoreTextFour;
    private Text scoreLabelOne, scoreLabelTwo, scoreLabelThree, scoreLabelFour;
    private ImageView playerViewOne, playerViewTwo, playerViewThree, playerViewFour;
    private Image playerIconOne, playerIconTwo, playerIconThree, playerIconFour;
    
    // <editor-fold defaultstate="collapsed" desc="Audioclips & URL declaration">
    private AudioClip biteSound0;
    private URL biteSoundFile0;
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Background images declaration">
    private Image backgroundLayer1;
    Prop bgLayer1;
    private Image backLayer1;
    Prop bLayer1;
    private Image middleLayer1;
    Prop mLayer1;
    private Image frontLayer1;
    Prop fLayer1;
    private Image boatClassic1, boatClassic2, boatClassic3;
    private Image boatModern1, boatModern2, boatModern3;
    private URL backgroundDir;
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Player images declaration">
    private Image bubbles1, bubbles2, bubbles3, bubbles4;
    private Image cleo1, cleo2, cleo3, cleo4;
    private Image fred1, fred2, fred3, fred4;
    private Image gill1, gill2, gill3, gill4;
    private URL playerDir;
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="NPC images declaration">
    private Image fish1, fish2, fish3;
    private Image plankton1;
    private URL npcDir;
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Event images declaration">
    private Image jeffrey1, jeffrey2, jeffrey3;
    private Image energyDrink;
    private Image fishHook;
    private Image seaweed;
    private URL eventDir;
    // </editor-fold>
    
    // TODO make this class dynamic. 
    // TODO if this class becomes dynamic we will ascend to god status.
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Clash of Fishes");
        root = new Group();
        scene = new Scene(root, WIDTH, HEIGHT, Color.WHITE);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        createSceneEventHandling();
        loadAudioAssets();
        loadImageAssets();
        createGameObjects();
        addGameObjectNodes();
        createObjectManager();
        createSplashScreenAndGameMenuNodes();
        addNodesToGroup();
        createStartGameLoop();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
    /**
     * Sets event handling for the scene object.
     * Based on user input, booleans are triggered to decide whether an user 
     * is actively using a key or not.
     */
    private void createSceneEventHandling() {
        scene.setOnKeyPressed((KeyEvent event) -> {
            switch (event.getCode()) {
                case UP:    up    = true; break;
                case DOWN:  down  = true; break;
                case LEFT:  left  = true; break;
                case RIGHT: right = true; break;
                case W:     wKey  = true; break;
                case A:     aKey  = true; break;
                case S:     sKey  = true; break;
                case D:     dKey  = true; break; 
                case SPACE: space = true; break;
            }
        });
        
        scene.setOnKeyReleased((KeyEvent event) -> {
            switch (event.getCode()) {
                case UP:    up    = false; break;
                case DOWN:  down  = false; break;
                case LEFT:  left  = false; break;
                case RIGHT: right = false; break;
                case W:     wKey  = false; break;
                case A:     aKey  = false; break;
                case S:     sKey  = false; break;
                case D:     dKey  = false; break;  
                case SPACE: space = false; break;   
            }
        });
    }
    
    /**
     * Loads audio assets into the game.
     */
    private void loadAudioAssets() {
        // TODO loading audio asset format:
        // URL object = this.getClass().getResource("/resource/sound.wav");
        // AudioClip object = new AudioClip(URL object.toString());
        biteSoundFile0 = this.getClass().getResource("/com/netgames/clashoffishes/audio/bite.wav");
        biteSound0 = new AudioClip(biteSoundFile0.toString());
    }
    
    /**
     * Loads image assets into the game.
     */
    private void loadImageAssets() {
        // TODO adding image asset format:
        // Image object = new Image("/resource/image.png", width, height, true, false, true);
        backgroundDir = this.getClass().getResource("/com/netgames/clashoffishes/images/background/");
        // <editor-fold defaultstate="collapsed" desc="Background layer image instantiation">
        backgroundLayer1 = new Image(backgroundDir.toString() + "BackgroundLayer1.png", 1024, 768, true, false, true);
        // </editor-fold>        
        
        // <editor-fold defaultstate="collapsed" desc="Back layer image instantiation">
        backLayer1 = new Image(backgroundDir.toString() + "BackLayer1.png", 1024, 506, true, false, true);
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Middle layer image instantiation">
        middleLayer1 = new Image(backgroundDir.toString() + "MiddleLayer1.png", 1024, 212, true, false, true);
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Front layer image instantiation">
        frontLayer1 = new Image(backgroundDir.toString() + "FrontLayer1.png", 1024, 316, true, false, true);
        // </editor-fold>
        
        playerDir = this.getClass().getResource("/com/netgames/clashoffishes/images/player/");
        // <editor-fold defaultstate="collapsed" desc="Bubbles image instantiation">
        bubbles1 = new Image(playerDir.toString() + "Bubbles1.png", 125, 78, true, false, true);
        bubbles2 = new Image(playerDir.toString() + "Bubbles2.png", 125, 78, true, false, true);
        bubbles3 = new Image(playerDir.toString() + "Bubbles3.png", 125, 78, true, false, true);
        bubbles4 = new Image(playerDir.toString() + "Bubbles4.png", 125, 78, true, false, true);
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Cleo image instantiation">        
        cleo1 = new Image(playerDir.toString() + "Cleo1.png", 105, 66, true, false, true);
        cleo2 = new Image(playerDir.toString() + "Cleo2.png", 105, 66, true, false, true);
        cleo3 = new Image(playerDir.toString() + "Cleo3.png", 105, 66, true, false, true);
        cleo4 = new Image(playerDir.toString() + "Cleo4.png", 105, 66, true, false, true);
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Fred image instantiation">
        fred1 = new Image(playerDir.toString() + "Fred1.png", 160, 109, true, false, true);
        fred2 = new Image(playerDir.toString() + "Fred2.png", 160, 109, true, false, true);
        fred3 = new Image(playerDir.toString() + "Fred3.png", 160, 109, true, false, true);
        fred4 = new Image(playerDir.toString() + "Fred4.png", 160, 109, true, false, true);
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Gill image instantiation">
        gill1 = new Image(playerDir.toString() + "Gill1.png", 126, 72, true, false, true);
        gill2 = new Image(playerDir.toString() + "Gill2.png", 126, 72, true, false, true);
        gill3 = new Image(playerDir.toString() + "Gill3.png", 126, 72, true, false, true);
        gill4 = new Image(playerDir.toString() + "Gill4.png", 126, 72, true, false, true);
        // </editor-fold>
        
        npcDir = this.getClass().getResource("/com/netgames/clashoffishes/images/npc/");
        // <editor-fold defaultstate="collapsed" desc="NPC image instantiation">
        //fish1 = new Image(npcDir.toString() + "*.png", 1, 1, true, false, true);
        //fish2 = new Image(npcDir.toString() + "*.png", 1, 1, true, false, true);
        //fish3 = new Image(npcDir.toString() + "*.png", 1, 1, true, false, true);
        //plankton1 = new Image(npcDir.toString() + "*.png", 1, 1, true, false, true);
        // </editor-fold>
        
        eventDir = this.getClass().getResource("/com/netgames/clashoffishes/images/event/");
        // <editor-fold defaultstate="collapsed" desc="Jeffrey image instantiation">
        jeffrey1 = new Image(eventDir.toString() + "Jeffrey1.png", 110, 105, true, false, true);
        jeffrey2 = new Image(eventDir.toString() + "Jeffrey2.png", 110, 105, true, false, true);
        jeffrey3 = new Image(eventDir.toString() + "Jeffrey3.png", 110, 105, true, false, true);
        // </editor-fold>        
        //energyDrink = new Image(eventDir.toString() + "*.png", 1, 1, true, false, true);
        //fishHook    = new Image(eventDir.toString() + "*.png", 1, 1, true, false, true);
        //seaweed     = new Image(eventDir.toString() + "*.png", 1, 1, true, false, true);
    }
    
    /**
     * Creates the necessary GameObjects for the game.
     */
    private void createGameObjects() {
        // TODO adding game objects format:
        // gameObject = new GameObject(this, SVG data, startX, startY, Images...);
        bgLayer1 = new Prop("", 0, 0, backgroundLayer1);
        bLayer1 = new Prop("", 0, (HEIGHT - backLayer1.getRequestedHeight()), backLayer1);
        mLayer1 = new Prop("", 0, (HEIGHT - middleLayer1.getRequestedHeight()), middleLayer1);
        fLayer1 = new Prop("", 0, (HEIGHT - frontLayer1.getRequestedHeight()), frontLayer1);
        // ((HEIGHT / 2) - (frontLayer1.getRequestedHeight() / 2))
        
        player = new Player(this, "", WIDTH / 2, HEIGHT / 2, bubbles1, bubbles2, bubbles3, bubbles4);
    }
    
    /**
     * Adds GameObjects to the root node.
     */
    private void addGameObjectNodes() {
        // TODO adding game object nodes
        // root.getChildren().add(gameObject);
        root.getChildren().add(bgLayer1.getSpriteFrame());
        root.getChildren().add(bLayer1.getSpriteFrame());
        root.getChildren().add(mLayer1.getSpriteFrame());
        root.getChildren().add(fLayer1.getSpriteFrame());
        
        root.getChildren().add(player.getSpriteFrame());
    }
    
    /**
     * Creates the ObjectManager object. This is necessary for the detection of
     * collision and the removal of objects from the game.
     */
    public void createObjectManager() {
        objectManager = new ObjectManager();
        // TODO adding an object to the object manager format:
        // objectManager.addCurrentObject(newobject);
        objectManager.addCurrentObject(bgLayer1);
        objectManager.addCurrentObject(bLayer1);
        objectManager.addCurrentObject(mLayer1);
        objectManager.addCurrentObject(fLayer1);
        
        //objectManager.addCurrentObject(player);
    }
    
    /**
     * Sets up the necessary game menu items.
     */
    private void createSplashScreenAndGameMenuNodes() {
        // TODO create a splashscreen and add menu items:
        // 
        //gameWindow = new ImageView();
        //gameWindow.setImage(backgroundLayer1);
        menu = new Group();
        
        menuBar = new Rectangle(600, 50, Color.BLACK);
        menuBar.setTranslateX((WIDTH - 600) / 2);
        menuBar.setTranslateY(HEIGHT - 50);
        menuBar.opacityProperty().set(0.5);
        menu.getChildren().add(menuBar);
        
        miniMap = new Rectangle(200, 200, Color.BLACK);
        miniMap.setTranslateX(WIDTH - 200);
        miniMap.opacityProperty().set(0.5);
        menu.getChildren().add(miniMap);   
        
        // <editor-fold defaultstate="collapsed" desc="Scoreboard">
        scoreWindow = new Rectangle(100, 440, Color.BLACK);
        scoreWindow.setTranslateX(0);
        scoreWindow.setTranslateY((HEIGHT / 2) - 220);
        scoreWindow.opacityProperty().set(0.5);
        menu.getChildren().add(scoreWindow);
        
        scoreText = new Text();
        scoreText.setText("Scoreboard");
        scoreText.setFill(Color.WHITE);
        scoreText.setFont(Font.font("System", FontWeight.BOLD, 14));
        scoreText.setLayoutX(10);
        scoreText.setLayoutY((HEIGHT / 2) - 195);
        menu.getChildren().add(scoreText);
        
        playerTextOne = new Text();
        playerTextOne.setText("Player one: ");
        playerTextOne.setFill(Color.WHITE);
        playerTextOne.setFont(Font.font("System", FontWeight.BOLD, 12));
        playerTextOne.setLayoutX(10);
        playerTextOne.setLayoutY((HEIGHT / 2) - 170);
        menu.getChildren().add(playerTextOne);
        
        playerViewOne = new ImageView();
        playerIconOne = new Image(playerDir.toString() + "Bubbles1.png", 80, 49, true, false, true);
        playerViewOne.setImage(playerIconOne);
        playerViewOne.setLayoutX(10);
        playerViewOne.setLayoutY((HEIGHT / 2) - 160);
        menu.getChildren().add(playerViewOne);
        
        scoreTextOne = new Text();
        scoreTextOne.setText("Score: ");
        scoreTextOne.setFill(Color.WHITE);
        scoreTextOne.setFont(Font.font("System", FontWeight.BOLD, 12));
        scoreTextOne.setLayoutX(10);
        scoreTextOne.setLayoutY((HEIGHT / 2) - 90);
        menu.getChildren().add(scoreTextOne);
        
        scoreLabelOne = new Text();
        scoreLabelOne.setText(String.valueOf(gameScore));
        scoreLabelOne.setFill(Color.WHITE);
        scoreLabelOne.setFont(Font.font("System", FontWeight.BOLD, 12));
        scoreLabelOne.setLayoutX(50);
        scoreLabelOne.setLayoutY((HEIGHT / 2) - 90);
        menu.getChildren().add(scoreLabelOne);
        
        playerTextTwo = new Text();
        playerTextTwo.setText("Player two: ");
        playerTextTwo.setFill(Color.WHITE);
        playerTextTwo.setFont(Font.font("System", FontWeight.BOLD, 12));
        playerTextTwo.setLayoutX(10);
        playerTextTwo.setLayoutY((HEIGHT / 2) - 70);
        menu.getChildren().add(playerTextTwo);
        
        playerViewTwo = new ImageView();
        playerIconTwo = new Image(playerDir.toString() + "Cleo1.png", 80, 50, true, false, true);
        playerViewTwo.setImage(playerIconTwo);
        playerViewTwo.setLayoutX(10);
        playerViewTwo.setLayoutY((HEIGHT / 2) - 60);
        menu.getChildren().add(playerViewTwo);
        
        scoreTextTwo = new Text();
        scoreTextTwo.setText("Score: ");
        scoreTextTwo.setFill(Color.WHITE);
        scoreTextTwo.setFont(Font.font("System", FontWeight.BOLD, 12));
        scoreTextTwo.setLayoutX(10);
        scoreTextTwo.setLayoutY((HEIGHT / 2) + 10);
        menu.getChildren().add(scoreTextTwo);
        
        scoreLabelTwo = new Text();
        scoreLabelTwo.setText("0");
        scoreLabelTwo.setFill(Color.WHITE);
        scoreLabelTwo.setFont(Font.font("System", FontWeight.BOLD, 12));
        scoreLabelTwo.setLayoutX(50);
        scoreLabelTwo.setLayoutY((HEIGHT / 2) + 10);
        menu.getChildren().add(scoreLabelTwo);
        
        playerTextThree = new Text();
        playerTextThree.setText("Player three: ");
        playerTextThree.setFill(Color.WHITE);
        playerTextThree.setFont(Font.font("System", FontWeight.BOLD, 12));
        playerTextThree.setLayoutX(10);
        playerTextThree.setLayoutY((HEIGHT / 2) + 30);
        menu.getChildren().add(playerTextThree);
        
        playerViewThree = new ImageView();
        playerIconThree = new Image(playerDir.toString() + "Fred1.png", 80, 54, true, false, true);
        playerViewThree.setImage(playerIconThree);
        playerViewThree.setLayoutX(10);
        playerViewThree.setLayoutY((HEIGHT / 2) + 40);
        menu.getChildren().add(playerViewThree);
        
        scoreTextThree = new Text();
        scoreTextThree.setText("Score: ");
        scoreTextThree.setFill(Color.WHITE);
        scoreTextThree.setFont(Font.font("System", FontWeight.BOLD, 12));
        scoreTextThree.setLayoutX(10);
        scoreTextThree.setLayoutY((HEIGHT / 2) + 110);
        menu.getChildren().add(scoreTextThree);
        
        scoreLabelThree = new Text();
        scoreLabelThree.setText("0");
        scoreLabelThree.setFill(Color.WHITE);
        scoreLabelThree.setFont(Font.font("System", FontWeight.BOLD, 12));
        scoreLabelThree.setLayoutX(50);
        scoreLabelThree.setLayoutY((HEIGHT / 2) + 110);
        menu.getChildren().add(scoreLabelThree);
        
        playerTextFour = new Text();
        playerTextFour.setText("Player four: ");
        playerTextFour.setFill(Color.WHITE);
        playerTextFour.setFont(Font.font("System", FontWeight.BOLD, 12));
        playerTextFour.setLayoutX(10);
        playerTextFour.setLayoutY((HEIGHT / 2) + 130);
        menu.getChildren().add(playerTextFour);
        
        playerViewFour = new ImageView();
        playerIconFour = new Image(playerDir.toString() + "Gill1.png", 126, 45, true, false, true);
        playerViewFour.setImage(playerIconFour);
        playerViewFour.setLayoutX(10);
        playerViewFour.setLayoutY((HEIGHT / 2) + 140);
        menu.getChildren().add(playerViewFour);
        
        scoreTextFour = new Text();
        scoreTextFour.setText("Score: ");
        scoreTextFour.setFill(Color.WHITE);
        scoreTextFour.setFont(Font.font("System", FontWeight.BOLD, 12));
        scoreTextFour.setLayoutX(10);
        scoreTextFour.setLayoutY((HEIGHT / 2) + 210);
        menu.getChildren().add(scoreTextFour);
        
        scoreLabelFour = new Text();
        scoreLabelFour.setText("0");
        scoreLabelFour.setFill(Color.WHITE);
        scoreLabelFour.setFont(Font.font("System", FontWeight.BOLD, 12));
        scoreLabelFour.setLayoutX(50);
        scoreLabelFour.setLayoutY((HEIGHT / 2) + 210);
        menu.getChildren().add(scoreLabelFour);
        // </editor-fold>
    }
    
    /**
     * Adds other nodes to the root Group object.
     */
    private void addNodesToGroup() {
        // TODO add nodes to the root Group:
        // root.getChildren().add(container);
        //root.getChildren().add(gameWindow);
        root.getChildren().add(menu);
    }
    
    /**
     * Creates a GameLoop object and runs an instance of this class.
     */
    private void createStartGameLoop() {
        gameLoop = new GameLoop(this);
        gameLoop.start();
    }
    
    /**
     * 
     * @return 
     */
    public boolean isUp() {
        return up;
    }

    /**
     * 
     * @param up 
     */
    public void setUp(boolean up) {
        this.up = up;
    }

    /**
     * 
     * @return 
     */
    public boolean isDown() {
        return down;
    }

    /**
     * 
     * @param down 
     */
    public void setDown(boolean down) {
        this.down = down;
    }

    /**
     * 
     * @return 
     */
    public boolean isLeft() {
        return left;
    }

    /**
     * 
     * @param left 
     */
    public void setLeft(boolean left) {
        this.left = left;
    }

    /**
     * 
     * @return 
     */
    public boolean isRight() {
        return right;
    }

    /**
     * 
     * @param right 
     */
    public void setRight(boolean right) {
        this.right = right;
    }

    /**
     * 
     * @return 
     */
    public boolean iswKey() {
        return wKey;
    }

    /**
     * 
     * @param wKey 
     */
    public void setwKey(boolean wKey) {
        this.wKey = wKey;
    }
    /**
     * 
     * @return 
     */
    public boolean isaKey() {
        return aKey;
    }

    /**
     * 
     * @param aKey 
     */
    public void setaKey(boolean aKey) {
        this.aKey = aKey;
    }

    /**
     * 
     * @return 
     */
    public boolean issKey() {
        return sKey;
    }

    /**
     * 
     * @param sKey 
     */
    public void setsKey(boolean sKey) {
        this.sKey = sKey;
    }

    /**
     * 
     * @return 
     */
    public boolean isdKey() {
        return dKey;
    }

    /**
     * 
     * @param dKey 
     */
    public void setdKey(boolean dKey) {
        this.dKey = dKey;
    }
    
    /**
     * 
     * @return 
     */
    public boolean isSpace() {
        return space;
    }

    /**
     * 
     * @param space 
     */
    public void setSpace(boolean space) {
        this.space = space;
    }
    
    /**
     * Plays the bite sound of a fish.
     */
    public void playBiteSound() {
        this.biteSound0.play();
    }

    /**
     * Gets the Group node instance that belongs to this GameManager and holds 
     * all the GameObjects.
     * @return the root Group belonging to this game session.
     */
    public Group getRoot() {
        return root;
    }

    /**
     * Gets the ObjectManager instance that belongs to this GameManager.
     * @return the objectManager belonging to this game session.
     */
    public ObjectManager getObjectManager() {
        return objectManager;
    }

    /**
     * Gets the Player instance that belongs to this GameManager.
     * @return The player belonging to this game session.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Gets the score of the current Player instance.
     * @return game score
     */
    public int getGameScore() {
        return gameScore;
    }

    /**
     * Sets the score of the current Player instance based on the added value.
     * @param gameScoreAddition added value
     */
    public void setGameScore(int gameScoreAddition) {
        this.gameScore = this.gameScore + gameScoreAddition;
    }

    /**
     * Updates the score for Player one on screen.
     */
    public void updateScoreLabelOne() {
        this.scoreLabelOne.setText(String.valueOf(this.gameScore));
    }
    
    
}
