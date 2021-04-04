package com.company.图;

import javax.swing.text.html.ListView;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author ShiWei
 * @date 2021/4/1 - 14:07
 * <p>
 * 注意：该图是无向图
 */
public class Graph {

    private ArrayList<String> vertexList; //存储顶点的集合，此处之所以使用arraylist，是因为它的底层是数组，可以通过元素取得下标
    private int[][] edges; //存储图对应的邻接矩阵
    private int numOfEdges; //表示边的个数
    private boolean[] isVisited; //定义数组boolean[]，记录某个节点是否被访问

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

        //测试一把，我们的dfs遍历
        System.out.println("深度遍历");
        graph.dfs();
        System.out.println();
        //测试一把，我们的bfs遍历
        System.out.println("广度优先");
        graph.bfs();

    }

    /**
     * 构造器
     *
     * @param n
     */
    public Graph(int n) {
        //初始化矩阵和列表
        vertexList = new ArrayList<String>(n);
        edges = new int[n][n];
        numOfEdges = 0;
        isVisited = new boolean[n];
    }

    /**
     * 得到第一个邻接节点的下标w
     *
     * @param index 当前节点的下标
     * @return 如果存在就返回对应的下标，否则返回-1
     */
    public int getFirstNeighhbor(int index) {
        for (int i = 0; i < vertexList.size(); i++) {
            if (edges[index][i] > 0) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 根据前一个邻接节点的下标获取下一个邻接节点的小标
     *
     * @param v1 当前节点的下标
     * @param v2 当前节点的前一个邻接节点的下标
     * @return
     */
    public int getNextNeighbor(int v1, int v2) {
        for (int i = v2 + 1; i < vertexList.size(); i++) {
            if (edges[v1][i] > 0) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 深度优先遍历算法
     * 该方法是针对一个节点的
     *
     * @param isVisited
     * @param i         i第一次是0
     */
    private void dfs(boolean[] isVisited, int i) {
        //首先我们访问该节点，输出
        System.out.print(getValueByIndex(i) + "->");
        //将节点设置为已经访问
        isVisited[i] = true;
        //查找节点i的第一个邻接节点w
        int w = getFirstNeighhbor(i);
        while (w != -1) {
            if (!isVisited[w]) {
                dfs(isVisited, w);
            }
            //如果w节点已经被访问过
            w = getNextNeighbor(i, w);
        }
    }

    /**
     * 对dfs进行一个重载，遍历我们所有的节点，并进行dfs
     */
    public void dfs() {
        isVisited = new boolean[5]; //将标记数组置空，防止干扰
        //遍历所有的节点，进行dfs[回溯]
        for (int i = 0; i < getNumOfVertex(); i++) {
            if (!isVisited[i]) {
                dfs(isVisited, i);
            }
        }
    }

    /**
     * 对bfs进行一个重载，遍历所有的节点，并进行bfs
     */
    public void bfs() {
        isVisited = new boolean[5]; //将标记数组置空，防止dfs的干扰
        //遍历所有的节点，进行bfs
        for (int i = 0; i < getNumOfVertex(); i++) {
            if (!isVisited[i]) {
                bfs(isVisited, i);
            }
        }
    }

    /**
     * 对一个节点进行广度优先搜索遍历的方法
     * @param isVisited
     * @param i
     */
    public void bfs(boolean[] isVisited, int i) {
        int u; //表示队列的头结点对应的下标
        int w; //邻接节点w
        //队列，记录节点访问的顺序
        LinkedList<Integer> queue = new LinkedList<>();
        //访问节点，输出节点信息
        System.out.print(getValueByIndex(i) + "=>");
        //标记为已访问
        isVisited[i] = true;
        //将节点加入队列
        queue.addLast(i);

        while (queue.isEmpty()) {
            //取出队列的头结点下标
            u = queue.removeLast();
            //得到第一个邻接节点的下标w
            w = getFirstNeighhbor(u);
            if (w != -1) { //找到
                //是否访问过
                if (!isVisited[w]) {
                    System.out.print(getValueByIndex(w) + "=>");
                    //标记已经访问过
                    isVisited[w] = true;
                    //入队
                    queue.addFirst(w);
                } else {
                    //以u为前驱点，找到w后面的下一个邻接节点
                    w = getNextNeighbor(u, w); //体现出我们的广度优先
                }
            }
        }
    }

    //自己写的
    /**
     * 对一个节点进行广度优先搜索遍历的方法
     * @param isVisited
     * @param i

    public void bfs(boolean[] isVisited, int i) {
        int u; //表示队列的头结点对应的下标
        int w; //邻接节点w
        LinkedList<Integer> queue = new LinkedList(); //队列，记录节点访问的顺序

        //1.访问初始节点并将该节点的下标加入队列，并标记为已访问
        System.out.print(vertexList.get(i) + "->");
        queue.addFirst(i);
        isVisited[i] = true;

        //2.当队列非空时，取出队列中的头节点，并遍历其邻接节点
        while (!queue.isEmpty()) {
            //取出队列的头结点下标
            u = (int)queue.removeLast();
            //遍历邻接节点
            for (int k = 0; k < vertexList.size(); k++) {
                if (edges[u][k] > 0) {
                    //如果当前邻接节点已被访问过，则跳过，如果没有被访问过，则进行访问
                    if (!isVisited[k]) {
                        //访问当前邻接节点
                        System.out.print(vertexList.get(k) + "->");
                        //将当前已访问的邻接节点加入队列
                        queue.addFirst(k);
                        //标记当前邻接节点为已访问
                        isVisited[k] = true;
                    } else {
                        continue;
                    }
                }
            }
        }
    }
     */

    /**
     * 得到第一个未被访问过的邻接节点的下标w
     * @param index
     * @return public int getFirstUnvisitedNeighbor(int index) {
    for (int i = 0; i < vertexList.size(); i++) {
    if (edges[index][i] > 0) {
    return i;
    }
    }
    }*/

    /**
     * 插入节点
     */
    public void insertVertex(String vertex) {
        vertexList.add(vertex);
    }

    /**
     * 添加边
     *
     * @param v1     表示点的下标即对应第几个顶点
     * @param v2     表示第二个顶点对应的下标
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
