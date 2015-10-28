package com.netgames.clashoffishes.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
    private final Canvas map; // Holds the map data 
    private final GraphicsContext gc; // Issues draw calls to the canvas
    private final int WIDTH;
    private final int HEIGHT;
    private final List<Marker> marker;
    private static Random rand = new Random();
    
    public GameMap(int WIDTH, int HEIGHT) {
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        this.map = new Canvas(WIDTH, HEIGHT);
        this.gc = map.getGraphicsContext2D();
        this.marker = new ArrayList<>();
        
        generateBackground(gc, "#1cc3dd", "#117d97");
        
        proceduralLayerAlgorithm(gc, marker, HEIGHT - 350, -1, "#24617c", "#24617c");
        proceduralLayerAlgorithm(gc, marker, HEIGHT - 220, 0, "#10496a", "#183150");
        proceduralLayerAlgorithm(gc, marker, HEIGHT - 100, 1, "#151424", "#0f1f2c");
    }
    
    /**
     * Generates the background for the canvas node.
     * It does this by first creating a gradient, then creating a rectangle that 
     * fills the given size of the game map and then fills the rectangle with 
     * the created gradient.
     * @param gc A GraphicsContext object that issues draw calls to the canvas.
     * @param beginColor The begin color of the gradient, in hex.
     * @param endColor  The end color of the gradient, in hex.
     */
    private void generateBackground(GraphicsContext gc, String beginColor, String endColor) {
        Stop[] stops = new Stop[] { 
            new Stop(0, Color.web(beginColor)), new Stop(1, Color.web(endColor)) 
        };
        LinearGradient linearGradient = new LinearGradient(0,0,0,1, true, CycleMethod.NO_CYCLE, stops);
        gc.setFill(linearGradient);
        gc.rect(0, 0, WIDTH, HEIGHT);
        gc.fill();
    }
    
    /**
     * This method holds the algorithm that creates the background layers.
     * It does this by first defining a gradient for the layer.
     * It then goes on to set a number of variables to determine the size of the 
     * layer and its boundaries.
     * Based on these variables, a loop will iterate a number of given times that
     * adds lines to the GraphicsContext object, which closes the path once the 
     * loop is done. This will create a layer shape that will then be filled.
     * @param gc A GraphicsContext object reference that holds the path and gradient information.
     * @param markers A list that contains all the line points of a given layer.
     * @param beginPoint The beginning point of the layer.
     * @param bias A differentiation item that allows the layers to lean more towards going up or going down.
     * @param beginColor The begin color of the gradient, in hex.
     * @param endColor  The end color of the gradient, in hex.
     */
    private void proceduralLayerAlgorithm(GraphicsContext gc, List<Marker> markers, int beginPoint, int bias, String beginColor, String endColor) {
        Stop[] stops = new Stop[] { 
            new Stop(0, Color.web(beginColor)), new Stop(1, Color.web(endColor)) 
        };
        LinearGradient linearGradient = new LinearGradient(0,0,0,1, true, CycleMethod.NO_CYCLE, stops);
        gc.setFill(linearGradient);
        gc.beginPath();
        gc.moveTo(0, HEIGHT);
        gc.lineTo(0, beginPoint);
        
        int absstepmax = 15;
        int xoffset = 10;
        int ymin = beginPoint;
        int ymax = HEIGHT;
        int xmax = (WIDTH / xoffset) + 1;
        int x = 0;
        int y = 15;
        int yOld = 0;
        int xOld = 0;
        
        for (int i = 0; i < xmax; i++) {
            yOld = 0;
            if (i == 0) { yOld = beginPoint; } 
                else { yOld = y; }
            xOld = x;
            int yTemp = yOld;
            y = y + (int)(rand.nextInt(2 * absstepmax) - absstepmax + bias);
           
            while ((y >= yOld - 2  && y <= yOld + 2)) {
                y = yTemp + (int)(rand.nextInt(2 * absstepmax) - absstepmax + bias);
            }
            
            y = Math.max(ymin, Math.min(ymax, y));
            x = x + xoffset;
            //gc.lineTo(x, y);
            markers.add(new Marker(x, y));
            gc.bezierCurveTo(xOld, yOld, x, y, x, y);
            //gc.quadraticCurveTo(xOld, yOld, x, y);
        }
        
        gc.lineTo(WIDTH, HEIGHT);
        gc.closePath();
        gc.fill();
    }
}
