package BinaryTree;

/*
* 二叉树镜像 https://leetcode.cn/problems/er-cha-shu-de-jing-xiang-lcof/
* 前序遍历，交换左右子节点
 */
public class OutputMirrorTree {

    /*
    * 递归
     */
    public TreeNode mirrorTree(TreeNode root) {
        if(root == null) return null;
        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;
        mirrorTree(root.left);
        mirrorTree(root.right);
        return root;
    }
}
