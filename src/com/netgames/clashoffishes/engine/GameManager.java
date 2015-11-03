package com.netgames.clashoffishes.engine;

import com.netgames.clashoffishes.engine.object.GameObject;
import com.netgames.clashoffishes.engine.object.Player;
import com.netgames.clashoffishes.engine.object.Prop;
import com.netgames.clashoffishes.engine.object.events.EnergyDrink;
import com.netgames.clashoffishes.engine.object.events.FishHook;
import com.netgames.clashoffishes.engine.object.events.Seaweed;
import java.net.URL;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.stage.Stage;

/**
 * Class that manages the game itself.
 *
 * @author Hein Dauven
 */
public class GameManager extends Application
{

    public static final double WIDTH = 1280, HEIGHT = 720;
    private boolean up, down, left, right;
    private boolean wKey, aKey, sKey, dKey;
    private boolean space;
    private Scene scene;
    private GameLoop gameLoop;
    private ObjectManager objectManager;
    private GameMap map;
    private GameMenu menu;
    private Player player;
    private String character = "Bubbles";
    private int gameScore = 0;
    private GameState gameState;
    private SimpleStringProperty timeLeft = new SimpleStringProperty();
    //Standaardwaarde is single player = evolution of time
    private final GameMode gameMode = GameMode.EVOLUTION_OF_TIME;
    private Group root;
    Stage thisStage;

    EnergyDrink energy;
    private ArrayList<FishHook> fishHooks;

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
    private Image plankton1, plankton2, plankton3, plankton4;
    private URL npcDir;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Event images declaration">
    private Image jeffrey1, jeffrey2, jeffrey3;
    private Image energyDrink1, fishHook1, seaweed1, diver1;
    private URL eventDir;
    // </editor-fold>

    /**
     * Default constructor
     */
    public GameManager()
    {
        // Empty
        fishHooks = new ArrayList<>();
    }

    /**
     * Constructor where a character number is given.
     *
     * @param character The chosen character
     */
    public GameManager(String character)
    {
        if (character.toUpperCase().equals("BUBBLES")
                || character.toUpperCase().equals("CLEO")
                || character.toUpperCase().equals("FRED")
                || character.toUpperCase().equals("GILL"))
        {
            this.character = character;
        }
        else
        {
            this.character = "BUBBLES";
        }
    }

    // TODO make this class dynamic. 
    // TODO if this class becomes dynamic we will ascend to god status.
    @Override
    public void start(Stage primaryStage)
    {
        thisStage = primaryStage;
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
        addNodesToGroup();
        createStartGameLoop();
    }

    public static void main(String[] args)
    {
        launch(args);
    }

    /**
     * Sets event handling for the scene object. Based on user input, booleans
     * are triggered to decide whether an user is actively using a key or not.
     */
    private void createSceneEventHandling()
    {
        scene.setOnKeyPressed((KeyEvent event) ->
        {
            switch (event.getCode())
            {
                case UP:
                    up = true;
                    break;
                case DOWN:
                    down = true;
                    break;
                case LEFT:
                    left = true;
                    break;
                case RIGHT:
                    right = true;
                    break;
                case W:
                    wKey = true;
                    break;
                case A:
                    aKey = true;
                    break;
                case S:
                    sKey = true;
                    break;
                case D:
                    dKey = true;
                    break;
                case SPACE:
                    space = true;
                    break;
            }
        });

        scene.setOnKeyReleased((KeyEvent event) ->
        {
            switch (event.getCode())
            {
                case UP:
                    up = false;
                    break;
                case DOWN:
                    down = false;
                    break;
                case LEFT:
                    left = false;
                    break;
                case RIGHT:
                    right = false;
                    break;
                case W:
                    wKey = false;
                    break;
                case A:
                    aKey = false;
                    break;
                case S:
                    sKey = false;
                    break;
                case D:
                    dKey = false;
                    break;
                case SPACE:
                    space = false;
                    break;
            }
        });
    }

    /**
     * Loads audio assets into the game.
     */
    private void loadAudioAssets()
    {
        // TODO loading audio asset format:
        // URL object = this.getClass().getResource("/resource/sound.wav");
        // AudioClip object = new AudioClip(URL object.toString());
        biteSoundFile0 = this.getClass().getResource("/com/netgames/clashoffishes/audio/bite.wav");
        biteSound0 = new AudioClip(biteSoundFile0.toString());
    }

    /**
     * Loads image assets into the game.
     */
    private void loadImageAssets()
    {
        // TODO adding image asset format:
        // Image object = new Image("/resource/image.png", width, height, true, false, true);
        // <editor-fold defaultstate="collapsed" desc="Background images instantiation">
        backgroundDir = this.getClass().getResource("/com/netgames/clashoffishes/images/background/");
        backgroundLayer1 = new Image(backgroundDir.toString() + "BackgroundLayer1.png", 1024, 768, true, false, true);
        backLayer1 = new Image(backgroundDir.toString() + "BackLayer1.png", 1024, 506, true, false, true);
        middleLayer1 = new Image(backgroundDir.toString() + "MiddleLayer1.png", 1024, 212, true, false, true);
        frontLayer1 = new Image(backgroundDir.toString() + "FrontLayer1.png", 1024, 316, true, false, true);
        // </editor-fold>

        playerDir = this.getClass().getResource("/com/netgames/clashoffishes/images/player/");
        // <editor-fold defaultstate="collapsed" desc="Bubbles image instantiation">
        bubbles1 = new Image(playerDir.toString() + "Bubbles1.png", 103, 66, true, false, true);
        bubbles2 = new Image(playerDir.toString() + "Bubbles2.png", 100, 66, true, false, true);
        bubbles3 = new Image(playerDir.toString() + "Bubbles3.png", 103, 66, true, false, true);
        bubbles4 = new Image(playerDir.toString() + "Bubbles4.png", 105, 66, true, false, true);
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Cleo image instantiation">        
        cleo1 = new Image(playerDir.toString() + "Cleo1.png", 120, 75, true, false, true);
        cleo2 = new Image(playerDir.toString() + "Cleo2.png", 117, 76, true, false, true);
        cleo3 = new Image(playerDir.toString() + "Cleo3.png", 120, 78, true, false, true);
        cleo4 = new Image(playerDir.toString() + "Cleo4.png", 125, 72, true, false, true);
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Fred image instantiation">
        fred1 = new Image(playerDir.toString() + "Fred1.png", 153, 109, true, false, true);
        fred2 = new Image(playerDir.toString() + "Fred2.png", 160, 109, true, false, true);
        fred3 = new Image(playerDir.toString() + "Fred3.png", 157, 109, true, false, true);
        fred4 = new Image(playerDir.toString() + "Fred4.png", 147, 109, true, false, true);
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Gill image instantiation">
        gill1 = new Image(playerDir.toString() + "Gill1.png", 123, 72, true, false, true);
        gill2 = new Image(playerDir.toString() + "Gill2.png", 126, 72, true, false, true);
        gill3 = new Image(playerDir.toString() + "Gill3.png", 123, 72, true, false, true);
        gill4 = new Image(playerDir.toString() + "Gill4.png", 120, 72, true, false, true);
        // </editor-fold>

        npcDir = this.getClass().getResource("/com/netgames/clashoffishes/images/npc/");
        // <editor-fold defaultstate="collapsed" desc="NPC image instantiation">
        //fish1 = new Image(npcDir.toString() + "*.png", 1, 1, true, false, true);
        //fish2 = new Image(npcDir.toString() + "*.png", 1, 1, true, false, true);
        //fish3 = new Image(npcDir.toString() + "*.png", 1, 1, true, false, true);
        //plankton1 = new Image(npcDir.toString() + "Plankton1.png", 25, 28, true, false, true);
        //plankton2 = new Image(npcDir.toString() + "Plankton2.png", 30, 32, true, false, true);
        //plankton3 = new Image(npcDir.toString() + "Plankton3.png", 33, 29, true, false, true);
        //plankton4 = new Image(npcDir.toString() + "Plankton4.png", 62, 77, true, false, true);
        // </editor-fold>

        eventDir = this.getClass().getResource("/com/netgames/clashoffishes/images/event/");
        // <editor-fold defaultstate="collapsed" desc="Jeffrey image instantiation">
        jeffrey1 = new Image(eventDir.toString() + "Jeffrey1.png", 110, 105, true, false, true);
        jeffrey2 = new Image(eventDir.toString() + "Jeffrey2.png", 110, 105, true, false, true);
        jeffrey3 = new Image(eventDir.toString() + "Jeffrey3.png", 110, 105, true, false, true);
        // </editor-fold>        
        energyDrink1 = new Image(eventDir.toString() + "EnergyDrink1.png", 50, 241, true, false, true);
        //fishHook1    = new Image(eventDir.toString() + "FishHook1.png", 89, 905, true, false, true);
        seaweed1 = new Image(eventDir.toString() + "Seaweed1.png", 193, 558, true, false, true);
        //diver1       = new Image(eventDir.toString() + "Diver1.png", 243, 184, true, false, true);
    }

    /**
     * Creates the necessary GameObjects for the game.
     */
    private void createGameObjects()
    {
        // TODO adding game objects format:
        // gameObject = new GameObject(this, SVG data, startX, startY, Images...);
        bgLayer1 = new Prop("", 0, 0, backgroundLayer1);
        bLayer1 = new Prop("", 0, (HEIGHT - backLayer1.getRequestedHeight()), backLayer1);
        mLayer1 = new Prop("", 0, (HEIGHT - middleLayer1.getRequestedHeight()), middleLayer1);
        fLayer1 = new Prop("", 0, (HEIGHT - frontLayer1.getRequestedHeight()), frontLayer1);
        // ((HEIGHT / 2) - (frontLayer1.getRequestedHeight() / 2))
        map = new GameMap((int) WIDTH, (int) HEIGHT);
        menu = new GameMenu(this);

        createPlayer();

        energy = new EnergyDrink("M 4,00 L 4,0 0,19 0,139 16,148 64,148 78,139 78,18 75,0 Z",
                200, 200, energyDrink1);
    }

    private void createPlayer()
    {
        switch (this.character.toUpperCase())
        {
            case "BUBBLES":
                player = new Player(this, "M 81,5 L 81,5 23,6 26,57 80,54 80,54 Z",
                        WIDTH / 2, HEIGHT / 2, bubbles1, bubbles2, bubbles3, bubbles4);
                break;
            case "CLEO":
                player = new Player(this, "M 81,5 L 81,5 23,6 26,57 80,54 80,54 Z",
                        WIDTH / 2, HEIGHT / 2, cleo1, cleo2, cleo3, cleo4);
                break;
            case "FRED":
                player = new Player(this, "M 81,5 L 81,5 23,6 26,57 80,54 80,54 Z",
                        WIDTH / 2, HEIGHT / 2, fred1, fred2, fred3, fred4);
                break;
            case "GILL":
                player = new Player(this, "M 81,5 L 81,5 23,6 26,57 80,54 80,54 Z",
                        WIDTH / 2, HEIGHT / 2, gill1, gill2, gill3, gill4);
                break;
        }
    }

    /**
     * Adds GameObjects to the root node.
     */
    private void addGameObjectNodes()
    {
        // TODO adding game object nodes
        // root.getChildren().add(gameObject);
        root.getChildren().add(bgLayer1.getSpriteFrame());
        root.getChildren().add(bLayer1.getSpriteFrame());
        root.getChildren().add(mLayer1.getSpriteFrame());
        root.getChildren().add(fLayer1.getSpriteFrame());

        // Comment this out to get the regular background
        root.getChildren().add(map.getMap());

        root.getChildren().add(player.getSpriteFrame());

        root.getChildren().add(energy.getSpriteFrame());
    }

    /**
     * Creates the ObjectManager object. This is necessary for the detection of
     * collision and the removal of objects from the game.
     */
    public void createObjectManager()
    {
        objectManager = new ObjectManager();
        // TODO adding an object to the object manager format:
        // objectManager.addCurrentObject(newobject);

        objectManager.addCurrentObject(energy);
        //objectManager.addCurrentObject(player);
    }

    /**
     * Adds nodes to the root Group object.
     */
    private void addNodesToGroup()
    {
        // TODO add nodes to the root Group:
        // root.getChildren().add(container);
        //root.getChildren().add(gameWindow);
        root.getChildren().add(menu.getScoreMenuGroup());
        root.getChildren().add(menu.getMenuBarGroup());
        root.getChildren().add(menu.getMiniMapGroup());
    }

    /**
     * Creates a GameLoop object and runs an instance of this class.
     */
    private void createStartGameLoop()
    {
        gameLoop = new GameLoop(this);
        gameLoop.start();
        gameState = GameState.RUNNING;

    }

    public void addRandomObject()
    {
        //Aanmaken waarden
        Image image;
        GameObject object = null;
        double px = WIDTH * Math.random() + 1;
        double py = HEIGHT * Math.random() + 1;

        int range = (3 - 1) + 1; // 3 moet veranderen als er meer objecten bijkomen.
        int randomGetal = (int) (Math.random() * range + 1);

        //Random object genereren
        if (randomGetal == 1)
        {
            image = new Image(eventDir.toString() + "EnergyDrink1.png", 30, 144.6, true, false, true);
            object = new EnergyDrink("M 4,00 L 4,0 0,19 0,139 16,148 64,148 78,139 78,18 75,0 Z", px, py, image);
        }

        if (randomGetal == 2)
        {
            image = new Image(eventDir.toString() + "FishHook1.png", 40, 406, true, false, true);
            object = new FishHook("", px, -300, image);
            fishHooks.add((FishHook)object);
            Path path = new Path();
            //path.getElements().add(new MoveTo(20, 20));
            //path.getElements().add(new CubicCurveTo(380, 0, 380, 120, 200, 120));
            //path.getElements().add(new CubicCurveTo(0, 120, 0, 240, 380, 240));
        }

        if (randomGetal == 3)
        {
            image = new Image(eventDir.toString() + "Seaweed1.png", 30, 87, true, false, true);
            object = new Seaweed("M 66.00,11.00"
                    + "           C 66.00,11.00 11.00,192.00 11.00,192.00"
                    + "             11.00,192.00 4.00,285.00 4.00,285.00"
                    + "             4.00,285.00 12.00,453.00 12.00,453.00"
                    + "             12.00,453.00 31.00,545.00 31.00,545.00"
                    + "             31.00,545.00 149.00,525.00 149.00,525.00"
                    + "             149.00,525.00 193.00,389.00 193.00,389.00"
                    + "             193.00,389.00 186.00,148.00 186.00,148.00"
                    + "             186.00,148.00 125.00,58.00 125.00,58.00", px, py, image);
        }
        //TODO Diver object aanmaken
/*
         if (randomGetal == 4)
         {
         image = new Image(eventDir.toString() + "Diver1.png", 243, 184, true, false, true);
         object = new Diver("", px, py, image);
         }
         */
        //Moet altijd uitgevoerd worden
        root.getChildren().add(object.getSpriteFrame());
        objectManager.addCurrentObject(object);
    }

    /**
     *
     * @return
     */
    public boolean isUp()
    {
        return up;
    }

    /**
     *
     * @param up
     */
    public void setUp(boolean up)
    {
        this.up = up;
    }

    /**
     *
     * @return
     */
    public boolean isDown()
    {
        return down;
    }

    /**
     *
     * @param down
     */
    public void setDown(boolean down)
    {
        this.down = down;
    }

    /**
     *
     * @return
     */
    public boolean isLeft()
    {
        return left;
    }

    /**
     *
     * @param left
     */
    public void setLeft(boolean left)
    {
        this.left = left;
    }

    /**
     *
     * @return
     */
    public boolean isRight()
    {
        return right;
    }

    /**
     *
     * @param right
     */
    public void setRight(boolean right)
    {
        this.right = right;
    }

    /**
     *
     * @return
     */
    public boolean iswKey()
    {
        return wKey;
    }

    /**
     *
     * @param wKey
     */
    public void setwKey(boolean wKey)
    {
        this.wKey = wKey;
    }

    /**
     *
     * @return
     */
    public boolean isaKey()
    {
        return aKey;
    }

    /**
     *
     * @param aKey
     */
    public void setaKey(boolean aKey)
    {
        this.aKey = aKey;
    }

    /**
     *
     * @return
     */
    public boolean issKey()
    {
        return sKey;
    }

    /**
     *
     * @param sKey
     */
    public void setsKey(boolean sKey)
    {
        this.sKey = sKey;
    }

    /**
     *
     * @return
     */
    public boolean isdKey()
    {
        return dKey;
    }

    /**
     *
     * @param dKey
     */
    public void setdKey(boolean dKey)
    {
        this.dKey = dKey;
    }

    /**
     *
     * @return
     */
    public boolean isSpace()
    {
        return space;
    }

    /**
     *
     * @param space
     */
    public void setSpace(boolean space)
    {
        this.space = space;
    }

    /**
     * Plays the bite sound of a fish.
     */
    public void playBiteSound()
    {
        this.biteSound0.play();
    }

    /**
     * Gets the Group node instance that belongs to this GameManager and holds
     * all the GameObjects.
     *
     * @return the root Group belonging to this game session.
     */
    public Group getRoot()
    {
        return root;
    }

    /**
     * Gets the ObjectManager instance that belongs to this GameManager.
     *
     * @return the objectManager belonging to this game session.
     */
    public ObjectManager getObjectManager()
    {
        return objectManager;
    }

    /**
     * Gets the Player instance that belongs to this GameManager.
     *
     * @return The player belonging to this game session.
     */
    public Player getPlayer()
    {
        return player;
    }

    /**
     * Gets the score of the current Player instance.
     *
     * @return game score
     */
    public int getGameScore()
    {
        return gameScore;
    }

    /**
     * Sets the score of the current Player instance based on the added value.
     *
     * @param gameScoreAddition added value
     */
    public void setGameScore(int gameScoreAddition)
    {
        this.gameScore = this.gameScore + gameScoreAddition;
    }

    /**
     * Updates the score for Player one on screen.
     */
    public void updateScoreLabelOne()
    {
        //this.scoreLabelOne.setText(String.valueOf(this.gameScore));
        menu.updateScoreLabelOne();
    }

    /**
     * Gets the GameLoop reference that belongs to this GameManager instance.
     *
     * @return GameLoop
     */
    public GameLoop getGameLoop()
    {
        return gameLoop;
    }

    /**
     * Gets the current state of the game and returns it to the caller.
     *
     * @return gameState enum
     */
    public GameState getGameState()
    {
        return gameState;
    }

    /**
     * Sets the current state of the game, based on the GameState enum.
     *
     * @param gameState GameState enum
     */
    public void setGameState(GameState gameState)
    {
        this.gameState = gameState;
    }

    GameMode getGameMode()
    {
        return this.gameMode;
    }

    public GameMenu getGameMenu()
    {
        return menu;
    }

    public void setTimeLeft(String secondsLeft)
    {
        this.timeLeft.set(secondsLeft);
    }

    public SimpleStringProperty getTimeLeft()
    {
        return this.timeLeft;
    }

    void closeStage()
    {
        thisStage.close();
    }

    Stage getStage()
    {
        return this.thisStage;
    }

    public ArrayList<FishHook> getFishHooks()
    {
        return this.fishHooks;
    }
}
