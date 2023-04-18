package ListNode;

/*
* 两数相加
* 力扣：https://leetcode.cn/problems/add-two-numbers/
 */
public class AddTwoNumsbers {

    public static void main(String[] args) {
        ListNode node1 = new ListNode(2);
        ListNode node2 = new ListNode(4);
        ListNode node3 = new ListNode(3);
        ListNode node4 = new ListNode(5);
        ListNode node5 = new ListNode(6);
        ListNode node6 = new ListNode(4);
        node1.next = node2;
        node2.next = node3;
        node4.next = node5;
        node5.next = node6;
        ListNode node = addTwoNumbers(node1, node4);
        System.out.println(node);
    }

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode res = null;
        ListNode cur = null;
        ListNode i = l1;
        ListNode j = l2;
        int tmp = 0;
        while(i != null || j != null){
            int count = (i == null ? 0 : i.val) + (j == null ? 0 : j.val);
            if(tmp != 0){
                count += tmp;
                tmp = 0;
            }
            if(count >= 10){
                tmp = count/10;
                count = count % 10;
            }
            if(cur == null){
                res = new ListNode(count);
                cur = res;
            }else {
                cur.next = new ListNode(count);
                cur = cur.next;
            }
            if(i != null) i = i.next;
            if(j != null)j = j.next;
        }
        if(tmp > 0){
            cur.next = new ListNode(tmp);
        }
        return res;
    }
}
