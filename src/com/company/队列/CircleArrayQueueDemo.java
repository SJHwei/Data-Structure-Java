package com.company.队列;

import java.util.Scanner;

/**
 * @author ShiWei
 * @date 2021/2/27 - 21:28
 */
public class CircleArrayQueueDemo {

    public static void main(String[] args) {
        //测试一把
        System.out.println("测试数组模拟环形队列的案例~");
        //创建一个队列
        CircleArray queue = new CircleArray(4); //说明: 设置的4, 其队列的有效数据的个数是3
        char key = ' '; //接收用户输入
        Scanner scanner = new Scanner(System.in);
        boolean loop = true;
        //输出一个菜单
        while(loop) {
            System.out.println("s(show): 显示队列");
            System.out.println("e(exit): 退出程序");
            System.out.println("a(add): 向队列中添加数据");
            System.out.println("g(get): 从队列中取出数据");
            System.out.println("h(head): 查看队列头元素");
            key = scanner.next().charAt(0); //接收一个字符
            switch (key) {
                case 's':
                    queue.showQueue();
                    break;
                case 'e': //退出
                    scanner.close();
                    loop = false;
                    break;
                case 'a':
                    System.out.println("请输入一个数:");
                    int value = scanner.nextInt();
                    queue.addQueue(value);
                    break;
                case 'g': //取出数据
                    try {
                        int res = queue.getQueue();
                        System.out.printf("取出的数据是%d\n", res);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 'h': //查看队列头的数据
                    try {
                        int res = queue.headQueue();
                        System.out.printf("队列头的数据是%d\n", res);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
        }
        System.out.println("程序退出~");
    }
}

//使用数组模拟环形队列-编写一个CircleArray类
class CircleArray {
    private int maxSize; //表示数组的最大容量
    //front变量的含义做一个调整: front就指向队列的第一个元素, 也就是说arr[front]就是队列的第一个元素
    //front的初始值=0
    private int front; //队列头
    //rear变量的含义做一个调整: rear指向队列的最后一个元素的后一个位置. 因为希望空出一个空间作为约定
    //rear的初始值=0
    private int rear; //队列尾
    private int[] arr; //该数组用于存放数据, 模拟队列

    public CircleArray(int maxSize) {
        this.maxSize = maxSize;
        arr = new int[maxSize];
        rear = front = 0; //可以不写, int类型的变量自动初始化为0
    }

    //判断队列是否满
    public boolean isFull() {
        return (rear + 1) % maxSize == front;
    }

    //判断队列是否为空
    public boolean isEmpty() {
        return front == rear;
    }

    //向队列中添加数据
    public void addQueue(int n) {
        //判断队列是否满
        if(isFull()) {
            System.out.println("队列满, 不能加入数据~");
            return;
        }
        //直接将数据加入
        arr[rear] = n;
        //将rear后移, 这里必须考虑取模
        rear = (rear + 1) %maxSize;
    }

    //获取队列的数据, 出队列
    public int getQueue() {
        //判断队列是否为空
        if(isEmpty()) {
            throw new RuntimeException("队列空, 不能取数据~"); //注意: 遇到异常, 程序停止, 所以不需要再加return;
        }
        //这里需要分析出front是指向队列的第一个元素
        //1. 先把front对应的值保留到一个临时变量
        //2. 将front后移, 考虑取模
        //3. 将临时变量保存的变量返回
        int value = arr[front];
        front = (front + 1) % maxSize;
        return value;
    }

    //显示队列的所有数据
    public void showQueue() {
        //判断队列是否为空
        if (isEmpty()) {
            System.out.println("队列空的, 没有数据~");
            return;
        }
        //遍历思路: 从front开始遍历, 遍历多少个元素
        for (int i = front; i < front + size(); i++) {
            System.out.printf("arr[%d]=%d\n", i % maxSize, arr[i % maxSize]);
        }
    }

    //求出当前队列有效数据的个数
    public int size() {
        return (rear + maxSize - front) % maxSize;
    }

    //显示队列的头数据, 注意不是取数据
    public int headQueue() {
        //判断队列是否为空
        if(isEmpty()) {
            throw new RuntimeException("队列为空, 没有数据~");
        }
        return arr[front];
    }
}