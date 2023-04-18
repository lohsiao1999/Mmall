package Dynamic.Knapsack.complete;

/*
* 零钱兑换 https://leetcode.cn/problems/coin-change-ii/
* 完全背包问题，即填满容量为amount的背包有多少种方式
 */
public class coinsChange {

    public int change(int amount, int[] coins) {
        int[] dp = new int[amount+1];
        dp[0] = 1;
        for(int i=0;i<coins.length;i++){
            for(int j=coins[i];j<=amount;j++){
                dp[j] += dp[j-coins[i]];
            }
        }
        return dp[amount];
    }
}
