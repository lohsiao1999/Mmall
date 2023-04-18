package ListNode;

/*
* 相交链表
* https://leetcode.cn/problems/intersection-of-two-linked-lists/
 */
public class IntersectionNode {

    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if(headB == null || headA == null) return null;
        ListNode a = headA;
        ListNode b = headB;
        //headA和headB同步遍历，链表A遍历至尾节点后面，即指针A指向null时，将指针A指向未遍历完成的链表B头部
        //此时指针A和指针B同步遍历链表B，当指针B遍历至null时，此时指针A指向的位置便是两个链表的长度差
        //此时再将指针B指向链表A，两个指针同时遍历，若存在相交点则两指针必然相遇
        while(a != b){
            a = a==null? headB : a.next;
            b = b==null? headA : b.next;
        }
        return a;
    }
}
