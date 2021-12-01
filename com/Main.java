package com;
//https://www.youtube.com/watch?v=1F9shq6KubY&ab_channel=CodingAP
import com.Engine.*;
import com.Engine.Graphics.Canvas;

import com.Engine.Math.Vector2f;



import org.lwjgl.glfw.GLFW;

public class Main implements Runnable {
    public Thread game;
    public Window window;
    public Input input;
    public final int WIDTH = 1280, HEIGHT = 760;

    private Canvas canvas;
    private GameLoop gameLoop;
    
    public void start(){
        game = new Thread(this,"game");
        game.run();
    }
    public void init(){
        System.out.println("Init Game");
        window = new Window(WIDTH,HEIGHT)
        .title("3D")
        .fullScreen(false)
        .frameCap(true);
       
        try{
            window.create();
            input = Input.getInstance();
        }catch(Exception e){System.out.println(e);}
        canvas = Canvas.getInstance();
        gameLoop = new GameLoop();
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
        if(input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)) {
            System.out.println("X:"+input.getScrollX()+" Y:"+input.getScrollY());
        }

    }
    private void render(){
        
        canvas.drawMatrix(gameLoop.draw(), 10);
        window.swapBuffers();
        
        //System.out.println("render Game");
    }
    public static void main(String[] args){
        new Main().start();
    }
}