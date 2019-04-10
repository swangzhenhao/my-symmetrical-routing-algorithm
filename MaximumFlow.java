package com.route.demo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MaximumFlow {
    public int N;//点的个数
    public int Xmax;//坐标系最大值
    public int Ymax;//坐标系最大值
    public int maxint=10000;//代表无限大的值
    public int n, m, s, t, u, v, c;//n是点的个数，m是边的个数，s起点，t终点，u,v,c用于描述每条边及其最大容量
    public Coordinate frontier[];
    public Coordinate sink[];
    public int graphModel[][];
    public int g[][];//有向图边的集合
    public Map<String,Integer> gMap;//使用hashMap的有向图边集合
    public int pre[];//标记在这条路径上当前节点的前驱,同时标记该节点是否在队列中
    public int low[];//标记从源点到当前节点实际还剩多少流量可用

    public int q[], head = 0, tail = 0;//队列
    public int Maxflow = 0;

    public MaximumFlow(int Xmax,int Ymax, int sum, int[][] graphModel, Coordinate[] frontier, Coordinate[] sink ) {//sum，n,m,u,v,c是否需要？后续尝试删减
        this.N = Xmax*Ymax;
        this.Xmax = Xmax;
        this.Ymax = Ymax;
        this.graphModel = graphModel;
        this.frontier = frontier;
        this.sink =sink;
        /*for(int i = 0; i<sink.length;i++){
            this.sink[i] = new Coordinate(sink[i].x,sink[i].y,sink[i].Xmax,sink[i].Ymax);
        }*/
        this.gMap = new HashMap<String, Integer>();//
        this.n = this.N-sum;
        this.g = new int[this.N+2][this.N+2];
        this.pre = new int[this.N+2];
        this.low = new int[this.N+2];
        this.q = new int[N*2];
    }

    public void push(int v)
    {
        q[tail++] = v;
    }

    public int pop()
    {
        return q[head++];
    }
    public void clear()
    {
        head = tail = 0;
    }

    public int min(int a, int b)
    {
        if (a > b)
        {
            return b;
        }
        return a;
    }

    public int maxFlow()
    {
        //scanf("%ld,%ld,%ld,%ld", &n,&m,&s,&t);
        for (int i = 0; i < N; i++)
        {
            int x = i%Xmax;
            int y = i/Xmax;
            if(graphModel[x][y] != 1) {
                int x1 = x+1;
                int y1 = y;
                int x2 = x;
                int y2 = y+1;
                int SN1 = x1+y1*Xmax;
                int SN2 = x2+y2*Xmax;
                if (x1 < Xmax && y1 <Ymax) {
                    if(graphModel[x1][y1] != 1){
                        g[i][SN1] = g[SN1][i] = 1;
                    }
                }
                if (x2 < Xmax && y2 < Ymax) {
                    if(graphModel[x2][y2] != 1){
                        g[i][SN2] = g[SN2][i] = 1;
                    }
                }
            }
            for(int j = 0; j < frontier.length; j++){//起点需要单独建立边
                if( i == frontier[j].SN) {
                    int x1 = x+1;
                    int y1 = y;
                    int x2 = x;
                    int y2 = y+1;
                    int x3 = x-1;
                    int y3 = y;
                    int x4 = x;
                    int y4 = y-1;
                    int SN1 =x1+y1*Xmax;
                    int SN2 =x2+y2*Xmax;
                    int SN3 =x3+y3*Xmax;
                    int SN4 =x4+y4*Xmax;
                    if (x1 < Xmax && y1 <Ymax) {
                        if(graphModel[x1][y1] != 1){
                            g[i][SN1]  = 1;
                        }
                    }
                    if (x2 < Xmax && y2 < Ymax) {
                        if(graphModel[x2][y2] != 1){
                            g[i][SN2]  = 1;
                        }
                    }
                    if ((x3 >= 0 && x3 < Xmax)  && y3 < Ymax) {
                        if(graphModel[x3][y3] != 1){
                            g[i][SN3] = 1;
                        }
                    }
                    if ( x4 < Xmax && (y4>=0 && y4 < Ymax)) {
                        if(graphModel[x4][y4] != 1){
                            g[i][SN4] = 1;
                        }
                    }
                }
            }
            /*for(int j = 0; j < sink.length; j++){//终点需要单独建立边
                if( i == sink[j].SN) {
                    int x1 = (i + 1) % Xmax;
                    int y1 = (i + 1) / Xmax;
                    int x2 = (i + Xmax) % Xmax;
                    int y2 = (i + Xmax) / Xmax;
                    int x3 = (i - 1) % Xmax;
                    int y3 = (i - 1) / Xmax;
                    int x4 = (i - Xmax) % Xmax;
                    int y4 = (i - Xmax) / Xmax;
                    if (x1 < Xmax && y1 <Ymax) {
                        if(graphModel[x1][y1] == 0){
                            g[i][i + 1]  = 0;
                        }
                    }
                    if (x2 < Xmax && y2 < Ymax) {
                        if(graphModel[x2][y2] == 0){
                            g[i][i + Xmax]  = 0;
                        }
                    }
                    if ((x3 >= 0 && x3 < Xmax) && (y3 >= 0 && y3 < Ymax)) {
                        if(graphModel[x3][y3] == 0){
                            g[i][i - 1] = 0;
                        }
                    }
                    if ((x4 >=0 && x4 < Xmax) && (y4>=0 && y4 < Ymax)) {
                        if(graphModel[x4][y4] == 0){
                            g[i][i - Xmax] = 0;
                        }
                    }
                }
            }*/

        }
        s = N;
        for(int i = 0; i < frontier.length; i++){
            g[s][frontier[i].SN]  = 1;
            n +=1;
        }
        t = N+1;
        for(int i = 0; i < sink.length; i++){
            g[sink[i].SN][t] = 1;
            n +=1;
        }
        /*n = 5;测试用代码
        m = 6;
        s = 1;
        t = 5;
        g[1][2] = g[2][1] = 4;
        g[1][3] = g[3][1] = 6;
        g[2][3] = g[3][2] = 2;
        g[2][4] = g[4][2] = 3;
        g[3][5] = g[5][3] = 4;
        g[4][5] = g[5][4] = 5;*/
        try {
            while (true)
            {
                Arrays.fill(low,-1);
                Arrays.fill(pre,-1);
                clear();
                push(s);
                low[s] = maxint;
                while (head < tail)//从起点开始一步步寻找路径，并更新能通过的最小流量
                {
                    int x = pop();
                    for (int i = 0; i <= N+1; i++)//设置为N+1使其能搜索到t
                    {
                        if (g[x][i] > 0 && pre[i] < 0)
                        {
                            push(i);
                            low[i] = min(low[x], g[x][i]);
                            pre[i] = x;
                        }//原版
                    }
                    if (pre[t] > 0)
                    {
                        break;
                    }
                }
                if (pre[t] > 0)
                {
                    int x = t;
                    Maxflow += low[t];
                    while (x != s)//从终点开始一步步返回并减少经过的路径的容量值
                    {
                        g[x][pre[x]] += low[t];
                        g[pre[x]][x] -= low[t];
                        x = pre[x];//原版
                    }
                }
                else
                {
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("no maxFlow");
            e.printStackTrace();
        }
        //System.out.println("最大流："+Maxflow);

        return Maxflow;
    }
    public int maxFlow2()
    {
        //scanf("%ld,%ld,%ld,%ld", &n,&m,&s,&t);
        for (int i = 0; i < N; i++)
        {
            int x = i%Xmax;
            int y = i/Xmax;
            if(graphModel[x][y] != 1) {
                int x1 = x+1;
                int y1 = y;
                int x2 = x;
                int y2 = y+1;
                int SN1 = x1+y1*Xmax;
                int SN2 = x2+y2*Xmax;
                if (x1 < Xmax && y1 <Ymax) {
                    if(graphModel[x1][y1] != 1){
                        //g[i][SN1] = g[SN1][i] = 1;
                        String temp1 = String.valueOf(i) + SN1;
                        gMap.put(temp1,1);
                        String temp2 = String.valueOf(SN1) + i;
                        gMap.put(temp2,1);
                    }
                }
                if (x2 < Xmax && y2 < Ymax) {
                    if(graphModel[x2][y2] != 1){
                        //g[i][SN2] = g[SN2][i] = 1;
                        String temp1 = String.valueOf(i) + SN2;
                        gMap.put(temp1,1);
                        String temp2 = String.valueOf(SN2) + i;
                        gMap.put(temp2,1);
                    }
                }
            }
            for(int j = 0; j < frontier.length; j++){//起点需要单独建立边
                if( i == frontier[j].SN) {
                    int x1 = x+1;
                    int y1 = y;
                    int x2 = x;
                    int y2 = y+1;
                    int x3 = x-1;
                    int y3 = y;
                    int x4 = x;
                    int y4 = y-1;
                    int SN1 =x1+y1*Xmax;
                    int SN2 =x2+y2*Xmax;
                    int SN3 =x3+y3*Xmax;
                    int SN4 =x4+y4*Xmax;
                    if (x1 < Xmax && y1 <Ymax) {
                        if(graphModel[x1][y1] != 1){
                            //g[i][SN1]  = 1;
                            String temp1 = String.valueOf(i) + SN1;
                            gMap.put(temp1,1);
                        }
                    }
                    if (x2 < Xmax && y2 < Ymax) {
                        if(graphModel[x2][y2] != 1){
                            //g[i][SN2]  = 1;
                            String temp1 = String.valueOf(i) + SN2;
                            gMap.put(temp1,1);
                        }
                    }
                    if ((x3 >= 0 && x3 < Xmax)  && y3 < Ymax) {
                        if(graphModel[x3][y3] != 1){
                            //g[i][SN3] = 1;
                            String temp1 = String.valueOf(i) + SN3;
                            gMap.put(temp1,1);
                        }
                    }
                    if ( x4 < Xmax && (y4>=0 && y4 < Ymax)) {
                        if(graphModel[x4][y4] != 1){
                            //g[i][SN4] = 1;
                            String temp1 = String.valueOf(i) + SN4;
                            gMap.put(temp1,1);
                        }
                    }
                }
            }
            /*for(int j = 0; j < sink.length; j++){//终点需要单独建立边
                if( i == sink[j].SN) {
                    int x1 = (i + 1) % Xmax;
                    int y1 = (i + 1) / Xmax;
                    int x2 = (i + Xmax) % Xmax;
                    int y2 = (i + Xmax) / Xmax;
                    int x3 = (i - 1) % Xmax;
                    int y3 = (i - 1) / Xmax;
                    int x4 = (i - Xmax) % Xmax;
                    int y4 = (i - Xmax) / Xmax;
                    if (x1 < Xmax && y1 <Ymax) {
                        if(graphModel[x1][y1] == 0){
                            g[i][i + 1]  = 0;
                        }
                    }
                    if (x2 < Xmax && y2 < Ymax) {
                        if(graphModel[x2][y2] == 0){
                            g[i][i + Xmax]  = 0;
                        }
                    }
                    if ((x3 >= 0 && x3 < Xmax) && (y3 >= 0 && y3 < Ymax)) {
                        if(graphModel[x3][y3] == 0){
                            g[i][i - 1] = 0;
                        }
                    }
                    if ((x4 >=0 && x4 < Xmax) && (y4>=0 && y4 < Ymax)) {
                        if(graphModel[x4][y4] == 0){
                            g[i][i - Xmax] = 0;
                        }
                    }
                }
            }*/

        }
        s = N;
        for(int i = 0; i < frontier.length; i++){
            //g[s][frontier[i].SN]  = 1;
            String temp1 = String.valueOf(s) + frontier[i].SN;
            gMap.put(temp1,1);
            n +=1;
        }
        t = N+1;
        for(int i = 0; i < sink.length; i++){
            //g[sink[i].SN][t] = 1;
            String temp1 = String.valueOf(sink[i].SN) + t;
            gMap.put(temp1,1);
            n +=1;
        }
        /*n = 5;测试用代码
        m = 6;
        s = 1;
        t = 5;
        g[1][2] = g[2][1] = 4;
        g[1][3] = g[3][1] = 6;
        g[2][3] = g[3][2] = 2;
        g[2][4] = g[4][2] = 3;
        g[3][5] = g[5][3] = 4;
        g[4][5] = g[5][4] = 5;*/
        try {
            while (true)
            {
                Arrays.fill(low,-1);
                Arrays.fill(pre,-1);
                clear();
                push(s);
                low[s] = maxint;
                while (head < tail)//从起点开始一步步寻找路径，并更新能通过的最小流量
                {
                    int x = pop();
                    for (int i = 0; i <= N+1; i++)//设置为N+1使其能搜索到t
                    {
                        int temp = 0;
                        String tempCode = String.valueOf(x)+i;
                        if(gMap.containsKey(tempCode)){
                            temp = gMap.get(tempCode);
                        }
                        if (temp > 0 && pre[i] < 0)
                        {
                            push(i);
                            low[i] = min(low[x], temp);
                            pre[i] = x;
                        }
                        /*if (g[x][i] > 0 && pre[i] < 0)
                        {
                            push(i);
                            low[i] = min(low[x], g[x][i]);
                            pre[i] = x;
                        }//原版*/
                    }
                    if (pre[t] > 0)
                    {
                        break;
                    }
                }
                if (pre[t] > 0)
                {
                    int x = t;
                    Maxflow += low[t];
                    while (x != s)//从终点开始一步步返回并减少经过的路径的容量值
                    {
                        int tempValue1 = 0;
                        int tempValue2 = 0;
                        String tempKey1 = String.valueOf(x)+ pre[x];
                        if(gMap.containsKey(tempKey1)){
                            tempValue1 = gMap.get(tempKey1)+low[t];
                        }
                        else{
                            tempValue1 += low[t];
                        }
                        gMap.put(tempKey1,tempValue1);
                        String tempKey2 = String.valueOf(pre[x])+ x;
                        if(gMap.containsKey(tempKey2)){
                            tempValue2 = gMap.get(tempKey2) - low[t];
                        }
                        else{
                            tempValue2 -= low[t];
                        }
                        gMap.put(tempKey2,tempValue2);
                        x = pre[x];
                        /*g[x][pre[x]] += low[t];
                        g[pre[x]][x] -= low[t];
                        x = pre[x];//原版*/
                    }
                }
                else
                {
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("no maxFlow");
            e.printStackTrace();
        }
        System.out.println("最大流："+Maxflow);

        return Maxflow;
    }
}
