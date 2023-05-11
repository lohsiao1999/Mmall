package backTracking;

import java.util.ArrayList;
import java.util.List;

/*
* 组合 https://leetcode.cn/problems/combinations/
 */
public class CombineNumber {

    public static void main(String[] args) {
        combine(4,2);
        for(List<Integer> list:res){
            System.out.println(list.toString());
        }
    }

    static List<List<Integer>> res = new ArrayList<>();
    public static List<List<Integer>> combine(int n, int k) {
        ArrayList<Integer> list = new ArrayList<>();
        dfs(list,k,n);
        return res;
    }

    public static void dfs(List<Integer> tmp,int k,int n){
        if(k == 0){
            ArrayList<Integer> list = new ArrayList<>(tmp);
            res.add(list);
            return;
        }
        for(int i=n;i>0;i--){
            tmp.add(i);
            dfs(tmp,k-1,i-1);
            tmp.remove(tmp.size()-1);
        }
    }

}
