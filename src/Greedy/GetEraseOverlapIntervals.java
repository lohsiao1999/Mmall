package Greedy;

import java.util.Arrays;

/*
* 无重叠区间 https://leetcode.cn/problems/non-overlapping-intervals/
 */
public class GetEraseOverlapIntervals {

    public int eraseOverlapIntervals(int[][] intervals) {
        Arrays.sort(intervals,(o1,o2)->Integer.compare(o1[1],o2[1]));
        int res=0;
        int right = intervals[0][1];
        for(int i=1;i<intervals.length;i++){
            if(right <= intervals[i][0]){
                right = intervals[i][1];
            }else{
                res++;
            }
        }
        return res;
    }
}
