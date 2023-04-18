package Dynamic;

/**
 * 斐波那契数列 https://leetcode.cn/problems/fei-bo-na-qi-shu-lie-lcof/
 * 循环求解。动态规划
 */
public class Fibonacci {

    public static void main(String[] args) {
        System.out.println(fib(2));
    }

    public static int fib(int n) {
        int a=0,b=1,sum;
        for(int i =0;i<n;i++){
            sum = (a+b) % 1000000007;
            a = b;
            b = sum;
        }
        //因为i从0开始，而第一次循环a=b，即第1次循环时a=F(1)，第n次返回时a=F(n)，所以最后返回a
        return a;
    }
}
