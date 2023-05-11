package backTracking;

import java.util.ArrayList;
import java.util.List;

/*
* 递增子序列 https://leetcode.cn/problems/non-decreasing-subsequences/
 */
public class GetSubsequences {

    public static void main(String[] args) {
        int[] nums = {1,2,3,1,1,1,1,1};
        findSubsequences(nums);
        for(List<Integer> tmp:res){
            System.out.println(tmp.toString());
        }
    }

    static List<List<Integer>> res = new ArrayList<>();
    public static List<List<Integer>> findSubsequences(int[] nums) {
        ArrayList<Integer> list = new ArrayList<>();
        dfs(nums,list,0);
        return res;
    }

    public static void dfs(int[] nums,List<Integer> tmp,int start){
        if(tmp.size()>1){
            ArrayList<Integer> list = new ArrayList<>(tmp);
            res.add(list);
        }
        int[] dir = new int[201];
        for(int i=start;i< nums.length;i++){
            if((!tmp.isEmpty() && nums[i]< tmp.get(tmp.size()-1)) || dir[nums[i]+100]==1) continue;
            //以当前值为起点向后查找下一个值
            tmp.add(nums[i]);
            //将当前层中的nums[i]标记为已存在，后续循环中不能再使用该元素
            dir[nums[i]+100] = 1;
            dfs(nums,tmp,i+1);
            tmp.remove(tmp.size()-1);
        }
    }
}
