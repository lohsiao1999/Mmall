package BinaryTree;

/*
* 打印二叉树
* BFS广度优先，即同一层节点从左到右打印
 */

import java.util.*;

public class PrintBinaryTree {

    public static void main(String[] args) {
        TreeNode treeNode1 = new TreeNode(3);
        TreeNode treeNode2 = new TreeNode(9);
        TreeNode treeNode3 = new TreeNode(20);
        TreeNode treeNode4 = new TreeNode(15);
        TreeNode treeNode5 = new TreeNode(7);
        treeNode1.left = treeNode2;
        treeNode1.right = treeNode3;
        treeNode3.left = treeNode4;
        treeNode3.right = treeNode5;

//        int[] order = levelOrder(treeNode1);
//        System.out.println(Arrays.toString(order)); //返回值 [3, 9, 20, 15, 7]

//        List<List<Integer>> lists = levelOrder2(treeNode1);
//        for (List<Integer> tmp: lists) {
//            System.out.println(Arrays.toString(tmp.toArray()));
//        }
        // 返回值 {[3],[9, 20],[15, 7]}

        List<List<Integer>> lists3 = levelOrder3(treeNode1);
        for (List<Integer> tmp: lists3) {
            System.out.println(Arrays.toString(tmp.toArray()));
        }
        // 返回值 {[3],[20, 9],[15, 7]}
    }

    /*
    * 方法：广度优先BFS
    * 利用队列先进先出的特性，获取当前节点值的同时，将节点的左右子节点顺序加入队列
     */
    public static int[] levelOrder(TreeNode root) {
        if(root == null) return new int[0];
        Deque<TreeNode> deque = new LinkedList<>();
        List<Integer> list = new ArrayList<>();
        deque.offer(root);
        while(!deque.isEmpty()){
            TreeNode temp = deque.poll();
            list.add(temp.val);
            if (null != temp.left) deque.offer(temp.left);
            if (null != temp.right) deque.offer(temp.right);
        }
        int[] res = new int[list.size()];
        for(int i =0;i<list.size();i++){
            res[i] = list.get(i);
        }
        return res;
    }

    public static List<List<Integer>> levelOrder2(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if(root == null) return res;
        Deque<TreeNode> deque = new LinkedList<>();
        deque.offer(root);
        while(!deque.isEmpty()){
            List<Integer> list = new ArrayList<>();
            //遍历同一层节点
            for(int i = deque.size();i>0;i--){
                TreeNode temp = deque.poll();
                list.add(temp.val);
                if (null != temp.left) deque.offer(temp.left);
                if (null != temp.right) deque.offer(temp.right);
            }
            res.add(list);
        }
        return res;
    }

    /*
    * 奇数层节点从左到右打印，偶数层节点从右到左打印
    * 利用双端队列的特性，通过flag判断元素正序或倒序加入list
     */
    public static List<List<Integer>> levelOrder3(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if(root == null) return res;
        Deque<TreeNode> deque = new LinkedList<>();
        deque.offer(root);
        Boolean flag = Boolean.TRUE;
        while(!deque.isEmpty()){
            LinkedList<Integer> list = new LinkedList<>();
            //遍历同一层节点
            for(int i = deque.size();i>0;i--){
                TreeNode temp = deque.poll();
                if (flag) list.add(temp.val);
                else list.addFirst(temp.val);
                if (null != temp.left) deque.offer(temp.left);
                if (null != temp.right) deque.offer(temp.right);
            }
            res.add(list);
            flag = !flag;
        }
        return res;
    }
}

class TreeNode{
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) { val = x; }
    public TreeNode(int _val,TreeNode _left,TreeNode _right) {
        val = _val;
        left = _left;
        right = _right;
    }
}
