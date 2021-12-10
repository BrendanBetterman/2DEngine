package com;

import com.Engine.Input;
import com.SandObj.Terrain;
import org.lwjgl.glfw.GLFW;
public class GameLoop implements Runnable{
    
    public Terrain world;

    private Input input;
    private int gridSize;
    private int xOff;
    private int frames;
    private int gameSpeed;
    private boolean held;
    public int type;
    private boolean holdingShift;
    private int replaceType;
    private int brushSize = 1;
    private int lastScroll=0;
    public GameLoop(int gridSize){
        this.gridSize = gridSize;
        world = new Terrain(5, 512);
        input = Input.getInstance();
        xOff =64;
        frames =0;
        gameSpeed =1;
        held = false;
        type = 1;
        holdingShift = false;
    }
    public int[][] draw(int width, int height){
        return world.draw(width, height, xOff);
    }
    public int[] drawCursor(){
        return new int[]{brushSize,type};
    }
    public void controls(int WIDTH,int HEIGHT){
        if(holdingShift){
            brushSize += (int)input.getScrollY() - lastScroll;
            lastScroll = (int)input.getScrollY();
            if(brushSize <1){
                brushSize = 1;
            }
            //brushSize = (int)input.getScrollY()<=0 ? 1: (int)(input.getScrollY()*2);
        }else{
            float Senstivity = 1.1f;
            if(Math.abs(input.getScrollY() - lastScroll) > Senstivity){
                type -= (int)((input.getScrollY() - lastScroll)/Senstivity);
                lastScroll = (int)input.getScrollY();
                if(type <1){
                    type = 1;
                }
                if(type >15){
                    type = 15;
                }
            }
            
        }
        

        //int moveSpeed = (int)input.getScrollY();
        int moveSpeed = 2;
        if(input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)) {
            //System.out.println("X:"+input.getMouseX()+" Y:"+input.getMouseY());
            if(holdingShift){
                if(brushSize ==1){
                    world.replaceSand((int)(input.getMouseX()/gridSize+xOff),(HEIGHT/gridSize)-(int)(input.getMouseY()/gridSize),type,replaceType);
                }else{
                    world.replaceSquare((int)(input.getMouseX()/gridSize+xOff),(HEIGHT/gridSize)-(int)(input.getMouseY()/gridSize),brushSize,type,replaceType);
                }
            }else{
                if(brushSize ==1){
                    world.addSand((int)(input.getMouseX()/gridSize+xOff),(HEIGHT/gridSize)-(int)(input.getMouseY()/gridSize),type);
                }else{
                    world.addSquare((int)(input.getMouseX()/gridSize+xOff),(HEIGHT/gridSize)-(int)(input.getMouseY()/gridSize),brushSize,type);
                }
            }
        }
        if(input.isKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT)){
            if(!holdingShift){
                replaceType = world.getType((int)input.getMouseX()/gridSize+xOff,(HEIGHT/gridSize)-(int)input.getMouseY()/gridSize);
                holdingShift =true;
            }
        }else{
            holdingShift = false;
        }

        if(input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_RIGHT)) {
            if(holdingShift){
                if(brushSize ==1){
                    world.replaceSand((int)(input.getMouseX()/gridSize+xOff),(HEIGHT/gridSize)-(int)(input.getMouseY()/gridSize),0,replaceType);
                }else{
                    world.replaceSquare((int)(input.getMouseX()/gridSize+xOff),(HEIGHT/gridSize)-(int)(input.getMouseY()/gridSize),brushSize,0,replaceType);
                }
            }else{
                if(brushSize ==1){
                    world.addSand((int)(input.getMouseX()/gridSize+xOff),(HEIGHT/gridSize)-(int)(input.getMouseY()/gridSize),0);
                }else{
                    world.addSquare((int)(input.getMouseX()/gridSize+xOff),(HEIGHT/gridSize)-(int)(input.getMouseY()/gridSize),brushSize,0);
                }
            }
        }
        

        for(int keys = 49; keys<58; keys++){
            if(input.isKeyDown(keys)){
                type = keys-48;
            }
        }

        //Movement
        if(input.isKeyDown(GLFW.GLFW_KEY_A)){
            if(xOff-moveSpeed >=0){
                xOff -=moveSpeed;
            }
        }
        if(input.isKeyDown(GLFW.GLFW_KEY_D)){

            if(xOff+moveSpeed<(4*512)){
                xOff +=moveSpeed;
            }
        }
        if(input.getMouseX() > WIDTH -25 && !holdingShift){
            if(xOff+moveSpeed<(4*512)){
                xOff +=moveSpeed;
            }
        }
        if(input.getMouseX() < 25 && !holdingShift){
            if(xOff-moveSpeed >=0){
                xOff -=moveSpeed;
            }
        }

        //Pause
        if(input.isKeyDown(GLFW.GLFW_KEY_SPACE)){
            if(gameSpeed !=5 && !held){
                gameSpeed = 5;
            }else if(!held){
                gameSpeed = 1;
            }
            held = true; 
        }
        if(!input.isKeyDown(GLFW.GLFW_KEY_SPACE)){
            held = false; 
        }
        if(input.isKeyDown(GLFW.GLFW_KEY_KP_1)){
            gameSpeed = 50;
        }
        if(input.isKeyDown(GLFW.GLFW_KEY_KP_2)){
            gameSpeed = 6;
        }
        if(input.isKeyDown(GLFW.GLFW_KEY_KP_3)){
            gameSpeed = 1;
        }
    }
    public void update(int width,int height){
        frames++;
        if(gameSpeed !=5){
            if(frames % gameSpeed ==0 || gameSpeed == 1){
                world.update(width,height,xOff);
            }
            
        }
        
        
        //world.addSand(rand.nextInt(width), 511, 1);
         
    }
    @Override
    public void run(){
        while(!input.isKeyDown(GLFW.GLFW_KEY_ESCAPE)){
            //for(int i=0; i<2; i++){
            update(1024, 512);
            
         System.out.println("running");
            }
       // }
        
            
           
        
    }
}
