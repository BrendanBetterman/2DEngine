package com.Engine.Graphics;

/*
import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;
import org.lwjgl.system.CallbackI.I;

import java.nio.*;
import static org.lwjgl.glfw.GLFW.*;
*/
import com.SandObj.SandObj;
import com.SandObj.SandType;


import static org.lwjgl.opengl.GL11.*;
import com.Engine.Math.Cluster;
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
    public void drawOutLine(double x,double y,int[] a,int gridSize){
        
        glColor4f(1.0f,0.0f,0.0f,0.05f);
        for(int i=-a[0]/2; i<=a[0]/2; i++){
            for(int u=-a[0]/2; u<=a[0]/2; u++){
                if(Math.abs(i)+2 > Math.abs(a[0]/2) || Math.abs(u)+2 > Math.abs(a[0]/2)){
                    drawQuad((float)y-gridSize+(u)*gridSize+(gridSize/1.5f), (float)x-gridSize+(i)*gridSize+(gridSize/1.5f), gridSize/1.5f, gridSize/1.5f);
                }
                
            }
        }
    }
    public void drawMatrix(int[][] a,float gridSize){
        
        glColor4f(1.0f,1.0f,1.0f,1.0f);
        for(int i =0; i< a.length; i++){
            for(int u =0; u<a[0].length; u++){
                if(a[i][u]!=0){
                    float[] color = SandType.typeColor(a[i][u]).colorf();
                    glColor4f(color[0],color[1],color[2],1.0f);
                    drawQuad((u)*gridSize, (i)*gridSize, gridSize, gridSize);
                }
            }
        }
    }
    public void drawMatrixCluster(int[][] a,float gridSize){
        glColor4f(1.0f,1.0f,1.0f,1.0f);
            try{
                int[][] c = Cluster.findHCluster(a);
                for(int i=0; i<c.length; i++){
                    try{
                        drawQuad((float)(c[i][0])*gridSize,(float)(c[i][2])*gridSize,(float)(c[i][1]+1)*gridSize,gridSize);
                    }catch(Exception e){}
                }
            }catch(Exception e){
                drawMatrix(a, gridSize);
            }
            

       
        
    }
    public void drawQuad(float x,float y, float width,float height,colorRGB color){
        //glColor4f(1.0f,1.0f,1.0f,1.0f);
        float[] colors = color.colorf();
        glColor4f(colors[0],colors[1],colors[2],colors[3]);
		glBegin(GL_POLYGON);
		
		glVertex2f(y,x);
		glVertex2f(height+y,x);
		glVertex2f(height+y,width+x);
		glVertex2f(y,width+x);
		glEnd();
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
