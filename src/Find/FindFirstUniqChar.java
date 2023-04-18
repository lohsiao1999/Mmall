package Find;

public class FindFirstUniqChar {

    public static void main(String[] args) {
        System.out.println(firstUniqChar("abaccdeff"));
    }

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
