package com.company.树结构的基础部分;


/**
 * @author ShiWei
 * @date 2021/3/17 - 14:10
 */
public class BinaryTreeDemo {

    public static void main(String[] args) {
        //先需要创建一颗二叉树
        BinaryTree binaryTree = new BinaryTree();
        //创建需要的节点
        HeroNode root = new HeroNode(1, "宋江");
        HeroNode node2 = new HeroNode(2, "吴用");
        HeroNode node3 = new HeroNode(3, "卢俊义");
        HeroNode node4 = new HeroNode(4, "林冲");
        HeroNode node5 = new HeroNode(5, "关胜");

        //说明：先手动创建该二叉树，后面学习递归的方式创建二叉树
        binaryTree.setRoot(root);
        root.setLeft(node2);
        root.setRight(node3);
        node3.setRight(node4);
        node3.setLeft(node5);

        //测试
        System.out.println("前序遍历"); //1,2,3,5,4
        binaryTree.preOrder();

        //测试
        System.out.println("中序遍历"); //2,1,5,3,4
        binaryTree.infixOrder();

        //测试
        System.out.println("后序遍历"); //2,5,4,3,1
        binaryTree.postOrder();

        //测试
        //前序遍历的次数：4
        System.out.println("前序遍历查找");
        HeroNode resNode = binaryTree.preOrderSearch(5);
        if (resNode != null) {
            System.out.printf("找到了，信息为no=%d name=%s\n", resNode.getNo(), resNode.getName());
        } else {
            System.out.printf("没有找到no=%d的英雄\n", 5);
        }

        //测试
        //中序遍历的次数：3
        System.out.println("中序遍历查找");
        resNode = binaryTree.infixOrderSearch(5);
        if (resNode != null) {
            System.out.printf("找到了，信息为no=%d name=%s\n", resNode.getNo(), resNode.getName());
        } else {
            System.out.printf("没有找到no=%d的英雄\n", 5);
        }

        //测试
        //后序遍历的次数：2
        System.out.println("后序遍历查找");
        resNode = binaryTree.postOrderSearch(5);
        if (resNode != null) {
            System.out.printf("找到了，信息为no=%d name=%s\n", resNode.getNo(), resNode.getName());
        } else {
            System.out.printf("没有找到no=%d的英雄\n", 5);
        }
    }
}

//定义BinaryTree二叉树
class BinaryTree {
    private HeroNode root;

    public void setRoot(HeroNode root) {
        this.root = root;
    }

    //前序遍历
    public void preOrder() {
        if (this.root != null) {
            this.root.preOrder();
        } else {
            System.out.println("二叉树为空，无法遍历");
        }
    }

    //中序遍历
    public void infixOrder() {
        if (this.root != null) {
            this.root.infixOrder();
        } else {
            System.out.println("二叉树为空，无法遍历");
        }
    }

    //中序遍历
    public void postOrder() {
        if (this.root != null) {
            this.root.postOrder();
        } else {
            System.out.println("二叉树为空，无法遍历");
        }
    }

    //前序遍历查找
    public HeroNode preOrderSearch(int no) {
        if (root != null) {
            return root.preOrderSearch(no);
        } else {
            return null;
        }
    }

    //中序遍历查找
    public HeroNode infixOrderSearch(int no) {
        if (root != null) {
            return root.infixOrderSearch(no);
        } else {
            return null;
        }
    }

    //后序遍历查找
    public HeroNode postOrderSearch(int no) {
        if (root != null) {
            return root.postOrderSearch(no);
        } else {
            return null;
        }
    }
}

//先创建HeroNode节点
class HeroNode {
    private int no;
    private String name;
    private HeroNode left; //默认null
    private HeroNode right; //默认null

    public HeroNode(int no, String name) {
        this.no = no;
        this.name = name;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HeroNode getLeft() {
        return left;
    }

    public void setLeft(HeroNode left) {
        this.left = left;
    }

    public HeroNode getRight() {
        return right;
    }

    public void setRight(HeroNode right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return "HeroNode{" +
                "no=" + no +
                ", name='" + name + '\'' +
                '}';
    }

    //编写前序遍历的方法
    public void preOrder() {
        System.out.println(this); //先输出当前节点
        //递归向左子树前序遍历
        if (this.left != null) {
            this.left.preOrder();
        }
        //递归向右子树前序遍历
        if (this.right != null) {
            this.right.preOrder();
        }
        return;
    }

    //编写中序遍历的方法
    public void infixOrder() {
        //递归向左子树中序遍历
        if (this.left != null) {
            this.left.infixOrder();
        }
        //输出当前节点
        System.out.println(this);
        //递归向右子树中序遍历
        if (this.right != null) {
            this.right.infixOrder();
        }
        return;
    }

    //编写后序遍历的方法
    public void postOrder() {
        if (this.left != null) {
            this.left.postOrder();
        }
        if (this.right != null) {
            this.right.postOrder();
        }
        System.out.println(this);
    }

    /**
     * 前序遍历查找
     *
     * @param no 查找no
     * @return 如果找到就返回Node，如果没有找到返回null
     */
    public HeroNode preOrderSearch(int no) {
        System.out.println("进入前序遍历");
        //比较当前节点是不是
        if (this.no == no) {
            return this;
        }
        //1.则判断当前节点的左子节点是否为空，如果不为空，则递归前序查找
        //2.如果左递归前序查找，找到节点，则返回，否则继续判断
        HeroNode resNode = null;
        if (this.left != null) {
            resNode = this.left.preOrderSearch(no);
        }
        if (resNode != null) { //说明左子树找到
            return resNode;
        }
        //当前节点的右子节点是否为空，如果不空，则继续向右递归
        if (this.right != null) {
            resNode = this.right.preOrderSearch(no);
        }
        return resNode;
    }

    //中序遍历查找
    public HeroNode infixOrderSearch(int no) {
        //判断当前节点的左子节点是否为空，如果不为空，则递归中序查找
        HeroNode resNode = null;
        if (this.left != null) {
            resNode = this.left.infixOrderSearch(no);
        }
        if (resNode != null) {
            return resNode;
        }
        System.out.println("进入中序查找");
        //如果找到，则返回，如果没有找到，就和当前节点比较，如果是则返回当前节点
        if (this.no == no) {
            return this;
        }

        //否则继续进行右递归的中序查找
        if (this.right != null) {
            resNode = this.right.infixOrderSearch(no);
        }
        return resNode;
    }

    //后序遍历查找
    public HeroNode postOrderSearch(int no) {
        //判断当前节点的左子节点是否为空，如果不为空，则递归后序查找
        HeroNode resNode = null;
        if (this.left != null) {
            resNode = this.left.postOrderSearch(no);
        }
        if (resNode != null) { //说明在左子树被找到
            return resNode;
        }

        //如果左子树没有找到，则向右子树递归进行后序遍历查找
        if (this.right != null) {
            resNode = this.right.postOrderSearch(no);
        }
        if (resNode != null) {
            return resNode;
        }
        System.out.println("进入后序查找");
        //如果左右子树都没有找到，就比较当前节点是不是
        if (this.no == no) {
            return this;
        }
        return resNode;
    }
}
