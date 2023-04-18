package ListNode;

import java.util.HashSet;
import java.util.Set;

/*
* 判断链表中是否存在环
* 链表问题解题技巧：
* 1）使用额外数据结构，数组或哈希表
* 2）快慢指针
 */

public class HasCycle {

    public static void main(String[] args) {
        ListNode n1 = new ListNode(3);
        ListNode n2 = new ListNode(0);
        ListNode n3 = new ListNode(2);
        ListNode n4 = new ListNode(4);
        n1.next = n2;
        n2.next = n3;
        n3.next = n4;
        n4.next = n2;
        System.out.println(hasCycle1(n1));
        System.out.println(hasCycle2(n1));
    }

    public static boolean hasCycle1(ListNode head) {
        Set<ListNode> set = new HashSet<>();
        ListNode temp = head;
        while(temp!= null){
            if(!set.add(temp)){
                return true;
            }
            temp = temp.next;
        }
        return false;
    }

    /*
    * 快慢指针：若链表中存在环，则一定有slow = fast
     */
    public static boolean hasCycle2(ListNode head) {
        if(head == null || head.next == null) return false;
        ListNode slow = head;
        ListNode fast = head;
        do {
            if(fast.next == null || fast.next.next == null) return false;
            slow = slow.next;
            fast = fast.next.next;
        }while (slow!=fast);
        return true;
    }
}
