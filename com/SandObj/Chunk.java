package com.SandObj;

public class Chunk {
    public SandObj[][] chunk;
    private int chunkSize;
    public Chunk(int chunkSize){
        this.chunkSize = chunkSize;
        createChunk();
    }
    private void createChunk(){
        chunk = new SandObj[chunkSize][chunkSize];
        for(int x=0; x<chunkSize; x++){
            for(int y=0; y<chunkSize; y++){
                chunk[x][y]= new SandObj(0);
            }
        }
    }
    public void addSand(int x,int y,int type){
        if(chunk[x][y] == null){
            chunk[x][y] = new SandObj(type);
        }else{
            chunk[x][y].reset(type);
        }
    }
}
