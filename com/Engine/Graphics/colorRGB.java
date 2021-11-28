package com.Engine.Graphics;

public class colorRGB {
    private int r,g,b,a;
    private float RGBtoF(int tmp){
        return (float)tmp/255;
    }
    public colorRGB(int r, int g, int b){
        setColorRGBA(r, g, b,255);
    }
    public colorRGB(int r, int g, int b, int a){
        setColorRGBA(r, g, b, a);
    }
    public void setColorRGBA(int r, int g, int b,int a){
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }
    
    public float[] colorf(){
        return new float[]{RGBtoF(r),RGBtoF(g),RGBtoF(b),RGBtoF(a)};
    }
    public int[] colorI(){
        return new int[]{r,g,b,a};
    }
}
