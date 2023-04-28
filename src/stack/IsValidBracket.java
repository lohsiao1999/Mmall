package stack;

import java.util.Stack;

/*
* 有效的括号 https://leetcode.cn/problems/valid-parentheses/
 */
public class IsValidBracket {

    public boolean isValid(String s) {
        if(s.length() == 0){
            return false;
        }
        Stack<Character> stack = new Stack<>();
        for(char c:s.toCharArray()){
            if(c == '('){
                stack.push(')');
            }else if( c == '['){
                stack.push(']');
            }else if( c == '{'){
                stack.push('}');
            }else if(stack.isEmpty() || c != stack.pop()){
                return false;
            }
        }
        return stack.isEmpty();
    }
}
