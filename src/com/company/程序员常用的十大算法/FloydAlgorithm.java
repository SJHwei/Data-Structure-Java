package com.company.程序员常用的十大算法;

import java.util.Arrays;

/**
 * @author ShiWei
 * @date 2021/4/13 - 14:09
 */
public class FloydAlgorithm {

    public static void main(String[] args) {
        //测试看看图是否创建成功
        char[] vertex = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        //创建邻接矩阵
        int[][] matrix = new int[vertex.length][vertex.length];
        final int N = 65535; //表示不可以连接
        matrix[0] = new int[]{0, 5, 7, N, N, N, 2};
        matrix[1] = new int[]{5, 0, N, 9, N, N, 3};
        matrix[2] = new int[]{7, N, 0, N, 8, N, N};
        matrix[3] = new int[]{N, 9, N, 0, N, 4, N};
        matrix[4] = new int[]{N, N, 8, N, 0, 5, 4};
        matrix[5] = new int[]{N, N, N, 4, 5, 0, 6};
        matrix[6] = new int[]{2, 3, N, N, 4, 6, 0};

        //创建Graph对象
        NewGraph newGraph = new NewGraph(vertex.length, matrix, vertex);
        //调用弗洛伊德算法
        newGraph.floyd();
        newGraph.show();
    }
}

//创建图
class NewGraph {

    private char[] vertex; //存放顶点的数组
    private int[][] dis; //保存从各个顶点出发到其他顶点的距离，最后的结果也是保留在该数组
    private int[][] pre; //保存到达目标的前驱顶点

    /**
     * 构造器
     *
     * @param length 大小
     * @param matrix 邻接矩阵
     * @param vertex 顶点数组
     */
    public NewGraph(int length, int[][] matrix, char[] vertex) {
        this.vertex = vertex;
        this.dis = matrix;
        this.pre = new int[length][length];
        //对pre数组初始化，注意 存放的是前驱顶点的下标，并不是顶点的值
        for (int i = 0; i < length; i++) {
            Arrays.fill(pre[i], i);
        }
    }

    /**
     * 显示pre数组和dis数组
     */
    public void show() {

        //为了显示便于阅读，优化一下输出
        for (int i = 0; i < dis.length; i++) {
            //先将pre数组输出的一行
            for (int j = 0; j < dis.length; j++) {
                System.out.print(vertex[pre[i][j]] + " ");
            }
            System.out.println();
            //再输出dis数组的一行数据
            for (int j = 0; j < dis.length; j++) {
                System.out.print("(" + vertex[i] + "到" + vertex[j] + "的最短路径是" + dis[i][j] + ") ");
            }
            System.out.println();
            System.out.println();
        }
    }

    //弗洛伊德算法，比较容易理解，而且容易实现
    public void floyd() {
        int len = 0; //变量保存距离
        //对中间顶点遍历，k就是中间顶点的下标
        for (int k = 0; k < dis.length; k++) {
            //从i顶点开始出发
            for (int i = 0; i < dis.length; i++) {
                //到达j顶点
                for (int j = 0; j < dis.length; j++) {
                    len = dis[i][k] + dis[k][j]; //求出从i顶点出发，经过k中间顶点，到达j顶点的距离
                    if (len < dis[i][j]) { //如果len小于dis[i][j]
                        dis[i][j] = len; //更新距离
                        pre[i][j] = pre[k][j]; //更新前驱顶点
                    }
                }
            }
        }
    }
}