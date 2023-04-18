package BinaryTree;

/*
* 树的子结构 https://leetcode.cn/problems/shu-de-zi-jie-gou-lcof/?envType=study-plan&id=lcof&plan=lcof&plan_progress=xhg9p5qg
 */
public class SubStructure {

    public static void main(String[] args) {
//        TreeNode n1 = new TreeNode(1);
//        TreeNode n2 = new TreeNode(2);
//        TreeNode n3 = new TreeNode(3);
//        TreeNode n4 = new TreeNode(4);
//        TreeNode n5 = new TreeNode(5);
//        n3.left = n4;
//        n3.right = n5;
//        n4.left = n1;
//        n4.right = n2;
//
//        TreeNode m1 = new TreeNode(1);
//        TreeNode m4 = new TreeNode(4);
//        m4.left = m1;

        TreeNode n1 = new TreeNode(2);
        TreeNode n2 = new TreeNode(3);
        TreeNode n3 = new TreeNode(2);
        TreeNode n4 = new TreeNode(1);
        n1.left = n2;
        n1.right = n3;
        n2.left = n4;

        TreeNode m1 = new TreeNode(2);
        TreeNode m2 = new TreeNode(3);
        TreeNode m3 = new TreeNode(2);
        m2.right = m1;
        m1.left = m3;

        System.out.println(isSubStructure(n1,m2));
    }

    /*
    * 递归
    * helper(A,B)判断以A为根节点的树是否包含B
    * isSubStructure(A.left,B) 和 isSubStructure(A.right,B) 则分别判断A的左子树和A的右子树是否包含B
     */
    public static boolean isSubStructure(TreeNode A, TreeNode B) {
        return A!=null && B!=null && (helper(A,B) || isSubStructure(A.left,B) || isSubStructure(A.right,B));
    }

    /*
    * son == null 说明B树节点已经遍历完成且之前没有返回false，说明A树包含B树，因此返回true
    * parent == null 说明A树节点已经遍历完且之前没有返回true，说明A树不包含B树，因此返回false
    * helper(parent.left, son.left) && helper(parent.right, son.right)分别对比A树B树的左右子树
     */
    public static Boolean helper(TreeNode parent,TreeNode son){
        if(son == null) return true;
        if(parent == null || parent.val != son.val) return false;
        return helper(parent.left, son.left) && helper(parent.right, son.right);
    }
}
