package backTracking;

import java.util.ArrayList;
import java.util.List;

/*
* 子集 https://leetcode.cn/problems/subsets/submissions/
 */
public class GetSubsets {

    List<List<Integer>> res = new ArrayList<>();
    public List<List<Integer>> subsets(int[] nums) {
        res.add(new ArrayList<>());
        dfs(nums,new ArrayList<>(),0);
        return res;
    }

    public void dfs(int[] nums,List<Integer> tmp ,int index){
        List<Integer> list = new ArrayList<>(tmp);
        res.add(list);
        for(int i = index;i<nums.length;i++){
            tmp.add(nums[i]);
            dfs(nums,tmp,i+1);
            tmp.remove(tmp.size()-1);
        }
    }

}
