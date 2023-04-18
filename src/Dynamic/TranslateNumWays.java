package Dynamic;

/*
* 将数字翻译成字符串
* 状态转移方程如下：
* 当nums[i-1]*10+ nums[i] ∈ [10,25]时：dp[i] = dp[i-1]+dp[i-2]
* 当nums[i-1]*10+ nums[i] ∈ [0,10) ∪ (26,99]时 dp[i] = dp[i-1]
* 解释：对于字符串125，当i = 3时，易知dp[i-1] = dp[2] = 2，即12有两种拆分方法为(1,2)和(12)
* 又 25 ∈ [10,25]，则此时 dp[i] = dp[i-1]+dp[i-2] = dp[2]+dp[1] = 3，即125有3种拆分方法 (1,2,5) (12,5) (1,25)
 */
public class TranslateNumWays {

    /*
    * 由题可推导出dp[1] = 1 ，dp[2] = 2，则dp[0] = 1。以dp[0]和dp[1]为起始值往下推到，因此循环起始值i == 1
     */
    public int translateNum1(int num) {
        String s = String.valueOf(num);
        int a=1,b=1;
        for(int i = 1;i<s.length();i++){
            String tmp = s.substring(i - 1, i + 1);
            int sum = (tmp.compareTo("10") >= 0 && tmp.compareTo("25") <= 0) ? a+b : a;
            b = a;
            a = sum;
        }
        return a;
    }

    public int translateNum2(int num) {
        int i = 1,j =1 ,k,l=num % 10;
        while(num > 0){
            num /= 10;
            k = num % 10;
            int sum = k*10 + l;
            int tmp = (sum >=10 && sum <= 25) ? i+j : i;
            j = i;
            i = tmp;
            l = k;
        }
        return i;
    }
}
