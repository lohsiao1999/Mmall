package Array;

import java.util.Arrays;

/*
* 分发糖果 https://leetcode.cn/problems/candy/
 */
public class GetCandyCount {
    public static void main(String[] args) {
        int[] nums = {1,0,2};
        candy(nums);
    }

    public static int candy(int[] ratings) {
        int[] left = new int[ratings.length];
        int[] right = new int[ratings.length];
        Arrays.fill(left,1);
        Arrays.fill(right,1);
        for(int i=1;i<ratings.length;i++){
            if(ratings[i] > ratings[i-1]) left[i] = left[i-1]+1;
        }
        int count = left[ratings.length-1];
        for(int j=ratings.length-2;j>=0;j--){
            if(ratings[j] >= ratings[j+1]) right[j] = right[j+1]+1;
            count += Math.max(left[j],right[j]);
        }
        return count;
    }
}
