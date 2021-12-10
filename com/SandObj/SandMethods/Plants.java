package com.SandObj.SandMethods;
import java.util.Random;

import com.SandObj.Terrain;
import com.SandObj.TerrainThread;
public class Plants {
    private static Random rand = new Random();
    //Accident
    public static void plague(Terrain terrain,int x, int y){
        int dir = rand.nextInt(3)-2;
        try{
            terrain.swap(x, y, x+dir, y+1);
        }catch(Exception e){}
    }
    public static void crystal(Terrain terrain,int x, int y){
        int dir = rand.nextInt(3)-2;
        try{
            terrain.swapAir(x, y, x+dir, y+dir);
        }catch(Exception e){}
    }
    public static void bush(Terrain terrain,int x, int y){
        
        if(rand.nextInt(20)==1){
            int dir = rand.nextInt(3)-1;
            try{
                if(terrain.getType(x+dir, y+1) ==0 && terrain.getType(x, y+1) ==0){
                    terrain.addSand(x+dir, y+1, terrain.getType(x, y));
                }
            }catch(Exception e){}
        }
    }
    public static void grass(Terrain terrain,int x, int y,int spreadType){
        int type = terrain.getType(x, y);
        
        //kill Grass if something is on top.
        if(rand.nextInt(20) ==1){
            if(terrain.getType(x, y+2) != 0){
                terrain.addSand(x, y, spreadType);
            }else{
                for(int i =-1; i<= 1; i++){
                for(int u =-1; u<=1; u+=2){
                    if(terrain.getType(x+u, y+i) == spreadType){
                        if(terrain.getType(x+u, y+i+2) == 0){
                            terrain.addSand(x+u, y+i, type);
                            //break;
                        }
                    }
              }
                } 
            }
        
        }
        


    }
    public static void tree(Terrain terrain,int x, int y){
        int type = terrain.getType(x, y);
        if(rand.nextInt(100)==1){
            
            int dir = rand.nextInt(3)-1;
            if(terrain.getType(x+dir, y-3) != type){
                try{
                if(terrain.getType(x+dir, y+1) ==0 && terrain.getType(x, y+1) ==0){
                    terrain.addSand(x+dir, y+1, terrain.getType(x, y));
                }
            }catch(Exception e){}
            }
            
        }
    }
}
