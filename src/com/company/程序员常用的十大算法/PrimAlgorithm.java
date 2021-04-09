package com.company.程序员常用的十大算法;

import java.util.Arrays;

/**
 * @author ShiWei
 * @date 2021/4/9 - 13:12
 */
public class PrimAlgorithm {

    public static void main(String[] args) {
        //测试看看图是否创建成功
        char[] data = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        int verxs = data.length;
        //邻接矩阵的关系使用二维数组表示，10000这个大数，表示两个点不连通
        int[][] weight = new int[][]{
                {10000, 5, 7, 10000, 10000, 10000, 2},
                {5, 10000, 10000, 9, 10000, 10000, 3},
                {7, 10000, 10000, 10000, 8, 10000, 10000},
                {10000, 9, 10000, 10000, 10000, 4, 10000},
                {10000, 10000, 8, 10000, 10000, 5, 4},
                {10000, 10000, 10000, 4, 5, 10000, 6},
                {2, 3, 10000, 10000, 4, 6, 10000}
        };

        //创建MGraph对象
        MGraph mGraph = new MGraph(verxs);
        //使用工具类创建图的邻接矩阵
        MinTree.createGraph(mGraph, verxs, data, weight);
        //输出
        MinTree.showGraph(mGraph);
        //测试普利姆算法
        MinTree.prim(mGraph, 0);

    }
}

//图类的工具类，用来对图进行增删查改，生成最小生成树等
//使用工具类就可以不改动图类
class MinTree {

    /**
     * 创建图的邻接矩阵
     *
     * @param graph  图对象
     * @param verxs  图对应的顶点个数
     * @param data   图的各个顶点的值
     * @param weight 图的邻接矩阵
     */
    public static void createGraph(MGraph graph, int verxs, char[] data, int[][] weight) {
        for (int i = 0; i < verxs; i++) {
            graph.data[i] = data[i];
            for (int j = 0; j < verxs; j++) {
                graph.weight[i][j] = weight[i][j];
            }
        }
    }

    /**
     * 显示图的邻接矩阵
     *
     * @param graph
     */
    public static void showGraph(MGraph graph) {
        for (int[] link : graph.weight) {
            System.out.println(Arrays.toString(link));
        }
    }

    /**
     * 编写prim算法，得到最小生成树
     *
     * @param graph 图
     * @param v 表示从图的第几个顶点开始生成
     */
    public static void prim(MGraph graph, int v) {
        //visited[]标记节点是否被访问过，visited[]默认元素都是0，表示没有访问过
        int visited[] = new int[graph.verxs];

        //把当前这个节点标记为已访问
        visited[v] = 1;
        //h1和h2记录两个顶点的下标
        int h1 = -1;
        int h2 = -1;
        int minWeight = 10000; //将minWeight初始化成一个大数，后面在遍历过程中，会被替换
        for (int k = 1; k < graph.verxs; k++) { //因为有graph.verxs个顶点，普利姆算法结束后，有graph.verxs-1条边

            //这个双层for循环是为了确定每一次生成的子图和哪个未被访问过的节点的距离最近
            for (int i = 0; i < graph.verxs; i++) { //遍历所以已经访问过的节点，i节点表示被访问过的节点
                for (int j = 0; j < graph.verxs; j++) { //遍历所有没有访问过的节点，j节点表示还没有访问过的节点
                    if (visited[i] == 1 && visited[j] == 0 && graph.weight[i][j] < minWeight) {
                        //替换minWeight，寻找已经访问过的节点和未访问过的节点间的权值最小的边
                        minWeight = graph.weight[i][j];
                        h1 = i;
                        h2 = j;
                    }
                }
            }
            //找到一条边是最小
            System.out.println("边<" + graph.data[h1] + "," + graph.data[h2] + ">权值：" + minWeight);
            //将当前这个节点标记为已经访问
            visited[h2] = 1;
            //minWeight重新设置为最大值10000
            minWeight = 10000;
        }

    }
}

//图类(图数据结构)
class MGraph {
    int verxs; //表示图的节点个数
    char[] data; //存放节点数据
    int[][] weight; //存放边，就是邻接矩阵

    public MGraph(int verxs) {
        this.verxs = verxs;
        this.data = new char[verxs];
        this.weight = new int[verxs][verxs];
    }
}

