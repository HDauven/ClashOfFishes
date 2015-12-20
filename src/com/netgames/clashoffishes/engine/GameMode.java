package com.netgames.clashoffishes.engine;

/**
 * @author Hein Dauven
 */
public enum GameMode {

    LAST_FISH_STANDING(1),
    EVOLUTION_OF_TIME(2),
    EVOLVED(3);

    private final int value;

    private GameMode(final int newValue) {
        this.value = newValue;
    }

    public int getValue() {
        return value;
    }

}
