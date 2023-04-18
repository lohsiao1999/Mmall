package math;

/*
* 回文数，如121，1221，12321即为回文数
* 当一个数为回文数时，其后半段翻转与前半段相等，
* 如1/12，12/12，12/123，观察可发现前半段tmp1与后半段tmp2关系为tmp1 == tmp2或tmp1/10 == tmp2
 */
public class PalindromeNum {

    public boolean isPalindrome(int x) {
        if(x<0 || (x%10 == 0 && x != 0)) return false;
        int tmp2 = x;
        int tmp1 = 0;
        while(tmp1<tmp2){
            tmp1 = tmp1 * 10 + (tmp2 % 10);
            tmp2 /= 10;
        }
        return tmp1 == tmp2 || tmp1/10 == tmp2;
    }
}
