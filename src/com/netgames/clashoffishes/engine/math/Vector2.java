package com.netgames.clashoffishes.engine.math;

/**
 * Created by bram on 1/10/15.
 */
public class Vector2 {
    private float x;
    private float y;

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2 set (Vector2 v) {
        x = v.x;
        y = v.y;
        return this;
    }

    public Vector2 set (float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public Vector2 sub (Vector2 v) {
        x -= v.x;
        y -= v.y;
        return this;
    }

    public Vector2 sub (float x, float y) {
        this.x -= x;
        this.y -= y;
        return this;
    }

    public Vector2 add (Vector2 v) {
        x += v.x;
        y += v.y;
        return this;
    }

    public Vector2 add (float x, float y) {
        this.x += x;
        this.y += y;
        return this;
    }
}
