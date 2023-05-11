package backTracking;

import java.util.ArrayList;
import java.util.List;

/*
* 组合总和 III https://leetcode.cn/problems/combination-sum-iii/submissions/
 */
public class GetCombinationSum3 {

    public static void main(String[] args) {
        combinationSum3(3,7);
        for(List<Integer> list:res){
            System.out.println(list.toString());
        }
    }

    static List<List<Integer>> res = new ArrayList<>();
    public static List<List<Integer>> combinationSum3(int k, int n) {
        List<Integer> list = new ArrayList<>();
        dfs(list,k,n,1);
        return res;
    }

    public static void dfs(List<Integer> tmp,int k,int n,int j){
        if(k==0 && n == 0){
            List<Integer> list = new ArrayList<>(tmp);
            res.add(list);
            return;
        }
        if(n==0 || k<=0) return;
        for(int i=j;i<=9;i++){
            tmp.add(i);
            dfs(tmp,k-1,n-i,i+1);
            tmp.remove(tmp.size()-1);
        }
    }
}
