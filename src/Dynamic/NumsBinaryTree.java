package Dynamic;

/*
* 不同的二叉搜索树
* 对于有i个节点的二叉搜索树来说，存在i个以不同节点为根节点的树
* 设当前以j为节点，则j的左子树存在j-1个节点，j的右子树存在i-j个节点，左子树的某一个排列方式对应右子树所有的排列方式
* 当左子树存在dp[j-1]种排列方式，右子树存在dp[i-j]种排列方式时，则以j为根节点的二叉树共有dp[j-1]* dp[i-j]种排列方式
* 因此可以推出状态转移方程为：
* dp[i] += dp[j-1]* dp[i-j] , j∈[1,i]
 */
public class NumsBinaryTree {

    public int numTrees(int n) {
        int[] res = new int[n+1];
        res[0] =1;
        for(int i=1;i<=n;i++){
            for(int k = 1;k<=i;k++){
                res[i] += res[k-1] * res[i-k];
            }
        }
        return res[n];
    }
}
