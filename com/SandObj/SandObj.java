package com.SandObj;

import com.Engine.Math.Vector2f;

public class SandObj {
    public int type;
    public boolean power;
    public Vector2f direction;
    public SandObj(int type){
        this.type = type;
        this.power = false;
        this.direction = new Vector2f(0, 0);
    }
}
