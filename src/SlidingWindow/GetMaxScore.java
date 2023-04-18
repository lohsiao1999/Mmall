package SlidingWindow;

/*
* 可获得的最大点数 https://leetcode.cn/problems/maximum-points-you-can-obtain-from-cards/
* 因为只能从开头或者结尾获取牌
* 因此抽取了k张牌以后，剩下的n-k张牌一定是连续的
* 因此利用滑动窗口，计算n-k张牌中最小的和，用总数减去该和，即为能抽取的最大牌数
 */
public class GetMaxScore {

    public static void main(String[] args) {
        int[] nums = {1,2,3,4,5,6,1};
        System.out.println(maxScore(nums,3));
    }

    public static int maxScore(int[] cardPoints, int k) {
        int windowLength = cardPoints.length -k;
        int count = 0,res = 0;
        for(int i=0;i<windowLength;i++){
            count += cardPoints[i];
            res += cardPoints[i];
        }
        int min = count;
        for(int i= windowLength;i<cardPoints.length;i++){
            res += cardPoints[i];
            count = count + cardPoints[i] - cardPoints[i-windowLength];
            min = Math.min(count,min);
        }
        return res-min;
    }
}
