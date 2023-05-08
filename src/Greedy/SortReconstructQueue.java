package Greedy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
* 根据身高重建队列 https://leetcode.cn/problems/queue-reconstruction-by-height/
 */
public class SortReconstructQueue {

    //首先将数组按照身高排降序，序号排升序
    //根据题目要求，身高为i的人，前面需要存在people[i][1]个身高相同或更高的人
    //数组排序后，对于第i个人而言，无论他排在哪个位置，对于前面i-1个人的排队顺序没有影响，因为第i个人比前面都要矮
    //所以我们只需要遍历数组，将数组中的元素插入序号的位置，这样就能保证第i个人前面存在people[i][1]个身高相同或更高的人
    public int[][] reconstructQueue(int[][] people) {
        //将数组按照身高排降序，序号排升序
        Arrays.sort(people,((o1, o2) -> o1[0] != o2[0] ? o2[0]-o1[0] : o1[1]-o2[1]));
        List<int[]> list = new ArrayList<>();
        for(int[] nums:people){
            list.add(nums[1],nums);
        }
        return list.toArray(new int[list.size()][]);
    }
}
