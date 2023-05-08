package Greedy;

/*
* 加油站 https://leetcode.cn/problems/gas-station/
 */
public class CompleteCircuit {

    /*
    * start 汽车起点
    * curSum 记录从i开往i+1加油站后的剩余油量，若剩余油量小于0，说明油量不足以支持汽车从i开向i+1。因此curSum重置为0，并将i+1设置为新的起点
    * total 记录整路程的加油量总和与耗油量总和的差值，若total小于0，说明无论从哪个加油站为起点都不支持汽车环游一圈
     */
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int curSum=0,total=0,start=0;
        for(int i=0;i< gas.length;i++){
            total += gas[i]-cost[i];
            curSum += gas[i]-cost[i];
            if(curSum<0){
                start = i+1;
                curSum = 0;
            }
        }
        return total<0 ? -1:start;
    }
}
