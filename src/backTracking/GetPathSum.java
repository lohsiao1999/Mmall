package backTracking;

/*
* 二叉树中和为某一值的路径 https://leetcode.cn/problems/er-cha-shu-zhong-he-wei-mou-yi-zhi-de-lu-jing-lcof/
 */

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GetPathSum {

    List<List<Integer>> res = new ArrayList<>();
    LinkedList<Integer> path = new LinkedList<>();

    public List<List<Integer>> pathSum(TreeNode root, int target) {
        dfs(root,target);
        return res;
    }

    public void dfs(TreeNode node,int target){
        LinkedList<Integer> list = new LinkedList<>();
        list.get(target);
        if(node == null) return;
        path.add(node.val);
        target -= node.val;
        if(target == 0 && node.left == null && node.right == null){
            res.add(new LinkedList(path));
        }
        dfs(node.left,target);
        dfs(node.right,target);
        path.removeLast();
    }

}

class TreeNode{
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) { val = x; }
}
