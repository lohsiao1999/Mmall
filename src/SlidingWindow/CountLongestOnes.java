package SlidingWindow;

/*
* 最大连续1的个数 III https://leetcode.cn/problems/max-consecutive-ones-iii/
* 给定一个二进制数组 nums 和一个整数 k，如果可以翻转最多 k 个 0 ，则返回 数组中连续 1 的最大个数 。
* 解法：滑动窗口，窗口内最多可容纳k个0，计算出窗口的最大值
 */
public class CountLongestOnes {

    public int longestOnes(int[] nums, int k) {
        int zero = 0;
        int left =0, right=0,res = 0;
        while(right < nums.length){
            if(nums[right] == 0){
                zero++;
            }
            //当0的数量超过k时
            while(zero > k){
                //左指针向前移动，遇见0则将zero减一
                if(nums[left++] == 0){
                    zero--;
                }
            }
            //算出窗口长度
            res = Math.max(res,right-left+1);
            right++;
        }
        return res;
    }
}
