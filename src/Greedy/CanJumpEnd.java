package Greedy;

/*
* 跳跃游戏 https://leetcode.cn/problems/jump-game/
 */
public class CanJumpEnd {

    public static void main(String[] args) {
        int[] nums ={0,1};
        System.out.println(canJump(nums));
    }

    //遍历每个格子，更新最大跳跃距离，若最大跳跃距离小于当前格子，则无法到达终点
    public static boolean canJump(int[] nums) {
        int max=0;
        for(int i=0;i<nums.length;i++){
            if(max<i) return false;
            max = Math.max(max,i+nums[i]);
        }
        return true;
    }
}
