package com.company.程序员常用的十大算法;

import com.sun.security.jgss.ExtendedGSSContext;

import java.util.Arrays;

/**
 * @author ShiWei
 * @date 2021/4/10 - 19:45
 */
public class KruskalCase {

    private int edgeNum; //边的个数
    private char[] vertexs; //顶点数组
    private int[][] matrix; //邻接矩阵
    //使用 INF 表示两个顶点不能连通
    private static final int INF = Integer.MAX_VALUE;

    public static void main(String[] args) {
        char[] vertexs = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        int matrix[][] = {
                {0, 12, INF, INF, INF, 16, 14},
                {12, 0, 10, INF, INF, 7, INF},
                {INF, 10, 0, 3, 5, 6, INF},
                {INF, INF, 3, 0, 4, INF, INF},
                {INF, INF, 5, 4, 0, 2, 8},
                {16, 7, 6, INF, 2, 0, 9},
                {14, INF, INF, INF, 8, 9, 0}
        };

        //创建KruskalCase对象实例
        KruskalCase kruskalCase = new KruskalCase(vertexs, matrix);
        kruskalCase.print();
        EdgeData[] edges = kruskalCase.getEdges();
        System.out.println("排序前：" + Arrays.toString(edges));
        kruskalCase.sortEdges(edges);
        System.out.println("排序后：" + Arrays.toString(edges));

        //测试kruskal算法
        kruskalCase.kruskal();
    }

    //构造器
    public KruskalCase(char[] vertexs, int[][] matrix) {
        //初始化顶点数和边的个数
        int vlen = vertexs.length;

        //初始化顶点，复制拷贝的方式
        this.vertexs = new char[vlen];
        for (int i = 0; i < vertexs.length; i++) {
            this.vertexs[i] = vertexs[i];
        }

        //初始化边，使用的是复制拷贝的方式
        this.matrix = new int[vlen][vlen];
        for (int i = 0; i < vlen; i++) {
            for (int j = 0; j < vlen; j++) {
                this.matrix[i][j] = matrix[i][j];
            }
        }

        //统计边
        for (int i = 0; i < vlen; i++) {
            for (int j = i + 1; j < vlen; j++) {
                if (matrix[i][j] != INF) {
                    edgeNum++;
                }
            }
        }
    }

    public void kruskal() {
        int index = 0; //表示最后结果数组的索引
        int[] ends = new int[vertexs.length]; //用于保存“已有最小生成树”中的每个顶点在最小生成树中的终点
        //创建结果数组，保存最后的最小生成树
        EdgeData[] rets = new EdgeData[edgeNum];

        //获取图中所有的边的集合，一共有12条
        EdgeData[] edges = getEdges();
        //按照边的权值大小进行排序(从小到大)
        sortEdges(edges);

        //遍历edges数组，将边添加到最小生成树中时，判断准备加入的边是否形成了回路，如果没有就加入rets，否则不能加入
        for (int i = 0; i < edgeNum; i++) {
            //获取到第i条边的第一个顶点
            int p1 = getPosition(edges[i].start);
            //获取到第i条边的第二个顶点
            int p2 = getPosition(edges[i].end);
            //获取p1这个顶点在已有最小生成树中的终点
            int m = getEnd(ends, p1);
            //获取p2这个顶点在已有最小生成树中的终点
            int n = getEnd(ends, p2);
            //判断是否构成回路
            if (m != n) { //没有构成回路
                ends[m] = n; //设置m在已有最小生成树中的终点
                rets[index++] = edges[i]; //有一条边加入到rets数组
            }
        }

        //统计并打印最小生成树，输出rets
        System.out.println("最小生成树为：");
        for (int i = 0; i < index; i++) {
            System.out.println(rets[i]);
        }
    }

    //打印邻接矩阵
    public void print() {
        System.out.println("邻接矩阵为：\n");
        for (int i = 0; i < vertexs.length; i++) {
            for (int j = 0; j < vertexs.length; j++) {
                System.out.printf("%12d", matrix[i][j]);
            }
            System.out.println();
        }
    }

    /**
     * 对边进行排序处理，冒泡排序
     *
     * @param edges
     */
    private void sortEdges(EdgeData[] edges) {
        for (int i = 0; i < edges.length - 1; i++) {
            for (int j = 0; j < edges.length - 1 - i; j++) {
                if (edges[j].weight > edges[j + 1].weight) {
                    EdgeData temp = edges[j];
                    edges[j] = edges[j + 1];
                    edges[j + 1] = temp;
                }
            }
        }
    }

    /**
     * 给定一个节点的值，得到该值在数组中的下标
     *
     * @param ch 顶点的值
     * @return 返回ch顶点对应的下标，如果找不到，返回-1
     */
    private int getPosition(char ch) {
        for (int i = 0; i < vertexs.length; i++) {
            if (vertexs[i] == ch) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 功能：获取图中边，放到EdgeData[]数组中，后面需要遍历该数组，是通过matrix邻接矩阵来获取
     *
     * @return
     */
    private EdgeData[] getEdges() {
        int index = 0;
        EdgeData[] edges = new EdgeData[edgeNum];
        for (int i = 0; i < vertexs.length; i++) {
            for (int j = i + 1; j < vertexs.length; j++) {
                if (matrix[i][j] != INF) {
                    edges[index++] = new EdgeData(vertexs[i], vertexs[j], matrix[i][j]);
                }
            }
        }
        return edges;
    }

    /**
     * 功能：获取下标为i的顶点的终点，用于后面判断两个顶点的终点是否相同
     * （重点方法）
     *
     * @param ends 数组就是记录了各个顶点对应的终点是哪个，ends数组是在遍历过程中，逐步形成
     * @param i    表示传入的顶点对应的下标
     * @return 返回的就是下标为i的这个顶点对应的终点的下标
     */
    private int getEnd(int[] ends, int i) {
        while (ends[i] != 0) {
            i = ends[i];
        }
        return i;
    }
}

//创建一个类EdgeData，它的对象实例就表示一条边
class EdgeData {
    char start; //边的一个点
    char end; //边的另一个点
    int weight; //边的权值

    public EdgeData(char start, char end, int weight) {
        this.start = start;
        this.end = end;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "EdgeData{" +
                "start=" + start +
                ", end=" + end +
                ", weight=" + weight +
                '}';
    }
}