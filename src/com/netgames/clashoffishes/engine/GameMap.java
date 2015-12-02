package com.netgames.clashoffishes.engine;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
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
    private final GraphicsContext gc; // Issues draw calls to the canvas
    private final int WIDTH;  // Width of the GameMap
    private final int HEIGHT; // Height of the GameMap
    private final List<Marker> backMarker; // Holds the marker points of the back layer
    private final List<Marker> middleMarker; // Holds the marker points of the middle layer
    private final List<Marker> frontMarker; // Holds the marker points of the front layer
    private final URL backgroundDir; // URL to the folder where all the background related images are located
    private final Random rand;
    
    public GameMap(int WIDTH, int HEIGHT) {
        // Instantiation/initialization of all the variables belonging to the GameMap
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        this.map = new Canvas(WIDTH, HEIGHT);
        this.gc = map.getGraphicsContext2D();
        this.backMarker = new ArrayList<>();
        this.middleMarker = new ArrayList<>();
        this.frontMarker = new ArrayList<>();
        this.backgroundDir = this.getClass().getResource("/com/netgames/clashoffishes/images/background/");
        this.rand = new Random();
        
        generateMap();
        generateMarkers();
    }
    
    // Map constructor that allows for a seed to be used.
    public GameMap(int WIDTH, int HEIGHT, int seed) {
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        this.map = new Canvas(WIDTH, HEIGHT);
        this.gc = map.getGraphicsContext2D();
        this.backMarker = new ArrayList<>();
        this.middleMarker = new ArrayList<>();
        this.frontMarker = new ArrayList<>();
        this.backgroundDir = this.getClass().getResource("/com/netgames/clashoffishes/images/background/");
        this.rand = new Random(seed);
        
        generateMap();
        generateMarkers();
    }
    
    /**
     * Method calls that generate the GameMap
     */
    private void generateMap() {
        generateBackground(gc, "#1cc3dd", "#117d97");
        
        proceduralLayerAlgorithm(gc, backMarker,   HEIGHT - 350, -1, "#24617c", "#24617c");
        proceduralLayerAlgorithm(gc, middleMarker, HEIGHT - 220,  0, "#10496a", "#183150");
        proceduralLayerAlgorithm(gc, frontMarker,  HEIGHT - 100,  1, "#151424", "#0f1f2c");
        
        proceduralBoatPlacement(gc, backMarker); // The boat will always be placed on the back layer 
    }
    
    /**
     * Meant for debugging purpose, comment out to get rid of the black dots in the actual game
     */
    private void generateMarkers() {
        proceduralMarkerPlacementAlgorithm(gc, backMarker);
        proceduralMarkerPlacementAlgorithm(gc, middleMarker);
        proceduralMarkerPlacementAlgorithm(gc, frontMarker);
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
     * @param gc A GraphicsContext object that issues draw calls to the canvas.
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
            markers.add(new Marker(x, y));
            gc.bezierCurveTo(xOld, yOld, x, y, x, y);
        }
        
        gc.lineTo(WIDTH, HEIGHT);
        gc.closePath();
        gc.fill();
    }
    
    /**
     * Places a boat on a randomly chosen marker, based on a number of conditions.
     * These conditions being that the marker does not allow the boat to be placed
     * outside of the game field itself.
     * Once a position is decided upon, all the markers that fall within the boats
     * reach, will be marked is populated, so that no objects can be placed on top
     * of the boat.
     * @param gc A GraphicsContext object that issues draw calls to the canvas.
     * @param markers A list that contains all the line points of a given layer.
     */
    private void proceduralBoatPlacement(GraphicsContext gc, List<Marker> markers) {
        Image boat = pickABoat();
        
        int pos = rand.nextInt(markers.size() - ((int)(boat.getWidth() / 10)) + 1);

        gc.drawImage(boat, markers.get(pos).getX(), markers.get(pos).getY() - boat.getHeight() + 75, boat.getWidth(), boat.getHeight());
        markers.get(pos).setIsObjectHolder(true);
        
        int beginBoat = markers.get(pos).getX();
        int endBoat = markers.get(pos).getX() + (int)boat.getWidth();
        
        for (Marker m : markers) {
            if (m.getX() >= beginBoat && m.getX() <= endBoat) {
                m.setIsPopulated(true);
            }
        }
    }
    
    /**
     * Picks one of the available Boat images through a random number generated 
     * from 0 inclusive to 6 exclusive.
     * @return One of the chosen images.
     */
    private Image pickABoat() {
        int boat = rand.nextInt(6);
        Image boatImage = null;
        switch (boat) {
            case 0:
                boatImage = new Image(backgroundDir.toString() + "BoatClassic1.png");
                break;
            case 1:
                boatImage = new Image(backgroundDir.toString() + "BoatClassic2.png");
                break;
            case 2:
                boatImage = new Image(backgroundDir.toString() + "BoatClassic3.png");
                break;
            case 3:
                boatImage = new Image(backgroundDir.toString() + "BoatModern1.png");
                break;
            case 4:
                boatImage = new Image(backgroundDir.toString() + "BoatModern2.png");
                break;
            case 5:
                boatImage = new Image(backgroundDir.toString() + "BoatModern3.png");
                break;
        }
        return boatImage;
    }
    
    /**
     * Based on the generated markers, this method places a dot on each of the 
     * corresponding markers to indicate where the markers are located.
     * These markers are used to place objects on top of them if they're free, 
     * else an object cannot be placed on top of them.
     * Note: This method is for debugging purposes.
     * @param gc A GraphicsContext object that issues draw calls to the canvas.
     * @param markers A list that contains all the line points of a given layer. 
     */
    private static void proceduralMarkerPlacementAlgorithm(GraphicsContext line, List<Marker> markers) {
        line.setStroke(Color.BLACK);
        line.setLineWidth(3);
        for (Marker m : markers) {
            if (m.isPopulated()) {
                line.setStroke(Color.RED);
            } else {
                line.setStroke(Color.BLACK);
            }
            line.strokeLine(m.getX(), m.getY(), m.getX(), m.getY() + 1);
        }
    }

    public Canvas getMap() {
        return map;
    }
    
    public void setMap(Canvas map) {
        this.map = map;
    }
}
