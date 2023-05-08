package Greedy;

/*
* 柠檬水找零 https://leetcode.cn/problems/lemonade-change/
 */
public class CanLemonadeChange {

    /*
    * 记录零钱数量
    * 1）若为5美元则直接收下
    * 2）若为10美元，则判断5美元数量。若5美元数量为0，返回false；否则5美元数量减一，10美元数量加一
    * 3）若为20美元，则优先使用10美元+5美元的方式找零，当不存在10美元时，才考虑使用3张5美元找零
     */
    public boolean lemonadeChange(int[] bills) {
        int five=0,ten=0;
        for(int bill:bills){
            if(bill == 5){
                five++;
            }else if(bill == 10){
                if (five==0) return false;
                five--;
                ten++;
            }else {
                if(five>0 && ten>0){
                    five--;
                    ten--;
                }else if(five>2){
                    five -=3;
                }else{
                    return false;
                }
            }
        }
        return true;
    }

}
