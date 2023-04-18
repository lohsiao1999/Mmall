package Array;

import java.util.PriorityQueue;

public class FindKthLargestNums {

    public static void main(String[] args) {
        int[] nums = {3,2,1,5,6,4};
        System.out.println(findKthLargest1(nums,2));
    }

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
            while(r>l && nums[r] >= temp) r--;
            if(r==l) break;
            nums[l++] = nums[r];
            while(r>l && nums[l] <=temp) l++;
            if(r==l) break;
            nums[r--] = nums[l];
        }
        nums[l] = temp;
        //当l小于k时，说明第k大的数位于[l+1,right]区间，因此对该区间排序
        if(l < k)  quickSelect(nums, l+1, right, k);
        //当l大于k时，说明第k大的数位于[left,l-1]区间，因此对该区间排序
        if(l > k)  quickSelect(nums, left, l-1, k);
        //当l等于k时，说明当前l指向的值即为第k大的数，返回结果
    }
}
