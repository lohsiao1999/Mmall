package backTracking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
* 全排列 https://leetcode.cn/problems/permutations/
 */
public class GetPermute {

    List<List<Integer>> res = new ArrayList<>();
    List<Integer> tmp = new ArrayList<>();
    public List<List<Integer>> permute(int[] nums) {
        dfs(nums);
        return res;
    }

    public void dfs(int[] nums){
        if(tmp.size() == nums.length){
            res.add(new ArrayList<>(tmp));
            return;
        }
        for(int i=0;i<nums.length;i++){
            int n = nums[i];
            if(n == -11) continue;
            tmp.add(n);
            nums[i] = -11;
            dfs(nums);
            nums[i] = n;
            tmp.remove(tmp.size()-1);
        }
    }

    public void dfs1(int[] nums,int k){
        if(k == nums.length){
            ArrayList<Integer> list = new ArrayList<>();
            for(int num:nums) list.add(num);
            res.add(list);
            return;
        }
        for(int i=k;i<nums.length;i++){
            swap(nums,i,k);
            //固定k位置，对k+1及之后的元素进行遍历变换
            dfs1(nums,k+1);
            swap(nums,i,k);
        }
    }

    public void swap(int[] nums,int l,int r){
        int n = nums[l];
        nums[l] = nums[r];
        nums[r] = n;
    }
}
