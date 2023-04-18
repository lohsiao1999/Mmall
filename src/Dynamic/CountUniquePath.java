package Dynamic;

/*
 * 不同路径 https://leetcode.cn/problems/unique-paths/
 * 动态规划，状态转移方程如下：
 * dp[i,j] = dp[i-1,j]+dp[i,j-1]
 * 初始化：由题目易知dp[0,n]和dp[n,0]均为1
 */
public class CountUniquePath {

    public int uniquePaths(int m, int n) {
        int[] res = new int[n+1];
        for(int i=1;i<=n;i++) res[i] = 1;
        for(int i =1;i<m;i++){
            for(int j = 1;j<=n;j++){
                res[j] = res[j] + res[j-1];
            }
        }
        return res[n];
    }
}
