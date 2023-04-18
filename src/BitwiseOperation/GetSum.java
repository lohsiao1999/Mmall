package BitwiseOperation;

/*
* 不用加减乘除做加法 https://leetcode.cn/problems/bu-yong-jia-jian-cheng-chu-zuo-jia-fa-lcof/
 */
public class GetSum {

    public int add(int a, int b) {
        //非进位和 n = a^b
        //进位和 c = (a&b) <<1
        //a+b = n+c，而因为禁止使用+号，因此可以循环计算n+c的进位和与非进位和，直到进位和为0
        int n = a^b;
        int c = (a&b)<<1;
        while(b!=0){ //进位和为0，计算结束
            a = n; //将非进位和赋值给a，循环计算
            b = c; //将进位和赋值给b，循环计算
            n = a^b; //计算n和c的非进位和，赋值给n
            c = (a&b)<<1; //计算n和c的进位和，赋值给n
        }
        return a;
    }
}
