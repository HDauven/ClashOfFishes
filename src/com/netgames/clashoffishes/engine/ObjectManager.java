package com.netgames.clashoffishes.engine;

import com.netgames.clashoffishes.engine.object.GameObject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Class that manages all the objects in the game.
 * 
 * @author Hein Dauven
 */
public class ObjectManager {
    private final List<GameObject> currentObjects;
    private final List<GameObject> collisionChecklist;
    private final Set<GameObject>  removedObjects;
    
    public ObjectManager() {
        this.currentObjects = new ArrayList<>();
        this.collisionChecklist = new ArrayList<>();
        this.removedObjects = new HashSet<>();
    }
}
