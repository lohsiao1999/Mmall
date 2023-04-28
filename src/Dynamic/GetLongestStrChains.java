package Dynamic;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/*
* 最长字符串链 https://leetcode.cn/problems/longest-string-chain/
 */
public class GetLongestStrChains {

    public int longestStrChain(String[] words) {
        int res =0;
        Map<String,Integer> dir = new HashMap<>();
        //将字符数组从小到大排序
        Arrays.sort(words,(o1,o2) -> o1.length()-o2.length());
        for(String str:words){
            int tmp = 0;
            for(int i=0;i<str.length();i++){
                StringBuilder builder = new StringBuilder(str);
                String s = builder.deleteCharAt(i).toString();
                //获取以当前字符串str为结尾的最长的子字符串链的长度
                tmp = Math.max(tmp,dir.getOrDefault(s,0));
            }
            //以当前字符串str为结尾的字符串链的长度加1
            dir.put(str,tmp+1);
            res = Math.max(res,tmp+1);
        }
        return res;
    }
}
