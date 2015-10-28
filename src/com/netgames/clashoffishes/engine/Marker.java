package com.netgames.clashoffishes.engine;

/**
 * Class that is used as a marker to mark a point on a given position.
 * 
 * @author Hein Dauven
 */
public class Marker {
    private int x;
    private int y;
    private boolean isPopulated;
    private boolean isObjectHolder;
    
    public Marker(int x, int y) {
        this.x = x;
        this.y = y;
        this.isPopulated = false;
        this.isObjectHolder = false;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public boolean isPopulated() {
        return isPopulated;
    }
    
    public void setIsPopulated(boolean isPopulated) {
        this.isPopulated = isPopulated;
    }
    
    public boolean isObjectHolder() {
        return isObjectHolder;
    }
    
    public void setIsObjectHolder(boolean isObjectHolder) {
        this.isObjectHolder = isObjectHolder;
    }
}
