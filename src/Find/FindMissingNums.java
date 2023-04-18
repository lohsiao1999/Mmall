package Find;

/*
* 查找缺失的数字
* e.g. 输入：[0,1,2,3,4,5,6,7,9] 输出：8
* 有序数组，二分查找，
* 跳出循环后，i指向右子数组的起始位，j指向左子数组的末位
 */
public class FindMissingNums {

    public static void main(String[] args) {
        int[] nums = {0,1,2,3,4,5,6,7,9};
        System.out.println(missingNumber(nums));
    }

    public static int missingNumber(int[] nums) {
        int i = 0,j = nums.length-1;
        while(i <= j){
            int mid = i + ((j-i) >> 1);
            if(mid < nums[mid]){
               j = mid -1;
            }else{
                i = mid + 1;
            }
        }
        return i;
    }
}
