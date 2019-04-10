package com.route.demo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by  WANG ZHENHAO on 2018/12/10
 */
public class RoutingAlgorithm {

    public static void main(String[] args)  throws IOException {
        /*LCS lcs = new LCS();
        long StartTime = 0;
        long EndTime = 0;
        StartTime = System.currentTimeMillis();
        String res = lcs.getLCS("123654","651234");
        EndTime = System.currentTimeMillis();
        System.out.println(res);
        System.out.println("程序运行时间:"+(EndTime-StartTime)+"ms");*///方案1测试
        long StartTime = 0;
        long EndTime = 0;
        StartTime = System.currentTimeMillis();//需要的话从这里开始
        //CafeRouter();
        MyRouter();
        EndTime = System.currentTimeMillis();
        System.out.println("程序运行时间:"+(EndTime-StartTime)+"ms");

        /*System.out.print("Enter a Char:");//观察内存使用情况时使用
        char i = (char) System.in.read();
        System.out.println("your char is :"+i);*/
    }

    public static void MyRouter(){
        //初始化数据
        int layerNum = 4;
        int netsNum = 10;
//int obstaclesNum = 19;
        int gridXmax = 15;
        int gridYmax = 10;//初始化数据
        int sum = 0;
        GraphModel graphModel[] = new GraphModel[layerNum];
        for(int i =0;i<layerNum;i++){
            graphModel[i] = new GraphModel(gridXmax,gridYmax);
        }
/*for(int k =0;k<layerNum;k++){//在各层随机生成障碍
    for(int i = 0;i<gridXmax;i++){
        for(int j = 0;j<gridYmax;j++) {
        double res = Math.random();
            if(res>0.05){
                graphModel[k].graph[i][j] = 0;
            }
            else{
                graphModel[k].graph[i][j] = 1;
            }
            //graphModel[0].graph[i][j] = 0;
            sum = sum + graphModel[k].graph[i][j];
        }
    }
}*/
        for(int i = 0;i<gridXmax;i++){
            for(int j = 0;j<gridYmax;j++) {
        /*double res = Math.random();
        if(res>0.05){
            graphModel[0].graph[i][j] = 0;
        }
        else{
            graphModel[0].graph[i][j] = 1;
        }*/
                graphModel[0].graph[i][j] = 0;
                sum = sum + graphModel[0].graph[i][j];
            }
        }

        Coordinate sources[] = new Coordinate[netsNum];//起点定义

        sources[0] =new Coordinate(0,1,gridXmax,gridYmax);
        sources[1] =new Coordinate(0,3,gridXmax,gridYmax);
        sources[2] =new Coordinate(0,5,gridXmax,gridYmax);
        sources[3] =new Coordinate(0,7,gridXmax,gridYmax);
        sources[4] =new Coordinate(0,9,gridXmax,gridYmax);
        sources[5] =new Coordinate(14,1,gridXmax,gridYmax);
        sources[6] =new Coordinate(14,3,gridXmax,gridYmax);
        sources[7] =new Coordinate(14,5,gridXmax,gridYmax);
        sources[8] =new Coordinate(14,7,gridXmax,gridYmax);
        sources[9] =new Coordinate(14,9,gridXmax,gridYmax);
/*sources[2] =new Coordinate(0,7,gridXmax,gridYmax);
sources[3] =new Coordinate(10,7,gridXmax,gridYmax);*/
        for(int i = 0; i<sources.length;i++){
            graphModel[0].graph[sources[i].x][sources[i].y] = 1;
        }
//初始化，后续更改为更灵活的方式
        Coordinate sinks[] = new Coordinate[netsNum];//终点定义
        sinks[0] =new Coordinate(14,6,gridXmax,gridYmax);
        sinks[1] =new Coordinate(14,8,gridXmax,gridYmax);
        sinks[2] =new Coordinate(10,0,gridXmax,gridYmax);
        sinks[3] =new Coordinate(11,0,gridXmax,gridYmax);
        sinks[4] =new Coordinate(12,0,gridXmax,gridYmax);
        sinks[5] =new Coordinate(0,6,gridXmax,gridYmax);
        sinks[6] =new Coordinate(0,8,gridXmax,gridYmax);
        sinks[7] =new Coordinate(4,0,gridXmax,gridYmax);
        sinks[8] =new Coordinate(3,0,gridXmax,gridYmax);
        sinks[9] =new Coordinate(2,0,gridXmax,gridYmax);
/*sinks[2] =new Coordinate(10,1,gridXmax,gridYmax);
sinks[3] =new Coordinate(0,1,gridXmax,gridYmax);*/

        NetPath path[] = new NetPath[netsNum];//net定义，包含目标长度，已走长度，未走长度，以及路径坐标点列表
        int distance[] = new int[netsNum];//初始距离
        int maxDistance = 0;
        for(int i = 0;i<netsNum;i++) {
            distance[i] = Math.abs(sinks[i].x - sources[i].x) + Math.abs(sinks[i].y - sources[i].y);
            if(distance[i]> maxDistance){
                maxDistance = distance[i];
            }
        }
        System.out.println("目标距离"+maxDistance);//测试是否生效
/*path[0] = new NetPath(maxDistance,maxDistance,0);
path[1] = new NetPath(maxDistance,maxDistance,0);
path[2] = new NetPath(maxDistance,maxDistance,0);
path[3] = new NetPath(maxDistance,maxDistance,0);*/
        for(int i = 0; i<path.length;i++){
            path[i] = new NetPath(maxDistance,maxDistance,0);
            Coordinate temp = new Coordinate(sources[i].x,sources[i].y,gridXmax,gridYmax);
            path[i].routingPath.add(temp);
        }
//初始化
        SymmetricNets syNetPairs[] = new SymmetricNets[5];
        syNetPairs[0] = new SymmetricNets(0,5);//对称nets定义
        syNetPairs[1] = new SymmetricNets(1,6);
        syNetPairs[2] = new SymmetricNets(2,7);
        syNetPairs[3] = new SymmetricNets(3,8);
        syNetPairs[4] = new SymmetricNets(4,9);
//System.out.println(syNetPairs1.isIntersected());//判断是否有intersection
//System.out.println(syNetPairs2.isIntersected());
        int sourcesOrder[] = {0,1,2,3,4,9,8,7,6,5};//依次为y轴，x轴，x值最大那条线上的点，y值最大那条线上的点(不一定，总之环绕图边一周），如何用程序转换后续解决，考虑在Coordinate里增加一个参数记录其标号
        int sinksOrder[] = {6,5,9,8,7,2,3,4,0,1};


        int lcs[][] = new int[netsNum][netsNum];//用于分层，后续修改为更灵活方式
        //从此处开始是routing的操作，但是没有考虑后续layer里的障碍，即假设只有第一层有障碍
        //StartTime = System.currentTimeMillis();
        for (int i=0; i<netsNum; i++) {
            for (int j=0; j<netsNum; j++) {
                lcs[i][j]=-1;
            }
        }
        LCS test = new LCS(netsNum);
        test.getLCS(sourcesOrder,sinksOrder,lcs);
        int LCSnum = 0;//用来记录LCS分出了几组数据
        for(int i = 0; i<test.layerAssign.size();i++){
            System.out.println(test.layerAssign.get(i));
            if(test.layerAssign.get(i) == 1000){
                LCSnum +=1;
            }
            if(i+1 <test.layerAssign.size() && test.layerAssign.get(i).equals(test.layerAssign.get(i+1))){
                break;
            }
        }
        System.out.println(LCSnum);
        int assignment[][] = new int[LCSnum][netsNum];
        for(int i = 0;i<LCSnum;i++){
            Arrays.fill(assignment[i],1000);//用于后面排序（貌似不需要进行排序操作）
        }
        int netsNumOfLayers[] = new int[LCSnum];
        int tempLayer = 0,tempNet=0;
        for(int k = 0; k<test.layerAssign.size();k++){//转化为数组，后续利用，记录每组有几个net
            if(tempLayer <LCSnum){
                if(test.layerAssign.get(k) == 1000){
                    netsNumOfLayers[tempLayer] = tempNet;
                    tempLayer +=1;
                    tempNet = 0;
                    continue;
                }
                assignment[tempLayer][tempNet] = test.layerAssign.get(k);
                tempNet++;
            }
        }
        /*for(int i = 0;i<LCSnum;i++){这个排序操作暂时不需要
            Arrays.sort(assignment[i]);
        }*/
        for(int i = 0;i< LCSnum;i++){
            Coordinate frontiers[] = new Coordinate[netsNumOfLayers[i]];
            Coordinate tempSinks[] = new Coordinate[netsNumOfLayers[i]];
            for(int j = 0;j<netsNumOfLayers[i];j++){//根据分组信息新建frontier和sink
                if(assignment[i][j] != 1000){
                    frontiers[j] = new Coordinate(sources[assignment[i][j]].x,sources[assignment[i][j]].y,gridXmax,gridYmax);
                    tempSinks[j] = sinks[assignment[i][j]];
                }

            }
            int proDirection[][] = new int[netsNumOfLayers[i]][4];//根据起点相对位置设置优先方向，0123对应左右下上，此处与对称率计算时方向的计数不同
            for(int j = 0;j<netsNumOfLayers[i];j++){
               if(j+1<netsNumOfLayers[i]){
                   if(frontiers[j].x >frontiers[j+1].x){
                        proDirection[j][1] = 1;
                   }
                   if(frontiers[j].x < frontiers[j+1].x){
                       proDirection[j][0] = 1;
                   }
                   if(frontiers[j].y > frontiers[j+1].y){
                       proDirection[j][3] = 1;
                   }
                   if(frontiers[j].y < frontiers[j+1].y){
                       proDirection[j][2] = 1;
                   }
               }
               if(frontiers[j].x == 0){
                   proDirection[j][1] = 1;
               }
               if(frontiers[j].x == (gridXmax -1)){
                    proDirection[j][0] = 1;
               }
               if(frontiers[j].y == 0){
                    proDirection[j][3] = 1;
               }
                if(frontiers[j].y == (gridYmax - 1)){
                    proDirection[j][2] = 1;
                }

            }
            //System.out.println(Arrays.toString(proDirection[0]));
            //System.out.println(Arrays.toString(proDirection[1]));
            /*for(int j = 0;j<netsNumOfLayers[i];j++){
                System.out.println(frontiers[j].x+","+frontiers[j].y);
                System.out.println(tempSinks[j].x+","+tempSinks[j].y);
            }*/
            //检测这一组nets适合放在哪一层，即分层操作
            for(int j = 0;j<layerNum;j++){
               if(!graphModel[j].isAssigned){
                   MaximumFlow maximumTest = new MaximumFlow(gridXmax,gridYmax,sum,graphModel[j].graph,frontiers,tempSinks);//计算最大流，将每组nets分配到一层layer
                   int tempMax = maximumTest.maxFlow();
                   if(tempMax == netsNumOfLayers[i]){//data2在所有最大流部分都要处理，
                       graphModel[j].isAssigned = true;
                       for(int k = 0;k<netsNumOfLayers[i];k++){
                           path[assignment[i][k]].layer = j;//标记路径在哪个layer
                       }
                       break;
                   }
               }
            }
            for(int j=0;j<netsNumOfLayers[i];j++){
                int lay = path[assignment[i][j]].layer;
                graphModel[lay].graph[frontiers[j].x][frontiers[j].y] = 1;
            }

            for(int j = 0;j<netsNumOfLayers[i];j++){
                int lay = path[assignment[i][j]].layer;
                boolean syNetRouted =false;
                //检测每个net的对称net是否已经routing过了，若已经routing则直接复制路径
                for(int k =0; k<syNetPairs.length;k++){//这里对于遇到障碍的情况没有处理，在完成处理部分编程之前不随机插入障碍
                    if(syNetPairs[k].net1Number == assignment[i][j]){
                        if(syNetPairs[k].net2Routed){
                            syNetRouted =true;
                            int xOffset = 0;
                            int yOffset = 0;
                            //path[assignment[i][j]].routingPath = (ArrayList<Coordinate>)syNetPairs[k].routingPath2.clone();
                            path[assignment[i][j]].routingPath.clear();
                            for(int l = 0;l<syNetPairs[k].routingPath2.size();l++){
                                Coordinate temp = new Coordinate(((gridXmax-1) -syNetPairs[k].routingPath2.get(l).x)+xOffset,
                                        syNetPairs[k].routingPath2.get(l).y+yOffset, gridXmax,gridYmax);//关于坐标系的中轴线对称
                                MaximumFlow maximumTest = new MaximumFlow(gridXmax,gridYmax,sum,graphModel[lay].graph,frontiers,tempSinks);//计算最大流，将每组nets分配到一层layer
                                int tempMax = maximumTest.maxFlow();
                                if(l == 0){
                                    path[assignment[i][j]].routingPath.add(temp);
                                }
                                if(graphModel[lay].graph[temp.x][temp.y] != 1 ){//为了添加起点, 排除已被占据的点
                                    path[assignment[i][j]].routingPath.add(temp);
                                    path[assignment[i][j]].fixedLength +=1;
                                    path[assignment[i][j]].remainedLength -= 1;
                                    graphModel[lay].graph[temp.x][temp.y] = 1;
                                    if(temp.x == tempSinks[j].x && temp.y == tempSinks[j].y){
                                        break;
                                    }
                                }
                                /*if(l+1 <syNetPairs[k].routingPath2.size()){
                                    Coordinate tempNext = new Coordinate(((gridXmax-1) -syNetPairs[k].routingPath2.get(l+1).x)+xOffset,
                                            syNetPairs[k].routingPath2.get(l+1).y+yOffset, gridXmax,gridYmax);//关于坐标系的中轴线对称
                                    frontiers[j] = temp;
                                    if(graphModel[lay].graph[tempNext.x][tempNext.y] == 1){
                                        int tempDistance[] = {10000,10000,10000,10000};
                                        int tempDistanceW = 10000;
                                        int tempDistanceE = 10000;
                                        int tempDistanceN = 10000;
                                        int tempDistanceS = 10000;
                                        int orientation = 0;
                                        int tempDistanceO = 10000;//开始的曼哈顿距离
                                        if((frontiers[j].x - 1)>= 0 &&(frontiers[j].x - 1)< gridXmax
                                                && frontiers[j].y >= 0 && frontiers[j].y < gridYmax){
                                            if(graphModel[lay].graph[(frontiers[j].x - 1)][frontiers[j].y] != 1){
                                                //假设移动到这个方向上的相邻点，计算最大流
                                                int tempGpValue = graphModel[lay].graph[(frontiers[j].x - 1)][frontiers[j].y];
                                                sum += 1;
                                                graphModel[lay].graph[(frontiers[j].x - 1)][frontiers[j].y] = 1;
                                                frontiers[j].x = frontiers[j].x - 1;
                                                frontiers[j].updateSN();
                                                MaximumFlow maximumTest = new MaximumFlow(gridXmax,gridYmax,sum,graphModel[lay].graph,frontiers,tempSinks);//计算最大流，将每组nets分配到一层layer
                                                int tempMax = maximumTest.maxFlow();
                                                frontiers[j].x = frontiers[j].x + 1;
                                                frontiers[j].updateSN();
                                                graphModel[lay].graph[(frontiers[j].x - 1)][frontiers[j].y] = tempGpValue;
                                                sum -= 1;
                                                if(tempMax >= netsNumOfLayers[i]) {
                                                    tempDistanceW = Math.abs(tempSinks[j].x - (frontiers[j].x - 1)) + Math.abs(tempSinks[j].y - frontiers[j].y);
                                                    if (tempDistanceO > tempDistanceW) {
                                                        //orientation = 1;
                                                        tempDistance[0] = tempDistanceW;//调整了，记得修改
                                                    }
                                                }

                                            }
                                        }
                                        if((frontiers[j].x + 1)>= 0 &&(frontiers[j].x + 1)< gridXmax
                                                && frontiers[j].y >= 0 && frontiers[j].y < gridYmax){
                                            if(graphModel[lay].graph[(frontiers[j].x + 1)][frontiers[j].y] != 1){
                                                //假设移动到这个方向上的相邻点，计算最大流
                                                int tempGpValue = graphModel[lay].graph[(frontiers[j].x + 1)][frontiers[j].y];
                                                sum += 1;
                                                graphModel[lay].graph[(frontiers[j].x + 1)][frontiers[j].y] = 1;
                                                frontiers[j].x = frontiers[j].x + 1;
                                                frontiers[j].updateSN();
                                                MaximumFlow maximumTest = new MaximumFlow(gridXmax,gridYmax,sum,graphModel[lay].graph,frontiers,tempSinks);//计算最大流，将每组nets分配到一层layer
                                                int tempMax = maximumTest.maxFlow();
                                                frontiers[j].x = frontiers[j].x - 1;
                                                frontiers[j].updateSN();
                                                graphModel[lay].graph[(frontiers[j].x + 1)][frontiers[j].y] = tempGpValue;
                                                sum -= 1;
                                                if(tempMax >= netsNumOfLayers[i]) {
                                                    tempDistanceE = Math.abs(tempSinks[j].x - (frontiers[j].x + 1)) + Math.abs(tempSinks[j].y - frontiers[j].y);
                                                    if (tempDistanceO > tempDistanceE) {
                                                        //orientation = 2;
                                                        tempDistance[1] = tempDistanceE;
                                                    }
                                                }

                                            }
                                        }
                                        if(frontiers[j].x>= 0 && frontiers[j].x < gridXmax
                                                && (frontiers[j].y -1) >= 0 && (frontiers[j].y -1) < gridYmax){
                                            if(graphModel[lay].graph[frontiers[j].x][(frontiers[j].y-1)] != 1){
                                                //假设移动到这个方向上的相邻点，计算最大流
                                                int tempGpValue = graphModel[lay].graph[frontiers[j].x][(frontiers[j].y-1)];
                                                sum += 1;
                                                graphModel[lay].graph[frontiers[j].x][(frontiers[j].y-1)] = 1;
                                                frontiers[j].y = frontiers[j].y - 1;
                                                frontiers[j].updateSN();
                                                MaximumFlow maximumTest = new MaximumFlow(gridXmax,gridYmax,sum,graphModel[lay].graph,frontiers,tempSinks);//计算最大流，将每组nets分配到一层layer
                                                int tempMax = maximumTest.maxFlow();
                                                frontiers[j].y = frontiers[j].y + 1;
                                                frontiers[j].updateSN();
                                                graphModel[lay].graph[frontiers[j].x][(frontiers[j].y-1)] = tempGpValue;
                                                sum -= 1;
                                                if(tempMax >= netsNumOfLayers[i]) {
                                                    tempDistanceS = Math.abs(tempSinks[j].x - frontiers[j].x) + Math.abs(tempSinks[j].y - (frontiers[j].y - 1));
                                                    if (tempDistanceO > tempDistanceS) {
                                                        //保留新的曼哈顿距离及对应的方向
                                                        //orientation = 3;
                                                        tempDistance[2] = tempDistanceS;
                                                    }
                                                }
                                            }

                                        }
                                        if(frontiers[j].x>= 0 &&frontiers[j].x< gridXmax
                                                && (frontiers[j].y + 1) >= 0 && (frontiers[j].y + 1) < gridYmax){
                                            if(graphModel[lay].graph[frontiers[j].x][(frontiers[j].y+1)] != 1){
                                                //假设移动到这个方向上的相邻点，计算最大流
                                                int tempGpValue = graphModel[lay].graph[frontiers[j].x][(frontiers[j].y+1)];
                                                sum += 1;
                                                graphModel[lay].graph[frontiers[j].x][(frontiers[j].y+1)] = 1;
                                                frontiers[j].y = frontiers[j].y + 1;
                                                frontiers[j].updateSN();
                                                MaximumFlow maximumTest = new MaximumFlow(gridXmax,gridYmax,sum,graphModel[lay].graph,frontiers,tempSinks);//计算最大流，将每组nets分配到一层layer
                                                int tempMax = maximumTest.maxFlow();
                                                frontiers[j].y = frontiers[j].y - 1;
                                                frontiers[j].updateSN();
                                                graphModel[lay].graph[frontiers[j].x][(frontiers[j].y+1)] = tempGpValue;
                                                sum -= 1;
                                                if(tempMax >= netsNumOfLayers[i]) {
                                                    tempDistanceN = Math.abs(tempSinks[j].x - frontiers[j].x) + Math.abs(tempSinks[j].y - (frontiers[j].y + 1));
                                                    if (tempDistanceO > tempDistanceN) {
                                                        //orientation = 4;
                                                        tempDistance[3] = tempDistanceN;
                                                    }
                                                }
                                            }
                                        }
                                        int temporientation = 0;
                                        int tempPro = 0;
                                        for(int m=0; m<4;m++){
                                            if(tempDistance[m]<tempDistanceO){
                                                tempDistanceO = tempDistance[m];
                                                temporientation = m+1;
                                                tempPro = proDirection[j][m];
                                            }
                                            else if(tempDistance[m] == tempDistanceO){
                                                if(proDirection[j][m]>= tempPro){
                                                    temporientation = m+1;
                                                    tempPro = proDirection[j][m];
                                                }
                                            }

                                        }
                                        orientation = temporientation;
                                        System.out.println("方向" + orientation);
                                        switch (orientation){
                                            case 1:
                                                //移动后，之前占据的值赋值为1，即已经被占用
                                                frontiers[j].x = frontiers[j].x - 1;
                                                xOffset = -1;
                                                break;
                                            case 2:
                                                frontiers[j].x = frontiers[j].x + 1;
                                                xOffset = 1;
                                                break;
                                            case 3:
                                                frontiers[j].y = frontiers[j].y - 1;
                                                yOffset = -1;
                                                break;
                                            case 4:
                                                frontiers[j].y = frontiers[j].y + 1;
                                                yOffset = 1;
                                                break;
                                            default:
                                                break;
                                        }
                                        if(orientation == 0){
                                            System.out.println("no feasible path");
                                            break;
                                        }
                                        frontiers[j].updateSN();
                                        graphModel[lay].graph[frontiers[j].x][frontiers[j].y] = 1;
                                        sum += 1;
                                        Coordinate tempAdd = new Coordinate(frontiers[j].x,frontiers[j].y,gridXmax,gridYmax);
                                        path[assignment[i][j]].routingPath.add(tempAdd);
                                        path[assignment[i][j]].fixedLength +=1;
                                        path[assignment[i][j]].remainedLength -= 1;//?没有完成
                                        if(tempAdd.x == tempSinks[j].x && tempAdd.y == tempSinks[j].y){
                                            break;
                                        }
                                    }
                                }*/



                                //path[assignment[i][j]].routingPath.get(l).updateSN();
                            }
                            break;
                        }
                    }
                    else if(syNetPairs[k].net2Number == assignment[i][j]){
                        if(syNetPairs[k].net1Routed){
                            syNetRouted =true;
                            //path[assignment[i][j]].routingPath = (ArrayList<Coordinate>)syNetPairs[k].routingPath1.clone();
                            path[assignment[i][j]].routingPath.clear();
                            for(int l = 0;l<syNetPairs[k].routingPath1.size();l++){
                                Coordinate temp = new Coordinate(((gridXmax-1) -syNetPairs[k].routingPath1.get(l).x),syNetPairs[k].routingPath1.get(l).y,
                                        gridXmax,gridYmax);//关于坐标系的中轴线对称
                                frontiers[j] = temp;
                                MaximumFlow maximumTest = new MaximumFlow(gridXmax,gridYmax,sum,graphModel[lay].graph,frontiers,tempSinks);//计算最大流，将每组nets分配到一层layer
                                int tempMax = maximumTest.maxFlow();
                                if(l==0){
                                    path[assignment[i][j]].routingPath.add(temp);
                                }
                                if(graphModel[lay].graph[temp.x][temp.y] != 1 ){//为了添加起点,排除已被占据的点，后续添加对有障碍情况的处理
                                    path[assignment[i][j]].routingPath.add(temp);
                                    path[assignment[i][j]].fixedLength +=1;
                                    path[assignment[i][j]].remainedLength -= 1;
                                    graphModel[lay].graph[temp.x][temp.y] = 1;
                                }
                            }
                            break;
                        }
                    }
                    else{
                        syNetRouted =false;
                    }
                }
                //若对称的net没有被routing过，则开始routing
                if(!syNetRouted){
                    //采用bounding box方法，每次对需要routing的net及序列上相邻的net划分优先区域，值为2是优先的，为5是不优先的，保持0的则用于的detour部分
                    if(frontiers[j].x > tempSinks[j].x && frontiers[j].y > tempSinks[j].y){
                        for(int k = tempSinks[j].x;k<=frontiers[j].x;k++){
                            for(int l = tempSinks[j].y;l<=frontiers[j].y;l++){
                                if(graphModel[lay].graph[k][l] != 1){
                                    graphModel[lay].graph[k][l] = 2;
                                }
                            }
                        }
                    }
                    if(frontiers[j].x <= tempSinks[j].x && frontiers[j].y > tempSinks[j].y){
                        for(int k = frontiers[j].x;k<=tempSinks[j].x;k++){
                            for(int l = tempSinks[j].y;l<=frontiers[j].y;l++){
                                if(graphModel[lay].graph[k][l] != 1){
                                    graphModel[lay].graph[k][l] = 2;
                                }
                            }
                        }
                    }
                    if(frontiers[j].x > tempSinks[j].x && frontiers[j].y <= tempSinks[j].y){
                        for(int k = tempSinks[j].x;k<=frontiers[j].x;k++){
                            for(int l = frontiers[j].y;l<=tempSinks[j].y;l++){
                                if(graphModel[lay].graph[k][l] != 1){
                                    graphModel[lay].graph[k][l] = 2;
                                }
                            }
                        }
                    }
                    if(frontiers[j].x <= tempSinks[j].x && frontiers[j].y <= tempSinks[j].y){
                        for(int k = frontiers[j].x;k<=tempSinks[j].x;k++){
                            for(int l = frontiers[j].y;l<=tempSinks[j].y;l++){
                                if(graphModel[lay].graph[k][l] != 1){
                                    graphModel[lay].graph[k][l] = 2;
                                }
                            }
                        }
                    }
                    if(j+1 < netsNumOfLayers[i]){
                        if(frontiers[j+1].x > tempSinks[j+1].x && frontiers[j+1].y > tempSinks[j+1].y){
                            for(int k = tempSinks[j+1].x;k<=frontiers[j+1].x;k++){
                                for(int l = tempSinks[j+1].y;l<=frontiers[j+1].y;l++){
                                    if(graphModel[lay].graph[k][l] != 1){
                                        graphModel[lay].graph[k][l] += 3;
                                    }
                                }
                            }
                        }
                        if(frontiers[j+1].x <= tempSinks[j+1].x && frontiers[j+1].y > tempSinks[j+1].y){
                            for(int k = frontiers[j+1].x;k<=tempSinks[j+1].x;k++){
                                for(int l = tempSinks[j+1].y;l<=frontiers[j+1].y;l++){
                                    if(graphModel[lay].graph[k][l] != 1){
                                        graphModel[lay].graph[k][l] += 3;
                                    }
                                }
                            }
                        }
                        if(frontiers[j+1].x > tempSinks[j+1].x && frontiers[j+1].y <= tempSinks[j+1].y){
                            for(int k = tempSinks[j+1].x;k<=frontiers[j+1].x;k++){
                                for(int l = frontiers[j+1].y;l<=tempSinks[j+1].y;l++){
                                    if(graphModel[lay].graph[k][l] != 1){
                                        graphModel[lay].graph[k][l] += 3;
                                    }
                                }
                            }
                        }
                        if(frontiers[j+1].x <= tempSinks[j+1].x && frontiers[j+1].y <= tempSinks[j+1].y){
                            for(int k = frontiers[j+1].x;k<=tempSinks[j+1].x;k++){
                                for(int l = frontiers[j+1].y;l<=tempSinks[j+1].y;l++){
                                    if(graphModel[lay].graph[k][l] != 1){
                                        graphModel[lay].graph[k][l] += 3;
                                    }
                                }
                            }
                        }
                    }
                    //到达终点前重复的比较四个方向的连通性，距离差，优先区域优先方向，依次排除并选择出下一步的方向
                    while(frontiers[j].x != tempSinks[j].x || frontiers[j].y != tempSinks[j].y){
                        int tempDistance[] = {10000,10000,10000,10000};
                        int proArea[] ={0,0,0,0};
                        int tempDistanceW = 10000;
                        int tempDistanceE = 10000;
                        int tempDistanceN = 10000;
                        int tempDistanceS = 10000;
                        int orientation = 0;
                        int tempDistanceO = Math.abs(tempSinks[j].x - frontiers[j].x) + Math.abs(tempSinks[j].y - frontiers[j].y);//开始的曼哈顿距离
                        if((frontiers[j].x - 1)>= 0 &&(frontiers[j].x - 1)< gridXmax
                                && frontiers[j].y >= 0 && frontiers[j].y < gridYmax){
                            if(graphModel[lay].graph[(frontiers[j].x - 1)][frontiers[j].y] != 1){
                                //假设移动到这个方向上的相邻点，计算最大流
                                if(graphModel[lay].graph[(frontiers[j].x - 1)][frontiers[j].y] == 2){
                                    proArea[0] = 1;
                                }
                                int tempGpValue = graphModel[lay].graph[(frontiers[j].x - 1)][frontiers[j].y];
                                sum += 1;
                                graphModel[lay].graph[(frontiers[j].x - 1)][frontiers[j].y] = 1;
                                frontiers[j].x = frontiers[j].x - 1;
                                frontiers[j].updateSN();
                                MaximumFlow maximumTest = new MaximumFlow(gridXmax,gridYmax,sum,graphModel[lay].graph,frontiers,tempSinks);//计算最大流，将每组nets分配到一层layer
                                int tempMax = maximumTest.maxFlow();
                                frontiers[j].x = frontiers[j].x + 1;
                                frontiers[j].updateSN();
                                graphModel[lay].graph[(frontiers[j].x - 1)][frontiers[j].y] = tempGpValue;
                                sum -= 1;
                                if(tempMax >= netsNumOfLayers[i]) {
                                    tempDistanceW = Math.abs(tempSinks[j].x - (frontiers[j].x - 1)) + Math.abs(tempSinks[j].y - frontiers[j].y);
                                    if (tempDistanceO > tempDistanceW) {
                                        //orientation = 1;
                                        tempDistance[0] = tempDistanceW;//调整了，记得修改
                                    }
                                }

                            }
                        }
                        if((frontiers[j].x + 1)>= 0 &&(frontiers[j].x + 1)< gridXmax
                                && frontiers[j].y >= 0 && frontiers[j].y < gridYmax){
                            if(graphModel[lay].graph[(frontiers[j].x + 1)][frontiers[j].y] != 1){
                                //假设移动到这个方向上的相邻点，计算最大流
                                if(graphModel[lay].graph[(frontiers[j].x + 1)][frontiers[j].y] == 2){
                                    proArea[1] = 1;
                                }
                                int tempGpValue = graphModel[lay].graph[(frontiers[j].x + 1)][frontiers[j].y];
                                sum += 1;
                                graphModel[lay].graph[(frontiers[j].x + 1)][frontiers[j].y] = 1;
                                frontiers[j].x = frontiers[j].x + 1;
                                frontiers[j].updateSN();
                                MaximumFlow maximumTest = new MaximumFlow(gridXmax,gridYmax,sum,graphModel[lay].graph,frontiers,tempSinks);//计算最大流，将每组nets分配到一层layer
                                int tempMax = maximumTest.maxFlow();
                                frontiers[j].x = frontiers[j].x - 1;
                                frontiers[j].updateSN();
                                graphModel[lay].graph[(frontiers[j].x + 1)][frontiers[j].y] = tempGpValue;
                                sum -= 1;
                                if(tempMax >= netsNumOfLayers[i]) {
                                    tempDistanceE = Math.abs(tempSinks[j].x - (frontiers[j].x + 1)) + Math.abs(tempSinks[j].y - frontiers[j].y);
                                    if (tempDistanceO > tempDistanceE) {
                                        //orientation = 2;
                                        tempDistance[1] = tempDistanceE;
                                    }
                                }

                            }
                        }
                        if(frontiers[j].x>= 0 && frontiers[j].x < gridXmax
                                && (frontiers[j].y -1) >= 0 && (frontiers[j].y -1) < gridYmax){
                            if(graphModel[lay].graph[frontiers[j].x][(frontiers[j].y-1)] != 1){
                                //假设移动到这个方向上的相邻点，计算最大流
                                if(graphModel[lay].graph[frontiers[j].x][(frontiers[j].y-1)] == 2){
                                    proArea[2] = 1;
                                }
                                int tempGpValue = graphModel[lay].graph[frontiers[j].x][(frontiers[j].y-1)];
                                sum += 1;
                                graphModel[lay].graph[frontiers[j].x][(frontiers[j].y-1)] = 1;
                                frontiers[j].y = frontiers[j].y - 1;
                                frontiers[j].updateSN();
                                MaximumFlow maximumTest = new MaximumFlow(gridXmax,gridYmax,sum,graphModel[lay].graph,frontiers,tempSinks);//计算最大流，将每组nets分配到一层layer
                                int tempMax = maximumTest.maxFlow();
                                frontiers[j].y = frontiers[j].y + 1;
                                frontiers[j].updateSN();
                                graphModel[lay].graph[frontiers[j].x][(frontiers[j].y-1)] = tempGpValue;
                                sum -= 1;
                                if(tempMax >= netsNumOfLayers[i]) {
                                    tempDistanceS = Math.abs(tempSinks[j].x - frontiers[j].x) + Math.abs(tempSinks[j].y - (frontiers[j].y - 1));
                                    if (tempDistanceO > tempDistanceS) {
                                        //保留新的曼哈顿距离及对应的方向
                                        //orientation = 3;
                                        tempDistance[2] = tempDistanceS;
                                    }
                                }
                            }

                        }
                        if(frontiers[j].x>= 0 &&frontiers[j].x< gridXmax
                                && (frontiers[j].y + 1) >= 0 && (frontiers[j].y + 1) < gridYmax){
                            if(graphModel[lay].graph[frontiers[j].x][(frontiers[j].y+1)] != 1){
                                //假设移动到这个方向上的相邻点，计算最大流
                                if(graphModel[lay].graph[frontiers[j].x][(frontiers[j].y+1)] == 2){
                                    proArea[3] = 1;
                                }
                                int tempGpValue = graphModel[lay].graph[frontiers[j].x][(frontiers[j].y+1)];
                                sum += 1;
                                graphModel[lay].graph[frontiers[j].x][(frontiers[j].y+1)] = 1;
                                frontiers[j].y = frontiers[j].y + 1;
                                frontiers[j].updateSN();
                                MaximumFlow maximumTest = new MaximumFlow(gridXmax,gridYmax,sum,graphModel[lay].graph,frontiers,tempSinks);//计算最大流，将每组nets分配到一层layer
                                int tempMax = maximumTest.maxFlow();
                                frontiers[j].y = frontiers[j].y - 1;
                                frontiers[j].updateSN();
                                graphModel[lay].graph[frontiers[j].x][(frontiers[j].y+1)] = tempGpValue;
                                sum -= 1;
                                if(tempMax >= netsNumOfLayers[i]) {
                                    tempDistanceN = Math.abs(tempSinks[j].x - frontiers[j].x) + Math.abs(tempSinks[j].y - (frontiers[j].y + 1));
                                    if (tempDistanceO > tempDistanceN) {
                                        //orientation = 4;
                                        tempDistance[3] = tempDistanceN;
                                    }
                                }
                            }
                        }
                        int temporientation = 0;
                        int tempPro = 0;
                        for(int k=0; k<4;k++){
                            if(tempDistance[k]<tempDistanceO){
                                tempDistanceO = tempDistance[k];
                                temporientation = k+1;
                                tempPro = proDirection[j][k]+proArea[k];
                            }
                            else if(tempDistance[k] == tempDistanceO){
                                if((proDirection[j][k]+proArea[k])>= tempPro){
                                    temporientation = k+1;
                                    tempPro = proDirection[j][k]+proArea[k];
                                }
                            }

                        }
                        orientation = temporientation;
                        System.out.println("方向" + orientation);
                        switch (orientation){
                            case 1:
                                //移动后，之前占据的值赋值为1，即已经被占用
                                frontiers[j].x = frontiers[j].x - 1;
                                break;
                            case 2:
                                frontiers[j].x = frontiers[j].x + 1;
                                break;
                            case 3:
                                frontiers[j].y = frontiers[j].y - 1;
                                break;
                            case 4:
                                frontiers[j].y = frontiers[j].y + 1;
                                break;
                            default:
                                break;
                        }
                        if(orientation == 0){
                            System.out.println("no feasible path");
                            break;
                        }
                        frontiers[j].updateSN();
                        graphModel[lay].graph[frontiers[j].x][frontiers[j].y] = 1;
                        sum += 1;
                        Coordinate temp = new Coordinate(frontiers[j].x,frontiers[j].y,gridXmax,gridYmax);
                        path[assignment[i][j]].routingPath.add(temp);
                        path[assignment[i][j]].fixedLength +=1;
                        path[assignment[i][j]].remainedLength -= 1;
                    }
                    //detour部分，为了满足长度需要，同样需要检测连通性和判断优先级，此时值为0的优先级高于值为2的
                    while(path[assignment[i][j]].remainedLength >1 && path[assignment[i][j]].fixedLength !=0){//两种思路，一种从起点，一种从终点，每次detour以2为单位，对于距离差为1的无法等长，且依赖于最大流判断连通性
                        int tempLength = path[assignment[i][j]].routingPath.size();
                        for(int k = 0;k < tempLength;k++){
                            int proDetour[] ={0,0,0,0};
                            int orientation = 0;
                            boolean hasDetour = false;//判断是否detour了
                            Coordinate tempCor1 = new Coordinate(path[assignment[i][j]].routingPath.get(k).x,
                                    path[assignment[i][j]].routingPath.get(k).y,gridXmax,gridYmax);
                            if(k+1 <tempLength){
                                Coordinate tempCor2= new Coordinate(path[assignment[i][j]].routingPath.get(k+1).x,
                                        path[assignment[i][j]].routingPath.get(k+1).y,gridXmax,gridYmax);
                                if((tempCor1.x - 1)>= 0 &&(tempCor1.x - 1)< gridXmax && tempCor1.y >= 0 && tempCor1.y < gridYmax
                                && (tempCor2.x - 1)>= 0 &&(tempCor2.x - 1)< gridXmax && tempCor2.y >= 0 && tempCor2.y < gridYmax){
                                    if(graphModel[lay].graph[(tempCor1.x - 1)][tempCor1.y] != 1 && graphModel[lay].graph[(tempCor2.x - 1)][tempCor2.y] != 1){
                                        //假设移动到这个方向上的相邻点，计算最大流
                                        int tempGpValue1 = graphModel[lay].graph[(tempCor1.x - 1)][tempCor1.y];
                                        int tempGpValue2 = graphModel[lay].graph[(tempCor2.x - 1)][tempCor2.y];
                                        graphModel[lay].graph[(tempCor1.x - 1)][tempCor1.y] = 1;
                                        graphModel[lay].graph[(tempCor2.x - 1)][tempCor2.y] = 1;
                                        MaximumFlow maximumTest = new MaximumFlow(gridXmax,gridYmax,sum,graphModel[lay].graph,frontiers,tempSinks);//计算最大流，将每组nets分配到一层layer
                                        int tempMax = maximumTest.maxFlow();
                                        graphModel[lay].graph[(tempCor1.x - 1)][tempCor1.y] = tempGpValue1;
                                        graphModel[lay].graph[(tempCor2.x - 1)][tempCor2.y] = tempGpValue2;
                                        if(tempMax >=netsNumOfLayers[i]){
                                            if(graphModel[lay].graph[(tempCor1.x - 1)][tempCor1.y] == 2){
                                                proDetour[0] += 2;
                                            }
                                            if(graphModel[lay].graph[(tempCor2.x - 1)][tempCor2.y] == 2){
                                                proDetour[0] += 2;
                                            }
                                            if(graphModel[lay].graph[(tempCor1.x - 1)][tempCor1.y] == 0){
                                                proDetour[0] += 3;
                                            }
                                            if(graphModel[lay].graph[(tempCor2.x - 1)][tempCor2.y] == 0){
                                                proDetour[0] += 3;
                                            }
                                            if(graphModel[lay].graph[(tempCor1.x - 1)][tempCor1.y] == 5){
                                                proDetour[0] += 1;
                                            }
                                            if(graphModel[lay].graph[(tempCor2.x - 1)][tempCor2.y] == 5){
                                                proDetour[0] += 1;
                                            }
                                        }
                                    }
                                }
                                if((tempCor1.x + 1)>= 0 &&(tempCor1.x + 1)< gridXmax && tempCor1.y >= 0 && tempCor1.y < gridYmax
                                && (tempCor2.x + 1)>= 0 &&(tempCor2.x + 1)< gridXmax && tempCor2.y >= 0 && tempCor2.y < gridYmax){
                                    if(graphModel[lay].graph[(tempCor1.x + 1)][tempCor1.y] != 1 && graphModel[lay].graph[(tempCor2.x + 1)][tempCor2.y] != 1){
                                        //假设移动到这个方向上的相邻点，计算最大流

                                        int tempGpValue1 = graphModel[lay].graph[(tempCor1.x + 1)][tempCor1.y];
                                        int tempGpValue2 = graphModel[lay].graph[(tempCor2.x + 1)][tempCor2.y];
                                        graphModel[lay].graph[(tempCor1.x + 1)][tempCor1.y] = 1;
                                        graphModel[lay].graph[(tempCor2.x + 1)][tempCor2.y] = 1;
                                        MaximumFlow maximumTest = new MaximumFlow(gridXmax,gridYmax,sum,graphModel[lay].graph,frontiers,tempSinks);//计算最大流，将每组nets分配到一层layer
                                        int tempMax = maximumTest.maxFlow();
                                        graphModel[lay].graph[(tempCor1.x + 1)][tempCor1.y] = tempGpValue1;
                                        graphModel[lay].graph[(tempCor2.x + 1)][tempCor2.y] = tempGpValue2;
                                        if(tempMax >=netsNumOfLayers[i]){
                                            if(graphModel[lay].graph[(tempCor1.x + 1)][tempCor1.y] == 2){
                                                proDetour[1] += 2;
                                            }
                                            if(graphModel[lay].graph[(tempCor2.x + 1)][tempCor2.y] == 2){
                                                proDetour[1] += 2;
                                            }
                                            if(graphModel[lay].graph[(tempCor1.x + 1)][tempCor1.y] == 0){
                                                proDetour[1] += 3;
                                            }
                                            if(graphModel[lay].graph[(tempCor2.x + 1)][tempCor2.y] == 0){
                                                proDetour[1] += 3;
                                            }
                                            if(graphModel[lay].graph[(tempCor1.x + 1)][tempCor1.y] == 5){
                                                proDetour[1] += 1;
                                            }
                                            if(graphModel[lay].graph[(tempCor2.x + 1)][tempCor2.y] == 5){
                                                proDetour[1] += 1;
                                            }
                                        }
                                    }
                                }
                                if(tempCor1.x>= 0 && tempCor1.x < gridXmax && (tempCor1.y -1) >= 0 && (tempCor1.y -1) < gridYmax
                                && tempCor2.x>= 0 && tempCor2.x < gridXmax && (tempCor2.y -1) >= 0 && (tempCor2.y -1) < gridYmax){
                                    if(graphModel[lay].graph[tempCor1.x][(tempCor1.y-1)] != 1 && graphModel[lay].graph[tempCor2.x][(tempCor2.y-1)] != 1){
                                        int tempGpValue1 = graphModel[lay].graph[tempCor1.x][(tempCor1.y-1)];
                                        int tempGpValue2 = graphModel[lay].graph[tempCor2.x][(tempCor2.y-1)];
                                        graphModel[lay].graph[tempCor1.x][(tempCor1.y-1)] = 1;
                                        graphModel[lay].graph[tempCor2.x][(tempCor2.y-1)] = 1;
                                        MaximumFlow maximumTest = new MaximumFlow(gridXmax,gridYmax,sum,graphModel[lay].graph,frontiers,tempSinks);//计算最大流，将每组nets分配到一层layer
                                        int tempMax = maximumTest.maxFlow();
                                        graphModel[lay].graph[tempCor1.x][(tempCor1.y-1)] = tempGpValue1;
                                        graphModel[lay].graph[tempCor2.x][(tempCor2.y-1)] = tempGpValue2;
                                        if(tempMax >=netsNumOfLayers[i]){
                                            if(graphModel[lay].graph[tempCor1.x][(tempCor1.y-1)] == 2){
                                                proDetour[2] += 2;
                                            }
                                            if(graphModel[lay].graph[tempCor2.x][(tempCor2.y-1)] == 2){
                                                proDetour[2] += 2;
                                            }
                                            if(graphModel[lay].graph[tempCor1.x][(tempCor1.y-1)] == 0){
                                                proDetour[2] += 3;
                                            }
                                            if(graphModel[lay].graph[tempCor2.x][(tempCor2.y-1)] == 0){
                                                proDetour[2] += 3;
                                            }
                                            if(graphModel[lay].graph[tempCor1.x][(tempCor1.y-1)] == 5){
                                                proDetour[2] += 1;
                                            }
                                            if(graphModel[lay].graph[tempCor2.x][(tempCor2.y-1)] == 5){
                                                proDetour[2] += 1;
                                            }
                                        }

                                    }

                                }
                                if(tempCor1.x>= 0 && tempCor1.x < gridXmax && (tempCor1.y +1) >= 0 && (tempCor1.y +1) < gridYmax
                                        && tempCor2.x>= 0 && tempCor2.x < gridXmax && (tempCor2.y +1) >= 0 && (tempCor2.y +1) < gridYmax){
                                    if(graphModel[lay].graph[tempCor1.x][(tempCor1.y+1)] != 1 && graphModel[lay].graph[tempCor2.x][(tempCor2.y+1)] != 1){
                                        int tempGpValue1 = graphModel[lay].graph[tempCor1.x][(tempCor1.y+1)];
                                        int tempGpValue2 = graphModel[lay].graph[tempCor2.x][(tempCor2.y+1)];
                                        graphModel[lay].graph[tempCor1.x][(tempCor1.y+1)] = 1;
                                        graphModel[lay].graph[tempCor2.x][(tempCor2.y+1)] = 1;
                                        MaximumFlow maximumTest = new MaximumFlow(gridXmax,gridYmax,sum,graphModel[lay].graph,frontiers,tempSinks);//计算最大流，将每组nets分配到一层layer
                                        int tempMax = maximumTest.maxFlow();
                                        graphModel[lay].graph[tempCor1.x][(tempCor1.y+1)] = tempGpValue1;
                                        graphModel[lay].graph[tempCor2.x][(tempCor2.y+1)] = tempGpValue2;
                                        if(tempMax >=netsNumOfLayers[i]){
                                            if(graphModel[lay].graph[tempCor1.x][(tempCor1.y+1)] == 2){
                                                proDetour[3] += 2;
                                            }
                                            if(graphModel[lay].graph[tempCor2.x][(tempCor2.y+1)] == 2){
                                                proDetour[3] += 2;
                                            }
                                            if(graphModel[lay].graph[tempCor1.x][(tempCor1.y+1)] == 0){
                                                proDetour[3] += 3;
                                            }
                                            if(graphModel[lay].graph[tempCor2.x][(tempCor2.y+1)] == 0){
                                                proDetour[3] += 3;
                                            }
                                            if(graphModel[lay].graph[tempCor1.x][(tempCor1.y+1)] == 5){
                                                proDetour[3] += 1;
                                            }
                                            if(graphModel[lay].graph[tempCor2.x][(tempCor2.y+1)] == 5){
                                                proDetour[3] += 1;
                                            }
                                        }
                                    }

                                }
                                int temporientation = 0;
                                int tempProDetour = 0;
                                for(int l=0; l<4;l++){
                                    if(proDetour[l]> tempProDetour){
                                        tempProDetour = proDetour[l];
                                        temporientation = l +1;
                                    }
                                }
                                orientation = temporientation;
                                System.out.println("方向" + orientation);
                                switch (orientation){
                                    case 1:
                                        //detour坐标位移
                                        tempCor1.x = tempCor1.x - 1;
                                        tempCor2.x = tempCor2.x - 1;
                                        break;
                                    case 2:
                                        tempCor1.x = tempCor1.x + 1;
                                        tempCor2.x = tempCor2.x + 1;
                                        break;
                                    case 3:
                                        tempCor1.y = tempCor1.y - 1;
                                        tempCor2.y = tempCor2.y - 1;
                                        break;
                                    case 4:
                                        tempCor1.y = tempCor1.y + 1;
                                        tempCor2.y = tempCor2.y + 1;
                                        break;
                                    default:
                                        break;
                                }
                                if(orientation == 0){
                                    System.out.println("no feasible detour");
                                }
                                else{
                                    hasDetour =true;
                                    tempCor1.updateSN();
                                    tempCor2.updateSN();
                                    graphModel[lay].graph[tempCor1.x][tempCor1.y] = 1;
                                    graphModel[lay].graph[tempCor2.x][tempCor2.y] = 1;
                                    sum += 2;
                                    path[assignment[i][j]].routingPath.add(k+1,tempCor2);
                                    path[assignment[i][j]].routingPath.add(k+1,tempCor1);
                                    path[assignment[i][j]].fixedLength +=2;
                                    path[assignment[i][j]].remainedLength -= 2;

                                    if(path[assignment[i][j]].remainedLength <=1){
                                        break;
                                    }
                                }
                            }
                            if(hasDetour){
                                tempLength +=2;
                            }

                        }
                    }
                }


                //检测feasible,可以不用//不需要建立新的起点终点集合，若每步都检测，则这部分不需要
                if(netsNumOfLayers[i]-j-1>0){

                    MaximumFlow newTest = new MaximumFlow(gridXmax,gridYmax,sum,graphModel[lay].graph,frontiers,tempSinks);
                    int restFlow = newTest.maxFlow();
                    System.out.println("feasible?" + restFlow);
                    /*if(restFlow != restNumOfNets){
                        j--;
                        continue;
                    }*/
                }



                System.out.println("routing has ended");
                //记录路径部分，一个net的routing和detour部分完成后将其路径记录在对称类中，用于判断是否可以复制，以及计算对称率
                for(int k =0; k<syNetPairs.length;k++){
                    if(syNetPairs[k].net1Number == assignment[i][j]){
                        syNetPairs[k].net1Routed = true;
                        //syNetPairs[k].routingPath1 = (ArrayList<Coordinate>)path[assignment[i][j]].routingPath.clone();
                        syNetPairs[k].routingPath1.clear();
                        syNetPairs[k].layer1 = path[assignment[i][j]].layer;
                        for(int l = 0;l<path[assignment[i][j]].routingPath.size();l++){
                            Coordinate temp = new Coordinate(path[assignment[i][j]].routingPath.get(l).x,path[assignment[i][j]].routingPath.get(l).y,
                                    gridXmax,gridYmax);//关于坐标系的中轴线对称
                            syNetPairs[k].routingPath1.add(temp);
                            //path[assignment[i][j]].routingPath.get(l).updateSN();
                        }
                    }
                    else if(syNetPairs[k].net2Number == assignment[i][j]){
                        syNetPairs[k].net2Routed = true;
                        //syNetPairs[k].routingPath2 = (ArrayList<Coordinate>)path[assignment[i][j]].routingPath.clone();
                        syNetPairs[k].routingPath2.clear();
                        syNetPairs[k].layer2 = path[assignment[i][j]].layer;
                        for(int l = 0;l<path[assignment[i][j]].routingPath.size();l++){
                            Coordinate temp = new Coordinate(path[assignment[i][j]].routingPath.get(l).x,path[assignment[i][j]].routingPath.get(l).y,
                                    gridXmax,gridYmax);//关于坐标系的中轴线对称
                            syNetPairs[k].routingPath2.add(temp);
                            //path[assignment[i][j]].routingPath.get(l).updateSN();
                        }
                    }
                }
            }
        }
        double tempSyRate = 0;
        for(int i=  0;i<syNetPairs.length;i++){
            tempSyRate += syNetPairs[i].calSymmetricalRate();
        }
        tempSyRate = tempSyRate/syNetPairs.length;
        /*System.out.println("目标长度:"+path[0].targetLength);
        System.out.println("net1 length:"+path[0].fixedLength);
        System.out.println("net2 length:"+path[1].fixedLength);
        System.out.println("完成后占用的区域："+sum);*/
        System.out.println("totalSymmetricalRate:"+tempSyRate);
        int lengthDiff = 0;
        int totalLength = 0;
        for(int i = 0;i<path.length;i++){
            System.out.println("net"+i+" path in layer"+path[i].layer);
            //System.out.println(path[i].remainedLength);
            lengthDiff += path[i].remainedLength;
            totalLength += path[i].fixedLength;
            //path[i].routingPath.add(2,sources[i]);测试add功能
            path[i].printRoutingPath();
        }
        System.out.println("Length difference"+lengthDiff);
        System.out.println("总线长"+ totalLength);
        //测试clone是否生效了
        /*syNetPairs[0].routingPath.add(sources[0]);
        path[0].routingPath = (ArrayList<Coordinate>)syNetPairs[0].routingPath.clone();
        path[0].printRoutingPath();*/
        //System.out.println(Arrays.toString(assignment[0]));
        //System.out.println(Arrays.toString(netsNumOfLayers));
        //System.out.println(num);
        //System.out.println(sourcesOrder[3]);
    }

    public static void MyRouter2(){

    }

    public static void CafeRouter(){
    long StartTime = 0;
    long EndTime = 0;
    int graphModel[][] = new int[120][120];//网格图模型定义
    int sum = 0;
    for(int i = 0;i<120;i++){
        for(int j = 0;j<120;j++) {
            double res = Math.random();
            if(res>0.05){
                graphModel[i][j] = 0;
            }
            else{
                graphModel[i][j] = 1;
            }
            graphModel[i][j] = 0;
            sum = sum + graphModel[i][j];
        }
    }
    //MaximumFlow ex = new MaximumFlow();
    //ex.maxFlow();
    System.out.println("完成前占用的区域"+sum);

/*sources[0] =new Coordinate(0,2,gridXmax,gridYmax);
sources[1] =new Coordinate(5,2,gridXmax,gridYmax);*/
/*sources[2] =new Coordinate(0,7,gridXmax,gridYmax);
sources[3] =new Coordinate(10,7,gridXmax,gridYmax);*/

//初始化，后续更改为更灵活的方式

    Coordinate source[] = new Coordinate[20];//起点定义
    for(int i= 0; i<20;i++){
        if(i<10){
            source[i] = new Coordinate(0,119-3*i,120,120);
        }
        else{
            source[i] = new Coordinate(119,119-3*(i-10),120,120);
        }
    }
    //初始化，后续更改为更灵活的方式
    Coordinate sink[] = new Coordinate[20];//终点定义
        for(int i= 0; i<20;i++){
            if(i<10){
                sink[i] = new Coordinate(59-3*i,0,120,120);
            }
            else{
                sink[i] = new Coordinate(60+3*(i-10),0,120,120);
            }
        }
    //初始化
    NetPath path[] = new NetPath[20];//net定义，包含目标长度，已走长度，未走长度，以及路径坐标点列表

    Coordinate frontier[] = new Coordinate[20];//routing前端定义

    for(int i = 0; i<source.length;i++){
        graphModel[source[i].x][source[i].y] = 1;
        frontier[i] = source[i];
        }
        int distance[] = new int[20];//初始距离
        int maxDistance = 0;
        for(int i = 0;i<20;i++) {
            distance[i] = Math.abs(sink[i].x - source[i].x) + Math.abs(sink[i].y - source[i].y);
            if(distance[i]> maxDistance){
                maxDistance = distance[i];
            }
        }
        for(int i = 0; i<path.length;i++){
            path[i] = new NetPath(maxDistance,maxDistance,0);
            Coordinate temp = new Coordinate(source[i].x,source[i].y,120,120);
            path[i].routingPath.add(temp);
        }

        //long sum = 0;
    try {
        int focusedNet = 0;
        //int count = 0;
        /*graphModel[source[0].x][source[0].y] = 1;//？？
        graphModel[source[1].x][source[1].y] = 1;
        graphModel[sink[0].x][sink[0].y] = 0;
        graphModel[sink[1].x][sink[1].y] = 0;
        Coordinate temp1 = new Coordinate(source[0].x,source[0].y,20,20);//因为java对象引用的特性，因此需要这样操作
        Coordinate temp2 = new Coordinate(source[1].x,source[1].y,20,20);
        path[0].routingPath.add(temp1);
        path[1].routingPath.add(temp2);*/
        int tempDistanceO = distance[0];//这里的处理有问题，达不到等长，需要进一步detour
        //MaximumFlow test = new MaximumFlow(20,20,sum,graphModel,frontier,sink);//测试
        StartTime = System.currentTimeMillis();
        //test.maxFlow();
        while(true){
            for(int i = 0; i<path.length;i++){
                if(i == 0){
                    focusedNet = i;
                }
                else{
                    if(path[i].remainedLength > path[i-1].remainedLength){
                        focusedNet = i;
                    }
                }
            }

            //int tempDistanceC = 10000;
            int tempDistance[] = {10000,10000,10000,10000};
            int tempDistanceW = 10000;
            int tempDistanceE = 10000;
            int tempDistanceN = 10000;
            int tempDistanceS = 10000;
            int orientation = 0;
            tempDistanceO = Math.abs(sink[focusedNet].x - frontier[focusedNet].x) + Math.abs(sink[focusedNet].y - frontier[focusedNet].y);//开始的曼哈顿距离
            //tempDistanceC = Math.abs(sink[focusedNet].x - frontier[focusedNet].x) + Math.abs(sink[focusedNet].y - frontier[focusedNet].y);
            //四个判断是比较四个方向上的点
            if((frontier[focusedNet].x - 1)>= 0 &&(frontier[focusedNet].x - 1)< 120
                    && frontier[focusedNet].y >= 0 && frontier[focusedNet].y < 120){
                if(graphModel[(frontier[focusedNet].x - 1)][frontier[focusedNet].y] != 1){
                    //graphModel[frontier[focusedNet].x][frontier[focusedNet].y] = 1;//假设移动到这个方向上的相邻点，计算最大流
                    sum += 1;
                    graphModel[(frontier[focusedNet].x - 1)][frontier[focusedNet].y] = 1;
                    frontier[focusedNet].x = frontier[focusedNet].x - 1;
                    frontier[focusedNet].updateSN();
                    MaximumFlow maximumtemp3 = new MaximumFlow(120,120,sum,graphModel,frontier,sink);
                    int tempMax = maximumtemp3.maxFlow();
                    frontier[focusedNet].x = frontier[focusedNet].x + 1;
                    frontier[focusedNet].updateSN();
                    //graphModel[frontier[focusedNet].x][frontier[focusedNet].y] = 1;
                    graphModel[(frontier[focusedNet].x - 1)][frontier[focusedNet].y] = 0;
                    sum -= 1;
                    if(tempMax >= 20){
                        tempDistanceW = Math.abs(sink[focusedNet].x - (frontier[focusedNet].x - 1)) + Math.abs(sink[focusedNet].y - frontier[focusedNet].y);
                        if(tempDistanceO > tempDistanceW){
                            //orientation = 1;
                            tempDistance[2] = tempDistanceW;//调整了，记得修改
                        }
                    }
                }
            }
            if((frontier[focusedNet].x + 1)>= 0 &&(frontier[focusedNet].x + 1)< 120
                    && frontier[focusedNet].y >= 0 && frontier[focusedNet].y < 120){
                if(graphModel[(frontier[focusedNet].x + 1)][frontier[focusedNet].y] != 1){
                    // graphModel[frontier[focusedNet].x][frontier[focusedNet].y] = 1;//假设移动到这个方向上的相邻点，计算最大流
                    sum += 1;
                    graphModel[(frontier[focusedNet].x + 1)][frontier[focusedNet].y] = 1;
                    frontier[focusedNet].x = frontier[focusedNet].x + 1;
                    frontier[focusedNet].updateSN();
                    MaximumFlow maximumtemp4 = new MaximumFlow(120,120,sum,graphModel,frontier,sink);
                    int tempMax = maximumtemp4.maxFlow();
                    frontier[focusedNet].x = frontier[focusedNet].x - 1;
                    frontier[focusedNet].updateSN();
                    //graphModel[frontier[focusedNet].x][frontier[focusedNet].y] = 1;
                    graphModel[(frontier[focusedNet].x + 1)][frontier[focusedNet].y] = 0;
                    sum -= 1;
                    if(tempMax >= 20){
                        tempDistanceE = Math.abs(sink[focusedNet].x - (frontier[focusedNet].x + 1)) + Math.abs(sink[focusedNet].y - frontier[focusedNet].y);
                        if(tempDistanceO > tempDistanceE){
                            //orientation = 2;
                            tempDistance[3] = tempDistanceE;
                        }
                    }
                }
            }
            if(frontier[focusedNet].x>= 0 && frontier[focusedNet].x < 120
                    && (frontier[focusedNet].y -1) >= 0 && (frontier[focusedNet].y -1) < 120){
                if(graphModel[frontier[focusedNet].x][(frontier[focusedNet].y-1)] != 1){
                    //graphModel[frontier[focusedNet].x][frontier[focusedNet].y] = 1;//假设移动到这个方向上的相邻点，计算最大流
                    sum += 1;
                    graphModel[frontier[focusedNet].x][(frontier[focusedNet].y-1)] = 1;
                    frontier[focusedNet].y = frontier[focusedNet].y - 1;
                    frontier[focusedNet].updateSN();
                    MaximumFlow maximumtemp1 = new MaximumFlow(120,120,sum,graphModel,frontier,sink);
                    int tempMax = maximumtemp1.maxFlow();
                    frontier[focusedNet].y = frontier[focusedNet].y + 1;//消除假设值，让前端回到移动前位置
                    frontier[focusedNet].updateSN();
                    //graphModel[frontier[focusedNet].x][frontier[focusedNet].y] = 1;
                    graphModel[frontier[focusedNet].x][(frontier[focusedNet].y-1)] = 0;
                    sum -= 1;
                    if( tempMax >= 20){
                        tempDistanceS = Math.abs(sink[focusedNet].x - frontier[focusedNet].x) + Math.abs(sink[focusedNet].y - (frontier[focusedNet].y-1));
                        if(tempDistanceO> tempDistanceS){
                            //保留新的曼哈顿距离及对应的方向
                            //orientation = 3;
                            tempDistance[0] = tempDistanceS;
                        }
                    }
                }

            }
            if(frontier[focusedNet].x>= 0 &&frontier[focusedNet].x< 120
                    && (frontier[focusedNet].y + 1) >= 0 && (frontier[focusedNet].y + 1) < 120){
                if(graphModel[frontier[focusedNet].x][(frontier[focusedNet].y+1)] != 1){
                    //graphModel[frontier[focusedNet].x][frontier[focusedNet].y] = 1;//假设移动到这个方向上的相邻点，计算最大流
                    sum += 1;
                    graphModel[frontier[focusedNet].x][(frontier[focusedNet].y+1)] = 1;
                    frontier[focusedNet].y = frontier[focusedNet].y + 1;
                    frontier[focusedNet].updateSN();
                    MaximumFlow maximumtemp2 = new MaximumFlow(120,120,sum,graphModel,frontier,sink);
                    int tempMax = maximumtemp2.maxFlow();
                    frontier[focusedNet].y = frontier[focusedNet].y - 1;
                    frontier[focusedNet].updateSN();
                    //graphModel[frontier[focusedNet].x][frontier[focusedNet].y] = 1;
                    graphModel[frontier[focusedNet].x][(frontier[focusedNet].y + 1)] = 0;
                    sum -= 1;
                    if( tempMax >= 20){
                        tempDistanceN = Math.abs(sink[focusedNet].x - frontier[focusedNet].x) + Math.abs(sink[focusedNet].y - (frontier[focusedNet].y+1));
                        if(tempDistanceO> tempDistanceN){
                            //orientation = 4;
                            tempDistance[1] = tempDistanceN;
                        }
                    }
                }
            }
            int temporientation = 0;
            for(int i=0; i<4;i++){
                if(tempDistance[i]<tempDistanceO){
                    tempDistanceO = tempDistance[i];
                    temporientation = i+1;
                }
                else if(tempDistance[i] == tempDistanceO){
                    temporientation = i+1;
                }
                else{
                }
            }
            orientation = temporientation;
            System.out.println("方向" + orientation);
            switch (orientation){
                case 1:
                    //graphModel[frontier[focusedNet].x][frontier[focusedNet].y] = 1;//移动后，之前占据的值赋值为1，即已经被占用
                    //frontier[focusedNet].x = frontier[focusedNet].x - 1;
                    frontier[focusedNet].y = frontier[focusedNet].y - 1;
                    break;
                case 2:
                    //graphModel[frontier[focusedNet].x][frontier[focusedNet].y] = 1;
                    //frontier[focusedNet].x = frontier[focusedNet].x + 1;
                    frontier[focusedNet].y = frontier[focusedNet].y + 1;
                    break;
                case 3:
                    //graphModel[frontier[focusedNet].x][frontier[focusedNet].y] = 1;
                    //frontier[focusedNet].y = frontier[focusedNet].y - 1;
                    frontier[focusedNet].x = frontier[focusedNet].x - 1;
                    break;
                case 4:
                    //graphModel[frontier[focusedNet].x][frontier[focusedNet].y] = 1;
                    //frontier[focusedNet].y = frontier[focusedNet].y + 1;
                    frontier[focusedNet].x = frontier[focusedNet].x + 1;
                    break;
                default:
                    break;
            }
            if(orientation == 0){
                System.out.println("no feasible path");
                break;
            }
            frontier[focusedNet].updateSN();
            //distance[focusedNet] = tempDistanceO;
            graphModel[frontier[focusedNet].x][frontier[focusedNet].y] = 1;
            sum += 1;
            Coordinate temp = new Coordinate(frontier[focusedNet].x,frontier[focusedNet].y,120,120);
            path[focusedNet].routingPath.add(temp);
            path[focusedNet].fixedLength +=1;
            path[focusedNet].remainedLength -= 1;
            if(frontier[0].x == sink[0].x && frontier[0].y == sink[0].y && frontier[1].x == sink[1].x && frontier[1].y == sink[1].y){
                System.out.println("routing has ended");
                break;
            }

        }
        EndTime = System.currentTimeMillis();
    } catch (Exception e) {
        System.out.println("no feasible path");
        e.printStackTrace();
    }
    System.out.println("目标长度:"+path[0].targetLength);
    System.out.println("net1 length:"+path[0].fixedLength);
    System.out.println("net2 length:"+path[1].fixedLength);
    System.out.println("程序运行时间:"+(EndTime-StartTime)+"ms");
    System.out.println("完成后占用的区域："+sum);
    System.out.println("net1 path");
    path[0].printRoutingPath();
    System.out.println("net2 path");
    path[1].printRoutingPath();
}
}
