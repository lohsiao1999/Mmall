package SlidingWindow;

import java.util.HashMap;
import java.util.Map;

/*
* 最小覆盖子串 https://leetcode.cn/problems/minimum-window-substring/
 */
public class GetMinSubString {

    public String minWindow(String s, String t) {
        Map<Character,Integer> need = new HashMap<>();
        Map<Character,Integer> window = new HashMap<>();
        char[] source = s.toCharArray();
        char[] target = t.toCharArray();
        int left=0,right=0,valid=0,start=0,len=Integer.MAX_VALUE;
        for(char c:target) need.put(c,need.getOrDefault(c,0)+1);
        while(right < source.length){
            char tmp = source[right];
            right++;
            if(need.containsKey(tmp)){
                window.put(tmp,window.getOrDefault(tmp,0)+1);
                if(window.get(tmp).equals(need.get(tmp))){
                    valid++;
                }
            }
            while(valid == need.size()){
                if(right-left < len){
                    start =left;
                    len = right-left;
                }

                char d = source[left];
                left++;
                if(need.containsKey(d)){
                    window.put(d,window.get(d)-1);
                    if(window.get(d).compareTo(need.get(d))<0){
                        valid--;
                    }
                }
            }
        }
        return len==Integer.MAX_VALUE ? "" : s.substring(start,start+len);
    }
}
