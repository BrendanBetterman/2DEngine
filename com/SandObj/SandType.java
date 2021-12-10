package com.SandObj;

import com.Engine.Graphics.colorRGB;
import com.SandObj.SandMethods.Fire;
import com.SandObj.SandMethods.Gravity;
import com.SandObj.SandMethods.Plants;

public class SandType {
    public static void typeUpdate(int type,Terrain terrain,int x,int y){
        switch(type){
            case 1://sand
            case 2:
            case 3:
            case 10://dirt
                Gravity.sandGravity(terrain,x,y);
                break;
            case 5://fire
                Fire.burnFlamable(terrain,x,y);
                Fire.burnout(terrain,x,y);
                Gravity.structGravity(terrain,x,y);
                break;
            case 6://plant
                Gravity.structGravity(terrain,x,y);
                Plants.bush(terrain,x,y);
                break;
            case 7://water
            case 9://oil
                Gravity.liquidGravity(terrain,x,y);
                break;

            case 11:
                Gravity.sandGravity(terrain, x, y);
                Plants.grass(terrain,x,y,10);
                break;
            case 12://swarm
                Gravity.structGravity(terrain,x,y);
                Plants.bush(terrain,x,y);
                Plants.plague(terrain,x,y);
                break;
            case 13://tree
                Gravity.structGravity(terrain, x, y);
                Plants.tree(terrain, x, y);
                break;
            default:
                break;
        }
    }

    public static colorRGB typeColor(int type){
        switch(type){
            case 1:
                return new colorRGB(255, 155, 10);
            case 2:
            case 5:
                return new colorRGB(209, 96, 61);
            case 3:
                return new colorRGB(215, 141, 82);
            case 4:
            case 10:
                return new colorRGB(79, 56, 36);  
            case 6:
            case 13:
            case 11:
            case 8:
                return new colorRGB(123,125,38);
            case 7:
                return new colorRGB(96, 123, 125);
            default:
                return new colorRGB(0, 0, 0);
        }
    }
    public static boolean flamable(int type){
        switch(type){
            case 6:
            case 4:
            case 9:
            case 12:
            case 13:
            case 11:
            case 8:
                return true;
            default:
                return false;
        }
    }
    public static boolean isLiquid(int type){
        switch(type){
            case 7:
            case 9:
                return true;
            default:
                return false;
        }
    }
}
