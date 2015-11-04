package com.netgames.clashoffishes.engine.object;

import com.netgames.clashoffishes.engine.GameManager;
import static com.netgames.clashoffishes.engine.GameManager.HEIGHT;
import static com.netgames.clashoffishes.engine.GameManager.WIDTH;
import com.netgames.clashoffishes.engine.object.events.EnergyDrink;
import javafx.scene.image.Image;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.Shape;

/**
 * Class that represents an actual in-game player.
 *
 * @author Hein Dauven
 */
public class Player extends AnimatedObject
{

    protected GameManager gameManager;
    // TODO make the sprite size dynamic
    protected static final double SPRITE_PIXELS_X = 81;
    protected static final double SPRITE_PIXELS_Y = 81;
    // TODO make the boundaries dynamic
    protected static final double rightBoundary = (WIDTH - SPRITE_PIXELS_X);
    protected static final double leftBoundary = 0;
    protected static final double bottomBoundary = (HEIGHT - SPRITE_PIXELS_Y);
    protected static final double topBoundary = 0;
    boolean animator = false;
    int framecounter = 0;
    int runningspeed = 6;

    /**
     * Constructor for a Player object.
     *
     * @param manager Reference to the GameManager object that manages the game.
     * @param SVGData The vector path of a Player object.
     * @param xLocation The x start coordinate of a Player object.
     * @param yLocation The y start coordinate of a Player object.
     * @param spriteCels The images that form the animation of a Player object.
     */
    public Player(GameManager manager, String SVGData, double xLocation, double yLocation, Image... spriteCels)
    {
        super(SVGData, xLocation, yLocation, spriteCels);
        gameManager = manager;
        vX = 2;
        vY = 2;
    }

    /**
     * Executes a number of methods every game refresh.
     */
    @Override
    public void update()
    {
        setXYLocation();
        setBoundaries();
        setImageState();
        movePlayer(iX, iY);
        //playAudioClip();
        checkCollision();
    }

    /**
     * Changes the X and Y location of the Player object, based on given user
     * inputs.
     */
    private void setXYLocation()
    {
        if (gameManager.isRight())
        {
            iX += vX;
        }
        if (gameManager.isLeft())
        {
            iX -= vX;
        }
        if (gameManager.isDown())
        {
            iY += vY;
        }
        if (gameManager.isUp())
        {
            iY -= vY;
        }
    }

    /**
     * Sets the values of a Player objects boundaries, based on the size of the
     * game window.
     */
    private void setBoundaries()
    {
        if (iX >= rightBoundary)
        {
            iX = rightBoundary;
        }
        if (iX <= leftBoundary)
        {
            iX = leftBoundary;
        }
        if (iY >= bottomBoundary)
        {
            iY = bottomBoundary;
        }
        if (iY <= topBoundary)
        {
            iY = topBoundary;
        }
    }

    /**
     * Sets the image state of the Player object, based on user actions.
     * Effectively realizes animation.
     */
    private void setImageState()
    {
        /* check whether the character stands still */
        if (!gameManager.isRight()
                && !gameManager.isLeft()
                && !gameManager.isDown()
                && !gameManager.isUp())
        {
            spriteFrame.setImage(imageStates.get(0));
            animator = false;
            framecounter = 0;
            spriteFrame.setRotate(0);
            spriteBound.setRotate(0);
        }

        if (gameManager.isRight())
        {
            spriteFrame.setScaleX(1);
            spriteBound.setScaleX(1);
            this.setIsFlipH(false);
            if (!animator && (!gameManager.isDown() && !gameManager.isUp()))
            {
                spriteFrame.setImage(imageStates.get(1));
                if (framecounter >= runningspeed)
                {
                    animator = true;
                    framecounter = 0;
                    spriteFrame.setRotate(0);
                    spriteBound.setRotate(0);
                }
                else
                {
                    framecounter += 1;
                }
            }
            else if (animator)
            {
                spriteFrame.setImage(imageStates.get(2));
                if (framecounter >= runningspeed)
                {
                    animator = false;
                    framecounter = 0;
                    spriteFrame.setRotate(0);
                    spriteBound.setRotate(0);
                }
                else
                {
                    framecounter += 1;
                }
            }
        }

        if (gameManager.isLeft())
        {
            spriteFrame.setScaleX(-1);
            spriteBound.setScaleX(-1);
            this.setIsFlipH(true);
            if (!animator && (!gameManager.isDown() && !gameManager.isUp()))
            {
                spriteFrame.setImage(imageStates.get(1));
                if (framecounter >= runningspeed)
                {
                    animator = true;
                    framecounter = 0;
                    spriteFrame.setRotate(0);
                    spriteBound.setRotate(0);
                }
                else
                {
                    framecounter += 1;
                }
            }
            else if (animator)
            {
                spriteFrame.setImage(imageStates.get(2));
                if (framecounter >= runningspeed)
                {
                    animator = false;
                    framecounter = 0;
                    spriteFrame.setRotate(0);
                    spriteBound.setRotate(0);
                }
                else
                {
                    framecounter += 1;
                }
            }
        }

        if (gameManager.isDown())
        {
            spriteFrame.setImage(imageStates.get(1));
            if (gameManager.isLeft())
            {
                spriteFrame.setRotate(-45);
                spriteBound.setRotate(-45);
            }
            else if (gameManager.isRight())
            {
                spriteFrame.setRotate(45);
                spriteBound.setRotate(45);
            }
        }

        if (gameManager.isUp())
        {
            spriteFrame.setImage(imageStates.get(1));
            if (gameManager.isLeft())
            {
                spriteFrame.setRotate(45);
                spriteBound.setRotate(45);
            }
            else if (gameManager.isRight())
            {
                spriteFrame.setRotate(-45);
                spriteBound.setRotate(-45);
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
    private void movePlayer(double x, double y)
    {
        spriteFrame.setTranslateX(x);
        spriteFrame.setTranslateY(y);
    }

    /**
     * Controls the playing of an AudioClip based on certain conditions.
     */
    private void playAudioClip()
    {
        if (gameManager.isSpace())
        {
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
    public void checkCollision()
    {
        for (int i = 0; i < gameManager.getObjectManager().getCurrentObject().size(); i++)
        {
            GameObject object = gameManager.getObjectManager().getCurrentObject().get(i);
            if (collide(object))
            {
                // Calculates the amount of scores a Player gets after a collision takes place.
                scoringEngine(object);
                gameManager.playBiteSound();
                // Adds the object that the player collided with to the RemovedObjects list.
                gameManager.getObjectManager().addToRemovedObjects(object);
                // Removes the object that the player collided with from the root Node.
                gameManager.getRoot().getChildren().remove(object.getSpriteFrame());
                // Deletes all the removed objects from the game.
                gameManager.getObjectManager().resetRemovedObjects();
            }
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
    public boolean collide(GameObject object)
    {
        // Checks if the player ImageView has collided with objects ImageView.
        if (this.spriteFrame.getBoundsInParent().intersects(
                object.getSpriteFrame().getBoundsInParent()))
        {

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
            if (intersection.getBoundsInLocal().getWidth() != -1)
            {
                if (object instanceof EnergyDrink)
                {
                    this.vX = 2.5;
                    this.vY = 2.5;
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Framework that controls the scoring mechanism for the game.
     *
     * @param object that the Player object has collision with.
     */
    private void scoringEngine(GameObject object)
    {
        if (object instanceof Prop)
        {
            gameManager.setGameScore(-5);
        }
        else if (object instanceof PropV)
        {
            gameManager.setGameScore(-4);
        }
        else if (object instanceof PropH)
        {
            gameManager.setGameScore(-3);
        }
        else if (object instanceof PropB)
        {
            gameManager.setGameScore(-2);
        }
        else if (object instanceof EnergyDrink)
        {
            gameManager.setGameScore(10);
        }
        gameManager.updateScoreLabelOne();
    }
}
