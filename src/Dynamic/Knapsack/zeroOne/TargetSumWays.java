package Dynamic.Knapsack.zeroOne;

/*
* 目标和 https://leetcode.cn/problems/target-sum/
* 01背包问题
* 该问题可看作是装满容量为j的背包有多少种方法，其中dp[j]表示装满容量为j的背包存在dp[j]种方法
* 因此递推公式如下：
* dp[j] += dp[j-nums[i]]
 */
public class TargetSumWays {

    public int findTargetSumWays(int[] nums, int target) {
        int sum = 0;
        for(int i:nums) sum+=i;
        //如数组和小于目标值的绝对值，此时无解
        if(sum < Math.abs(target)) return 0;
        //left -(sum-left) = target
        // left = (sum+target)/2
        // left表示正数的和，若 (sum+target)/2不为整数，说明该问题无解
        if((sum+target)%2 == 1) return 0;
        int bagSize = (sum+target) >> 1;
        int[] dp = new int[bagSize+1];
        //初始化
        dp[0] = 1;
        for(int i=0;i<nums.length;i++){
            for(int j=bagSize;j>=nums[i];j--){
                dp[j] += dp[j-nums[i]];
            }
        }
        return dp[bagSize];
    }
}
