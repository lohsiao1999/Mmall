package Merge;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * 查找和最小的 K 对数字 https://leetcode.cn/problems/find-k-pairs-with-smallest-sums/description/?envType=study-plan-v2&envId=top-interview-150
 * 多路归并
 */
public class FindkSmallestPairs {

    public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        Queue<int[]> queue = new PriorityQueue<>((a,b) -> nums1[a[0]] + nums2[a[1]] - nums1[b[0]] - nums2[b[1]]);
        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0; i < Math.min(nums1.length,k); i++) {
            queue.offer(new int[]{i,0});
        }
        while (res.size()<k && !queue.isEmpty()){
            int[] indexs = queue.poll();
            res.add(new ArrayList<Integer>(2){{
                add(nums1[indexs[0]]);
                add(nums2[indexs[1]]);
            }});

            if(indexs[1] + 1 < nums2.length) queue.offer(new int[]{indexs[0],indexs[1] + 1});
        }
        return res;
    }
}
