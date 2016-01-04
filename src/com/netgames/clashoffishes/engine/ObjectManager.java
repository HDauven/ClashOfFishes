package com.netgames.clashoffishes.engine;

import com.netgames.clashoffishes.engine.object.GameObject;
import java.util.ArrayList;
import java.util.Arrays;
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
    private final List<GameObject> collideChecklist;
    private final Set<GameObject> removedObjects;

    /**
     *
     */
    public ObjectManager() {
        this.currentObjects = new ArrayList<>();
        this.collideChecklist = new ArrayList<>();
        this.removedObjects = new HashSet<>();
    }

    /**
     *
     * @return
     */
    public List<GameObject> getCurrentObject() {
        return currentObjects;
    }

    /**
     *
     * @param objects
     */
    public void addCurrentObject(GameObject... objects) {
        currentObjects.addAll(Arrays.asList(objects));
    }

    /**
     *
     * @param objects
     */
    public void removeCurrentObject(GameObject... objects) {
        currentObjects.removeAll(Arrays.asList(objects));
    }

    /**
     *
     */
    public void resetCurrentObject() {
        currentObjects.clear();
    }

    /**
     *
     * @return
     */
    public List getCollideChecklist() {
        return collideChecklist;
    }

    /**
     *
     */
    public void resetCollideChecklist() {
        collideChecklist.clear();
        collideChecklist.addAll(currentObjects);
    }

    /**
     *
     * @return
     */
    public Set getRemovedObject() {
        return removedObjects;
    }

    /**
     *
     * @param actors
     */
    public void addToRemovedObjects(GameObject... objects) {
        if (objects.length > 1) {
            removedObjects.addAll(Arrays.asList((GameObject[]) objects));
        } else {
            removedObjects.add(objects[0]);
        }
    }

    /**
     *
     */
    public void resetRemovedObjects() {
        currentObjects.removeAll(removedObjects);
        removedObjects.clear();
    }

    public GameObject getObject(int objectId) {
        for (GameObject go : this.currentObjects) {
            if (go.getID() == objectId) {
                return go;
            }
        }
        return null;
    }
}
