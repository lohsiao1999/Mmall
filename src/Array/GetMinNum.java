package Array;

/*
* 把数组排成最小的数 https://leetcode.cn/problems/ba-shu-zu-pai-cheng-zui-xiao-de-shu-lcof/
 */
public class GetMinNum {

    public static void main(String[] args) {
        int[] nums = {10,2};
        System.out.println(minNumber(nums));
    }

    public static String minNumber(int[] nums) {
        String[] strs = new String[nums.length];
        for(int i=0;i<nums.length;i++){
            strs[i] = String.valueOf(nums[i]);
        }
        qucikSort(strs,0,strs.length-1);
        StringBuilder res = new StringBuilder();
        for(String str:strs) res.append(str);
        return res.toString();
    }

    public static void qucikSort(String[] nums,int l,int r){
        if(l >= r) return;
        String temp = nums[l];
        int i=l,j=r;
        while(i<j){
            while((nums[j] + nums[l]).compareTo(nums[l] + nums[j]) >= 0 && i<j) j--;
            while((nums[i] + nums[l]).compareTo(nums[l] + nums[i]) <= 0 && i<j) i++;
            temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
        }
        nums[i] = nums[l];
        nums[l] = temp;
        qucikSort(nums,l,i-1);
        qucikSort(nums,i+1,r);
    }
}
