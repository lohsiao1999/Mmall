package stack;

import java.util.Deque;
import java.util.LinkedList;

public class MainStack {

    public static void main(String[] args) {
        MainStack test = new MainStack();
        test.push(-2);
        test.push(0);
        test.push(-3);
//        test.push(0);
//        test.push(0);
//        test.push(-3);
//        System.out.println(test.min());
        test.pop();
        System.out.println(test.top());
//        test.pop();
//        System.out.println(test.min());
//        test.pop();
//        System.out.println(test.min());
//        test.pop();
//        System.out.println(test.min());
    }

    /**
     * 方法1：使用辅助栈，存储当前值入栈时所对应的最小值
     *  1）当值入栈时，比较当前值和辅助栈顶的值，最小值存入辅助栈
     *  2）但元素出栈时，辅助栈的元素需要同步出栈
     *  3）任意时刻，辅助栈顶的值即为当前栈中的最小值

    Deque<Integer> mainStack;
    Deque<Integer> minStack;

    public MainStack() {
        mainStack = new LinkedList<>();
        minStack = new LinkedList<>();
        minStack.push(Integer.MAX_VALUE);
    }

    public void push(int x) {
        mainStack.push(x);
        minStack.push(Math.min(x,minStack.peek()));
    }

    public void pop() {
        mainStack.pop();
        minStack.pop();
    }

    public int top() {
        return mainStack.peek();
    }

    public int min() {
        return minStack.peek();
    }

    */

    /**
     * 方法2：栈中存入最小值和当前值的差值，即 minStack.push(x - min);
     *  1）栈顶值为负数时，表明当前值为栈中最小值min
     *  2）出栈时，需要判断出栈元素是否为栈中最小值，即判断栈顶元素是否小于0，小于0表明当前元素为栈中最小元素min，需要计算新的最小值
     *  3）新的最小值计算方法为，min - minStack.pop()，因为栈顶元素 = 当前元素 - 旧最小值计算出来的，所以 新最小值 = 现最小值 - 栈顶元素
     */

    Deque<Long> minStack;
    Long min;

    public MainStack() {
        minStack = new LinkedList<>();
    }

    public void push(int x) {
        if(minStack.isEmpty()){
            minStack.push(0L);
            min = (long)x;
        }else {
            minStack.push(x - min);
            min = Math.min(x,min);
        }
    }

    public void pop() {
        if(minStack.peek() < 0){
            min = min - minStack.pop();
            return;
        }
        minStack.pop();
    }

    public int top() {
        return minStack.peek() > 0 ? Math.toIntExact(min + minStack.peek()) : Math.toIntExact(min);
    }

    public int min() {
        return Math.toIntExact(min);
    }
}
