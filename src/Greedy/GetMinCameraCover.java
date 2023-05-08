package Greedy;

/*
* 监控二叉树 https://leetcode.cn/problems/binary-tree-cameras/
 */
public class GetMinCameraCover {

    /*
    * 0 无覆盖
    * 1 有摄像头
    * 2 有覆盖
     */
    int res=0;
    public int minCameraCover(TreeNode root) {
        //根节点无摄像头覆盖时
        if(dfs(root) == 0) res++;
        return res;
    }

    public int dfs(TreeNode node){
        //因为需要计算最少的摄像头数量，因此不应当在叶子节点设置摄像头
        //递归至叶子节点，返回2
        if(node == null) return 2;

        int left = dfs(node.left);
        int right = dfs(node.right);

        //当左子节点和右子节点有摄像头覆盖时，当前节点无覆盖
        if(left == 2 && right == 2) return 0;

        //当左子节点或右子节点无摄像头覆盖时，当前节点应当设立摄像头
        if(left == 0 || right == 0){
            res++;
            return 1;
        }
        //当左子节点或右子节点有摄像头时，当前节点有摄像头覆盖，返回2
        if (left == 1 || right == 1) return 2;
        return -1;
    }
}

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode() {}
    TreeNode(int val) { this.val = val; }
    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}
