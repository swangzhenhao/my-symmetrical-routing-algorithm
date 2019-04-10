package com.route.demo;

import java.util.ArrayList;

public class SymmetricNets {
    public double symmetricalRate;
    /*public Coordinate source[] = new Coordinate[2];
    public Coordinate sink[] = new Coordinate[2];*/
    public int net1Number;
    public int net2Number;
    public boolean net1Routed;
    public boolean net2Routed;
    public int layer1;
    public int layer2;
    public ArrayList<Coordinate> routingPath1 = new ArrayList<Coordinate>();
    public ArrayList<Coordinate> routingPath2 = new ArrayList<Coordinate>();

    public SymmetricNets(int net1,int net2) {
        symmetricalRate = 0;
        net1Number = net1;
        net2Number = net2;
        net1Routed =false;
        net2Routed = false;
    }

    public double calSymmetricalRate(){
        double tempRate = 0;
        int d1i=0,d1j = 0;//1,2,3,4对应上下左右方向
        int d2j = 0,d2i =0;//1,2,4,3对应上下左右方向(关于x=?)对称,如果对称轴更换这里的值需要修改。
        if(routingPath1.size() == routingPath2.size()){
            for(int i = 1;i<routingPath1.size();i++){
                if(i-2 >=0){
                    if(routingPath1.get(i-1).x > routingPath1.get(i-2).x){
                        d1j = 4;
                    }
                    else if(routingPath1.get(i-1).x < routingPath1.get(i-2).x){
                        d1j = 3;
                    }
                    else if(routingPath1.get(i-1).y > routingPath1.get(i-2).y){
                        d1j = 1;
                    }
                    else if(routingPath1.get(i-1).y < routingPath1.get(i-2).y){
                        d1j = 2;
                    }
                    if(routingPath2.get(i-1).x > routingPath2.get(i-2).x){
                        d2j = 3;
                    }
                    else if(routingPath2.get(i-1).x < routingPath2.get(i-2).x){
                        d2j = 4;
                    }
                    else if(routingPath2.get(i-1).y > routingPath2.get(i-2).y){
                        d2j = 1;
                    }
                    else if(routingPath2.get(i-1).y < routingPath2.get(i-2).y){
                        d2j = 2;
                    }
                }
                if(routingPath1.get(i).x > routingPath1.get(i-1).x){
                    d1i = 4;
                }
                else if(routingPath1.get(i).x < routingPath1.get(i-1).x){
                    d1i = 3;
                }
                else if(routingPath1.get(i).y > routingPath1.get(i-1).y){
                    d1i = 1;
                }
                else if(routingPath1.get(i).y < routingPath1.get(i-1).y){
                    d1i = 2;
                }
                if(routingPath2.get(i).x > routingPath2.get(i-1).x){
                    d2i = 3;
                }
                else if(routingPath2.get(i).x < routingPath2.get(i-1).x){
                    d2i = 4;
                }
                else if(routingPath2.get(i).y > routingPath2.get(i-1).y){
                    d2i = 1;
                }
                else if(routingPath2.get(i).y < routingPath2.get(i-1).y){
                    d2i = 2;
                }
                if(i == 1){
                    if(d1i == d2i){
                        tempRate = 100;
                    }
                    else{
                        tempRate = 50;
                    }
                }
                else{
                    if((d1i == d2i) && (d1j == d2j)){
                        tempRate = 100;
                    }
                    else if((d1i != d2i) && (d1j == d2j) && (d1i != d1j) && (d2i != d2j)){
                        tempRate = 75;
                    }
                    else if((d1i == d2i) && (d1j != d2j) && (d1i != d1j) && (d2i != d2j)){
                        tempRate = 75;
                    }
                    else if((d1i != d2i) && (d1j != d2j) && (d1i != d1j) && (d2i != d2j)){
                        tempRate = 75;
                    }

                    else if((d1i != d2i)  && (d1i == d1j) && (d2i == d2j)){
                        tempRate = 50;
                    }
                    else if((d1i != d2i) && (d1j == d2j) && (d1i == d1j)){
                        tempRate = 25;
                    }
                    else if((d1i == d2i)  && (d1i == d1j) && (d1j != d2j)){
                        tempRate = 25;
                    }
                }
                symmetricalRate += tempRate;
            }
            symmetricalRate += 50*Math.abs(layer1 - layer2);
            symmetricalRate = symmetricalRate/(routingPath1.size()-1+Math.abs(layer1 - layer2));
        }
        return symmetricalRate;
    }


    /*public int isIntersected(){
        if(source[0].y == source[1].y){
            if((source[0].x > source[1].x && sink[0].x < sink[1].x) || (source[0].x < source[1].x && sink[0].x > sink[1].x)){//等于的情况不会存在
                return 1;
            }
            else{
                return 0;
            }
        }
        else{
            if((source[0].y > source[1].y && sink[0].y < sink[1].y) || (source[0].y < source[1].y && sink[0].y > sink[1].y)){//等于的情况不会存在
                return 1;
            }
            else{
                return 0;
            }
        }
    }*/
}
