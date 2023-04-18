package Merge;

import java.util.HashMap;
import java.util.Map;

public class BuildBinaryTree {


    int[] preorder;
    Map<Integer,Integer> map =new HashMap<>();
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        this.preorder = preorder;
        for(int i = 0;i<inorder.length;i++){
            map.put(inorder[i],i);
        }
        return helper(0,0,inorder.length-1);
    }

    /*
    * root为根节点在先序遍历数组中的索引
    * left为二叉树左边界在中序遍历数组中的索引
    * right为二叉树右边界在中序遍历数组中的索引
     */
    public TreeNode helper(int root,int left,int right){
        if(left > right) return null;
        TreeNode node = new TreeNode(preorder[root]);
        //i为当前节点即根节点在中序遍历中的索引
        int i = map.get(preorder[root]);
        //在先序遍历中，当前节点的左子树根节点为root+1
        //在中序遍历中，当前节点的左子树左边界为left
        //在中序遍历中，当前节点的左子树右边界即为当前节点的左一位，为i-1
        node.left = helper(root+1,left,i-1);
        //在先序遍历中，当前节点的右子树根节点为root+1再加上左子树的长度i-left，即为root +(i-left)+1
        //在中序遍历中，当前节点的右子树左边界为当前节点的右一位
        //在中序遍历中，当前节点的右子树右边界为right
        node.right = helper(root +(i-left)+1,i+1,right);
        return node;
    }

}

class TreeNode {
     int val;
     TreeNode left;
     TreeNode right;
     TreeNode(int x) { val = x; }
 }
