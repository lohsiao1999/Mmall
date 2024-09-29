package Array;

import java.util.PriorityQueue;

/**
 * 找出第K大的数
 */
public class FindKthLargestNums {

    public static void main(String[] args) {
        int[] nums = {3,2,1,5,6,4};
        System.out.println(findKthLargest1(nums,2));
    }

    /**
     * 方法一：
     *  使用优先级队列。遍历数组中的数字i，当队列长度小于K时，i直接入队；当队列长度等于K时，判断队头的值（即最小值）是否大于i，否则取出该值替换为i
     */
    public static int findKthLargest1(int[] nums, int k) {
        PriorityQueue<Integer> head = new PriorityQueue<>();
        for(int i:nums){
            if(head.size() < k){
                head.add(i);
            }else if(head.peek() < i){
                head.poll();
                head.add(i);
            }
        }
        return head.peek();
    }

    public int findKthLargest2(int[] nums, int k) {
        quickSelect(nums,0,nums.length-1,nums.length-k);
        return nums[nums.length -k];
    }

    //快速选择排序变化

    /**
     *
     * @param nums 需排序的数组
     * @param left 左指针
     * @param right 右指针
     * @param k 第k大数的索引
     */
    public void quickSelect(int[] nums,int left,int right,int k){
        int temp = nums[left];
        int l = left,r=right;
        while(l<r){
            //找到小于锚定数的索引
            while(r>l && nums[r] >= temp) r--;
            if(r==l) break;
            //将小于锚定数的数据替换到索引l处（初始时为锚定数索引，所以此处++）
            nums[l++] = nums[r];
            //找到大于锚定数的索引
            while(r>l && nums[l] <=temp) l++;
            if(r==l) break;
            //将大于锚定数的数据替换到索引r处（此时索引r处数据已经替换到l-1索引处）
            nums[r--] = nums[l];
        }
        //替换完成后l==r，此时l，r索引指向位置即为锚定数所处位置
        nums[l] = temp;
        //因为只需要获取第K大的数，所以只需要找出第k大的数所处的区间并对该区间排序即可
        //当l小于k时，说明第k大的数位于[l+1,right]区间，因此对该区间排序
        if(l < k)  quickSelect(nums, l+1, right, k);
        //当l大于k时，说明第k大的数位于[left,l-1]区间，因此对该区间排序
        if(l > k)  quickSelect(nums, left, l-1, k);
        //当l等于k时，说明当前l指向的值即为第k大的数，返回结果
    }
}
