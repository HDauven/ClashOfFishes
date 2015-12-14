package com.netgames.clashoffishes.server.remote;

/**
 *
 * @author Stef
 */
public enum ObjectType
{
    ENERGYDRINK(1),
    FISHHOOK(2),
    JELLYFISH(3),
    SEAWEED(4);
    
    private final int value;
    private ObjectType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

