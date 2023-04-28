package Array;

//两个非重叠子数组的最大和 https://leetcode.cn/problems/maximum-sum-of-two-non-overlapping-subarrays/
public class GetMaxSumTwoNoOverlap {

    public static void main(String[] args) {
        int[] nums = {0,6,5,2,2,5,1,9,4};
        System.out.println(maxSumTwoNoOverlap(nums,1,2));
    }

    public static int maxSumTwoNoOverlap(int[] nums, int firstLen, int secondLen) {
        int n = nums.length;
        int[] sums = new int[n+1];
        //计算数组前缀和，注意边界
        for(int i=0;i<n;i++){
            sums[i+1] = sums[i]+nums[i];
        }
        int res = Integer.MIN_VALUE;
        int sumA=0,sumB=0;
        for(int i=firstLen+secondLen;i<=n;i++){
            //sumA 为长度为firstLen的子数组最大的和
            sumA = Math.max(sumA,sums[i-secondLen] - sums[i-firstLen-secondLen]);
            //sumB 为长度为secondLen的子数组最大的和
            //sumB和sumA最大的区别在于，在nums数组中，sumA计算长度为firstLen的子数组在长度为secondLen的子数组左边时的最大和
            //sumB计算长度为secondLen的子数组在长度为firstLen的子数组左边时的最大和
            sumB = Math.max(sumB,sums[i-firstLen] - sums[i-firstLen-secondLen]);
            res = Math.max(res,Math.max(sumA+sums[i]-sums[i-secondLen],sumB+sums[i]-sums[i-firstLen]));
        }
        return res;
    }

}
