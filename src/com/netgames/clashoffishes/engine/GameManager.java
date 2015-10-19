package com.netgames.clashoffishes.engine;

import java.net.URL;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import com.netgames.clashoffishes.engine.object.*;
import javafx.scene.image.ImageView;

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
    private StackPane root;
    private ImageView gameWindow;
    private GameLoop gameLoop;
    private ObjectManager objectManager;
    public Player player;
    
    // <editor-fold defaultstate="collapsed" desc="Audioclips & URL declaration">
    private AudioClip biteSound0;
    private URL biteSoundFile0;
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Player images declaration">
    private Image bubbles1, bubbles2, bubbles3, bubbles4;
    private Image cleo1, cleo2, cleo3, cleo4;
    private Image fred1, fred2, fred3, fred4;
    private Image gill1, gill2, gill3, gill4;
    private URL playerDir;
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
    
    // <editor-fold defaultstate="collapsed" desc="Event images declaration">
    private Image jeffrey1, jeffrey2, jeffrey3;
    private Image energyDrink;
    private URL eventDir;
    // </editor-fold>
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Clash of Fishes");
        root = new StackPane();
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
        addNodesToStackPane();
        createStartGameLoop();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
    /**
     *  
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
     * 
     */
    private void loadAudioAssets() {
        // TODO loading audio asset format:
        // URL object = this.getClass().getResource("/resource/sound.wav");
        // AudioClip object = new AudioClip(URL object.toString());
        biteSoundFile0 = this.getClass().getResource("/com/netgames/clashoffishes/audio/bite.wav");
        biteSound0 = new AudioClip(biteSoundFile0.toString());
    }
    
    /**
     * 
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
        
        eventDir = this.getClass().getResource("/com/netgames/clashoffishes/images/event/");
        // <editor-fold defaultstate="collapsed" desc="Jeffrey image instantiation">
        jeffrey1 = new Image(eventDir.toString() + "Jeffrey1.png", 110, 105, true, false, true);
        jeffrey2 = new Image(eventDir.toString() + "Jeffrey2.png", 110, 105, true, false, true);
        jeffrey3 = new Image(eventDir.toString() + "Jeffrey3.png", 110, 105, true, false, true);
        // </editor-fold>
    }
    
    /**
     * 
     */
    private void createGameObjects() {
        // TODO adding game objects format:
        // gameObject = new GameObject(this, SVG data, startX, startY, Images...);
        bgLayer1 = new Prop("", 0, 0, backgroundLayer1);
        bLayer1 = new Prop("", 0, ((HEIGHT / 2) - (backLayer1.getRequestedHeight() / 2)), backLayer1);
        mLayer1 = new Prop("", 0, ((HEIGHT / 2) - (middleLayer1.getRequestedHeight() / 2)), middleLayer1);
        fLayer1 = new Prop("", 0, ((HEIGHT / 2) - (frontLayer1.getRequestedHeight() / 2)), frontLayer1);
        
        player = new Player(this, "", 0, 0, bubbles1, bubbles2, bubbles3, bubbles4);
    }
    
    /**
     * 
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
     * 
     */
    public void createObjectManager() {
        objectManager = new ObjectManager();
        // TODO adding an object to the object manager format:
        // objectManager.addCurrentObject(newobject);
        objectManager.addCurrentObject(bgLayer1);
        objectManager.addCurrentObject(bLayer1);
        objectManager.addCurrentObject(mLayer1);
        objectManager.addCurrentObject(fLayer1);
        
        objectManager.addCurrentObject(player);
    }
    
    /**
     * 
     */
    private void createSplashScreenAndGameMenuNodes() {
        // TODO create a splashscreen and add menu items:
        // 
        //gameWindow = new ImageView();
        //gameWindow.setImage(backgroundLayer1);
    }
    
    private void addNodesToStackPane() {
        // TODO add nodes to the stack pane:
        // root.getChildren().add(container);
        //root.getChildren().add(gameWindow);
    }
    
    /**
     * 
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
     * 
     */
    public void playBiteSound() {
        this.biteSound0.play();
    }
}
