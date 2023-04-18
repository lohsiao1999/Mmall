package String;

import java.util.*;

/*
* 字母异位词分组 https://leetcode.cn/problems/group-anagrams/
 */
public class StringGroupAnagrams {

    //利用哈希表以排序过后的字符串作为key
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String,List<String>> map = new HashMap<>();
        for(String str:strs){
            char[] arr = str.toCharArray();
            Arrays.sort(arr);
            String key = new String(arr);
            List<String> list = map.getOrDefault(key,new ArrayList<>());
            list.add(str);
            map.put(key,list);
        }
        return new ArrayList<>(map.values());
    }
}
