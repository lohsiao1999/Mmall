package Dynamic;

import java.util.Arrays;

public class GoodsList {

    public static void main(String[] args) {
        int money = 10;
        int n = 5;
        int[] res = {3,5,0,4,2,0,3,5,2,3,4,2,6,4,0};
        int y =0;
        item[] s = new item[n+1];
        for(int i=1;i<=n;i++){
            int v = res[y++];
            int p = res[y++];
            int q = res[y++];
            if(s[i] == null){
                s[i] = new item(p,v,q);
            }else{
                s[i].initItem(p,v,q);
            }
            if(q > 0){
                if(s[q] == null) s[q] = new item();
                if(s[q].index1 == 0) s[q].setIndex1(i);
                else s[q].setIndex2(i);
            }
        }
        int[][] dp = new int[n+1][money+1];
        for(int i=1;i<=n;i++){
            int tempDp,g1=0,g2=0,g3=0,v1=0,v2=0,v3=0;
            item tmp = s[i];
            tempDp = tmp.v*tmp.p;
            if(tmp.index1>0){
                v1 = tmp.v + s[tmp.index1].v;
                g1 = tempDp + s[tmp.index1].v * s[tmp.index1].p;
            }
            if(tmp.index2>0){
                v2 = tmp.v + s[tmp.index2].v;
                g2 = tempDp + s[tmp.index2].v * s[tmp.index2].p;
            }

            if(tmp.index1>0 && tmp.index2>0){
                v3 = tmp.v + s[tmp.index1].v + s[tmp.index2].v;
                g3 = tempDp + s[tmp.index1].v * s[tmp.index1].p + s[tmp.index2].v * s[tmp.index2].p;
            }

            for(int j=money;j>0;j--){
                if(tmp.q>0){
                    dp[i][j] = dp[i-1][j];
                }else{
                    if(j>=tmp.v) dp[i][j] = Math.max(dp[i-1][j],dp[i-1][j-tmp.v]+tempDp);
                    else dp[i][j] = dp[i-1][j];
                    if(v1 >0 && j>=v1) dp[i][j] = Math.max(dp[i][j],dp[i-1][j-v1]+g1);
                    if(v2 >0 &&j>=v2) dp[i][j] = Math.max(dp[i][j],dp[i-1][j-v2]+g2);
                    if(v3 >0 &&j>=v3) dp[i][j] = Math.max(dp[i][j],dp[i-1][j-v3]+g3);
                }
            }
            System.out.println(Arrays.toString(dp[i]));
        }
        System.out.println(dp[n][money]);
    }
}

class item{
    int p;
    int v;
    int q;

    int index1;
    int index2;

    public item(){}

    public item(int p,int v,int q){
        this.v = v;
        this.q = q;
        this.p = p;
    }

    public void initItem(int p,int v,int q){
        this.v = v;
        this.q = q;
        this.p = p;
    }

    public void setIndex1(int index){
        this.index1 = index;
    }
    public void setIndex2(int index){
        this.index2 = index;
    }
}
