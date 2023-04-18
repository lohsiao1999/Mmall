package ListNode;

import java.util.HashMap;
import java.util.Map;

/*
* 删除链表的倒数第 N 个结点 https://leetcode.cn/problems/remove-nth-node-from-end-of-list/
 */

public class RemoveNthFromEnd {
    public static void main(String[] args) {
        ListNode n1 = new ListNode(1);
        ListNode n2 = new ListNode(2);
//        ListNode n3 = new ListNode(3);
//        ListNode n4 = new ListNode(4);
//        ListNode n5 = new ListNode(5);
        n1.next = n2;
//        n2.next = n3;
//        n3.next = n4;
//        n4.next = n5;

        ListNode node = removeNthFromEnd3(n1, 2);
        System.out.println(node);
    }

    /*
    * 使用哈希表，简单但效率低占用内存大
     */
    public static ListNode removeNthFromEnd(ListNode head, int n) {
        if(head == null || head.next == null) return null;
        Map<Integer,ListNode> map = new HashMap<>();
        ListNode temp = head;
        int i = 0;
        while (temp != null){
            map.put(i++,temp);
            temp = temp.next;
        }
        ListNode pre = map.get(i - n -1);
        if(pre == null) return map.get(i - n +1);
        pre.next = pre.next.next;
        return head;
    }

    /*
    * 采用双指针，快指针领先慢指针n个节点，当快指针到达终点时慢指针即指向倒数第n个节点
    * 因为获取待删除节点的前置节点更容易操作，因此快指针指向head.next
     */
    public static ListNode removeNthFromEnd2(ListNode head, int n) {
        if(head == null || head.next == null) return null;
        ListNode cur = new ListNode(0,head);
        ListNode slow = cur;
        ListNode fast = head;
        int i =0;
        while(fast != null){
            if(i >= n)
                slow = slow.next;
            i++;
            fast = fast.next;
        }
        slow.next = slow.next.next;
        return cur.next;
    }

    /*
    * 回溯递归：后序递归
     */
    public static ListNode removeNthFromEnd3(ListNode head, int n) {
        int res = trace(head,n);
        //当res == n时说明head节点即为待删除的节点
        if(res == n) return head.next;
        return head;
    }

    public static int trace(ListNode node, int n) {
        //递归终止条件
        if(node == null) return 0;
        int count = trace(node.next, n);
        //当count == n时说明当前节点为待删除节点的前置节点
        if(count == n){
            node.next = node.next.next;
        }
        return count+1;
    }
}
