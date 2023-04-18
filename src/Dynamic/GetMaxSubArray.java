package Dynamic;

/*
* 连续子数组的最大和 https://leetcode.cn/problems/lian-xu-zi-shu-zu-de-zui-da-he-lcof/submissions/
* 动态规划
 */
public class GetMaxSubArray {

    /*
    * 设最终结果为dp[i], 代表以元素nums[i]为结尾的连续子数组最大和，则状态转移方程如下
    * 1）当dp[i-1]>0时：dp[i] = dp[i-1] + nums[i]
    * 2）当dp[i-1]<=0时：说明dp[i-1]对于dp[i]的增大起负作用，因此 dp[i] = nums[i]
     */
    public int maxSubArray(int[] nums) {
        //令pre为dp[i-1]，因为dp[-1]为0，因此起始值为0
        int pre = 0,max = nums[0],cur;
        for(int num:nums){
            cur = num;
            //若pre<=0，取状态转移方程2，否则取1
            cur += Math.max(pre, 0);
            max = Math.max(max, cur);
            pre = cur;
        }
        return max;
    }
}
