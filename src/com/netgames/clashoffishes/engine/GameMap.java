package com.netgames.clashoffishes.engine;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;

/**
 * Class that constructs and draws the game map.
 * 
 * @author Hein
 */
public class GameMap {
    private Canvas map; // Holds the map data 
    private GraphicsContext gc; // Issues draw calls to the canvas
    
    
    public GameMap(int WIDTH, int HEIGHT) {
        map = new Canvas(WIDTH, HEIGHT);
        gc = map.getGraphicsContext2D();
        generateBackground(gc, "#1cc3dd", "#117d97");
    }
    
    /**
     * Generates the background for the scene node.
     * It does this by first creating a gradient, then creating a rectangle that 
     * fills the given size of the game map and then fills the rectangle with 
     * the created gradient.
     * @param gc A GraphicsContext object reference that holds the background information.
     * @param beginColor The begin color of the gradient, in hex.
     * @param endColor  The end color of the gradient, in hex.
     */
    private static void generateBackground(GraphicsContext gc, String beginColor, String endColor) {
        Stop[] stops = new Stop[] { 
            new Stop(0, Color.web(beginColor)), new Stop(1, Color.web(endColor)) 
        };
        LinearGradient linearGradient = new LinearGradient(0,0,0,1, true, CycleMethod.NO_CYCLE, stops);
        gc.setFill(linearGradient);
        gc.rect(0, 0, WIDTH, HEIGHT);
        gc.fill();
    }
}
