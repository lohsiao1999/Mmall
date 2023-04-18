package Find;

/*
* 查找旋转数组中的最小数字
* 一个可能存在重复元素值的数组numbers，它原来是一个升序排列的数组，并将数组最开始的若干个元素交换至数组的末尾。请返回旋转数组的最小元素。
* e.g.数组[3,4,5,1,2] 为 [1,2,3,4,5] 的一次旋转，该数组的最小值为 1。
* 有序数组查找优先考虑二分查找
 */
public class FindMinOnArray {

    public static void main(String[] args) {
        int[] nums = {3,1,3};
        System.out.println(minArray(nums));
    }

    /*
    * 方法，使用二分查找
    * 1）若numbers[mid] > numbers[j]，则最小值一定位于[mid+1,j]区间
    * 2）若numbers[mid] < numbers[j]，则最小值一定位于[i,mid]区间
    * 3）若numbers[mid] = numbers[j]，则无法判断最小值位于哪个区间，因此此时直接遍历 [i,j] 区间获取最小值
     */
    public static int minArray(int[] numbers) {
        int i  = 0,j = numbers.length-1;
        while(i < j){
            int mid = i + ((j-i)>>1);
            if(numbers[mid] > numbers[j]){
                i = mid+1;
            }else if(numbers[mid] < numbers[j]){
                j = mid;
            }else {
                int index = i;
                for(int k = i+1;k<j;k++){
                    if(numbers[k] < numbers[index]) index = k;
                }
                return numbers[index];
            }
        }
        return numbers[i];
    }
}
