package Find;

/*
* 在二维数组中，从左到右、从上到下非递减排序，在该二维数组中查找目标数值
* e.g.
* [
    [1,   4,  7, 11, 15],
    [2,   5,  8, 12, 19],
    [3,   6,  9, 16, 22],
    [10, 13, 14, 17, 24],
    [18, 21, 23, 26, 30]
* ]
* 输入：5 输出：true
* 输入： 20 输出： false
 */

public class FindIn2DArray {

    public static void main(String[] args) {
        int[][] matrix = {
                {1,   4,  7, 11, 15},{2,   5,  8, 12, 19},{3,   6,  9, 16, 22},
                {10, 13, 14, 17, 24},{18, 21, 23, 26, 30}};
        System.out.println(findNumberIn2DArray(matrix,5));
    }

    /*
    * 方法：因为二维数组从左到右从上到下非递减排序，因此从左下角元素（设索引为 (i,j) ）开始遍历
    * 1）当 matrix[i][j] > target时，意味着当前元素所属行的元素均大于target，因此i--
    * 2）当 matrix[i][j] < target时，意味着当前元素所属列的元素均小于target，因此j++
    * 1）当 matrix[i][j] == target时，返回true
     */
    public static boolean findNumberIn2DArray(int[][] matrix, int target) {
        int i = matrix.length -1, j = 0;
        while (i >= 0 && j < matrix[0].length){
            if(matrix[i][j] < target) j++;
            else if(matrix[i][j] > target) i--;
            else return true;
        }
        return false;
    }
}
