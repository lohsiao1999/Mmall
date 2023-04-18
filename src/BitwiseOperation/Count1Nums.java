package BitwiseOperation;

/*
* 二进制中1的个数 https://leetcode.cn/problems/er-jin-zhi-zhong-1de-ge-shu-lcof/?envType=study-plan&id=lcof&plan=lcof&plan_progress=xhg9p5qg
 */
public class Count1Nums {

    /*
    * 利用 n & n-1
     */
    public int hammingWeight1(int n) {
        int res=0;
        while(n!=0){
            res++;
            n &= n-1;
        }
        return res;
    }

    /*
    * 利用n&1，若结果为1，则说明n的二进制最后一位 为1
    * 判断完成后，n需要无符号右移>>> 1位
     */
    public int hammingWeight2(int n) {
        int res=0;
        while(n!=0){
            if((n&1) ==1) res++;
            n>>>= 1;
        }
        return res;
    }
}
