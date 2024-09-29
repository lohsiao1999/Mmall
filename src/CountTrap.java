
//接雨水 https://leetcode.cn/problems/trapping-rain-water/description/

import java.util.Stack;

public class CountTrap {

    public static void main(String[] args) {
        int[] height = {0,1,0,2,1,0,1,3,2,1,2,1};
        System.out.println(trap2(height));
    }

    public static int trap(int[] height) {
        int length = height.length;
        int[] left = new int[length];
        int[] right = new int[length];
        left[0] = height[0];
        right[length-1] = height[length-1];

        for(int i=1;i<length;i++){
            left[i] = Math.max(left[i-1],height[i]);
        }
        for(int j=length-2;j>=0;j--){
            right[j] = Math.max(right[j+1],height[j]);
        }
        int res = 0;
        for(int k = 1;k<length-1;k++){
            res += (Math.min(left[k],right[k]) - height[k]);
        }
        return res;
    }

    public static int trap2(int[] height) {
        int res = 0;
        Stack<Integer> stack = new Stack<>();
        for(int i=0;i< height.length;i++){
            //当右指针大于栈顶元素时，说明此时可能形成低洼积蓄雨水
            while(!stack.isEmpty() && height[i] > height[stack.peek()]){
                //获取栈顶元素
                int cur = stack.pop();
                if(stack.isEmpty()) break;
                int left = stack.peek();
                //取左右指针指向的最小高度并减去低洼处高度
                int h = Math.min(height[left],height[i])-height[cur];
                res += (i -left-1) * h;
            }
            stack.push(i);
        }
        return  res;
    }
}
