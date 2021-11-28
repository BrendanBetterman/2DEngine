package com.Engine;

import org.lwjgl.glfw.*;
import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.opengl.*;

import static org.lwjgl.opengl.GL11.*;



import com.Engine.Graphics.Canvas;
import com.Engine.Graphics.colorRGB;



public class Window{
    //Builder
    private int width, height;
    private String title = ""; 
    private Boolean fullScreen = false;
    private boolean frameCap = true;
    private Canvas canvas;

    //callbacks
    private GLFWWindowSizeCallback sizeCallback;
    private boolean isResized = true;
    //Window instance
    private long window;
    public Input input;
    private int[] windowPosX, windowPosY;

    //FPS
    private long time;
    private float FPS;
    private int frames;

    /**
     * Uses Builder Design Pattern for title and fullScreen
     * @param width
     * @param height
     */
    public Window(int width,int height){
        this.width = width;
        this.height = height;
    }
    public Window title(String title){
        this.title = title;
        return this;
    }
    public Window fullScreen(Boolean bool){
        this.fullScreen = bool;
        return this;
    }
    public Window frameCap(Boolean bool){
        this.frameCap = bool;
        return this;
    }
    
    //INIT's the window
    public void create(){
        if (!GLFW.glfwInit())
            throw new RuntimeException("Failed to init the GLFW");
        input = Input.getInstance();
        
        window = GLFW.glfwCreateWindow(width,height,title, fullScreen ? glfwGetPrimaryMonitor() : 0,0);
        
        if ( window == 0 )
			throw new RuntimeException("Failed to create the GLFW window");
        GLFWVidMode vidmode = GLFW.glfwGetVideoMode(glfwGetPrimaryMonitor());
        windowPosX = new int[]{(vidmode.width()-width)/2};
        windowPosY = new int[]{(vidmode.height()-height)/2};
        GLFW.glfwSetWindowPos(window,windowPosX[0],windowPosY[0]);
        GLFW.glfwMakeContextCurrent(window);
        GL.createCapabilities();
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity(); // Resets any previous projection matrices
		glOrtho(0, width, 0, height, 1, 0);
        glMatrixMode(GL_MODELVIEW);
        createCallbacks();

        GLFW.glfwShowWindow(window);
        if(frameCap){
            GLFW.glfwSwapInterval(1);//Limits to 60fps.
        }else{
            GLFW.glfwSwapInterval(0);//No Limit.
        }
        canvas = Canvas.getInstance();
        canvas.setBackGroundColor(new colorRGB(0,0,0));
        time = System.currentTimeMillis();

        
    }
    private void createCallbacks(){
        //Window call backs
        sizeCallback = new GLFWWindowSizeCallback(){
            public void invoke(long window,int w, int h){
                width = w;
                height = h;
                isResized = true;
            }
        };
        //Input callbacks
        GLFW.glfwSetKeyCallback(window, input.getKeyboardCallback());
        GLFW.glfwSetCursorPosCallback(window, input.getMouseMoveCallaback());
        GLFW.glfwSetMouseButtonCallback(window, input.getMouseButtonsCallback());
        GLFW.glfwSetScrollCallback(window, input.getMouseScrollCallback());
        //window callbacks
        GLFW.glfwSetWindowSizeCallback(window, this.sizeCallback);
        
    
    }
    public float getFPS(){
        return FPS;
    }
    public void destroy(){
        input.destroy();
        sizeCallback.free();
        GLFW.glfwWindowShouldClose(window);
        GLFW.glfwDestroyWindow(window);
        GLFW.glfwTerminate();
    }
    public void update(){
        if(isResized){
            GL11.glViewport(0, 0, width, height);
            isResized = false;
            if(fullScreen){
                GLFW.glfwSetWindowMonitor(window, GLFW.glfwGetPrimaryMonitor(), 0, 0, width, height, 0);
            }
        }
        
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        

        
        frames++;
        try{
            FPS = 1000/(System.currentTimeMillis()-time);
        }catch(Exception e){}
        time = System.currentTimeMillis();
        if(frames% 60 ==59){
            GLFW.glfwSetWindowTitle(window,title + " | FPS: " +FPS);
            frames = 0;
        }
    }
    public void swapBuffers(){
        
        GLFW.glfwSwapBuffers(window);
        colorRGB background = canvas.getBackGroundColor();
        float[] backgroundRGB = background.colorf();
        GL11.glClearColor(backgroundRGB[0], backgroundRGB[1], backgroundRGB[2], 1.0f);
        GLFW.glfwPollEvents();
    }
    public boolean shouldClose(){
        return GLFW.glfwWindowShouldClose(window);
    }

    public void setBackGroundColor(int[] RGB){
        canvas.setBackGroundColor(new colorRGB(RGB[0], RGB[1], RGB[2]));
    }
    public int getHeight(){
        return height;
    }
    public int getWidth(){
        return height;
    }
    public void toggleFullScreen(){
        isResized = true;
        if(fullScreen=!fullScreen)GLFW.glfwGetWindowPos(window, windowPosX, windowPosY); 
        GLFW.glfwSetWindowMonitor(window, fullScreen ? GLFW.glfwGetPrimaryMonitor() : 0,windowPosX[0],windowPosY[0],width,height,0);
    }
}