package com.SandObj;


//import com.SandObj.Terrain;
//import com.SandObj.SandType;
public class TerrainThread extends Thread{
    private int begin;
    private int end;
    private Terrain terrain;
    private int height;
    private int xOff;


    public TerrainThread(int height,int xOff,int begin,int end,Terrain terrain){
        this.begin = begin;
        this.end = end;
        this.terrain = terrain;
        this.height = height;
        this.xOff = xOff;

    }
    @Override
    public void run(){
        //System.out.println((xOff+end+begin)+"  " + (xOff+begin));
        for(int y=0; y<height; y++){
            for(int x = xOff+ begin; x<(xOff+end+begin); x++){
                try{
                    int tmp = terrain.terrain[x/terrain.chunkSize].chunk[x%terrain.chunkSize][y].type;
                    SandType.typeUpdate(tmp, terrain,x,y);
                }catch(Exception e){

                }
                
            }
        }
    }
}
