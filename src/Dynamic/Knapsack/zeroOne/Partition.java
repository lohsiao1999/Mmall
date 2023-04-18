package Dynamic.Knapsack.zeroOne;

/*
* 分割等和子集 https://leetcode.cn/problems/partition-equal-subset-sum/
*
* 01背包问题，递推公式为dp[j] = max(dp[j], dp[j - weight[i]] + value[i])，其中j表示容量，dp[j]表示容量为j的背包能容纳的最大价值
* 优化为1维数组后，因为01背包中以物品为维度循环，且因为一个物品只能被选择一次，因此需要从 j=target反向向前循环
 */
public class Partition {

    public static void main(String[] args) {
        int[] nums = {1,5,11,5};
        System.out.println(canPartition(nums));
    }

    public static boolean canPartition(int[] nums) {
        int sum=0;
        for(int i:nums) sum += i;
        if(sum%2 ==1) return false;
        int[] dp = new int[10001];
        int target = sum>>1;
        for(int i=0;i<nums.length;i++){
            for(int j =target;j>=nums[i];j--){
                dp[j] = Math.max(dp[j],dp[j-nums[i]] + nums[i]);
            }
        }
        return target == dp[target];
    }
}
