package Array;

import java.util.Arrays;

/*
    * 分发糖果 https://leetcode.cn/problems/candy/
 */
public class GetCandyCount {
    public static void main(String[] args) {
        int[] nums = {1,0,2};
        candy(nums);
    }

    //思路：贪心算法，根据规则”相邻两个孩子评分更高的孩子会获得更多的糖果“拆分为两步
    //首先从左到右遍历，比较当前孩子i得评分与上一个孩子i-1的评分，若评分ratings[i] > ratings[i-1]，按照规则，孩子i获得的糖果数量应当是孩子i-1的糖果数量+1个
    //然后从右到左遍历，比较当前孩子i得评分与后一个孩子i+1的评分，若评分ratings[i] > ratings[i+1]，按照规则，孩子i获得的糖果数量应当是孩子i+1的糖果数量+1个
    //最后取相同索引下糖果数量的最大值
    //为什么需要两遍遍历，因为从左向右遍历时，我们假设第1个孩子应获得1个糖果，从而推算出后续的孩子应该获取几个糖果
    //但是，第1个孩子获得的糖果数量受第2个孩子所影响，第2个孩子又受第3个孩子影响。。。以此类推，所以需要再从右向左遍历一次，并假设最后一位孩子应获得的糖果数为1
    //最后，对于相同索引位置，应当取最大的糖果数，这是一种贪心算法的思想。从局部最优推到全局最优

    public static int candy(int[] ratings) {
        int[] left = new int[ratings.length];
        int[] right = new int[ratings.length];
        Arrays.fill(left,1);
        Arrays.fill(right,1);
        for(int i=1;i<ratings.length;i++){
            if(ratings[i] > ratings[i-1]) left[i] = left[i-1]+1;
        }
        int count = left[ratings.length-1];
        for(int j=ratings.length-2;j>=0;j--){
            if(ratings[j] >= ratings[j+1]) right[j] = right[j+1]+1;
            count += Math.max(left[j],right[j]);
        }
        return count;
    }
}
