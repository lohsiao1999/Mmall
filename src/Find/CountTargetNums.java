package Find;

/*
* 统计有序数组中目标值出现的次数
* e.g. 输入：[5,7,7,8,8,10] 输出：2
* 有序数组查找，优先考虑二分查找
 */
public class CountTargetNums {

    public static void main(String[] args) {
        int[] nums = {5,7,7,8,8,10};
        System.out.println(search1(nums,8));
        System.out.println(search2(nums,8));
    }

    /*
    * 方法1；分别查询target值的左边界 left 和右边界 right，通过right -left - 1计算目标值的出现次数
     */
    public static int search1(int[] nums, int target) {
        int left,right;
        int i = 0,j = nums.length -1;

        //查找target值右边界
        //注意边界条件 <=，否则当i = j时，会漏掉对 num[i] 的判断
        while(i <= j){
           int mid = i + ((j-i) >> 1);
           if(nums[mid] <= target){
               //当中间值小于等于target时，因为要查找target值的右边界，因此需要继续查找右区间，所以将区间变为 [mid+1, j]
               i = mid + 1;
           }else {
               //当中间值大于target时，需要将区间变为 [i, mid -1]
               j = mid - 1;
           }
        }
        //当i 大于 j 时，说明此时 i 为目标值的右边界
        right = i;
        // 若数组中无 target ，则提前返回
        if(j >= 0 && nums[j] != target) return 0;

        //查找target值左边界
        //注意边界条件 <=，否则当i = j时，会漏掉对 num[i] 的判断
        i = 0;
        j = nums.length -1;
        while(i <= j){
            int mid = i + ((j-i) >> 1);
            if(nums[mid] < target){
                //当中间值小于target时，需要将区间变为 [mid+1, j]
                i = mid + 1;
            }else {
                //当中间值大于等于target时，因为此时需要查找target值的左边界，需要将区间变为 [i, mid -1]
                j = mid - 1;
            }
        }
        //当i 大于 j 时，说明此时 j 为目标值的左边界
        left = j;
        return right - left -1;
    }

    /*
    * 方法2：分别查找target和target-1值的右边界
     */
    public static int search2(int[] nums, int target) {
        return findRightBorder(nums,target) - findRightBorder(nums,target -1);
    }

    public static int findRightBorder(int[] nums, int target){
        int i = 0,j = nums.length -1;
        //注意边界条件i <= j ，否则当i = j时，会漏掉对 num[i] 的判断
        while(i <= j){
            int mid = i + ((j-i) >> 1);
            if(nums[mid] <= target){
                //当中间值小于等于target时，因为要查找target值的右边界，因此需要继续查找右区间，所以将区间变为 [mid+1, j]
                i = mid + 1;
            }else {
                j = mid - 1;
            }
        }
        return i;
    }

}
