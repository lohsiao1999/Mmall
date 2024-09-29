package Dynamic;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 单词拆分 https://leetcode.cn/problems/word-break/description/?envType=study-plan-v2&envId=top-interview-150
 */
public class JudgeWordBreak {

    public boolean wordBreak(String s, List<String> wordDict) {
        Set<String> dir = new HashSet<>(wordDict);
        boolean[] dp = new boolean[s.length()+1];
        dp[0] = true;
        for (int i = 0; i <= s.length(); i++) {
            for (int j = 0; j < i; j++) {
                //如果dp[j]为true，说明字符串前j个字符可通过字典中的单词组装出来
                //此时需要判断第j和第i个字符组成的词语是否存在字典中，若存在，说明前i位字符可由字典中的单词组装出来
                if(dp[j] && dir.contains(s.substring(j,i))){
                    dp[i] = true;
                    //跳出内层循环
                    break;
                }
            }
        }
        return dp[s.length()];
    }
}
