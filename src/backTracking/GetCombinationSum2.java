package backTracking;

import java.util.ArrayList;
import java.util.List;

public class GetCombinationSum2 {

    static List<List<Integer>> res = new ArrayList<>();
    public static List<List<Integer>> combinationSum2(int[] candidates, int target) {
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
            if (target - n < 0) break;
            //同一层循环中，如当前数字与前一个数字相同，则一定会重复，因此需要跳过
            if (i > start && candidates[i] == candidates[i - 1]) continue;
            tmp.add(n);
            dfs(tmp,candidates,target-n,i+1);
            tmp.remove(tmp.size()-1);
        }
    }
}
