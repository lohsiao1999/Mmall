package Dynamic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//出租车的最大盈利 https://leetcode.cn/problems/maximum-earnings-from-taxi/description/?envType=daily-question&envId=2023-12-08
/*
* 动态规划：
*   对于终点i，最大盈利dp[i]有两种情况：
*   （1）乘客在i点下车，dp[i] = dp[start] + end - start + tip
*   （2）乘客不在i点下车，dp[i] = dp[i-1]
 */
public class GetMaxTaxiEarnings {

    public long maxTaxiEarnings(int n, int[][] rides) {
        long[] dp = new long[n+1];
        Map<Integer, List<int[]>> map = new HashMap<>();
        for(int[] ride:rides){
            map.putIfAbsent(ride[1], new ArrayList<>());
            map.get(ride[1]).add(ride);
        }
        for(int i=1;i<=n;i++){
            dp[i] = dp[i-1];
            for(int[] ride : map.getOrDefault(i,new ArrayList<>())){
                dp[i] = Math.max(dp[i],dp[ride[0]] + ride[1] - ride[0] + ride[2]);
            }
        }
        return dp[n];
    }
}
