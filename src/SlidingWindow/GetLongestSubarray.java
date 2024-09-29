package SlidingWindow;

import java.util.TreeMap;

/*
* 绝对差不超过限制的最长连续子数组 https://leetcode.cn/problems/longest-continuous-subarray-with-absolute-diff-less-than-or-equal-to-limit/
* 给你一个整数数组 nums ，和一个表示限制的整数 limit，请你返回最长连续子数组的长度，该子数组中的任意两个元素之间的绝对差必须小于或者等于 limit 。
* 如果不存在满足条件的子数组，则返回 0 。
* 滑动窗口，以right指针为终点，向左遍历找出离其最远的left指针
 */

public class GetLongestSubarray {

    public int longestSubarray(int[] nums, int limit) {
        TreeMap<Integer,Integer> map = new TreeMap<>();
        int left=0,right=0,res=0;
        while(right < nums.length){
            //因为可能存在重复的元素，因此需要用value记录元素的数量
            map.put(nums[right],map.getOrDefault(nums[right],0)+1);
            //当窗口内绝对差大于限制值时，向左移动左指针直到绝对差小于限制值
            while(map.lastKey()-map.firstKey() > limit){
                //left指针右移，map中当前元素数量减1
                map.put(nums[left],map.get(nums[left])-1);
                if(map.get(nums[left]) == 0){
                    //当前map中nums[left]数量为0时，移除
                    map.remove(nums[left]);
                }
                //left指针被动右移
                left++;
            }
            res = Math.max(res,right-left+1);
            //right指针主动向右移动
            right++;
        }
        return res;
    }
}
