package String;

/*
* 最长回文子串 https://leetcode.cn/problems/longest-palindromic-substring/
 */
public class Palindrome {

    /*
    * 方法1：中心扩展法，选定 一个中心，向两边扩展并对比
    * 时间复杂度O(N^2)
    * 空间复杂度O(1)
     */
    public String longestPalindrome(String s) {
        if(s.length() == 1) return s;
        //记录最长回文串的起点和终点索引
        int start = 0,end = 0;
        for(int i=0;i<s.length();i++){
            //选定索引i位置的字符作为中心点，向两边拓展
            int len1 = helper(s,i,i);
            //选定索引i和索引i+1中间位置作为中心点
            int len2 = helper(s,i,i+1);
            int len = Math.max(len1,len2);
            if(len > end-start){
                start = i - (len-1)/2;
                end = i + (len >> 1);
            }
        }
        return s.substring(start,end+1);
    }

    public int helper(String s,int left,int right){
        int l = left,r = right;
        while(l >= 0 && r < s.length() && s.charAt(l) == s.charAt(r)){
            l--;
            r++;
        }
        //循环结束后，l指向回文串起始位置的前一位，r指向回文串结束位置的后一位，因此回文串长度为r-l-2+1 = r-l-1
        return r-l-1;
    }
}
