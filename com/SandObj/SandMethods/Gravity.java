package com.SandObj.SandMethods;

import com.SandObj.Terrain;

public class Gravity {
    
    public static void sandGravity(Terrain terrain,int x, int y){
        
        try{
            if(terrain.swapLiquid(x, y, x, y-1)){
            }else if(terrain.swapLiquid(x, y, x+1, y-1)){
            }else if(terrain.swapLiquid(x, y, x-1, y-1)){
            } 
        }catch(Exception e){}
    }
    public static void sandGravity(Terrain terrain,int gravity,int x, int y){
        try{
            if(terrain.swapLiquid(x, y, x, y-gravity)){
                terrain.terrain[x%terrain.chunkSize].chunk[x][y].stable = false;
                return;
            }
            if(terrain.terrain[x%terrain.chunkSize].chunk[x][y].stable){
                return;
            }
            if(terrain.swapLiquid(x, y, x+1, y-gravity)){
            }else if(terrain.swapLiquid(x, y, x-1, y-gravity)){
                terrain.terrain[x%terrain.chunkSize].chunk[x][y].stable = true;
            } 
        }catch(Exception e){}
    }
    public static void structGravity(Terrain terrain,int x, int y){
       
        try{
            if(!(terrain.getType(x-1, y-1) !=0 || terrain.getType(x+1, y-1) !=0)){
                terrain.swapAir(x, y, x, y-1);
            }
        }catch(Exception e){}
    }
    public static void clusterGravity(Terrain terrain,int x, int y){
        try{
            if(terrain.getType(x-1, y) != terrain.getType(x, y) || terrain.getType(x+1, y) != terrain.getType(x, y)){
                terrain.swapAir(x, y, x, y-1);
            }
            
        }catch(Exception e){}
    }
    public static void liquidGravity(Terrain terrain,int x, int y){
        int type = terrain.getType(x, y);
        boolean canMoveLeft = true;
        boolean canMoveRight = true;
        
        if(terrain.swapAir(x, y, x, y-1)){
        }else{
            int visco = 5;
            for(int i=1; i< visco; i++){
                if(terrain.swapAir(x, y, x+i, y) && canMoveRight){}
                else if(terrain.swapAir(x, y, x-i, y)&& canMoveLeft){}
                else if(terrain.getType(x+i, y) != type){
                    canMoveRight = false;
                }else if(terrain.getType(x-i, y) != type){
                    canMoveLeft = false;
                }
                if(canMoveLeft == false && canMoveRight == false){break;}
            }
        }
    }
}
