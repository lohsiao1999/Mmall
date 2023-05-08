package Greedy;

import java.util.Arrays;

/*
* 单调递增的数字 https://leetcode.cn/problems/monotone-increasing-digits/
 */
public class IncreasingDigits {

    public static void main(String[] args) {
        System.out.println(monotoneIncreasingDigits(322));
    }

    //从后往前遍历，如第i位数字大于第i+1位数字时不满足条件，则此时第i位数字需要减1，而第i+1位数字应当取9
    public static int monotoneIncreasingDigits(int n) {
        if(n<10) return n;
        char[] chars = Integer.toString(n).toCharArray();
        int length = chars.length;
        for(int i=length-2;i>=0;i--){
            if(chars[i]>chars[i+1]){
                chars[i] -= 1;
                int tmp = i+1;
                while (tmp<length) chars[tmp++] ='9';
            }
        }
        return Integer.parseInt(new String(chars));
    }
}
