package Greedy;

import java.util.Arrays;

/*
* 分发饼干 https://leetcode.cn/problems/assign-cookies/
 */
public class GetContentChild {

    //先排序数组，最大的饼干优先满足胃口最大的孩子
    public int findContentChildren(int[] g, int[] s) {
        if(g.length == 0 || s.length == 0) return 0;
        int res = 0;
        int index=s.length-1;
        Arrays.sort(g);
        Arrays.sort(s);
        for(int i=g.length-1;i>=0;i--){
            if(index<0) break;
            if(s[index]>=g[i]){
                res++;
                index--;
            }
        }
        return res;
    }
}
