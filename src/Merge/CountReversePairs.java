package Merge;

import java.util.Arrays;

/*
* 数组中的逆序对
* 在数组中的两个数字，如果前面一个数字大于后面的数字，则这两个数字组成一个逆序对。输入一个数组，求出这个数组中的逆序对的总数。
* e.g. 输入：[7,5,6,4] 输出：5
* 采用归并排序计算逆序对
* 时间复杂度：O(N log N)，其中N为数组长度
* 空间复杂度：O(N)，辅助数组temp占用O(N)大小的额外空间
 */

public class CountReversePairs {

    public static void main(String[] args) {
        //int[] nums = {7,5,6,4,1,111};
//        System.out.println(reversePairs(nums));

        int[] nums = {4,2,3};
        temp = new int[nums.length];
        mergeSort1(nums,0, nums.length-1);
        System.out.println(Arrays.toString(nums));
    }

     static int[] temp;

    public static int reversePairs(int[] nums) {
        temp = new int[nums.length];
        return mergeSort(nums,0, nums.length-1);
    }

    public static int mergeSort(int[] nums,int left,int right){
        //递归中止条件，此时子数组的长度为1，终止划分
        if(left >= right) return 0;
        int mid = left + ((right - left) >> 1);
        int res = mergeSort(nums,left,mid) + mergeSort(nums,mid+1, right);

        //将nums闭区间[left,right]内的元素复制到辅助数组temp中
        for(int k = left;k<=right;k++){
            temp[k] = nums[k];
        }
        //合并，i指向左子数组的起始位置；j指向右子数组的起始位置
        int i=left,j = mid+1;
        for (int k = left;k<=right;k++){
            if(i == mid+1){
                //i == mid+1 意味着左子数组已经合并完成，因此只需对右子数组的元素进行合并
                nums[k] = temp[j++];
            }else if(j == right+1 || temp[i] <= temp[j]){
                /* j == right+1 意味着右子数组已经合并完成；
                *  temp[i] <= temp[j]表示在左子数组中 i指向的值 <= 右子数组中 j指向的值
                * 以上两种情况都需要合并左子数组
                */
                nums[k] = temp[i++];
            }else {
                //当前情况意味着左子数组和右子数组都没有合并完成，且temp[i] > temp[j]，所以需要合并右子数组
                nums[k] = temp[j++];
                // temp[i] > temp[j]，因此 temp[i] temp[j],temp[i+1] temp[j], ..., temp[mid] temp[j]均构成逆序对，数量为mid -i+1
                res += mid - i + 1;
            }
        }
        return res;
    }

    /*
    * 归并排序
     */
    public static void mergeSort1(int[] nums,int left,int right){
        //递归中止条件
        if(left >= right) return;
        int mid = left + ((right - left) >> 1);
        //排序左子数组
        mergeSort1(nums,left,mid);
        //排序右子数组
        mergeSort1(nums,mid+1, right);

        //将nums闭区间[left,right]内的元素复制到辅助数组temp中
        for(int k = left;k<=right;k++){
            temp[k] = nums[k];
        }
        //合并，i指向左子数组的起始位置；j指向右子数组的起始位置
        int i=left,j = mid+1;
        for (int k = left;k<=right;k++){
            if(j<=right && (i == mid+1 || temp[i] > temp[j])){
                //需要先判断j索引是否已经越过right
                //i == mid+1 意味着左子数组已经合并完成，
                //temp[i] > temp[j] 表示在左子数组中 i指向的值 > 右子数组中 j指向的值
                //以上两种情况都需要合并右子数组
                nums[k] = temp[j++];
            }else if(i<=mid && (j == right+1 || temp[i] <= temp[j])){
                /* 需要先判断i索引是否已经越过mid
                 * j == right+1 意味着右子数组已经合并完成；
                 *  temp[i] <= temp[j]表示在左子数组中 i指向的值 <= 右子数组中 j指向的值
                 * 以上两种情况都需要合并左子数组
                 */
                nums[k] = temp[i++];
            }
        }
    }



}
