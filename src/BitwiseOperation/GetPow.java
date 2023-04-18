package BitwiseOperation;

/*
* 数值的整数次方 https://leetcode.cn/problems/shu-zhi-de-zheng-shu-ci-fang-lcof/
 */
public class GetPow {

    /*
    * 若n为偶数，x^n=x^n/2 * x^n/2
    * 若n为奇数，则为 x^n= x * x^n/2 * x^n/2
    * 因此通过 b&1（同理于b%2）计算n是否为奇数，当为奇数时先乘上x
     */
    public double myPow(double x, int n) {
        if(x==0) return 0;
        long b=n;
        double res = 1.0;
        if(b<0){
            x = 1/x;
            b = -b;
        }
        while(b>0){
            if((b&1) == 1) res *= x;
            x *= x;
            b>>=1;
        }
        return res;
    }
}
