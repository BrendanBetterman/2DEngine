package com.Engine.Math;
import java.util.ArrayList;
public class Cluster {
    public static int[][] findHCluster(int[][] sand){
        ArrayList<int[]> clust = new ArrayList<int[]>();
        int clustIndex =0;
        int lastType = 0;//changed
        for(int i=0; i<sand.length; i++){//rows
			for(int u=0; u<sand[0].length; u++){//collumns
                if(u==0){//if on edge
                    //don't index before
                    if(sand[i][u]!=0){
                        clust.add(new int[]{u,0,i,sand[i][u]});
                        lastType= sand[i][u];
                        clustIndex++;
                    }
                }else{
                    //Find if there is sand 
                    //Check front and back to see if it can cluster
                    if(sand[i][u]!=0){
                        if(sand[i][u-1] != sand[i][u] && lastType !=0 && sand[i][u] != 0){
                            clust.add(new int[]{u,0,i,sand[i][u]});
                            lastType= sand[i][u];
                            clustIndex++;
                        }
                        if(sand[i][u-1] == sand[i][u] && sand[i][u] != 0){
                            lastType = sand[i][u];
                            clust.set(clustIndex-1,new int[]{clust.get(clustIndex-1)[0],u-clust.get(clustIndex-1)[0],i,lastType});
                        }
                        
                    }
                }
            }
        }
        
        int[][] temp = new int[clustIndex][4];
        for (int i =0;i<clustIndex;i++){
            temp[i] = clust.get(i);
        }
        //System.out.println(stringArray(temp));
        return temp;
    }
}
