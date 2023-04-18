package Dynamic;

/*
* 青蛙跳台阶
* 与斐波那契数列类似，不同点在于初始值不同，F(0) = 1, F(1) = 1, F(2) = 2
 */
public class CountNumsWays {

    public int numWays(int n) {
        int a=1,b=1,sum;
        for(int i =0;i<n;i++){
            sum = (a+b) % 1000000007;
            a = b;
            b = sum;
        }
        return a;
    }
}
