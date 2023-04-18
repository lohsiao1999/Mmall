package backTracking;

/*
* 机器人的运动范围
* 回溯算法 dfs
* 位数和规律为：
*     1） (i+1) % 10 = 0时，S(x+1) = S(x)-8。S(x)即为x的位数和，设x=19，其位数和S(x)=1+9=10，则S(20)的位数和S(20)为2+0 =2
*     2） (i+1) % 10 != 0时，S(x+1) = S(x)+1。设x=1，其位数和S(x)=1，则2的位数和 S(2)为2
 */
public class RobotMovingCount {

    int m,n,k;
    boolean[][] visited;

    public int movingCount(int m, int n, int k) {
        this.k = k;
        this.m = m;
        this.n = n;
        this.visited = new boolean[m][n];
        return dfs(0,0,0,0);
    }

    public int dfs(int i,int j,int si,int sj){
        if(i >= m || j>= n || visited[i][j] || k < si+sj) return 0;
        visited[i][j] = true;
        int nextSi = (i+1)%10 == 0 ? si-8 : si+1;
        int nextSj = (j+1)%10 == 0 ? sj-8 : sj+1;
        return 1+dfs(i+1,j,nextSi,sj)+dfs(i,j+1,si,nextSj);
    }
}
