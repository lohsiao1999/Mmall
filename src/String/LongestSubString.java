package String;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/*
* 无重复字符的最长子字符串
* 滑动窗口
* 即左右指针，右指针向右移动，当其指向的char==左指针指向的char时，左指针向右移动，循环往复直到右指针指向字符串末尾
 */
public class LongestSubString {

    public static void main(String[] args) {
        System.out.println(lengthOfLongestSubstring3(" "));
    }

    public static int lengthOfLongestSubstring(String s) {
        Set<Character> set = new HashSet<>();
        int res=0,left = 0,right = 0;
        int length = s.length();
        while (right < length || left < length){
            if(left >0 ) set.remove(s.charAt(left-1));
            while (right < length && !set.contains(s.charAt(right))){
                set.add(s.charAt(right++));
            }
            res = Math.max(res,right-left);
            left++;
        }
        return res;
    }

    /*
    * 针对双指针方法进行优化
    * 方法1中两个循环2会存在多次无效循环
    * 如acddgre字符串中，当右指针指向第二个d时，此时左指针仍然指向a，需要再循环4次才能指向第二个d
    * map保存char元素的索引，当存在重复char时直接通过map获取重复元素索引的下一个
     */
    public static int lengthOfLongestSubstring2(String s) {
        Map<Character,Integer> map = new HashMap<>();
        //start为左指针，end为右指针
        int res=0,start = 0;
        for(int end = 0;end<s.length();end++){
            char c = s.charAt(end);
            //重复字符，start直接跳转至重复元素索引+1的位置
            if(map.containsKey(c)){
                start = Math.max(map.get(c)+1, start);
            }
            res = Math.max(end - start +1,res);
            map.put(c,end);
        }
        return res;
    }

    public static int lengthOfLongestSubstring3(String s) {
       int[] arr = new int[128];
       int i = 0,res =0;
        for(int j = 0;j<s.length();j++){
           char c = s.charAt(j);
           if(arr[c] > 0 ){
               i = Math.max(i,arr[c]);
           }
           res = Math.max(j - i +1,res);
           arr[c] = j+1;
       }
        return res;
    }
}
