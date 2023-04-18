package Dynamic;

/*
* 买卖股票  https://leetcode.cn/problems/gu-piao-de-zui-da-li-run-lcof/?envType=study-plan&id=lcof&plan=lcof&plan_progress=xhg9p5qg
 */
public class GetMaxProfit {

    public int maxProfit(int[] prices) {
        //cost保存股票的最低价格
        int cost = Integer.MAX_VALUE;
        //利润
        int profit = 0;
        for(int price: prices){
            cost = Math.min(cost,price);
            profit = Math.max(profit,price - cost);
        }
        return profit;
    }
}
