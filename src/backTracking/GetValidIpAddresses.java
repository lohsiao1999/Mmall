package backTracking;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/*
* 复原 IP 地址 https://leetcode.cn/problems/restore-ip-addresses/submissions/
 */
public class GetValidIpAddresses {

    public static void main(String[] args) {
        String s = "101023";
        System.out.println(restoreIpAddresses(s).toString());
    }

    static List<String> res = new ArrayList<>();
    public static List<String> restoreIpAddresses(String s) {
        StringBuilder builder = new StringBuilder(s);
        dfs(builder,0,0);
        return res;
    }

    public static void dfs(StringBuilder sb,int start,int pointCount){
        //当‘.’的数量达到3时，ip地址分割完成
        if(pointCount == 3){
            //判断最后一位数是否属于0-255
            if(validIp(sb,start,sb.length()-1))  res.add(sb.toString());
            return;
        }
        //计算循环次数，ip地址范围从0到255，因此从start起始最多取3位数
        int count = Math.min(start+3,sb.length());
        for(int i=start;i<count;i++){
            //剪枝，计算以i+1为分割点是否合理
            if(sb.length()-i-1 > 3*(3-pointCount) || (pointCount == 2 && sb.length()-i-1 < 1)) continue;
            if(validIp(sb,start,i)){
                pointCount++;
                sb.insert(i+1,'.');
                dfs(sb,i+2,pointCount);
                pointCount--;
                sb.deleteCharAt(i+1);
            }else {
                continue;
            }
        }
    }

    public static Boolean validIp(StringBuilder sb,int start,int end){
        int len = end - start + 1;
        // 大于 1 位的时候，不能以 0 开头
        if (len >3 ||len == 0 || (len > 1 && sb.charAt(start) == '0')) return false;
        // 转成 int 类型
        int res = 0;
        for (int i = start; i <= end; i++) {
            res = res * 10 + sb.charAt(i) - '0';
        }
        return res<256;
    }
}
