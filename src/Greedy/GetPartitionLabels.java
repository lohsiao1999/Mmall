package Greedy;

import java.util.ArrayList;
import java.util.List;
/*
* 划分字母区间 https://leetcode.cn/problems/partition-labels/
 */
public class GetPartitionLabels {

    public List<Integer> partitionLabels(String s) {
        int[] dir = new int[26];
        char[] chars = s.toCharArray();
        for(int i=0;i<chars.length;i++){
            dir[chars[i]-'a'] = i;
        }
        int left =0;
        int right = 0;
        List<Integer> res = new ArrayList<>();
        for(int i=0;i<chars.length;i++){
            right = Math.max(right,dir[chars[i]-'a']);
            if(i == right){
                res.add(right-left+1);
                left = i+1;
            }
        }
        return res;
    }
}
