package backTracking;

import java.util.ArrayList;
import java.util.List;

/*
* 组合总和 https://leetcode.cn/problems/combination-sum/
 */
public class GetCombinationSum {

    public static void main(String[] args) {
        int[] candidates = {2,3,6,7};
        combinationSum(candidates,7);
        for(List<Integer> list:res){
            System.out.println(list.toString());
        }
    }

    static List<List<Integer>> res = new ArrayList<>();
    public static List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<Integer> list = new ArrayList<>();
        dfs(list,candidates,target,0);
        return res;
    }

    public static void dfs(List<Integer> tmp,int[] candidates,int target,int start){
        if(target == 0){
            List<Integer> list = new ArrayList<>(tmp);
            res.add(list);
            return;
        }
        for(int i=start;i<candidates.length;i++){
            int n = candidates[i];
            //如果下一个数取值已经大于target，不再递归
            if (target - n < 0) continue;
            tmp.add(n);
            dfs(tmp,candidates,target-n,i);
            tmp.remove(tmp.size()-1);
        }
    }
}
