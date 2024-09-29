package Find;

/**
 * 找出第一个非重复的字符
 */
public class FindFirstUniqChar {

    public static void main(String[] args) {
        System.out.println(firstUniqChar("abaccdeff"));
    }

    /**
     * 两次遍历
     * 第一次遍历记录所有字符出现的次数
     * 第二次遍历返回第一个只出现一次的字符
     *
     */
    public static char firstUniqChar(String s) {
        int[] res = new int[26];
        char[] chars = s.toCharArray();
        for(char c:chars){
            res[c-'a'] ++;
        }
        for(char c:chars){
            if(res[c - 'a'] == 1){
                return c;
            }
        }
        return ' ';
    }
}
