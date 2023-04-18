package Array;
/*
* 盛水最多的容器
 */
public class GetMaxArea {

    public int maxArea(int[] height) {
        int max = 0;
        int i=0,j=height.length-1;
        while(i<j){
            max = height[i] < height[j] ?
                    Math.max(max, (j-i) * Math.min(height[i++],height[j])):
                    Math.max(max, (j-i) * Math.min(height[i],height[j--]));
        }
        return max;
    }

    /*
    * 优化思路：当移动指针后height[k]的值小于移动前的最小值，则继续移动指针
     */
    public int maxArea2(int[] height) {
        int max = 0;
        int i=0,j=height.length-1;
        while(i<j){
            int minVal = Math.min(height[i],height[j]);
            max = Math.max(max, (j-i) * minVal);
            //若height[i]小于移动前的最小值，则跳过该值
            while(height[i] <= minVal && i < j){
                i++;
            }
            while(height[j] <= minVal && i < j){
                j--;
            }
        }
        return max;
    }
}
