package Greedy;

/*
* 买卖股票的最佳时机 II https://leetcode.cn/problems/best-time-to-buy-and-sell-stock-ii/
 */
public class GetMaxProfit {

    /*
    * 当天的股票价格高于前一天，便产生了收益，将当天收益加入最终受益当中
     */
    public int maxProfit(int[] prices) {
        int n = prices.length;
        int res =0;
        for(int i=1;i<n;i++){
            if(prices[i]>prices[i-1]) res += (prices[i]-prices[i-1]);
        }
        return res;
    }
}
