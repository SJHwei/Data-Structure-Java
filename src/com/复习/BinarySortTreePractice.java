package com.复习;

/**
 * @author ShiWei
 * @date 2021/4/11 - 9:32
 */
public class BinarySortTreePractice {

    public static void main(String[] args) {

        int arr[] = {7, 3, 10, 12, 5, 1, 9, 2};

        BinarySortTree binarySortTree = new BinarySortTree();

        for (int i = 0; i < arr.length; i++) {
            binarySortTree.add(arr[i]);
        }

        System.out.println("中序遍历二叉排序树：");
        binarySortTree.infixOrder();

//        BSTNode targetNode = binarySortTree.findTargetNode(4);
//        BSTNode parentNode = binarySortTree.findParentNode(4);
//        System.out.println("targetNode = " + targetNode);
//        System.out.println("parentNode = " + parentNode);
        System.out.println();
        binarySortTree.deleteNode(10);
        System.out.println(binarySortTree.getRoot().right);
        System.out.println("删除节点后中序遍历二叉排序树：");
        binarySortTree.infixOrder();
    }
}

/**
 * 二叉排序树类，以根节点代表代表该二叉排序树
 */
class BinarySortTree {

    //用根节点代表该二叉排序树
    private BSTNode root;

    /**
     * 返回根节点
     *
     * @return
     */
    public BSTNode getRoot() {
        return root;
    }

    /**
     * 往该二叉排序树中添加节点
     *
     * @param value 要添加的节点的值
     */
    public void add(int value) {
        BSTNode node = new BSTNode(value);
        if (root == null) {
            root = node;
        } else {
            root.add(node);
        }
    }

    /**
     * 中序遍历该二叉排序树
     */
    public void infixOrder() {
        if (root == null) {
            System.out.println("该二叉排序树为空，无法遍历");
        } else {
            if (root.left != null) {
                root.left.infixOrder();
            }
            System.out.println(root);
            if (root.right != null) {
                root.right.infixOrder();
            }
        }
    }

    /**
     * 删除值为value的节点
     *
     * @param value
     */
    public void deleteNode(int value) {
        //1.查找要删除的节点
        BSTNode targetNode = findTargetNode(value);
        //如果要删除的节点不存在，则返回
        if (targetNode == null) {
            return;
        }
        //2.查找要删除的节点的父节点
        //注意：其实不必要单独处理根节点。因为如果要删除的节点的父节点为空，则查找到的节点就一定是根节点，处理即可；如果父节点不为空，则一定不是父节点。
        BSTNode parentNode = findParentNode(value);
        //3.处理根节点情况
        if (targetNode == root) {
            if (root.left == null && root.right == null) { //要删除的根节点没有子树
                root = null;
                return;
            } else if (root.left != null && root.right != null) { //要删除的根节点的两棵子树全不为空
                //找到右子树的最小值节点
                BSTNode temp = findAndDeleteRightMinNode(root.right);
                root.value = temp.value;
            } else { //要删除的根节点只有一棵子树
                if (root.left != null) {
                    root = root.left;
                } else {
                    root = root.right;
                }
            }
            return;
            //4.根据不同情况删除节点，此时要删除的节点一定不是根节点，而且当根节点存在时，父节点也一定存在
        } else {
            //第一种情况：要删除的节点是叶子节点
            if (targetNode.left == null && targetNode.right == null) {
                if (parentNode.left == targetNode) {
                    parentNode.left = null;
                } else {
                    parentNode.right = null;
                }
                //第三种情况：要删除的节点的两棵子树全不为空
                //此处先判断第三种情况，因为第二种情况比较复杂，所以放到else中处理
            } else if (targetNode.left != null && targetNode.right != null) {
                //找到右子树的最小值节点
                BSTNode temp = findAndDeleteRightMinNode(targetNode.right);
                targetNode.value = temp.value;
                //第二种情况：要删除的节点只有一棵子树
            } else {
                if (targetNode.left != null) {
                    if (parentNode.left == targetNode) {
                        parentNode.left = targetNode.left;
                    } else {
                        parentNode.right = targetNode.left;
                    }
                } else {
                    if (parentNode.left == targetNode) {
                        parentNode.left = targetNode.right;
                    } else {
                        parentNode.right = targetNode.right;
                    }
                }
            }
        }

    }

    /**
     * 找到以right为根的二叉排序树的最小节点
     *
     * @param right 根节点
     * @return
     */
    private BSTNode findAndDeleteRightMinNode(BSTNode right) {
        //注意：不能之间在right节点上处理，下面的处理right=right.left会影响原来的树，所以需要重新声明一个引用newNode，当这个引用变化时，不会影响right
        BSTNode newNode = right;
        //循环的查找左节点，就会找到最小节点
        while (newNode.left != null) {
            newNode = newNode.left;
        }
        //删除最小
        deleteNode(newNode.value);
        return newNode;
    }

    /**
     * 找到要删除的节点
     *
     * @param value
     * @return
     */
    public BSTNode findTargetNode(int value) {
        if (root.value == value) {
            return root;
        }
        return root.search(value);
    }

    /**
     * 找到要删除的节点的父节点
     *
     * @param value
     * @return
     */
    public BSTNode findParentNode(int value) {
        if (root.value == value) {
            return null;
        }
        return root.parentSearch(value);
    }
}


/**
 * BSTNode节点类
 */
class BSTNode {

    int value;
    BSTNode left;
    BSTNode right;

    public BSTNode(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "BSTNode{" +
                "value=" + value +
                '}';
    }


    public void add(BSTNode node) {
        if (this.value == node.value) {
            System.out.println("该二叉树中存在该节点，插入失败");
            return;
        }
        if (node.value < this.value) {
            if (this.left == null) {
                this.left = node;
            } else {
                this.left.add(node);
            }
        } else {
            if (this.right == null) {
                this.right = node;
            } else {
                this.right.add(node);
            }
        }
    }

    public void infixOrder() {
        if (this.left != null) {
            this.left.infixOrder();
        }
        System.out.println(this);
        if (this.right != null) {
            this.right.infixOrder();
        }
    }

    public BSTNode search(int value) {
        //找到
        if (this.value == value) {
            return this;
        }
        //如果要查找的节点的值小于当前节点的值，则向左子树递归查找
        if (value < this.value) {
            if (this.left == null) {
                System.out.println("该二叉排序树中没有该节点");
                return null;
            } else {
                return this.left.search(value);
            }
            //如果要查找的节点的值大于当前节点的值，则向右子树递归查找
        } else {
            if (this.right == null) {
                System.out.println("该二叉排序树中没有该节点");
                return null;
            } else {
                return this.right.search(value);
            }
        }
    }

    public BSTNode parentSearch(int value) {
        if (this.value > value) {
            if (this.left != null && this.left.value == value) {
                return this;
            } else if (this.left == null) {
                System.out.println("要查找的节点不存在，故该父节点也不存在");
                return null;
            } else {
                return this.left.parentSearch(value);
            }
        } else {
            if (this.right != null && this.right.value == value) {
                return this;
            } else if (this.right == null) {
                System.out.println("要查找的节点不存在，故该父节点也不存在");
                return null;
            } else {
                return this.right.parentSearch(value);
            }
        }
    }
}
