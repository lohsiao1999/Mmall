package Greedy;

/*
* 摆动序列 https://leetcode.cn/problems/wiggle-subsequence/
 */
public class GetWiggleMaxLength {

    //记录前一个差值pre，若小于等于0且当前差值cur大于0 或者 前一个差值pre大于等于0且当前差值cur小于0，则结果加一，表示当前最长摆动序列长度加1
    public int wiggleMaxLength(int[] nums) {
        int n = nums.length;
        if (n<=1) return n;
        int pre=0,cur=0,res =1;
        for(int i=0;i<n-1;i++){
            cur = nums[i+1]-nums[i];
            //此处其实有三种情况
            //1）前一个差值等于0时，则当前差值无论是大于0或者小于0，均属于摆动序列
            //2）前一个差值小于0时，则当前差值必须大于0
            //3）前一个差值大于0时，则当前差值必须小于0
            if((pre<=0 && cur>0) || (pre>=0 && cur<0)){
                res++;
                //当满足条件时，记录当前差值以判断下一个差值
                pre = cur;
            }
        }
        return res;
    }
}
