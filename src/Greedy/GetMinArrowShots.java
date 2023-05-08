package Greedy;

import java.util.Arrays;

/*
* 用最少数量的箭引爆气球 https://leetcode.cn/problems/minimum-number-of-arrows-to-burst-balloons/
 */
public class GetMinArrowShots {

    public static void main(String[] args) {
        int[][] nums = {{-2147483646,-2147483645},{2147483646,2147483647}};
        System.out.println(findMinArrowShots(nums));
    }

    /*
    * 按照右边界升序排序，当第i个气球左边界大于当前右边界right时，更新右边界的值为第i个气球的右边界
     */
    public static int findMinArrowShots(int[][] points) {
        Arrays.sort(points,((o1, o2) -> Integer.compare(o1[1],o2[1])));
        int res =1;
        int right=points[0][1];
        for(int i=1;i<points.length;i++){
            if(right >= points[i][0]) continue;
            res++;
            right = points[i][1];
        }
        return res;
    }
}
