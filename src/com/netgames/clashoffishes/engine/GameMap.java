package com.netgames.clashoffishes.engine;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

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
    }
    
    
}
