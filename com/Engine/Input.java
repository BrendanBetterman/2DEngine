package com.Engine;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWScrollCallback;

public class Input {
    //Singleton Design Pattern
    private static Input INSTANCE;
    public static Input getInstance(){
        if(INSTANCE == null){
            INSTANCE = new Input();
        }
        return INSTANCE;
    }
    //Keys
    private boolean[] keys = new boolean[GLFW.GLFW_KEY_LAST];
    private boolean[] mouseButtons = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];
    private double mouseX,mouseY;
    private double scrollX, scrollY;

    //CallBacks
    private GLFWKeyCallback keyboard;
    private GLFWCursorPosCallback mouseMove;
    private GLFWMouseButtonCallback mouse;
    private GLFWScrollCallback mouseScroll;

    /**
     * This is a singleton Input.getInstance();
     */
    public Input(){
        keyboard = new GLFWKeyCallback() {
            @Override 
            public void invoke(long window,int key, int scancode,int action,int mods){
                keys[key] = (action != GLFW.GLFW_RELEASE);
            }
        };
        mouseMove = new GLFWCursorPosCallback() {
            @Override 
            public void invoke(long window,double xPos,double yPos){
                mouseX = xPos;
                mouseY = yPos;
            }
        };
        mouse = new GLFWMouseButtonCallback() {
            public void invoke(long window,int button,int action,int mods){
                mouseButtons[button] = (action != GLFW.GLFW_RELEASE);
            }
               
        };
        mouseScroll = new GLFWScrollCallback() {
            public void invoke(long window,double offsetX,double offsetY){
                scrollX += offsetX;
                scrollY += offsetY;

            }
        };
    }
    public void destroy(){
        keyboard.free();
        mouseMove.free();
        mouse.free();
        mouseScroll.free();
    }
    public boolean isKeyDown(int key){
        return keys[key];
    }
    public boolean isButtonDown(int button){
        return mouseButtons[button];
    }
    public double getMouseX(){
        return mouseX;
    }
    public double getMouseY(){
        return mouseY;
    }
    public double getScrollX(){
        return scrollX;
    }
    public double getScrollY(){
        return scrollY;
    }
    public GLFWKeyCallback getKeyboardCallback(){
        return keyboard;
    }
    public GLFWCursorPosCallback getMouseMoveCallaback(){
        return mouseMove;
    }
    public GLFWMouseButtonCallback getMouseButtonsCallback(){
        return mouse;
    }
    public GLFWScrollCallback getMouseScrollCallback(){
        return mouseScroll;
    }
    
    
}
