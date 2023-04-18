package String;

import java.util.ArrayList;
import java.util.List;

/*
* N字形变换 https://leetcode.cn/problems/zigzag-conversion/
* 当字符串按照N字形排列时，其索引值先增大后减小，因此使用flag，当到达边界时，flag = -flag，控制索引的增大或减小
 */
public class ConvertToN {

    public String convert(String s, int numRows) {
        if(numRows < 2) return s;
        List<StringBuilder> list = new ArrayList<>();
        for(int i=0;i<numRows;i++) list.add(new StringBuilder());
        int i=0;
        int flag = -1;
        for(char c : s.toCharArray()){
            list.get(i).append(c);
            if(i == 0 || i == numRows-1) flag = -flag;
            i += flag;
        }
        StringBuilder res = new StringBuilder();
        for(StringBuilder sb:list) res.append(sb);
        return res.toString();
    }
}
