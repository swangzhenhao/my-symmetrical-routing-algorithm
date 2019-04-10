package com.route.demo;

public class Coordinate {
    public int x;
    public int y;
    public int Xmax;
    public int Ymax;
    public int SN;//序号
    public int layer;
    public int label;
    //public int isOccupied = 0;//是否可用

    public Coordinate(int x, int y, int xmax, int ymax) {
        this.x = x;
        this.y = y;
        Xmax = xmax;
        Ymax = ymax;
        SN = x + Xmax*y;
    }

    public void updateSN(){
        SN = x + Xmax*y;
    }
}
