package Array;

/*
* 删除有序数组中的重复项 https://leetcode.cn/problems/remove-duplicates-from-sorted-array/
* 删除有序数组中的重复项 II https://leetcode.cn/problems/remove-duplicates-from-sorted-array-ii/
 */
public class RemoveDuplicatesNum {

    public int removeDuplicates(int[] nums) {
        int res=0;
        for(int num:nums){
            if(res<1 || nums[res-1] != num) nums[res++] = num;
        }
        return res;
    }

    public int removeDuplicates2(int[] nums) {
        int res=0;
        for(int num:nums){
            if(res<2 || nums[res-2] != num) nums[res++] = num;
        }
        return res;
    }

}
