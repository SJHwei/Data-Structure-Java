package com.company.链表;

/**
 * @author ShiWei
 * @date 2021/2/28 - 10:28
 */
public class SingleLinkedListDemo {
}

//定义单链表管理节点
class SingleLinkedList {
    //头结点标识了整个单链表
    //先初始化一个头结点, 头结点不要动, 不存放具体的数据
    private HeroNode head = new HeroNode(0, "", "");

    //添加节点到单链表
    //思路: 当不考虑编号顺序时
    //1. 找到当前链表的最后节点
    //2. 将最后这个节点的next指向新的节点
    public void add(HeroNode heroNode) {
        //因为head节点不能动, 因此需要一个辅助变量temp
        HeroNode temp = head;
        //遍历单链表, 找到最后一个节点
        while(true) {
            //找到链表的最后
            if (temp.next == null) {
                break;
            }
            //如果没有找到最后, 将temp后移
            temp = temp.next;
        }
        //当退出while循环时, temp就指向了链表的最后一个节点
        //将最后这个节点的next指向新节点
        temp.next = heroNode;
    }

    //第二种方式在添加英雄时, 根据排名将英雄插入到指定位置
    //如果有这个排名, 则添加失败, 并给出提示
    public void addByOrder(HeroNode heroNode) {
        //因为头结点不能动, 因此任然通过一个辅助指针(变量)来帮助找到添加的位置
        //因为是单链表, 所以找到的temp是位于添加的前一个节点, 否则插入不了
        HeroNode temp = head;
        boolean flag = false; //flag标志添加的编号是否存在, 默认为false

        while(true) {
            if (temp.next == null) { //说明temp已经在链表的最后
                break;
            }
            if (temp.next.no > heroNode.no) { //位置找到, 就在temp的后面
                break;
            } else if (temp.next.no == heroNode.no) { //说明希望添加的heroNode的编号已经存在
                flag = true; //说明编号存在
                break;
            }
            temp = temp.next; //后移
        }

        //判断flag的值
        if(flag) { //不能添加, 说明编号存在
            System.out.printf("准备插入的节点的编号%d已经存在了, 不能加入\n", heroNode.no);
        } else {
            //将新节点插入到单链表中, temp的后面
            heroNode.next = temp.next;
            temp.next = heroNode;
        }
    }

    //修改节点的信息, 根据no编号来修改, 即no编号不能改
    //说明: 根据newHeroNode的no来修改即可.
    public void upDate(HeroNode newHeroNode) {
        //判断单链表是否为空
        if(head.next == null) {
            System.out.println("链表为空~");
            return;
        }
        //根据no编号找到需要修改的节点
        //定义一个辅助变量
        HeroNode temp = head.next;
        boolean flag = false; //表示是否找到该节点
        while(true) {
            if(temp.next == null) {
                break; //已经遍历完了
            }
            if(temp.no == newHeroNode.no) {
                //找到了
                flag = true;
                break;
            }
            temp = temp.next;
        }

        //根据flag判断是否找到要修改的节点
        if(flag) {
            temp.name = newHeroNode.name;
            temp.nickname = newHeroNode.nickname;
        } else { //没有找到
            System.out.printf("没有找到编号%d的节点, 不能修改\n", newHeroNode.no);
        }
    }

    //删除节点
    //思路:
    //1. head不能动, 因此需要一个temp辅助节点找到待删除节点的前一个节点
    //2. 在比较时, 是temp.next.no和需要删除的节点的no比较
    public void del(int no) {
        HeroNode temp = head; //保证可以删除第一个节点
        boolean flag = false; //标志是否找到待删除节点
        while(true) {
            if(temp.next == null) { //已经到链表最后
                break;
            }
            if(temp.next.no == no) {
                //找到的待删除节点的前一个节点
                flag = true;
                break;
            }
            temp = temp.next;
        }

        //判断flag
        if(flag) { //找到了
            //可以删除
            temp.next = temp.next.next;
        } else {
            System.out.printf("要删除的%d节点不存在\n", no);
        }

    }

    //显示链表(遍历)
    public void list() {
        //判断链表是否为空
        if(head.next == null) {
            System.out.println("链表为空");
            return;
        }
        //因为头结点标识了整个单链表, 所以不能动, 因此需要一个辅助变量来遍历
        HeroNode temp = head;
        while (true) {
            //判断是否到链表最后
            if (temp == null) {
                break;
            }
            //输出节点信息
            System.out.println(temp); //调用HeroNode的toString方法
            //将temp后移, 一定小心
            temp = temp.next;
        }
    }
}

//定义HeroNode, 每个HeroNode对象就是一个节点
class HeroNode {
    public int no;
    public String name;
    public String nickname;
    public HeroNode next; //指向下一个节点

    //构造器
    public HeroNode(int no, String name, String nickname) {
        this.no = no;
        this.name = name;
        this.nickname = nickname;
        //next默认是null
    }

    //为了显示方便, 重写toString方法

    @Override
    public String toString() {
        return "HeroNode{" +
                "no=" + no +
                ", name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                ", next=" + next +
                '}';
    }
}
