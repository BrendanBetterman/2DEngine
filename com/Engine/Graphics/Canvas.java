package com.Engine.Graphics;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;
import org.lwjgl.system.CallbackI.I;

import java.nio.*;

import com.SandObj.SandObj;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
/**
 * Design Pattern: Singleton
 */
public class Canvas {
    private static Canvas INSTANCE;
    private colorRGB background;
    public static Canvas getInstance(){
        if (INSTANCE == null){
            INSTANCE = new Canvas();
            
           
        } 
        return INSTANCE;
    }
    public void setBackGroundColor(colorRGB color){
        background = color;
    }
    public colorRGB getBackGroundColor(){
        return background;
    }
    public void drawBackground(){

    }
    public void drawSand(SandObj[][] a,float gridSize){
        for(int i =0; i< a.length; i++){
            for(int u =0; u<a[i].length; u++){
                if(a[i][u].type!=0){
                    glColor4f(1.0f,1.0f,1.0f,1.0f);
                    drawQuad(i*gridSize, u*gridSize, gridSize, gridSize);
                }
            }
        }
    }
    public void drawMatrix(int[][] a,float gridSize){
        glColor4f(1.0f,1.0f,1.0f,1.0f);
        for(int i =0; i< a.length; i++){
            for(int u =0; u<a[i].length; u++){
                if(a[i][u]!=0){
                    drawQuad(i*gridSize, u*gridSize, gridSize, gridSize);
                }
            }
        }
    }
    public void drawQuad(float x,float y, float width,float height){
        //glColor4f(1.0f,1.0f,1.0f,1.0f);
		glBegin(GL_POLYGON);
		
		glVertex2f(y,x);
		glVertex2f(height+y,x);
		glVertex2f(height+y,width+x);
		glVertex2f(y,width+x);
		glEnd();
    }
    public void changeString(String str){
        
    }
}
