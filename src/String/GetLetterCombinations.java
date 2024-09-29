package String;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetLetterCombinations {

    List<String> res = new ArrayList<>();
    String[] map = {"abc","def","ghi","jkl","mno","pqrs","tuv","wxyz"};
    public List<String> letterCombinations(String digits) {
        dfs(digits,new StringBuilder(),0);
        return res;
    }

    public void dfs(String target,StringBuilder sb,int k){
        if(k == target.length()){
            res.add(sb.toString());
            return;
        }
        String tmp = map[target.charAt(k)-'2'];
        for(int i=0;i<tmp.length();i++){
            sb.append(tmp.charAt(i));
            dfs(target, sb, k+1);
            sb.deleteCharAt(sb.length()-1);
        }
    }
}
