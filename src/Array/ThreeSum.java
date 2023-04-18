package Array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ThreeSum {

    public List<List<Integer>> threeSum(int[] nums) {
        //排序数组
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        for(int k =0;k < nums.length-2;k++){
            if(nums[k] > 0) break;
            if(k > 0 && nums[k] == nums[k-1]) continue;
            int i = k+1;
            int j = nums.length-1;
            while(i<j){
                int sum = nums[k]+nums[i]+nums[j];
                if(sum < 0){
                    //因为数组有序，和小于0说明nums[i]的值需要增大才可能使和为0
                    while(i<j && nums[i] == nums[++i]);
                }else if(sum > 0){
                    //因为数组有序，和大于0说明nums[j]的值需要减小才可能使和为0
                    while(i<j && nums[j] == nums[--j]);
                }else{
                    result.add(Arrays.asList(nums[k],nums[i],nums[j]));
                    while(i<j && nums[i] == nums[++i]);
                    while(i<j && nums[j] == nums[--j]);
                }
            }
        }
        return result;
    }
}
