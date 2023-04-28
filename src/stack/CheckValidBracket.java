package stack;

import java.util.Stack;

/*
* 有效的括号字符串 https://leetcode.cn/problems/valid-parenthesis-string/
 */
public class CheckValidBracket {

    public static void main(String[] args) {
        String s = "((((()(()()()*()(((((*)()*(**(())))))(())()())(((())())())))))))(((((())*)))()))(()((*()*(*)))(*)()";
        System.out.println(checkValidString(s));
    }

    public static boolean checkValidString(String s) {
        //l表示左括号个数的最小可能取值，不能小于0
        //r表示左括号个数的最大可能取值
        int l = 0,r = 0;
        for(char c:s.toCharArray()){
            if(c == '('){
                //当前char为左括号时，l和r均加一
                l++;
                r++;
            }else if (c == ')'){
                //当前char为右括号时，l和r均减一
                l--;
                r--;
            }else{
                //当char为*时，因为*可能变为右括号，所以l减1，即左括号个数的最小可能取值减少1
                l--;
                //因为*可能变为左括号，所以r加1，即左括号个数的最大可能取值增加1
                r++;
            }
            //最小可能取值不能为负数，因此需要重新取0
            l = Math.max(l,0);
            //当l为负值时，会重新取0。因此当l>r时，r必定为负数，说明此时右括号数量大于左括号的最大可能取值，字符串的不符合规则
            if(l>r) return false;
        }
        //左括号的最小可能取值为0时，该字符串才符合规则
        return l==0;
    }
}
