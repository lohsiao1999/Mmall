package BinaryTree;

/*
* 将二叉搜索树转化为循环双向链表
* 解法：二叉搜索树的中序遍历有序递增，利用该特性构建链表
* void dfs(TreeNode node){
        //遍历左子节点
        dfs(node.left);
        //根节点打印
        System.out.println(node.val);
        //遍历右子节点
        dfs(node.right);
    }
 */

public class TreeToDoublyList {

    TreeNode pre,head;

    public TreeNode solution(TreeNode root){
        if(root == null) return root;
        dfs(root);
        //递归结束后pre指向最右子节点，而head指向最左子节点
        head.left = pre;
        pre.right = head;
        return head;
    }

    //中序遍历起点为最左子节点
    void dfs(TreeNode node){
        //递归终止条件
        if(node == null) return;
        //遍历左子节点
        dfs(node.left);
        if(pre == null){
            //前置节点为空，说明当前节点为最左子节点，即为头节点
            head = node;
        } else{
            //前置节点指向当前节点
            pre.right = node;
        }
        node.left = pre;
        //将pre指向当前节点
        pre = node;
        //遍历右子节点
        dfs(node.right);
    }
}
