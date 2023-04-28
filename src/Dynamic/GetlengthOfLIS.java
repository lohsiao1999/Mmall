package Dynamic;

import java.util.Arrays;

/*
* 最长递增子序列 https://leetcode.cn/problems/longest-increasing-subsequence/
 */
public class GetlengthOfLIS {

    /*
    * 动态规划，分别计算以每个元素为结尾的数组的最长递增子序列
     */
    public int lengthOfLIS(int[] nums) {
        int[] dp = new int[nums.length];
        Arrays.fill(dp,1);
        int result =dp[0];
        for(int i=1;i<nums.length;i++){
            for(int j=0;j<i;j++){
                if(nums[i]>nums[j]) dp[i] = Math.max(dp[i],dp[j]+1);
            }
            result = Math.max(result,dp[i]);
        }
        return result;
    }

    /*
    * 二分查找，创建辅助数组tail，保证tail数组的单调递增
     */
    public int lengthOfLIS2(int[] nums) {
        int[] tail = new int[nums.length];
        int res =0;
        for (int num:nums){
            int left=0,right=res;
            while(left<right){
                int mid = left+ ((right-left)>>1);
                if(num > tail[mid]) left = mid+1;
                else right = mid;
            }
            //循环结束后，left指向当前元素在tail数组中的位置
            tail[left] = num;
            //此处有两种情况
            //1.tail数组不存在元素时，right==res，直接当当前元素加入tail数组
            //2。tail数组的元素都小于当前元素，当前元素加入tail数组，res长度加1
            if(right == res) res++;
        }
        return res;
    }
}
