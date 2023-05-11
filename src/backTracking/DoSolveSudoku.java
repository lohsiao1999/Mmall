package backTracking;

/*
* 解数独 https://leetcode.cn/problems/sudoku-solver/
 */
public class DoSolveSudoku {

    public void solveSudoku(char[][] board) {
        dfs(board);
    }

    public Boolean dfs(char[][] board){
        for(int i=0;i<board.length;i++){
            for(int j=0;j<board[0].length;j++){
                if(board[i][j] != '.') continue;
                for(char k='1';k<='9';k++){
                    if(valid(board,i,j,k)){
                        board[i][j] = k;
                        //一旦成功就直接返回结果
                        if(dfs(board)) return Boolean.TRUE;
                        board[i][j] = '.';
                    }
                }
                //当前位置(i,j)填入任何数字都不合理，因此直接返回上一层递归
                return Boolean.FALSE;
            }
        }
        //所有位置都遍历完成后，没有提前返回false，说明此时数组已经填写完成
        return Boolean.TRUE;
    }

    public Boolean valid(char[][] board,int i,int j,char k){
        //检验第j列上是否已经存在k
        for (char[] chars : board) {
            if (chars[j] == k) return Boolean.FALSE;
        }
        //检验第i行上是否已经存在k
        for(int m =0;m< board[i].length;m++){
            if(board[i][m] == k) return Boolean.FALSE;
        }
        //当前坐标(i,j)所属九宫格列行起始位置；
        int row = 3 * (i/3);
        //当前坐标(i,j)所属九宫格列起始位置；
        int col = 3 * (j/3);
        //检验坐标(i,j)所属九宫格是否已经存在k
        for(int n=row;n<row+3;n++){
            for(int m=col;m<col+3;m++){
                if(board[n][m] == k) return Boolean.FALSE;
            }
        }
        return Boolean.TRUE;
    }
}
