package com.company.树结构的实际应用;

import java.util.IllegalFormatCodePointException;

/**
 * @author ShiWei
 * @date 2021/3/24 - 12:20
 */
public class BinarySortTreeDemo {

    public static void main(String[] args) {
        int arr[] = {7, 3, 10, 12, 5, 1, 9, 2};
        BinarySortTree binarySortTree = new BinarySortTree();
        //循环的添加节点到二叉排序树
        for (int i = 0; i < arr.length; i++) {
            binarySortTree.add(new BstNode(arr[i]));
        }

        //中序遍历二叉排序树
        System.out.println("中序遍历二叉排序树~");
        binarySortTree.infixOrder();

        //测试一下删除叶子节点，删除有一棵子树的节点，删除有两棵子树的节点
        binarySortTree.delNode(2);
        binarySortTree.delNode(5);
        binarySortTree.delNode(9);
        binarySortTree.delNode(12);
        binarySortTree.delNode(7);
        binarySortTree.delNode(3);
        binarySortTree.delNode(10);
        binarySortTree.delNode(1);

        System.out.println("root=" + binarySortTree.getRoot());

        System.out.println("删除节点后");
        binarySortTree.infixOrder();


    }
}

//创建二叉排序树
class BinarySortTree {
    private BstNode root;

    public BstNode getRoot() {
        return root;
    }

    //查找要删除的节点
    public BstNode search(int value) {
        if (root == null) {
            return null;
        } else {
            return root.search(value);
        }
    }

    //查找父节点
    public BstNode searchParent(int value) {
        if (root == null) {
            return null;
        } else {
            return root.searchParent(value);
        }
    }

    //编写方法：
    //1.返回的以node为根节点的二叉排序树的最小节点的值
    //2.删除node为根节点的二叉排序树的最小节点
    public int delRightTreeMin(BstNode node) {
        BstNode target = node;
        //循环的查找左节点，就会找到最小值
        while (target.left != null) {
            target = target.left;
        }
        //这时target就指向了最小节点
        //删除最小节点
        delNode(target.value);
        return target.value;
    }

    //删除节点
    public void delNode(int value) {
        if (root == null) {
            return ;
        } else {
            //1.需要先去找到要删除的节点targetNode
            BstNode targetNode = search(value);
            //如果没有找到要删除的节点
            if (targetNode == null) {
                return;
            }
            //如果发现要删除的节点就是根节点，而且根节点没有孩子节点
            if (root.value == value && root.left == null && root.right == null) {
                root = null;
                return;
            }
            //注意：存在一个问题，如果要删除的节点就是根节点，而且根节点有孩子节点，怎么办？ 下面代码已解决

            //注意：一个小技巧，可以把分类最多的情况留到else中，例如：下面代码先判断是否是叶子节点，是否是有两棵子树的节点，把只有一棵子树的情况（这种情况又分为两种：左子树和右子树）留到了else中。

            //去找到targetNode的父节点
            BstNode parent = searchParent(value);
            //如果要删除的节点是叶子节点
            if (targetNode.left == null && targetNode.right == null) {
                //判断targetNode是父节点的左子节点，还是右子节点
                if (parent.left != null && parent.left.value == value) {
                    parent.left = null;
                } else if (parent.right != null && parent.right.value == value) {
                    parent.right = null;
                }
            } else if (targetNode.left != null && targetNode.right != null) { //删除有两棵子树的节点
                int minVal = delRightTreeMin(targetNode.right);
                targetNode.value = minVal;

            } else { //删除只有一棵子树的节点
                //如果要删除的节点有左子节点
                if (targetNode.left != null) {
                    if (parent != null) {
                        //如果targetNode是parent的左子节点
                        if (parent.left.value == value) {
                            parent.left = targetNode.left;
                        } else { // targetNode是parent的右子节点
                            parent.right = targetNode.left;
                        }
                    } else {
                        root = targetNode.left;
                    }
                } else { //如果要删除的节点有右子节点
                    if (parent != null) {
                        //如果targetNode是parent的左子节点
                        if (parent.left.value == value) {
                            parent.left = targetNode.right;
                        } else { // targetNode是parent的右子节点
                            parent.right = targetNode.right;
                        }
                    } else {
                        root = targetNode.right;
                    }
                }
            }
        }
    }

    //添加节点的方法
    public void add(BstNode node) {
        if (root == null) {
            root = node; //如果root为空直接让root指向node
        } else {
            root.add(node);
        }
    }

    //中序遍历
    public void infixOrder() {
        if (root != null) {
            root.infixOrder();
        } else {
            System.out.println("二叉排序树为空，不能遍历");
        }
    }
}

//创建BstNode节点
class BstNode {
    int value;
    BstNode left;
    BstNode right;

    public BstNode(int value) {
        this.value = value;
    }


    //查找要删除的节点

    /**
     * @param value 希望删除的节点
     * @return 如果找到返回该节点，否则返回null
     */
    public BstNode search(int value) {
        if (value == this.value) { //找到
            return this;
        } else if (value < this.value) {
            //如果左子节点为空
            if (this.left == null) {
                return null;
            }
            return this.left.search(value);
        } else { //如果查找的值不小于当前节点，向右子树递归查找
            if (this.right == null) {
                return null;
            }
            return this.right.search(value);
        }
    }

    //查找要删除节点的父节点
    public BstNode searchParent(int value) {
        //如果当前节点就是要删除的节点的父节点，就返回
        if ((this.left != null && this.left.value == value) || (this.right != null && this.right.value == value)) {
             return this;
        } else {
            //如果查找的值小于当前节点的值，并且当前节点的左子节点不为空
            if (value < this.value && this.left != null) {
                return this.left.searchParent(value); //向左子树递归查找
            } else if (value >= this.value && this.right != null) { //注意：此处是 >= ，是因为要与上面的方法相对应。遇到值相同的节点时放到右子树
                return this.right.searchParent(value); //向右子树递归查找
            } else {
                return null; //没有找到父节点
            }
        }
    }

    @Override
    public String toString() {
        return "BstNode{" +
                "value=" + value +
                '}';
    }

    //添加节点的方法
    //递归的形式添加节点，注意需要满足二叉排序树的要求
    public void add(BstNode node) {
//        if (node == null) {
//            return;
//        }
        if (this.value == node.value) {
            System.out.println("该二叉排序树中已有该节点，故插入节点失败");
            return;
        }

        //判断传入的节点的值，和当前子树的根节点的值的关系
        if (node.value < this.value) {
            //如果当前节点左子节点为null
            if (this.left == null) {
                this.left = node;
            } else {
                //递归的向左子树添加
                this.left.add(node);
            }
        } else { //添加的节点的值大于当前节点的值
            if (this.right == null) {
                this.right = node;
            } else {
                //递归向右子树添加
                this.right.add(node);
            }
        }
    }

    //中序遍历
    public void infixOrder() {
        if (this.left != null) {
            this.left.infixOrder();
        }
        System.out.println(this);
        if (this.right != null) {
            this.right.infixOrder();
        }
    }
}

