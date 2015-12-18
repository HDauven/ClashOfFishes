package com.netgames.clashoffishes.engine;

/**
 * Created by bram on 1/10/15.
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
