package BinaryTree;

/*
* 判断二叉树是否镜像 https://leetcode.cn/problems/dui-cheng-de-er-cha-shu-lcof/
 */
public class JudgeSymmetric {

    public boolean isSymmetric(TreeNode root) {
        return root==null || helper(root.left,root.right);
    }

    public Boolean helper(TreeNode left,TreeNode right){
        //当左子树节点和右子树节点为空时，说明左子树和右子树已经遍历完成且未返回false，左子树和右子树互为镜像，返回true
        if(left == null && right == null) return true;
        //左右子树节点只有一个越过子节点，或者子节点值不相等，说明不对称，返回false
        if(left == null || right == null || left.val!= right.val) return false;
        return helper(left.left,right.right) && helper(left.right,right.left);
    }
}
