/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netgames.clashoffishes.engine.object.events;

/**
 *
 * @author Stef
 */
public enum ObjectType {

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
