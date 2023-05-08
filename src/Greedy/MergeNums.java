package Greedy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
* 合并区间 https://leetcode.cn/problems/merge-intervals/
 */
public class MergeNums {

    /*
    * 合并区间，按照左区间升序排序，保证左边界尽可能小的情况下，右边界尽可能的大
     */
    public static void main(String[] args) {
        int[][] nums = {{2,3},{4,5},{6,7},{8,9},{1,10}};
        System.out.println(Arrays.toString(merge(nums)[0]));
    }

    public static int[][] merge(int[][] intervals) {
        Arrays.sort(intervals,((o1, o2) -> Integer.compare(o1[0],o2[0])));
        int left=intervals[0][0];
        int right = intervals[0][1];
        List<int[]> res = new ArrayList<>();
        for(int i=1;i<intervals.length;i++){
            if(right >= intervals[i][0]){
                right = Math.max(right,intervals[i][1]);
            }else{
                res.add(new int[]{left,right});
                left = intervals[i][0];
                right = intervals[i][1];
            }
        }
        res.add(new int[]{left,right});
        return res.toArray(new int[res.size()][]);
    }
}
