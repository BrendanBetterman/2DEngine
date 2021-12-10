package com.SandObj.SandMethods;

import java.util.Random;

import com.SandObj.SandType;
import com.SandObj.Terrain;

public class Fire {
    public static void burnFlamable(Terrain terrain,int x, int y){
        int radius = 1;
        Random rand = new Random();
      
        if (rand.nextInt(5)==1){
            for(int i = -radius; i<=radius; i++){
                for(int u = -radius; u <=radius; u++){
                    try{
                        if(SandType.flamable(terrain.getType(x+i, y+u))){
                            terrain.addSand(x+i, y+u, terrain.getType(x, y));
                        }
                    }catch(Exception e){}
                }
            }
        }

    }
    public static void burnout(Terrain terrain,int x, int y){
        Random rand = new Random();
        if(rand.nextInt(10) ==1){
            terrain.addSand(x, y, 0);
        }
    }
}
