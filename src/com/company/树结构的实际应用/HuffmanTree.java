package com.company.树结构的实际应用;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author ShiWei
 * @date 2021/3/21 - 14:00
 */
public class HuffmanTree {

    public static void main(String[] args) {
        int arr[] = {13, 7, 8, 3, 29, 6, 1};
        Node root = createHuffmanTree(arr);

        //测试一把
        preOrder(root);
    }

    //编写一个前序遍历的方法
    public static void preOrder(Node root) {
        if (root != null) {
            root.preOrder();
        } else {
            System.out.println("是空树，不能遍历~~");
        }
    }

    //创建赫夫曼树的方法

    /**
     *
     * @param arr 需要创建成哈夫曼树的数组
     * @return 创建好后的赫夫曼树的root节点
     */
    public static Node createHuffmanTree(int[] arr) {
        //第一步为了操作方便
        //1.遍历arr数组
        //2.将arr的每个元素构建成一个Node
        //3.将Node放入到ArrayList中
        List<Node> nodes = new ArrayList<>();
        for (int value : arr) {
            nodes.add(new Node(value));
        }

        //我们处理的过程是一个循环的过程

        while (nodes.size() > 1) {
            //排序，从小到大
            Collections.sort(nodes);
//            System.out.println("nodes = " + nodes);

            //第二步：取出根节点权值最小的两棵二叉树
            //（1）取出权值最小的节点(二叉树)
            Node leftNode = nodes.get(0);
            //（2）取出权值第二小的节点(二叉树)
            Node rightNode = nodes.get(1);
            //（3）构建一棵新的二叉树
            Node parent = new Node(leftNode.value + rightNode.value);
            parent.left = leftNode;
            parent.right = rightNode;
            //（4）从ArrayList删除处理过的二叉树
            nodes.remove(leftNode);
            nodes.remove(rightNode);
            //（5）将parent加入到nodes
            nodes.add(parent);
        }

        //返回哈夫曼树的root节点
        return nodes.get(0);
    }
}

//创建节点类
//为了让Node对象支持Collections集合排序
//让Node实现Comparable接口
class Node implements Comparable<Node> {
    int value; //节点权值
    Node left; //指向左子节点
    Node right; //指向右子节点

    public Node(int value) {
        this.value = value;
    }

    //写一个前序遍历
    public void preOrder() {
        System.out.println(this);
        if (this.left != null) {
            this.left.preOrder();
        }
        if (this.right != null) {
            this.right.preOrder();
        }
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                '}';
    }

    public int compareTo(Node o) {
        //表示从小到大排序，即如果当前节点的权值大于参数节点的权值，则将当前节点排在参数节点之后
        //如果想要从大到小排，则加个负号即可
        return this.value - o.value;
    }
}
