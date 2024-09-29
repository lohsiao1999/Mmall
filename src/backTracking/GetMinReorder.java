package backTracking;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

//重新规划路线 https://leetcode.cn/problems/reorder-routes-to-make-all-paths-lead-to-the-city-zero/description/?envType=daily-question&envId=Invalid+Date
//构建树，并从0开始bfs
public class GetMinReorder {

    public int minReorder(int n, int[][] connections) {
        List<List<int[]>> dir = new ArrayList<>();
        for(int i=0;i<n;i++){
            dir.add(new ArrayList<>());
        }
        for(int[] con : connections){
            dir.get(con[0]).add(new int[]{con[1],1}); //节点con[0] 指向 con[1]节点，因此值为1
            dir.get(con[1]).add(new int[]{con[0],0});
        }

        boolean[] visited = new boolean[n];
        Queue<Integer> queue = new LinkedList<>();
        int res = 0;
        queue.offer(0);
        visited[0] = true;

        while (!queue.isEmpty()){
            Integer node = queue.poll();
            for(int[] path:dir.get(node)){
                //节点已处理，跳过
                if(visited[path[0]]) continue;
                //标记邻居节点为已处理
                visited[path[0]] = true;
                //相邻节点入队
                queue.offer(path[0]);
                //若当前节点指向相邻节点，加1
                res += path[1];
            }
        }
        return res;
    }
}
