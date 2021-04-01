package com.company.图;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author ShiWei
 * @date 2021/4/1 - 14:07
 *
 * 注意：该图是无向图
 */
public class Graph {

    private ArrayList<String> vertexList; //存储顶点的集合，此处之所以使用arraylist，是因为它的底层是数组，可以通过元素取得下标
    private int[][] edges; //存储图对应的邻接矩阵
    private int numOfEdges; //表示边的个数

    public static void main(String[] args) {
        //测试一把图是创建ok
        int n = 5; //节点个数
        String vertexs[] = {"A", "B", "C", "D", "E"};
        //创建图对象
        Graph graph = new Graph(5);
        //循环的添加节点
        for (String vertex : vertexs) {
            graph.insertVertex(vertex);
        }
        //添加边
        graph.insertEdge(0, 1, 1);
        graph.insertEdge(0, 2, 1);
        graph.insertEdge(1, 2, 1);
        graph.insertEdge(1, 3, 1);
        graph.insertEdge(1, 4, 1);

        //显示邻接矩阵
        graph.showGraph();

    }

    /**
     * 构造器
     * @param n
     */
    public Graph(int n) {
        //初始化矩阵和列表
        vertexList = new ArrayList<String>(n);
        edges = new int[n][n];
        numOfEdges = 0;
    }

    /**
     * 插入节点
     */
    public void insertVertex(String vertex) {
        vertexList.add(vertex);
    }

    /**
     * 添加边
     * @param v1 表示点的下标即对应第几个顶点
     * @param v2 表示第二个顶点对应的下标
     * @param weight
     */
    public void insertEdge(int v1, int v2, int weight) {
        edges[v1][v2] = weight;
        edges[v2][v1] = weight;
        //注意：边的数目加1
        numOfEdges++;
    }


    //图中常用的方法

    //返回节点的个数
    public int getNumOfVertex() {
        return vertexList.size();
    }

    //返回边的数目
    public int getNumOfEdges() {
        return numOfEdges;
    }

    //返回节点i(下标)对应的节点的数据
    public String getValueByIndex(int i) {
        return vertexList.get(i);
    }

    //返回v1和v2的权值
    public int getWeight(int v1, int v2) {
        return edges[v1][v2];
    }

    //显示图对应的矩阵
    //因为数组可以使用arrays工具类中的tostring方法直接输出，所以不必使用双层循环
    public void showGraph() {
        for (int[] edge : edges) {
            System.out.println(Arrays.toString(edge));
        }
    }

    /*
    public void showGraph() {
        for (int i = 0; i < edges.length; i++) {
            for (int value : edges[i]) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }*/

    //当数组中元素和下标有明确对应关系时，值和下标可以分开使用，之间的对应关系默认即可，不需要非得将两者联系起来。
    /**
     * 添加边和该边的两个顶点
    public void add(String source, String target, int weight) {
        //首先将顶点保存到列表中
        vertexList.add(source);
        vertexList.add(target);
        //根据元素得到下标
        int sourceIndex = vertexList.indexOf(source);
        int targetIndex = vertexList.indexOf(target);
        //根据下标将该边存入邻接矩阵中
        edges[sourceIndex][targetIndex] = weight;
        //该图是无向图
        edges[targetIndex][sourceIndex] = weight;
    }
     */

}
