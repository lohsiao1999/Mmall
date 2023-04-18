package Dynamic;

/*
* 礼物的最大价值 https://leetcode.cn/problems/li-wu-de-zui-da-jie-zhi-lcof/?envType=study-plan&id=lcof&plan=lcof&plan_progress=xhg9p5qg
 */
public class GiftMaxValue {

    /*
    * 动态规划
    * 由题易知 f(i,j) = max{ f(i-1,j),f(i,j-1)} + grid[i][j]
    * 为了避免边界问题 将结果数组行列各加一
     */
    public int maxValue(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        int[][] res = new int[rows+1][cols+1];
        for(int i=1;i<= rows;i++){
            for(int j = 1;j <= cols;j++){
                res[i][j] = Math.max(res[i-1][j],res[i][j-1]) + grid[i-1][j-1];
            }
        }
        return res[rows][cols];
    }

    /*
    * 空间优化
    * 上述方法使用二维数组保存结果，其实只需要一维数组便可
     */
    public int maxValue2(int[][] grid) {
        int n = grid[0].length;
        int[] res = new int[n+1];
        for(int i=1;i<= grid.length; i++){
            for(int j = 1;j <= n;j++){
                res[j] = Math.max(res[j], res[j-1]) + grid[i-1][j-1];
            }
        }
        return res[n];
    }
}
