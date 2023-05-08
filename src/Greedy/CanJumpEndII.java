package Greedy;

public class CanJumpEndII {

    public int jump(int[] nums) {
        if(nums.length == 1) return 0;
        int curDistance=0,nextDistance=0,n= nums.length,res=0;
        //注意边界条件n-1，当索引i指向n-1位置时，此时只需要再走一步，即res++便能到达终点，因此无需遍历至n
        for(int i=0;i<n-1;i++){
            //计算最大的下一步覆盖范围
            nextDistance = Math.max(i+nums[i],nextDistance);
            //若i到达当前最大覆盖范围右边界
            if(i == curDistance){
                res++;
                curDistance = nextDistance;
            }
        }
        return res;
    }
}
