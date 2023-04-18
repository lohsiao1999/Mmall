package Dynamic.Knapsack.zeroOne;



/*
* 最后一块石头重量2 https://leetcode.cn/problems/last-stone-weight-ii/
*
* 01背包问题
* 与Partition分割等和子集子集类似，将数组分为两个基本等和的子数组
* 其中一个子数组和为res[target]，另一边为sum-res[target]，两者的差值即为答案
 */
public class StoneWeightII {

    public int lastStoneWeightII(int[] stones) {
        int sum = 0;
        for(int i:stones) sum+=i;
        int target = sum >> 1;
        int[] res = new int[1501];
        for(int i=0;i<stones.length;i++){
            for(int j = target;j>=stones[i];j--){
                res[j] = Math.max(res[j],res[j-stones[i]]+stones[i]);
            }
        }
        return sum-res[target]-res[target];
    }
}
