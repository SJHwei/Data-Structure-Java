package com.company.程序员常用的十大算法;

import java.util.concurrent.ForkJoinPool;

/**
 * @author ShiWei
 * @date 2021/4/6 - 18:46
 */
public class KnapsackProblem {

    public static void main(String[] args) {
        int weight[] = {1, 4, 3}; //物品的重量
        int price[] = {1500, 3000, 2000}; //物品的价格
        int m = 4; //背包的容量
        int n = price.length; //物品的个数

        //1.创建二维数组
        //v[i][j]：表示前i个物品放入容量为j的背包的最大价值
        int v[][] = new int[n + 1][m + 1];
        //为了记录放入商品的情况，定义一个二维数组
        //path[i][j]=1：表示第i个物品放入到了容量为j的包中
        int path[][] = new int[n + 1][m + 1];

        //2.初始化二维数组：初始第一行和第一列，在本程序中，不用手动初始化也可以，因为默认为0
        //初始化第一列
        for (int i = 0; i < n + 1; i++) {
            v[i][0] = 0;
        }
        //初始化第一行
        for (int i = 0; i < m + 1; i++) {
            v[0][i] = 0;
        }

        //3.根据前面得到的公式来动态规划处理
        for (int i = 1; i < n + 1; i++) { //不处理第一行，i从1开始
            for (int j = 1; j < m + 1; j++) { //不处理第一列，j从1开始
                //注意：因为我们程序i是从1开始的，因此原来公式中的weight[i]和price[i]修改成i-1
                if (j < weight[i - 1]) {
                    v[i][j] = v[i - 1][j];
                } else {
//                    v[i][j] = Math.max(v[i - 1][j], v[i - 1][j - weight[i - 1]] + price[i - 1]);
                    //为了记录商品存放到背包的情况，不能直接使用上面的公式，需要使用if-else来体现公式
                    if (v[i - 1][j] < v[i - 1][j - weight[i - 1]] + price[i - 1]) {
                        v[i][j] = v[i - 1][j - weight[i - 1]] + price[i - 1];
                        path[i][j] = 1;
                    } else {
                        v[i][j] = v[i - 1][j];
                    }
                }
            }
        }

        //输出一下v，看看目前的情况
        for (int[] ints : v) {
            for (int anInt : ints) {
                System.out.print(anInt + " ");
            }
            System.out.println();
        }

        //输出一下path，看看哪些物品放入了背包中
        //遍历path，这样输出会把所有的放入情况都得到，其实我们只需要最后的放入
//        for (int i = 0; i < path.length; i++) {
//            for (int j = 0; j < path[i].length; j++) {
//                if (path[i][j] == 1) {
//                    System.out.printf("第%d个物品放入背包中\n", i);
//                }
//            }
//        }

        int i = path.length - 1; //行的最大下标
        int j = path[i].length - 1; //列的最大下标
        while (i > 0 && j > 0) { //从path的最后开始找
            if (path[i][j] == 1) {
                System.out.printf("第%d个物品放入背包\n", i);
                j -= weight[i - 1];
            }
            i--;
        }
    }
}
