package com.company.程序员常用的十大算法;

/**
 * @author ShiWei
 * @date 2021/4/5 - 13:30
 */
public class Hanoitower {

    public static void main(String[] args) {
        hanoiTower(5, 'A', 'B', 'C');
    }

    /**
     * 汉诺塔的移动的方法
     * 使用分治算法
     *
     * @param num
     * @param a
     * @param b
     * @param c
     */
    public static void hanoiTower(int num, char a, char b, char c) {
        //如果只有一个盘
        if (num == 1) {
            System.out.println("第1个盘从 " + a + "->" + c);
        } else {
            //如果n>=2，总是可以看做是两个盘：1最下边的一个盘；2上面的所有盘
            //1.先把最上面的所有盘从A->B，移动过程会使用到c
            hanoiTower(num - 1, a, c, b);
            //2.把最下边的盘A->C
            System.out.println("第" + num + "个盘从 " + a + "->" + c);
            //3.把B塔的所有盘从B->C，移动过程使用到a
            hanoiTower(num - 1, b, a, c);
        }
    }
}
