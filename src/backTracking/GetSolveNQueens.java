package backTracking;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/*
* N皇后 https://leetcode.cn/problems/n-queens/
 */
public class GetSolveNQueens {

    List<List<String>> res = new ArrayList<>();
    String s = ".........";
    public List<List<String>> solveNQueens(int n) {
        int[] nums = new int[n];
        dfs(nums,0);
        return res;
    }

    public void dfs(int[] nums,int count){
        if(count == nums.length){
            List<String> list =new ArrayList<>();
            for(int num:nums){
                StringBuilder builder = new StringBuilder(s.substring(9 - nums.length));
                builder.setCharAt(num,'Q');
                list.add(builder.toString());
            }
            res.add(list);
        }
        //遍历循环第count+1行的所有位置
        for(int i=0;i<nums.length;i++){
            //当前位置i可以摆放皇后
            if(valid(nums,count,i)){
                nums[count] = i;
                dfs(nums,count+1);
                nums[count] = 0;
            }
        }
    }

    //当前行为count+1行
    public Boolean valid(int[] nums,int count,int index){
        for(int i=0;i<count;i++){
            //num为第i+1行皇后的摆放位置
            int num = nums[i];
            //当前行与第i+1行的差值，当前行为count+1，差值为(count+1)-(i+1) = count-i
            int tmp = count-i;
            /*
            * 3种情况下，当前摆放位置不合理
            * 1.当前位置index与第i+1行皇后的摆放位置num相等，此时index与num处于同一列
            * 2.当前位置index的左下角斜线上第i+1行已经摆放了皇后
            * 3.当前位置index的右下角斜线上第i+1行已经摆放了皇后
             */
            if(index == num ||index-tmp == num || index+tmp == num) return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

}
