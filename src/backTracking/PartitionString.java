package backTracking;

import java.util.ArrayList;
import java.util.List;

/*
* 分割回文串 https://leetcode.cn/problems/palindrome-partitioning/
 */
public class PartitionString {

    List<List<String>> res = new ArrayList<>();
    public List<List<String>> partition(String s) {
        List<String> list = new ArrayList<>();
        dfs(s,list,0);
        return res;
    }

    private void dfs(String s,List<String> tmp,int start){
        if(start>=s.length()){
            List<String> list = new ArrayList<>(tmp);
            res.add(list);
            return;
        }
        //固定左边界，不断扩大右边界，判断是否回文串
        for(int i=start;i<s.length();i++){
            //若s.substring(start,i+1)的字符串不为回文串，跳过本次循环
            if(!isPalindrome(start,i,s)) continue;

            tmp.add(s.substring(start,i+1));
            dfs(s,tmp,i+1);
            tmp.remove(tmp.size()-1);
        }
    }

    private Boolean isPalindrome(int left,int right,String s){
        for(int i=left,j=right;i<j;i++,j--){
            if(s.charAt(i) != s.charAt(j)){
                return Boolean.FALSE;
            }
        }
        return Boolean.TRUE;
    }
}
