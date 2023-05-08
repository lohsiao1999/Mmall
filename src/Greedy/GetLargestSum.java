package Greedy;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Stream;

/*
* K 次取反后最大化的数组和 https://leetcode.cn/problems/maximize-sum-of-array-after-k-negations/
 */
public class GetLargestSum {

    public static void main(String[] args) {
        int[] nums = {-2,5,0,2,-2};
        System.out.println(largestSumAfterKNegations(nums,3));
    }

    public static int largestSumAfterKNegations(int[] nums, int k) {
//        merge(nums,0, nums.length-1);
//        int res = 0;
//        for(int i=nums.length-1;i>=0;i--){
//            if(nums[i]<0 && k > 0){
//                nums[i] *= -1;
//                k--;
//            }
//            res += nums[i];
//        }
//        return k>0 && k%2==1 ? res-(nums[0]<<1) : res;
        Arrays.sort(nums);
        int res = 0,min = Integer.MAX_VALUE;
        for(int i=0;i<nums.length;i++){
            if(nums[i]<0 && k > 0){
                nums[i] *= -1;
                k--;
            }
            res += nums[i];
            min = Math.min(nums[i],min);
        }
        return k%2==1 ? res-(min<<1) : res;
    }

    public static void merge(int[] nums,int left,int right){
        if(left>=right) return;
        int mid = left+((right-left)>>1);
        merge(nums,left,mid);
        merge(nums,mid+1,right);
        int l=left,r = mid+1;
        int[] temp = new int[nums.length];
        for(int i=left;i<=right;i++){
            temp[i]=nums[i];
        }
        for(int i=left;i<=right;i++){
            if(r<=right && (l>mid || Math.abs(temp[l])>Math.abs(temp[r]))){
                nums[i] = temp[r++];
            }else if(l<=mid && (r>right || Math.abs(temp[r])>=Math.abs(temp[l]))){
                nums[i] = temp[l++];
            }
        }
    }
}
