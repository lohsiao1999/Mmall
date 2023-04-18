package Dynamic;

public class CountUniquePath2 {

    public static void main(String[] args) {
        int[][] nums = {
                {0,1},{0,0}
        };
        System.out.println(uniquePathsWithObstacles(nums));
    }

    public static int uniquePathsWithObstacles(int[][] obstacleGrid) {
        if(obstacleGrid[0][0] == 1) return 0;
        int j = obstacleGrid[0].length;
        int[] res = new int[j+1];
        for(int k = 1;k<=j;k++){
            if(obstacleGrid[0][k-1] == 1){
                break;
            }else{
                res[k] = 1;
            }
        }
        for(int i = 1;i<obstacleGrid.length;i++){
            for(int k = 1;k <= j;k++){
                if(obstacleGrid[i][k-1] == 1){
                    res[k] = 0;
                }else{
                    res[k] = res[k] + res[k-1];
                }
            }
        }
        return res[j];
    }
}
