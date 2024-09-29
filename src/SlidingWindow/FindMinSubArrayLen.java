package SlidingWindow;

/**
 * 长度最小子数组 https://leetcode.cn/problems/minimum-size-subarray-sum/description/?envType=study-plan-v2&envId=top-interview-150
 */
public class FindMinSubArrayLen {

    public int minSubArrayLen(int target, int[] nums) {
        int res =Integer.MAX_VALUE;
        int left=0;
        int right=0;
        int sum=0;
        while(right<nums.length){
            sum += nums[right];
            while (sum >= target && left<=right){
                res = Math.min(res,right-left+1);
                sum -= nums[left];
                left++;
            }
            right++;
        }
        return res;
    }
}
