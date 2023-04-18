package backTracking;

/*
* 矩阵中的路径 https://leetcode.cn/problems/ju-zhen-zhong-de-lu-jing-lcof/submissions/
* 回溯算法 dfs
 */
public class ExistWord {

    public static void main(String[] args) {
        char[][] board = {
                {'A','B','C','E'},{'S','F','C','S'},{'A','D','E','E'}
        };
        String n = "ABCB";
        System.out.println(exist(board,n));
    }

    public static boolean exist(char[][] board, String word) {
        char[] words = word.toCharArray();
        for(int i =0;i<board.length;i++){
            for(int j=0;j<board[0].length;j++){
                //暴力索引以矩阵中每个元素为开头是否包含目标单词
                if(dfs(board,words,i,j,0)) return true;
            }
        }
        return false;
    }

    public static boolean dfs(char[][] board, char[] words,int i,int j,int k){
        if(i<0 || i>=board.length || j<0 || j>=board[0].length || k>= words.length ||board[i][j] != words[k]) return false;
        if(k == words.length-1) return true;
        board[i][j] = '\0';
        boolean res = dfs(board,words,i-1,j,k+1) || dfs(board,words,i,j-1,k+1) ||
                dfs(board,words,i+1,j,k+1) || dfs(board,words,i,j+1,k+1);
        board[i][j] = words[k];
        return res;
    }
}
