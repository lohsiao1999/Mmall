import java.util.*;

public class Test {


        public static void main(String[] args) {
            int[] nums = {-2,1,-3,4,-1,2,1,-5,4};
            int res=0,pre=0,cur=0;
            for(int num:nums){
                cur = num;
                //若pre<=0，取状态转移方程2，否则取1
                cur += Math.max(pre, 0);
                res = Math.max(res, cur);
                pre = cur;
            }
            System.out.println(res);
//            int[] prices = {7,1,5,3,6,4};
//            int n = prices.length;
//            int[] dp = new int[n+1];
//            for(int i=1;i<=n;i++){
//                for(int j=i;j>0;j--){
//                    dp[i] = Math.max(dp[i],prices[i-1]-prices[j-1]+dp[j-1]);
//                }
//            }
//            System.out.println(Arrays.toString(dp));
//            Scanner sc = new Scanner(System.in);
//            String a = "78";
//            String b = "ux";
//            Map<Integer, List<String>> map = initMap();
//            List<String> result = new ArrayList<>();
//            result.addAll(map.get(Integer.valueOf(a.charAt(0) - '0')));
//            int size = result.size();
//            for(int k=0;k<size;k++){
//                String str = result.get(k);
//                for(int i = 1; i < a.length(); i++){
//                    List<String> temp = map.get(Integer.valueOf(a.charAt(i) - '0'));
//                    for(int j = 0; j < temp.size(); j++){
//                        String s = str + temp.get(j);
//                        result.add(s);
//                    }
//                }
//            }
//            for(String str:result){
//                if(str.length() == a.length() && !str.contains(b)){
//                    System.out.print(str+",");
//                }
//            }
        }

        public static Map<Integer,List<String>> initMap(){
            Map<Integer,List<String>> map = new HashMap<>();
            List<String> list0 = new ArrayList<>();
            list0.add("a");
            list0.add("b");
            list0.add("c");
            map.put(0,list0);
            List<String> list1 = new ArrayList<>();
            list1.add("d");
            list1.add("e");
            list1.add("f");
            map.put(1,list1);
            List<String> list2 = new ArrayList<>();
            list2.add("g");
            list2.add("h");
            list2.add("i");
            map.put(2,list2);
            List<String> list3 = new ArrayList<>();
            list3.add("j");
            list3.add("k");
            list3.add("l");
            map.put(3,list3);
            List<String> list4 = new ArrayList<>();
            list4.add("m");
            list4.add("n");
            list4.add("o");
            map.put(4,list4);
            List<String> list5 = new ArrayList<>();
            list5.add("p");
            list5.add("q");
            list5.add("r");
            map.put(5,list5);
            List<String> list6 = new ArrayList<>();
            list6.add("s");
            list6.add("t");
            map.put(6,list6);
            List<String> list7 = new ArrayList<>();
            list7.add("u");
            list7.add("v");
            map.put(7,list7);
            List<String> list8 = new ArrayList<>();
            list8.add("w");
            list8.add("x");
            map.put(8,list8);
            List<String> list9 = new ArrayList<>();
            list9.add("y");
            list9.add("z");
            map.put(9,list9);
            return map;
        }



}
