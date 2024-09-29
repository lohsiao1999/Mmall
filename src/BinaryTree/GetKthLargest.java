package BinaryTree;

/*
* 二叉搜索树的第k大节点 https://leetcode.cn/problems/er-cha-sou-suo-shu-de-di-kda-jie-dian-lcof/
* 中序遍历的倒叙为 右根左
* void dfs(TreeNode node){
        //遍历右子节点
        dfs(node.left);
        //根节点打印
        System.out.println(node.val);
        //遍历左子节点
        dfs(node.right);
    }
 */
public class GetKthLargest {

    int res,k;
    public int kthLargest(TreeNode root, int k) {
        this.k = k;
        dfs(root);
        return res;
    }

    /**
     * 二叉搜索树，中序遍历为单调递增
     * 因此使用倒序的中序遍历，此时为单调递减，可轻松找出第K大的数据
     */
    public void dfs(TreeNode node){
        if(node == null) return;
        dfs(node.right);
        if(k == 0) return;
        if(--k == 0) res = node.val;
        dfs(node.left);
    }
}
