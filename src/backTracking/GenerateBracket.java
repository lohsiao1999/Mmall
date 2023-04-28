package backTracking;

import java.util.ArrayList;
import java.util.List;

/*
* 生成匹配的括号 https://leetcode.cn/problems/IDBivT/
 */
public class GenerateBracket {

    public static void main(String[] args) {
        System.out.println(generateParenthesis(3));
    }

    static List<String> res = new ArrayList<>();
    public static List<String> generateParenthesis(int n) {
        dfs(new StringBuilder(),n,n);
        return res;
    }

    public static void dfs(StringBuilder sb,int l,int r){
        if(l == 0 && r == 0){
            res.add(sb.toString());
            return;
        }
        if(l>0){
            sb.append('(');
            dfs(sb,l-1,r);
            sb.deleteCharAt(sb.length()-1);
        }
        if(r>0 && r>l){
            sb.append(')');
            dfs(sb,l,r-1);
            sb.deleteCharAt(sb.length()-1);
        }
    }
}
