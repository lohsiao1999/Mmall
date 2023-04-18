package SlidingWindow;

/*
* 最大连续1的个数 III https://leetcode.cn/problems/max-consecutive-ones-iii/
* 滑动窗口，窗口内最多可容纳k个0，计算出窗口的最大值
 */
public class CountLongestOnes {

    public int longestOnes(int[] nums, int k) {
        int zero = 0;
        int left =0, right=0,res = 0;
        while(right < nums.length){
            if(nums[right] == 0){
                zero++;
            }
            while(zero > k){
                if(nums[left++] == 0){
                    zero--;
                }
            }
            res = Math.max(res,right-left+1);
            right++;
        }
        return res;
    }
}
