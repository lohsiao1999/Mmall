package SlidingWindow;

/*
* 摘水果 https://leetcode.cn/problems/maximum-fruits-harvested-after-at-most-k-steps/
* 因为移动步数有限，所以需要尽可能减少无效的移动，在x轴上获取尽可能多的水果。因此移动方式只能是先左后右或先右后左、或者一直向右或向左移动
* 无论是先右后左，还是先左后右，应该选择离起点startPos最近的边界作为优先移动的方向，这样重复的无效移动步数是最少的
* 重复的无效移动步数为Math.min(Math.abs(startPos-fruits[left][0]),Math.abs(startPos-fruits[right][0]))
 */
public class CountTotalFruits {

    public int maxTotalFruits(int[][] fruits, int startPos, int k) {
        int left=0,right=0,n=fruits.length,sum=0,res=0;
        //滑动窗口，固定右边界
        while(right<n){
            sum += fruits[right][1];
            //当步数超过k时，左边界向右移动
            while(left<=right && Math.min(Math.abs(startPos-fruits[left][0]),Math.abs(startPos-fruits[right][0])) + (fruits[right][0]-fruits[left][0]) > k){
                sum -= fruits[left][1];
                left++;
            }
            res = Math.max(sum,res);
            right++;
        }
        return res;
    }
}
