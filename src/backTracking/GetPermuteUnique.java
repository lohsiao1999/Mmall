package backTracking;

import java.util.ArrayList;
import java.util.List;

/*
* 全排列 II https://leetcode.cn/problems/permutations-ii/
 */
public class GetPermuteUnique {

    public static void main(String[] args) {
        int[] nums= {2,2,1,1};
        permuteUnique(nums);
        for(List<Integer> list:res){
            System.out.println(list.toString());
        }
    }

    static List<List<Integer>> res = new ArrayList<>();
    public static List<List<Integer>> permuteUnique(int[] nums) {
        dfs(nums,0);
        return res;
    }

    public static void dfs(int[] nums,int k){
        if(k == nums.length){
            ArrayList<Integer> list = new ArrayList<>();
            for(int num:nums) list.add(num);
            res.add(list);
            return;
        }
        int[] n = new int[21];
        for(int i=k;i< nums.length;i++){
            //在当前层中，若nums[i]元素已经交换过，后续遇到相同元素时，直接跳过
            if(n[nums[i]+10] == 1) continue;
            n[nums[i]+10] = 1;
            swap(nums,k,i);
            dfs(nums, k+1);
            swap(nums,i,k);
        }
    }

    public static void swap(int[] nums,int l,int r){
        int n= nums[l];
        nums[l] = nums[r];
        nums[r] = n;
    }
}
