package com.SandObj;

import com.Engine.Math.Vector2f;
import com.SandObj.SandMethods.MultiBlock;
import com.Engine.Math.Noise;
import java.util.Random;


public class Terrain {
    public Chunk[] terrain;
    public int chunkSize;
    public int[] selected = new int[]{0,0};
    //private int worldSize;
    public int[][] buffer;
    public Terrain(int worldSize,int chunkSize){
        this.chunkSize = chunkSize; 
        //this.worldSize = worldSize;
        newTerrainNoise(worldSize);
    }
    //World Generation
    /*
    private void newTerrain(int worldSize){
        terrain = new Chunk[worldSize];
        for(int i=0; i<worldSize; i++){
            terrain[i] = new Chunk(chunkSize);
        }
    }*/
    private void newTerrainNoise(int worldSize){
        terrain = new Chunk[worldSize];
        for(int i=0; i<worldSize; i++){
            terrain[i] = new Chunk(chunkSize);
        }
        //perlin noise
        Noise HeightMap = new Noise(new Random(),5.0f,worldSize*chunkSize,1);
        HeightMap.initialise();
        //Simplex simp = new Simplex(1200);
       // Noise Spice = new Noise(new Random(),10.0f,worldSize*chunkSize,1);
       // Spice.initialise();

       //Biomes
       Noise temperature = new Noise(new Random(),1.0f,worldSize*chunkSize,1);
       Noise humidity = new Noise(new Random(),1.0f,worldSize*chunkSize,1);
       temperature.initialise();
       humidity.initialise();

        HeightMap.initialise();
        int waterLevel = 20;
        Random rand = new Random();

        for(int i = 0; i<(worldSize*chunkSize);i++){
            //System.out.println(simp.eval(i / 100, 0, 0.0));
            //addCollumn(i, 0, (int)((simp.eval(i/5 , 0, 0.0)+1)*3+10), 1);
            //System.out.println(((Perlin.grid_[i][0]+1)*10));

            int height = (int)(Math.abs(HeightMap.grid_[i][0])*8)+10;
            if(temperature.grid_[i][0] >0.0f && humidity.grid_[i][0] <0.0f){
                addCollumn(i, 0, height, 1);
                if(height < waterLevel){
                    addCollumn(i, height, (waterLevel-height), 7);
                }else if(rand.nextInt(128) ==1){
                    addSand(i, height+1, 13);
                }
            }else{
                addCollumn(i, 0, height-2, 10);
                if(height < waterLevel){
                    addCollumn(i, height-2, 2, 1);
                    addCollumn(i, height, (waterLevel-height), 7);
                }else if(rand.nextInt(8) ==1){
                    addMulti(i,height+1,MultiBlock.tree());
                    
                }else if(height < waterLevel +2){
                    addCollumn(i, height-2, 2, 1);
                }else{
                    addCollumn(i, height-2, 2, 11);
                }
            }
            
        }

    }
    private void addCollumn(int x,int y,int height,int type){
        for(int h = y; h<height+y; h++){
            addSand(x, h, type);
        }
    }

    //Modifiers. 
    public void addMulti(int x, int y,int[][] Multi){
        for(int i =0; i< Multi[0].length; i++){
            for(int u = 0; u<Multi.length; u++){
                int type =  Multi[Multi.length-1-u][i];
                if(type !=0){
                    addSand(i+x, u+y-1, type);
                }
                
            }
        }
    }
    public void addSand(int x, int y,int type){
        try{
            int currentChunk = (int)(x/chunkSize);
            terrain[currentChunk].addSand(x%chunkSize,y,type);
        }catch(Exception e){}
        
    }
    public void addSquare(int x,int y,int size,int type){
        for(int i=-(size/2);i<size/2; i++){
            for(int u=-(size/2);u<size/2; u++){
                addSand(x+i, y+u, type);
            }
        }
    }
    public void replaceSand(int x, int y, int type,int replaceType){
        try{
            int currentChunk = (int)(x/chunkSize);
            if(getType(x, y) == replaceType){
                terrain[currentChunk].addSand(x%chunkSize,y,type);
            }   
        }catch(Exception e){}
    }
    public void replaceSquare(int x, int y,int size,int type,int replaceType){
        for(int i=-(size/2);i<size/2; i++){
            for(int u=-(size/2);u<size/2; u++){
                replaceSand(x+i, y+u, type,replaceType);
            }
        }
    }
    public void swap(int x, int y, int x1,int y1){
        int tmp = getType(x, y);
        terrain[(int)(x/chunkSize)].chunk[x%chunkSize][y].type = getType(x1, y1);
        terrain[(int)(x1/chunkSize)].chunk[x1%chunkSize][y1].type = tmp;
    }
    public boolean swapAir(int x, int y, int x1,int y1){
        if(getType(x1, y1) ==0){
            swap(x, y, x1, y1);
            return true;
        }
        return false;
    }
    
    public boolean swapLiquid(int x, int y, int x1,int y1){
        if(getType(x1, y1) == 0 || SandType.isLiquid(getType(x1, y1))){
            if(getType(x-1, y) == 0){
                int type = getType(x, y);
                addSand(x, y, 0);
                addSand(x-1, y, getType(x1, y1));
                addSand(x1, y1, type); 
            }else if(getType(x+1, y) == 0){
                int type = getType(x, y);
                addSand(x, y, 0);
                addSand(x+1, y, getType(x1, y1));
                addSand(x1, y1, type); 
            }else{
                swap(x, y, x1, y1);
            }
            
            return true;
        }
        return false;
    }
    public void setDir(int x, int y,float x1,float y1){
        int currentChunk = (int)(x/chunkSize);
        terrain[currentChunk].chunk[x%chunkSize][y].direction.set(x1, y1);;
    }


    //getters
    public int getType(int x, int y){
        int currentChunk = (int)(x/chunkSize);
        return terrain[currentChunk].chunk[x%chunkSize][y].type;
    }
    public Vector2f getDir(int x,int y){
        int currentChunk = (int)(x/chunkSize);
        return terrain[currentChunk].chunk[x%chunkSize][y].direction;
    }
    public int[][] draw(int width,int height,int xOff){
        int[][] tmp = new int[width][height];
        for(int x=0; x<width; x++){
            for(int y=0; y<height; y++){
                tmp[x][y]=terrain[(x+xOff)/chunkSize].chunk[(x+xOff)%chunkSize][y].type;
            }
        }
        return tmp;
    }
    //Loop
    public void update(int width,int height,int xOff){
       //set buffer
        Random rand = new Random();
        int tmpoff = rand.nextInt(10);
        for(int chunk =3; chunk >= 0; chunk--){
            TerrainThread tmp = new TerrainThread(height,xOff+tmpoff,chunk*(width/3),(width/3),this);
            tmp.start();
            
        }
        

       /*
        for(int y =0; y<height; y++){
            for(int x =xOff; x<(width+xOff); x++){
                //for(int x =(width+xOff); x<xOff; x--){
                
                int tmp = terrain[x/chunkSize].chunk[x%chunkSize][y].type;
                this.selected[0] = x;
                this.selected[1] = y;
                SandType.typeUpdate(tmp, this,x,y);
            }
        }
        */
       //push buffer to terrain.
    }
}
