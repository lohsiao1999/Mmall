package Find;

/*
* 查找数组中重复的数字
* 在一个长度为 n 的数组 nums 里的所有数字都在 0～n-1 的范围内。
* 数组中某些数字是重复的，但不知道有几个数字重复了，也不知道每个数字重复了几次。请找出数组中任意一个重复的数字。
* 返回数组当中的某个重复数字
* e.g. 输入:[2, 3, 1, 0, 2, 5, 3] 输出： 2或者3
 */

public class FindRepeatNum {

    public static void main(String[] args) {
        int[] nums = {2, 3, 1, 0, 2, 5, 3};
        System.out.println(findRepeatNumber(nums));
    }

    /*
    * 方法：原地交换
    * 将值交换至数组中与值相等的索引处，即 nums[i] = i
     */
    public static int findRepeatNumber(int[] nums) {
        int i = 0;
        while(i < nums.length){
            if(nums[i] == i){
                //值与索引相等，因此无需操作，跳过当前值
                i++;
                continue;
            }
            //num[i] 为当前值，nums[nums[i]] == nums[i] 说明当前值和索引值为当前值处的值相等，即为重复值
            //e.g. [0,1,2,3,2] 若 i = 4，则nums[i] = 2,  nums[nums[i]] = nums[2] = 2。因为 nums[nums[i]] == nums[i]，所以数字2为重复数字
            if(nums[nums[i]] == nums[i]) return nums[i];

            //将当前值交换至与当前值相等的索引处，使 nums[i] = i
            int temp = nums[i];
            nums[i] = nums[temp];
            nums[temp] = temp;
        }
        return -1;
    }
}
