package com.复习;

/**
 * @author ShiWei
 * @date 2021/4/9 - 11:04
 */
public class BinaryTreePractice {

    public static void main(String[] args) {
        //创建一棵二叉树
        BinaryTree binaryTree1 = new BinaryTree();
        BinaryTreeNode node1 = new BinaryTreeNode(1);
        BinaryTreeNode node2 = new BinaryTreeNode(2);
        BinaryTreeNode node3 = new BinaryTreeNode(3);
        binaryTree1.setRoot(node1);
        node1.setLeft(node2);
        node1.setRight(node3);

        //再创建一棵二叉树
        BinaryTree binaryTree2 = new BinaryTree();
        BinaryTreeNode node11 = new BinaryTreeNode(1);
        BinaryTreeNode node21 = new BinaryTreeNode(2);
        BinaryTreeNode node31 = new BinaryTreeNode(3);
        binaryTree2.setRoot(node11);
        node11.setLeft(node21);
        node11.setRight(node31);
        //测试该二叉树的plusOne方法
//        binaryTree1.plusOne();
//        System.out.println(binaryTree1.getRoot());
        //测试二叉树的isSameTree方法
        boolean sameTree = binaryTree1.isSameTree(node11);
        System.out.println("sameTree = " + sameTree);
    }
}

/**
 * 二叉树类
 */
class BinaryTree {

    private BinaryTreeNode root;

    public BinaryTreeNode getRoot() {
        return root;
    }

    public void setRoot(BinaryTreeNode root) {
        this.root = root;
    }

    public BinaryTree() {
        this.root = null;
    }

    /**
     * 给二叉树的每个节点加1
     */
    public void plusOne() {
        root.setVal(root.getVal() + 1);
        if (root.getLeft() != null) {
            root.getLeft().plusOne();
        }
        if (root.getRight() != null) {
            root.getRight().plusOne();
        }
    }

    public boolean isSameTree(BinaryTreeNode rootTree) {
        //如果两棵树全为空，则一定相同
        if (root == null && rootTree == null) {
            return true;
        }
        //如果一棵为空，另一棵不为空，则不同
        if ((root == null && rootTree != null) || (root != null && rootTree == null)) {
            return false;
        }
        //如果两棵全不为空，但是值不同，则两棵树不同
        if (root.getVal() != rootTree.getVal()) {
            return false;
        }
        //剩下的情况就是全不为空，且值相同，则递归判断左右子树是否相同
        boolean sameLeftTree = false;
        if (root.getLeft() != null && rootTree.getLeft() != null) {
            sameLeftTree = root.getLeft().isSameTree(rootTree.getLeft());
        } else if (root.getLeft() == null && rootTree.getLeft() == null) {
            sameLeftTree = true;
        }
        boolean sameRightTree = false;
        if (root.getRight() != null && rootTree.getRight() != null) {
            sameRightTree = root.getRight().isSameTree(rootTree.getRight());
        } else if (root.getRight() == null && rootTree.getRight() == null) {
            sameRightTree = true;
        }
        return sameLeftTree && sameRightTree;
    }
}

/**
 * 节点类
 */
class BinaryTreeNode {

    private int val;
    private BinaryTreeNode left;
    private BinaryTreeNode right;

    public BinaryTreeNode(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public BinaryTreeNode getLeft() {
        return left;
    }

    public void setLeft(BinaryTreeNode left) {
        this.left = left;
    }

    public BinaryTreeNode getRight() {
        return right;
    }

    public void setRight(BinaryTreeNode right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return "BinaryTreeNode{" +
                "val=" + val +
                ", left=" + left +
                ", right=" + right +
                '}';
    }

    /**
     * 给以当前节点为根的树的每个节点加1
     */
    public void plusOne() {
        this.val = this.val + 1;
        if (this.left != null) {
            this.left.plusOne();
        }
        if (this.right != null) {
            this.right.plusOne();
        }
    }

    public boolean isSameTree(BinaryTreeNode left) {
        if (this == null && left == null) {
            return true;
        }
        if ((this == null && left != null) || (this != null && left == null)) {
            return false;
        }
        if (this.getVal() != left.getVal()) {
            return false;
        }
        boolean sameLeftTree = false;
        if (this.getLeft() != null && left.getLeft() != null) {
            sameLeftTree = this.getLeft().isSameTree(left.getLeft());
        } else if (this.getLeft() == null && left.getLeft() == null) {
            sameLeftTree = true;
        }
        boolean sameRightTree = false;
        if (this.getRight() != null && left.getRight() != null) {
            sameRightTree = this.getRight().isSameTree(left.getRight());
        } else if (this.getRight() == null && left.getRight() == null) {
            sameRightTree = true;
        }
        return sameLeftTree && sameRightTree;
    }
}
