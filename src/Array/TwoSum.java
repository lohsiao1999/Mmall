package Array;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/*
* 两数之和，https://leetcode.cn/problems/two-sum/?favorite=2cktkvj
* 利用哈希表存储target-x的索引，遍历数组时通过containsKey判断是否存在target-x
 */
public class TwoSum {

    public int[] twoSum(int[] nums, int target) {
        Map<Integer,Integer> map =new HashMap<>();
        for(int i = 0;i<nums.length;i++){
            if(map.containsKey(target-nums[i])){
                return new int[]{map.get(target-nums[i]), i};
            }
            map.put(nums[i],i);
        }
        return new int[0];
    }
}
