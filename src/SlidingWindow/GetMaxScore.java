package SlidingWindow;

/*
* 可获得的最大点数 https://leetcode.cn/problems/maximum-points-you-can-obtain-from-cards/
* 几张卡牌 排成一行，每张卡牌都有一个对应的点数。点数由整数数组 cardPoints 给出。每次行动，你可以从行的开头或者末尾拿一张卡牌，最终你必须正好拿 k 张卡牌。
* 你的点数就是你拿到手中的所有卡牌的点数之和。给你一个整数数组 cardPoints 和整数 k，请你返回可以获得的最大点数。
* 解析：
*   因为只能从开头或者结尾获取牌，所以抽取了k张牌以后，剩下的n-k张牌一定是连续的
*   因此利用滑动窗口，计算n-k张牌中最小的和，用总数减去该和，即为能抽取的最大牌数
 */
public class GetMaxScore {

    public static void main(String[] args) {
        int[] nums = {1,2,3,4,5,6,1};
        System.out.println(maxScore(nums,3));
    }

    public static int maxScore(int[] cardPoints, int k) {
        int windowLength = cardPoints.length -k;
        int count = 0,res = 0;
        //先计算初始窗口的总和，并假设当前窗口总和即为最小值
        for(int i=0;i<windowLength;i++){
            count += cardPoints[i];
            res += cardPoints[i];
        }
        int min = count;
        //窗口向后移动并计算值
        for(int i= windowLength;i<cardPoints.length;i++){
            res += cardPoints[i];
            count = count + cardPoints[i] - cardPoints[i-windowLength];
            min = Math.min(count,min);
        }
        return res-min;
    }
}
