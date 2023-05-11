package backTracking;

import java.util.*;

/*
* 重新安排行程 https://leetcode.cn/problems/reconstruct-itinerary/
 */
public class GetItinerary {

    /*Hierholzer 算法
    * 1。从起点出发，进行深度优先搜索
    * 2. 沿一条边从一个顶点道道另一个顶点时，删除该边
    * 3. 若当前节点不存在通路时，将其加入结果集
    * 4. 将结果逆序，即为一个完整的通路
     */
    List<String> res = new LinkedList<>();
    Map<String, PriorityQueue<String>> dir = new HashMap<>();
    public List<String> findItinerary(List<List<String>> tickets) {
        for (List<String> item:tickets){
            String start = item.get(0);
            String end = item.get(1);
            if(!dir.containsKey(start)){
                //将终点加入优先级队列，按照字典序排序
                //优先级队列加入元素时间复杂度为logn
                PriorityQueue<String> queue = new PriorityQueue<>();
                queue.offer(end);
                dir.put(start,queue);
            }else dir.get(start).offer(end);
        }
        dfs("JFK");
        Collections.reverse(res);
        return res;
    }

    public void dfs(String start){
        //以start为起点遍历，每经过一个顶点便删除一条边，直到不存在通路时，将当前节点加入结果集
        while(dir.containsKey(start) && !dir.get(start).isEmpty()){
            dfs(dir.get(start).poll());
        }
        res.add(start);
    }
}
