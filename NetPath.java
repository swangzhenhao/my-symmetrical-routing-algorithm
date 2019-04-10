package com.route.demo;

import java.util.ArrayList;

public class NetPath {
    public ArrayList<Coordinate> routingPath = new ArrayList<Coordinate>();
    public int targetLength;
    public int remainedLength;
    public int fixedLength;
    public int layer = 0;

    public NetPath(int targetLength, int remainedLength, int fixedLength) {
        this.targetLength = targetLength;
        this.remainedLength = remainedLength;
        this.fixedLength = fixedLength;
    }

    public void printRoutingPath(){
        for(int i=0;i<this.routingPath.size();i++){
            if(i == this.routingPath.size()-1){
                System.out.print("("+routingPath.get(i).x+","+routingPath.get(i).y+")");
            }
            else{
                System.out.print("("+routingPath.get(i).x+","+routingPath.get(i).y+")-->");
            }

        }
        System.out.println(" ");
    }
}
