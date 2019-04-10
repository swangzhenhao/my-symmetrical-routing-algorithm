package com.route.demo;

import java.util.ArrayList;
import java.util.Stack;

public class LCS {
    //子骄学姐的方案
    public int size;
    public ArrayList<Integer> layerAssign = new ArrayList<Integer>();

    public LCS(int size) {
        this.size = size;
    }

    public void getLCS(int start[], int sink[], int lcs[][]){
        int c[];
        int i,j,k,m,n=0,a,imin=0,jmin=0;
        c=new int[size];

        for(i=0;i<size;i++){
            for(j=0;j<size;j++){
                if(start[i] == sink[j]){
                    c[i]=j;
                }
            }
        }
        for (m=0; m<size; m++) {
            if (c[m]!=-1) {
                for (k=0; k<2*size-1; k++) {
                    for (i=0; i<size; i++) {
                        j=k-i;
                        if (i>=imin && j>=jmin && c[i]==j) {
                            for (a=0;a<size; a++) {
                                if (lcs[n][a]==-1) {
                                    lcs[n][a]=start[i];
                                    break;
                                }
                            }
                            c[i]=-1;
                            imin=i;
                            jmin=j;
                        }
                    }
                }
                n=n+1;
                imin=0;
                jmin=0;
            }
        }
        for (i=0; i<size; i++) {
            for (j=0; j<size; j++) {
                if (lcs[i][j]!=-1) {
                    //System.out.print(lcs[i][j]);
                    layerAssign.add(lcs[i][j]);
                }
            }
            layerAssign.add(1000);
            //System.out.print("\n");
        }
    }
    //方案1
    /*private Stack<Character> stack = new Stack<Character>();
    private void printLcs(char d[][], String a, int i ,int j)
    {
        if(i==0||j==0)
        {}
        else if(d[i][j]=='x')
        {
            stack.push(a.charAt(i-1));
            printLcs(d,a,i-1,j-1);
        }
        else if (d[i][j]=='h')
            printLcs(d,a,i-1,j);
        else
            printLcs(d,a,i,j-1);
    }
    public String getLCS(String a,String b)
    {
        int[][] c = new int[a.length()+1][b.length()+1];
        char[][] d = new char [a.length()+1][b.length()+1];
        for(int i = 0; i<=a.length();i++)
            c[i][0]=0;
        for(int j = 0; j<=b.length();j++)
            d[0][j]=0;
        for(int i = 1; i<=a.length();i++)
        {
            for(int j = 1; j<=b.length();j++)
            {
                if(a.charAt(i-1)==b.charAt(j-1))
                {
                    c[i][j] = c[i-1][j-1]+1;	d[i][j] = 'x';
                }
                else if (c[i-1][j] >= c[i][j-1])
                {
                    c[i][j] = c[i-1][j];		d[i][j] = 'h';
                }
                else
                {
                    c[i][j] = c[i][j-1];		d[i][j] = 'w';
                }
            }
        }

        printLcs(d , a , a.length(),b.length());
        String res = "";
        while(!stack.empty())
            res += stack.pop().toString();
        return res;
    }*/
}
