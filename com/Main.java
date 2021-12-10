package com;


//https://www.youtube.com/watch?v=1F9shq6KubY&ab_channel=CodingAP
import com.Engine.*;
import com.Engine.Graphics.Canvas;

import com.Engine.Graphics.UI;
import com.Engine.Graphics.colorRGB;

import org.lwjgl.glfw.GLFW;


public class Main implements Runnable {
    public Thread game;
    
    public Window window;
    public Input input;
    public final int WIDTH = 1280, HEIGHT = 760;
    private int gridSize = 5;
    private Canvas canvas;
    private GameLoop gameLoop;
    private UI tmp = new UI();
    public void start(){
        game = new Thread(this,"game");
        
        
        game.run();
        
    }
    public void init(){
        System.out.println("Init Game");
        window = new Window(WIDTH,HEIGHT)
        .title("3D")
        .fullScreen(false)
        .frameCap(false);
        
        try{
            window.create();
            input = Input.getInstance();
        }catch(Exception e){System.out.println(e);}
        canvas = Canvas.getInstance();
        gameLoop = new GameLoop(gridSize);
      
        canvas.setBackGroundColor(new colorRGB(78, 205, 196));
    }
    
    public void run(){
        init();
        
        while (!window.shouldClose() && !input.isKeyDown(GLFW.GLFW_KEY_ESCAPE)){
            
            update();
            render();
            
            if(input.isKeyDown(GLFW.GLFW_KEY_F11)) window.toggleFullScreen();
        }
        window.destroy();
    }
    private void update(){
         
        window.update();
        if(input.getMouseY() >HEIGHT -125){
            tmp.setY(((int)input.getMouseY()-HEIGHT+125)<50 ? (int)input.getMouseY()-HEIGHT+125 : 50 );
        }
        gameLoop.update(WIDTH/gridSize,HEIGHT/gridSize<=512 ? HEIGHT/gridSize+gridSize : 512);
        gameLoop.controls(WIDTH,HEIGHT);
        
        
    }
    private void render(){
      
        canvas.drawMatrix(gameLoop.draw(WIDTH/gridSize+gridSize,HEIGHT/gridSize<=512 ? HEIGHT/gridSize+gridSize : 512), gridSize);
        canvas.drawOutLine(input.getMouseX(),HEIGHT-input.getMouseY(),gameLoop.drawCursor(),gridSize);
        
        tmp.setSize(50);
        tmp.setX(WIDTH/2);
        
        tmp.setType(gameLoop.type);
        tmp.render();
        window.swapBuffers();
        
        //System.out.println("render Game");
    }
    public static void main(String[] args){
        new Main().start();
    }
}