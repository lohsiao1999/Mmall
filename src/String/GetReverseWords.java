package String;

public class GetReverseWords {

    public String reverseWords(String s) {
        s.trim();
        StringBuilder sb =new StringBuilder();
        int j = s.length()-1;
        int i = j;
        while(i>=0){
            while(i>=0 && s.charAt(i) != ' '){
                i--;
            }
            sb.append(s, i+1, j+1);
            while(i>=0 && s.charAt(i) == ' '){
                i--;
            }
            j=i;
        }
        return sb.toString().trim();
    }
}
