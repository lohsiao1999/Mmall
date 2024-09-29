package Array;

/*
* 删除有序数组中的重复项 https://leetcode.cn/problems/remove-duplicates-from-sorted-array/
* 删除有序数组中的重复项 II https://leetcode.cn/problems/remove-duplicates-from-sorted-array-ii/
 */
public class RemoveDuplicatesNum {

    public int removeDuplicates(int[] nums) {
        int res=0;
        for(int num:nums){
            //res所指向位置为不重复数字数组的下一位
            //如果res 小于1，即为0，当前为起始位，所以nums[res++] = num主要作用为res索引数向前一位
            //当res 大于等于1时，只需要判断当前数字num是否res-1位置的数字是否相等，若不相等，则说明该数据不重复，res指向位置替换为该数同时res向前
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
