package BinaryTree;

public class GetMinDiffInBST {

    public static void main(String[] args) {
        GetMinDiffInBST getMinDiffInBST = new GetMinDiffInBST();
        TreeNode n1 = new TreeNode(4);
        TreeNode n2 = new TreeNode(2);
        TreeNode n3 = new TreeNode(6);
        TreeNode n4 = new TreeNode(1);
        TreeNode n5 = new TreeNode(3);
        n2.setChild(n4,n5);
        n1.setChild(n2,n3);
        System.out.println(getMinDiffInBST.minDiffInBST(n1));
    }

    int result = Integer.MAX_VALUE;
    public int minDiffInBST(TreeNode root) {
        pre(root);
        return result;
    }

    public int pre(TreeNode root){
        if(root == null){
            return Integer.MAX_VALUE;
        }

        int left = pre(root.left);
        int right = pre(root.right);
        int tmp = Math.min(Math.abs(root.val - right), Math.abs(root.val - left));
        result = Math.min(result, tmp);
        return root.val;
    }
}
