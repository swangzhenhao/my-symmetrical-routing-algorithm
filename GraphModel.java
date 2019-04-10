package com.route.demo;

public class GraphModel {
    public int graph[][];
    public boolean isAssigned = false;

    public GraphModel(int x, int y) {
        this.graph = new int[x][y];
    }
}
