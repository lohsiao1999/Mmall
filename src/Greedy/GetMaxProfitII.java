package Greedy;

/*
* 买卖股票的最佳时机含手续费 https://leetcode.cn/problems/best-time-to-buy-and-sell-stock-with-transaction-fee/
 */
public class GetMaxProfitII {

    public int maxProfit(int[] prices, int fee) {
        int minPrice = prices[0],res =0;
        for(int i=1;i<prices.length;i++){
            //更新最低价格
            if(prices[i]<minPrice) minPrice = prices[i];

            //若当前股票价格减去最小价格后大于手续费，则产生收益
            if(prices[i]-minPrice>fee){
                //计算收益
                res += prices[i]-minPrice-fee;
                //将当天价格减去手续费设置为最低价格，避免当天价格用于明天股票买卖的价格计算时，重复计算手续费
                minPrice = prices[i]-fee;
            }
        }
        return res;
    }

    public int maxProfit1(int[] prices, int fee) {
        int minPrice = prices[0],res =0;
        for(int i=1;i<prices.length;i++){
            //更新最低价格
            if(prices[i]<minPrice) minPrice = prices[i];

            //若当前股票价格减去最小价格后大于手续费，则产生收益
            if(prices[i]-minPrice>fee){
                //计算收益
                res += prices[i]-minPrice-fee;
                //将当天价格减去手续费设置为最低价格，避免当天价格用于明天股票买卖的价格计算时，重复计算手续费
                minPrice = prices[i]-fee;
            }
        }
        return res;
    }
}
