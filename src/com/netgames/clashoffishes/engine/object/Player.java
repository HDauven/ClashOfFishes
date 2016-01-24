package com.netgames.clashoffishes.engine.object;

import com.netgames.clashoffishes.Administration;
import com.netgames.clashoffishes.engine.GameManager;
import static com.netgames.clashoffishes.engine.GameManager.HEIGHT;
import static com.netgames.clashoffishes.engine.GameManager.WIDTH;
import com.netgames.clashoffishes.engine.GameMode;
import com.netgames.clashoffishes.engine.object.events.EnergyDrink;
import com.netgames.clashoffishes.engine.object.events.FishHook;
import com.netgames.clashoffishes.engine.object.events.Seaweed;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.Shape;

/**
 * Class that represents an actual in-game player.
 *
 * @author Hein Dauven
 */
public class Player extends AnimatedObject {

    private int id;
    protected GameManager gameManager;
    private boolean up, down, left, right;
    private boolean space;
    private boolean biteAnimationActive;
    private static int standardSpeed = 2;
    // TODO make the sprite size dynamic
    protected static final double SPRITE_PIXELS_X = 81;
    protected static final double SPRITE_PIXELS_Y = 81;
    // TODO make the boundaries dynamic
    protected static final double rightBoundary = (WIDTH - SPRITE_PIXELS_X);
    protected static final double leftBoundary = 0;
    protected static final double bottomBoundary = (HEIGHT - SPRITE_PIXELS_Y);
    protected static final double topBoundary = 0;
    boolean animator = false;
    private boolean reverseMovement = false;
    int framecounter = 0;
    int runningspeed = 6;
    int biteframeone = 6;
    int biteframetwo = 12;
    int biteframethree = 18;
    int biteframefour = 24;
    int biteframefive = 30;
    int scoreChange = 0;
    
    // TODO ints that hold values with regard to isAlive status.
    int maxTimeDead = 300;
    int timeDead = 0;

    private int playerID;
    private int score;

    Thread tSpeed;

    /**
     * Constructor for a Player object.
     *
     * @param manager Reference to the GameManager object that manages the game.
     * @param SVGData The vector path of a Player object.
     * @param xLocation The x start coordinate of a Player object.
     * @param yLocation The y start coordinate of a Player object.
     * @param spriteCels The images that form the animation of a Player object.
     */
    public Player(int id, GameManager manager, String SVGData, double xLocation, double yLocation, int playerID, Image... spriteCels) {
        super(id, SVGData, xLocation, yLocation, spriteCels);
        gameManager = manager;
        vX = 2;
        vY = 2;
        this.playerID = playerID;
        this.score = 0;
        this.isAlive = true;
    }

    /**
     * Executes a number of methods every game refresh.
     */
    @Override
    public void update() {
        setXYLocation();
        setBoundaries();
        setImageState();
        movePlayer(iX, iY);
        checkLifeStatus();
    }

    /**
     * Changes the X and Y location of the Player object, based on given user
     * inputs.
     */
    private void setXYLocation() {
        if (isRight()) {
            iX += vX;
        }
        if (isLeft()) {
            iX -= vX;
        }
        if (isDown()) {
            iY += vY;
        }
        if (isUp()) {
            iY -= vY;
        }
    }

    /**
     * Sets the values of a Player objects boundaries, based on the size of the
     * game window.
     */
    private void setBoundaries() {
        if (iX >= rightBoundary) {
            iX = rightBoundary;
        }
        if (iX <= leftBoundary) {
            iX = leftBoundary;
        }
        if (iY >= bottomBoundary) {
            iY = bottomBoundary;
        }
        if (iY <= topBoundary) {
            iY = topBoundary;
        }
    }

    /**
     * Sets the image state of the Player object, based on user actions.
     * Effectively realizes animation.
     */
    private void setImageState() {
        /* check whether the character stands still */
        if (!biteAnimationActive) {
            if (!isRight()
                    && !isLeft()
                    && !isDown()
                    && !isUp()) {
                spriteFrame.setImage(imageStates.get(0));
                animator = false;
                framecounter = 0;
                spriteFrame.setRotate(0);
                spriteBound.setRotate(0);
            }

            if (isRight()) {
                spriteFrame.setScaleX(1);
                spriteBound.setScaleX(1);
                this.setIsFlipH(false);
                if (!animator && (!isDown() && !isUp())) {
                    spriteFrame.setImage(imageStates.get(1));
                    if (framecounter >= runningspeed) {
                        animator = true;
                        framecounter = 0;
                        spriteFrame.setRotate(0);
                        spriteBound.setRotate(0);
                    } else {
                        framecounter += 1;
                    }
                } else if (animator) {
                    spriteFrame.setImage(imageStates.get(2));
                    if (framecounter >= runningspeed) {
                        animator = false;
                        framecounter = 0;
                        spriteFrame.setRotate(0);
                        spriteBound.setRotate(0);
                    } else {
                        framecounter += 1;
                    }
                }
            }

            if (isLeft()) {
                spriteFrame.setScaleX(-1);
                spriteBound.setScaleX(-1);
                this.setIsFlipH(true);
                if (!animator && (!isDown() && !isUp())) {
                    spriteFrame.setImage(imageStates.get(1));
                    if (framecounter >= runningspeed) {
                        animator = true;
                        framecounter = 0;
                        spriteFrame.setRotate(0);
                        spriteBound.setRotate(0);
                    } else {
                        framecounter += 1;
                    }
                } else if (animator) {
                    spriteFrame.setImage(imageStates.get(2));
                    if (framecounter >= runningspeed) {
                        animator = false;
                        framecounter = 0;
                        spriteFrame.setRotate(0);
                        spriteBound.setRotate(0);
                    } else {
                        framecounter += 1;
                    }
                }
            }

            if (isDown()) {
                spriteFrame.setImage(imageStates.get(1));
                if (isLeft()) {
                    spriteFrame.setRotate(-45);
                    spriteBound.setRotate(-45);
                } else if (isRight()) {
                    spriteFrame.setRotate(45);
                    spriteBound.setRotate(45);
                }
            }

            if (isUp()) {
                spriteFrame.setImage(imageStates.get(1));
                if (isLeft()) {
                    spriteFrame.setRotate(45);
                    spriteBound.setRotate(45);
                } else if (isRight()) {
                    spriteFrame.setRotate(-45);
                    spriteBound.setRotate(-45);
                }
            }
        } else {
            // execute bite animation 
            if (framecounter >= biteframefive) {
                spriteFrame.setImage(imageStates.get(4));
                biteAnimationActive = false;
                framecounter = 0;
            } else if (framecounter >= biteframefour) {
                spriteFrame.setImage(imageStates.get(5));
                framecounter++;
            } else if (framecounter >= biteframethree) {
                spriteFrame.setImage(imageStates.get(6));
                framecounter++;
            } else if (framecounter >= biteframetwo) {
                spriteFrame.setImage(imageStates.get(5));
                framecounter++;
            } else if (framecounter >= biteframeone) {
                spriteFrame.setImage(imageStates.get(4));
                framecounter++;
            } else {
                framecounter++;
            }
        }
    }

    /**
     * Sets the Player objects new location, based on the new X and Y
     * coordinates.
     *
     * @param x coordinate of the Player object
     * @param y coordinate of the Player object
     */
    private void movePlayer(double x, double y) {
        spriteFrame.setTranslateX(x);
        spriteFrame.setTranslateY(y);
    }
    
    // Check the life status of the player
    // and pick whether a player should be revived or not
    private void checkLifeStatus() {
        if (!isAlive) {
            if (gameManager.getGameMode().equals(GameMode.EVOLUTION_OF_TIME)) {
                // Evolution of time game rule
                // xxx Example of possible implementation.
                timeDead++;
                if (timeDead == maxTimeDead) {
                    reviveFish();
                    timeDead = 0;
                }
            } else if (gameManager.getGameMode().equals(GameMode.EVOLVED)) {
                // Evolved game rule
                timeDead++;
                if (timeDead == maxTimeDead) {
                    reviveFish();
                    timeDead = 0;
                }
            }else if (gameManager.getGameMode().equals(GameMode.LAST_FISH_STANDING)) {
                // Last fish standing game rule
                // Fishes do not respawn in LFS
            }            
        }
    }

    /**
     * Controls the playing of an AudioClip based on certain conditions.
     */
    private void playAudioClip() {
        if (isSpace()) {
            gameManager.playBiteSound();
        }
    }

    /**
     * This method checks for each GameObject whether it has collision with the
     * player of the game. The detection algorithm is called each time a new
     * screen refresh has been called upon.
     *
     * This method will call the collide method on all GameObjects and if it
     * detects collision with the player, get rid of the object it has collision
     * with.
     */
    public void checkCollision() {
        for (int i = 0; i < gameManager.getObjectManager().getCurrentObject().size(); i++) {
            GameObject object = gameManager.getObjectManager().getCurrentObject().get(i);
            if (collide(object)) {
                // Calculates the amount of scores a Player gets after a collision takes place.
                biteAnimationActive = true;
                scoringEngine(object);
                collisionReaction(object);
                gameManager.playBiteSound();
                // Adds the object that the player collided with to the RemovedObjects list.
                if (gameManager.isMultiplayer()) {
                    sendCollision(object);
                }
                //gameManager.getObjectManager().addToRemovedObjects(object);
                // Removes the object that the player collided with from the root Node.
                gameManager.getRoot().getChildren().remove(object.getSpriteFrame());
                // Deletes all the removed objects from the game.
                gameManager.getObjectManager().resetRemovedObjects();
            }
        }
    }

    private void sendCollision(GameObject object) {
        try {
            gameManager.getGameServer().collision(playerID, object.getID(), this.scoreChange);
        } catch (RemoteException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method checks for collision detection between the Player object and
     * another GameObject object. It goes through a number of steps to ascertain
     * whether collision has taken place with the given object.
     *
     * First, it checks whether the Player objects ImageView node and the
     * GameObjects ImageView node have intersected. (via the BoundsInParent of
     * Node)
     *
     * If this is the case, the SVG path of both objects are used to create a
     * new Shape via the SVGPath class intersect method. (which returns a new
     * shape)
     *
     * Based on the result of this intersect, the new Shape will be checked to
     * see whether it has a width. If the new Shape has no width, we know the
     * shape is empty and no intersection took place.
     *
     * @param object A GameObject that is part of the ObjectManager.
     * @return Whether collision took place or not.
     */
    @Override
    public boolean collide(GameObject object) {
        // Checks if the player ImageView has collided with objects ImageView.
        if (this.spriteFrame.getBoundsInParent().intersects(
                object.getSpriteFrame().getBoundsInParent())) {

            // A shape is generated based on the SVG path of the player and the object
            // If the shapes intersect, a new shape is created.
            Shape intersection = SVGPath.intersect(
                    gameManager.getPlayer().getSpriteBound(),
                    object.getSpriteBound());

            // Based on the prior succes iiintersection we will check whether the collision really
            // took place. If no collision took place, the object shouldn't have any size,
            // so the width would result in a '-1' (-1 meaning no width available).
            // We check here for the opposite of '-1' (0 and more) to see whether collision
            // took place.
            if (intersection.getBoundsInLocal().getWidth() != -1) {
                return true;
            }
        }
        return false;
    }

    /**
     * Framework that controls the scoring mechanism for the game.
     *
     * @param object that the Player object has collision with.
     */
    private void scoringEngine(GameObject object) {
        scoreChange = 0;
        if (object instanceof Seaweed) {
            scoreChange = -5;
            updateScore(-5);
        } else if (object instanceof FishHook) {
            scoreChange = -2;
            updateScore(-2);
        } else if (object instanceof EnergyDrink) {
            scoreChange = 10;
            updateScore(10);
        }

        gameManager.updateScoreLabel(this.playerID, this.score);
    }

    public void updateScore(int score) {
        this.score += score;
    }

    private void collisionReaction(GameObject object) {
        if (object instanceof Seaweed) {
            if (gameManager.isMultiplayer()) {
                sendSpeedUpdate(1.3, true);
                enforceReverseMovement(true);
                reverseMovement = true;
            } else {
                this.updateSpeed(1.3);
            }
        } else if (object instanceof FishHook) {
            if (gameManager.isMultiplayer()) {
                sendSpeedUpdate(0.5, false);
                enforceReverseMovement(false);
            } else {
                this.updateSpeed(0.5);
            }
        } else if (object instanceof EnergyDrink) {
            if (gameManager.isMultiplayer()) {
                sendSpeedUpdate(2.7, false);
                enforceReverseMovement(false);
                reverseMovement = false;
            } else {
                this.updateSpeed(2.7);
            }
        }

        if (!gameManager.isMultiplayer()) {
            gameManager.getObjectManager().removeCurrentObject(object);
            sendSpeedUpdate(1.3, true);
            enforceReverseMovement(true);
        } else if (object instanceof FishHook) {
            sendSpeedUpdate(0.5, false);
            enforceReverseMovement(false);
        } else if (object instanceof EnergyDrink) {
            sendSpeedUpdate(2.7, false);
            enforceReverseMovement(false);
        }

        if (!gameManager.isMultiplayer()) {
            gameManager.getObjectManager().removeCurrentObject(object);
        } else {
            try {
                gameManager.getGameServer().deleteObject(object.getID());
            } catch (RemoteException re) {
                re.printStackTrace();
            }
        }
    }

    /**
     * Informs the players of an update in the players speed.
     */
    private void sendSpeedUpdate(double speed, boolean reverseMovement) {
        try {
            gameManager.getGameServer().updateSpeed(speed, playerID, reverseMovement);
        } catch (RemoteException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    public int getID() {
        return this.id;
    }

    public void updateSpeed(double newSpeed) {
        this.setvX(newSpeed);
        this.setvY(newSpeed);
        if (gameManager.isMultiplayer()) {
            tSpeed = new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        Thread.sleep(3000);
                        vX = standardSpeed;
                        vY = standardSpeed;
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            tSpeed.start();
        }
    }

    public int getPlayerID() {
        return playerID;
    }

    public int getScore() {
        return score;
    }

    public void updateMove(String key, boolean pressed) {
        if (!reverseMovement) {
            switch (key) {
                case "UP":
                    this.setUp(pressed);
                    break;
                case "DOWN":
                    this.setDown(pressed);
                    break;
                case "LEFT":
                    this.setLeft(pressed);
                    break;
                case "RIGHT":
                    this.setRight(pressed);
                    break;
                case "ENTER":
                    // Method that eats fish
                    eatFish();
                    break;
            }
        } else {
            switch (key) {
                case "UP":
                    this.setDown(pressed);
                    break;
                case "DOWN":
                    this.setUp(pressed);
                    break;
                case "LEFT":
                    this.setRight(pressed);
                    break;
                case "RIGHT":
                    this.setLeft(pressed);
                    break;
                case "ENTER":
                    // Method that eats fish
                    eatFish();
                    break;
            }
        }
    }
    
    // Method that checks whether a fish collides with another fish
    // If fish collides with other fish, check who has the highest score,
    // remove other fish and inform others of the removal.
    public void eatFish() {
        // TODO implement eat fish
        // a new method should be made named checkFishCollision with some modifications to checkCollision.
        // Collide method can be re-used for this method.
        
        System.out.println("test: " + this.isAlive);
        for (Player player : gameManager.getPlayers()) {
            if (collide(player)) {
                player.isAlive = false;
                System.out.println(player.getPlayerID());
            }
        }
        
        
    }

    public boolean isReverseMovement() {
        return reverseMovement;
    }

    public void enforceReverseMovement(boolean isReversed) {
        if (reverseMovement != isReversed) {
            reverseKeys();
        }
    }

    public void reverseKeys() {
        boolean tempRight = right;
        boolean tempLeft = left;
        boolean tempUp = up;
        boolean tempDown = down;
        
        if (tempRight) {
            this.setRight(false);
            this.setLeft(true);
        }

        if (tempLeft) {
            this.setLeft(false);
            this.setRight(true);
        }

        if (tempUp) {
            this.setUp(false);
            this.setDown(true);
        }

        if (tempDown) {
            this.setDown(false);
            this.setUp(true);
        }
    }

    public void setReverseMovement(boolean reverseMovement) {
        this.reverseMovement = reverseMovement;
    }

    public void setBiteAnimationActive(boolean biteAnimationActive) {
        this.biteAnimationActive = biteAnimationActive;
    }

    private void reviveFish() {
        this.isAlive = true;
    }
}
